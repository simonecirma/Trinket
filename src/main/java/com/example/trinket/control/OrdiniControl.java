package com.example.trinket.control;

import com.example.trinket.model.OrdiniModel;
import com.example.trinket.model.PacchettoModel;
import com.example.trinket.model.UtenteModel;
import com.example.trinket.model.bean.*;
import com.google.gson.Gson;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet(name = "OrdiniControl", value = "/OrdiniControl")
public class OrdiniControl extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final OrdiniModel ordiniModel = new OrdiniModel();
    private final PacchettoModel pacchettoModel = new PacchettoModel();
    private final UtenteModel utenteModel = new UtenteModel();


    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String MESSAGGIO = "Si è verificato un errore: ";
    private static final String ERRORE = "/error.jsp";
    private static final String EMAIL = "Email";


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        String action = request.getParameter("action");

        try {
            if (action != null) {
                if (action.equalsIgnoreCase("OttieniOrdini")) {
                    ottieniOrdini(request, response);
                } else if (action.equalsIgnoreCase("filtriOrdini")) {
                    filtriOrdini(request, response);
                } else if (action.equalsIgnoreCase("AggiungiAlCarrello")) {
                    aggiungiAlCarrello(request, response);
                } else if (action.equalsIgnoreCase("RimuoviDalCarrello")) {
                    rimuoviDalCarrello(request, response);
                } else if (action.equalsIgnoreCase("AggiornaQuantita")) {
                    aggiornaQuantita(request, response);
                } else if (action.equalsIgnoreCase("OrdiniPerUtenteAdmin")) {
                    ordiniPerUtenteAdmin(request, response);
                }
            }
        } catch (Exception e) {
            request.setAttribute(ERROR_MESSAGE, MESSAGGIO + e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher(ERRORE);
            try {
                dispatcher.forward(request, response);
            } catch (ServletException | IOException ex) {
                log("Errore durante il reindirizzamento alla pagina di errore", ex);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        doGet(request, response);
    }

    public void ottieniOrdini(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = (String) request.getSession().getAttribute(EMAIL);
        List<OrdineBean> ordini;
        ordini = ordiniModel.ricercaOrdiniUtente(email);
        for (OrdineBean bean : ordini) {
            int id = bean.getIdOrdine();
            List<CompostoBean> composto;
            List<PacchettoBean> pacchettiOrdine = new ArrayList<>();
            List<Integer> quantitaPacchettiOrdine = new ArrayList<>();
            composto = ordiniModel.dettagliOrdine(id);
            for (CompostoBean bean2 : composto) {
                String codice = bean2.getCodSeriale();
                PacchettoBean bean3;
                bean3 = pacchettoModel.getPacchettoById(codice);
                pacchettiOrdine.add(bean3);
                int i = bean2.getQuantita();
                quantitaPacchettiOrdine.add(i);
            }
            bean.setPacchetti(pacchettiOrdine);
            bean.setQuantitaPacchetto(quantitaPacchettiOrdine);
        }
        request.setAttribute("Ordini", ordini);
        RequestDispatcher dispatcher = request.getRequestDispatcher("ordini.jsp");
        dispatcher.forward(request, response);
    }

    public void filtriOrdini(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = (String) request.getSession().getAttribute(EMAIL);
        List<OrdineBean> ordini;
        ordini = ordiniModel.ricercaOrdiniUtente(email);
        String data1 = request.getParameter("dataInizio");
        String data2 = request.getParameter("dataFine");
        if (!data1.isEmpty() && !data2.isEmpty()) {
            Date dataInizio = java.sql.Date.valueOf(data1);
            Date dataFine = java.sql.Date.valueOf(data2);
            ordini = ordiniModel.ordiniPerData(email, dataInizio, dataFine);
        }
        String prezzoMinimo = request.getParameter("prezzoMinimo");
        String prezzoMassimo = request.getParameter("prezzoMassimo");
        if (!prezzoMinimo.isEmpty() && !prezzoMassimo.isEmpty()) {
            float minimo = Float.parseFloat(prezzoMinimo);
            float massimo = Float.parseFloat(prezzoMassimo);
            ordini = ordiniModel.ordiniPerPrezzo(ordini, email, minimo, massimo);
        }
        for (OrdineBean bean : ordini) {
            int id = bean.getIdOrdine();
            List<CompostoBean> composto;
            List<PacchettoBean> pacchettiOrdine = new ArrayList<>();
            List<Integer> quantitaPacchettiOrdine = new ArrayList<>();
            composto = ordiniModel.dettagliOrdine(id);
            for (CompostoBean bean2 : composto) {
                String codice = bean2.getCodSeriale();
                PacchettoBean bean3;
                bean3 = pacchettoModel.getPacchettoById(codice);
                pacchettiOrdine.add(bean3);
                int i = bean2.getQuantita();
                quantitaPacchettiOrdine.add(i);
            }
            bean.setPacchetti(pacchettiOrdine);
            bean.setQuantitaPacchetto(quantitaPacchettiOrdine);
        }
        request.setAttribute("Ordini", ordini);
        RequestDispatcher dispatcher = request.getRequestDispatcher("ordini.jsp");
        dispatcher.forward(request, response);
    }

    public void aggiungiAlCarrello(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String quant = request.getParameter("quantita");
        int i = Integer.parseInt(quant);
        String id = request.getParameter("id");
        HttpSession session = request.getSession();

        CarrelloBean carrello = (CarrelloBean) session.getAttribute("carrello");
        if (carrello == null) {
            carrello = new CarrelloBean();
            session.setAttribute("carrello", carrello);
        }

        List<PacchettoBean> pacchetti = carrello.getPacchetti();
        List<Integer> quantita = carrello.getQuantita();

        boolean flag = false;
        int counter = 0;
        for (PacchettoBean bean : pacchetti) {
            if (bean.getCodSeriale().equals(id)) {
                flag = true;
                break;
            }
            counter++;
        }

        if (flag) {
            int prima = quantita.get(counter);
            prima += i;
            quantita.set(counter, prima);
        } else {
            pacchetti.add(pacchettoModel.getPacchettoById(id));
            quantita.add(i);
        }

        carrello.setPacchetti(pacchetti);
        carrello.setQuantita(quantita);

        session.setAttribute("carrello", carrello);

        List<PacchettoBean> pacchetti2;
        pacchetti2 = pacchettoModel.getPacchetti();
        for (PacchettoBean bean : pacchetti2) {
            List<ImmaginiBean> immagini;
            immagini = pacchettoModel.immaginiPerPacchetto(bean.getCodSeriale());
            bean.setImmagini(immagini);
        }

        request.setAttribute("pacchetti", pacchetti2);
        RequestDispatcher dispatcher = request.getRequestDispatcher("catalogo.jsp");
        dispatcher.forward(request, response);
    }

    public void rimuoviDalCarrello(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String codice = request.getParameter("id");

        CarrelloBean carrello = (CarrelloBean) session.getAttribute("carrello");
        List<PacchettoBean> pacchetti = carrello.getPacchetti();
        List<Integer> quantita = carrello.getQuantita();

        int i;

        for (i = 0; i < pacchetti.size(); i++) {
            PacchettoBean bean = pacchetti.get(i);
            if (bean.getCodSeriale().equals(codice)) {
                pacchetti.remove(i);
                quantita.remove(i);
            }
        }

        carrello.setQuantita(quantita);
        carrello.setPacchetti(pacchetti);

        session.setAttribute("carrello", carrello);

        String email = (String) request.getSession().getAttribute(EMAIL);
        if (email != null) {
            List<IndirizzoBean> indirizzi;
            List<MetodoPagamentoBean> metodi;
            indirizzi = utenteModel.ricercaIndirizzi(email);
            metodi = utenteModel.ricercaMetodoPagamento(email);
            request.setAttribute("indirizzi", indirizzi);
            request.setAttribute("metodi", metodi);
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("carrello.jsp");
        dispatcher.forward(request, response);
    }

    public void aggiornaQuantita(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int index = Integer.parseInt(request.getParameter("index"));
        int nuovaQuantita = Integer.parseInt(request.getParameter("quantita"));

        HttpSession session = request.getSession();
        CarrelloBean carrello = (CarrelloBean) session.getAttribute("carrello");
        List<Integer> quantita = carrello.getQuantita();
        quantita.set(index, nuovaQuantita);
        session.setAttribute("carrello", carrello);

        Gson gson = new Gson();
        String json = gson.toJson(nuovaQuantita);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
    }

    public void ordiniPerUtenteAdmin(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String email = request.getParameter(EMAIL);
        List<OrdineBean> ordini;
        ordini = ordiniModel.ricercaOrdiniUtente(email);
        for (OrdineBean bean : ordini) {
            int id = bean.getIdOrdine();
            List<CompostoBean> composto;
            List<PacchettoBean> pacchettiOrdine = new ArrayList<>();
            List<Integer> quantitaPacchettiOrdine = new ArrayList<>();
            composto = ordiniModel.dettagliOrdine(id);
            for (CompostoBean bean2 : composto) {
                String codice = bean2.getCodSeriale();
                PacchettoBean bean3;
                bean3 = pacchettoModel.getPacchettoById(codice);
                pacchettiOrdine.add(bean3);
                int i = bean2.getQuantita();
                quantitaPacchettiOrdine.add(i);
            }
            bean.setPacchetti(pacchettiOrdine);
            bean.setQuantitaPacchetto(quantitaPacchettiOrdine);
        }
        request.setAttribute("Ordini", ordini);
        RequestDispatcher dispatcher = request.getRequestDispatcher("ordini.jsp");
        dispatcher.forward(request, response);
    }
}