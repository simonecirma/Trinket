package com.example.trinket.control;

import com.example.trinket.model.OrdiniModel;
import com.example.trinket.model.PacchettoModel;
import com.example.trinket.model.bean.PacchettoBean;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "PacchettoControl", value = "/PacchettoControl")
public class PacchettoControl extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final OrdiniModel ordiniModel = new OrdiniModel();


    private static final  String ERROR_MESSAGE = "errorMessage";
    private static final  String MESSAGGIO = "Si è verificato un errore: ";
    private static final  String ERRORE = "/error.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        String action = request.getParameter("action");

        try {
            if (action != null) {
                if(action.equalsIgnoreCase("DettagliPacchetto")){
                    dettagliPacchetto(request, response);
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        doGet(request, response);
    }

    public void dettagliPacchetto(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String codice = request.getParameter("id");
        PacchettoBean pacchetto;
        pacchetto = ordiniModel.getPacchettoById(codice);
        request.setAttribute("pacchetto", pacchetto);
        RequestDispatcher dispatcher = request.getRequestDispatcher("dettagliPacchetto.jsp");
        dispatcher.forward(request, response);
    }
}
