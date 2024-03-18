package com.example.trinket.control;

import com.example.trinket.model.PacchettoModel;
import com.example.trinket.model.bean.ImmaginiBean;
import com.example.trinket.model.bean.PacchettoBean;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "PacchettoControl", value = "/PacchettoControl")
public class PacchettoControl extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final PacchettoModel pacchettoModel = new PacchettoModel();


    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String MESSAGGIO = "Si è verificato un errore: ";
    private static final String ERRORE = "/error.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        String action = request.getParameter("action");

        try {
            if (action != null) {
                if (action.equalsIgnoreCase("DettagliPacchetto")) {
                    dettagliPacchetto(request, response);
                } else if (action.equalsIgnoreCase("OttieniPacchetti")) {
                    ottieniPacchetti(request, response);
                }else if (action.equalsIgnoreCase("FiltriPacchetti")) {
                    filtriPacchetti(request, response);
                }else if (action.equalsIgnoreCase("FiltriIndex")) {
                    filtriIndex(request, response);
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

    public void dettagliPacchetto(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String codice = request.getParameter("id");
        PacchettoBean pacchetto;
        pacchetto = pacchettoModel.getPacchettoById(codice);
        request.setAttribute("pacchetto", pacchetto);
        RequestDispatcher dispatcher = request.getRequestDispatcher("dettagliPacchetto.jsp");
        dispatcher.forward(request, response);
    }

    public void ottieniPacchetti(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<PacchettoBean> pacchetti;
        pacchetti = pacchettoModel.getPacchetti();
        for (PacchettoBean bean : pacchetti) {
            List<ImmaginiBean> immagini;
            immagini = pacchettoModel.immaginiPerPacchetto(bean.getCodSeriale());
            bean.setImmagini(immagini);
        }
        request.setAttribute("pacchetti", pacchetti);
        RequestDispatcher dispatcher = request.getRequestDispatcher("catalogo.jsp");
        dispatcher.forward(request, response);
    }

    public void filtriPacchetti(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] options = request.getParameterValues("option");
        String prezzoMinimo = request.getParameter("prezzoMinimo");
        String prezzoMassimo = request.getParameter("prezzoMassimo");
        String[] tipi = request.getParameterValues("option_tipo");

        List<PacchettoBean> pacchetti;
        pacchetti = pacchettoModel.getPacchetti();

        if(options != null && options.length > 0) {
            int[] giorni = new int[options.length];

            for (int i = 0; i < options.length; i++) {
                giorni[i] = Integer.parseInt(options[i]); // Converti la stringa in un intero e assegna all'array di interi
            }
            pacchetti = pacchettoModel.filtroDurata(giorni);
        }
        if(!prezzoMinimo.isEmpty() && !prezzoMassimo.isEmpty()){
            float minimo = Float.parseFloat(prezzoMinimo);
            float massimo = Float.parseFloat(prezzoMassimo);
            pacchetti = pacchettoModel.filtroPrezzo(pacchetti, minimo, massimo);
        }
        if(tipi != null && tipi.length > 0) {
            pacchetti = pacchettoModel.filtroTipo(pacchetti, tipi);
        }

        for (PacchettoBean bean : pacchetti) {
            List<ImmaginiBean> immagini;
            immagini = pacchettoModel.immaginiPerPacchetto(bean.getCodSeriale());
            bean.setImmagini(immagini);
        }
        request.setAttribute("pacchetti", pacchetti);
        RequestDispatcher dispatcher = request.getRequestDispatcher("catalogo.jsp");
        dispatcher.forward(request, response);
    }

    public void filtriIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String tipo = request.getParameter("tipo");
        List<PacchettoBean> pacchetti = new ArrayList<>();
        pacchetti = pacchettoModel.filtroTipoIndex(tipo);

        for (PacchettoBean bean : pacchetti) {
            List<ImmaginiBean> immagini;
            immagini = pacchettoModel.immaginiPerPacchetto(bean.getCodSeriale());
            bean.setImmagini(immagini);
        }

        request.setAttribute("pacchetti", pacchetti);
        RequestDispatcher dispatcher = request.getRequestDispatcher("catalogo.jsp");
        dispatcher.forward(request, response);
    }
}
