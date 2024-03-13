package com.example.trinket.control;

import com.example.trinket.model.OrdiniModel;
import com.example.trinket.model.bean.CompostoBean;
import com.example.trinket.model.bean.OrdineBean;
import com.example.trinket.model.bean.PacchettoBean;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet(name = "OrdiniControl", value = "/OrdiniControl")
public class OrdiniControl extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final OrdiniModel ordiniModel = new OrdiniModel();

    private static final  String ERROR_MESSAGE = "errorMessage";
    private static final  String MESSAGGIO = "Si è verificato un errore: ";
    private static final  String ERRORE = "/error.jsp";
    private static final  String EMAIL = "Email";



    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        String action = request.getParameter("action");

        try {
            if (action != null) {
                if(action.equalsIgnoreCase("OttieniOrdini")){
                    ottieniOrdini(request, response);
                }else if(action.equalsIgnoreCase("filtriOrdini")){
                    filtriOrdini(request, response);
                }
            }
        }catch (Exception e) {
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response){
        doGet(request, response);
    }

    public void ottieniOrdini(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = (String) request.getSession().getAttribute(EMAIL);
        List<OrdineBean> ordini;
        ordini = ordiniModel.ricercaOrdiniUtente(email);
        for(OrdineBean bean : ordini){
            int id = bean.getIdOrdine();
            List<CompostoBean> composto;
            List<PacchettoBean> pacchettiOrdine = new ArrayList<>();
            List<Integer> quantitaPacchettiOrdine = new ArrayList<>();
            composto = ordiniModel.dettagliOrdine(id);
            for(CompostoBean bean2 : composto){
                String codice = bean2.getCodSeriale();
                PacchettoBean bean3;
                bean3 = ordiniModel.getPacchettoById(codice);
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
        Date dataInizio = java.sql.Date.valueOf(request.getParameter("dataInizio"));
        Date dataFine = java.sql.Date.valueOf(request.getParameter("dataFine"));
        System.out.println(dataInizio);
        System.out.println(dataFine);
        if(dataInizio != null && dataFine != null){
            System.out.println("CIAOOOO");
            ordini = ordiniModel.ordiniPerData(email, dataInizio, dataFine);
        }
        String prezzoMinimo = request.getParameter("prezzoMinimo");
        String prezzoMassimo = request.getParameter("prezzoMassimo");
        System.out.println(prezzoMinimo);
        System.out.println(prezzoMassimo);
        if(prezzoMinimo != null && prezzoMassimo != null){
            System.out.println("CIAOOOO 2");
            float minimo = Float.parseFloat(prezzoMinimo);
            float massimo = Float.parseFloat(prezzoMassimo);
            ordini = ordiniModel.ordiniPerPrezzo(email, minimo, massimo);
        }
        for(OrdineBean bean : ordini){
            int id = bean.getIdOrdine();
            List<CompostoBean> composto;
            List<PacchettoBean> pacchettiOrdine = new ArrayList<>();
            List<Integer> quantitaPacchettiOrdine = new ArrayList<>();
            composto = ordiniModel.dettagliOrdine(id);
            for(CompostoBean bean2 : composto){
                String codice = bean2.getCodSeriale();
                PacchettoBean bean3;
                bean3 = ordiniModel.getPacchettoById(codice);
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
