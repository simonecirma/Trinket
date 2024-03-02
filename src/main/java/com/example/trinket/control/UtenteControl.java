package com.example.trinket.control;

import com.example.trinket.model.UtenteModel;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@WebServlet(name = "UtenteControl", value = "/UtenteControl")
public class UtenteControl extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final UtenteModel utenteModel = new UtenteModel();

    private static final  String ERROR_MESSAGE = "errorMessage";
    private static final  String MESSAGGIO = "Si è verificato un errore: ";
    private static final  String ERRORE = "/error.jsp";
    private static final  String NOTIFICA = "notifica";



    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if (action != null) {
                if (action.equalsIgnoreCase("ImmagineProfilo")) {
                    inserisciImmagine(request, response);
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    public void inserisciImmagine(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Part file = request.getPart("immagineProfilo");
        String email = (String) request.getSession().getAttribute("Email");
        String immagine = String.valueOf(UUID.randomUUID());
        String directory = "Immagini/ImgUtente/";
        String path = request.getServletContext().getRealPath("/") +directory;
        String path2 = path + immagine;
        try(FileOutputStream fos = new FileOutputStream(path2);
            InputStream is = file.getInputStream()){
            byte[] data = new byte[is.available()];
            if(is.read(data) > 0)
            {
                fos.write(data);
            }
        }catch(IOException e){
            request.setAttribute(ERROR_MESSAGE, MESSAGGIO + e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher(ERRORE);
            dispatcher.forward(request, response);
        }
        utenteModel.inserisciImmagine(immagine, email);
        request.getSession().setAttribute("Immagine", immagine);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/profilo.jsp");
        dispatcher.forward(request, response);
    }
}
