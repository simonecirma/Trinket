package com.example.trinket.Control;

import com.example.trinket.Model.Bean.UtenteBean;
import com.example.trinket.Model.UtenteModel;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.sql.Date;

@WebServlet(name = "AccessoControl", value = "/AccessoControl")
public class AccessoControl extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final UtenteModel utenteModel = new UtenteModel();

    String index = "/index.jsp";
    String errorMessage = "errorMessage";
    String messaggio = "Si è verificato un errore: ";
    String errore = "/error.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        String action = request.getParameter("action");
        try {
            if (action != null) {
                if (action.equalsIgnoreCase("Login")) {
                    login(request, response);
                }else if (action.equalsIgnoreCase("Logout")){
                    logout(request, response);
                }else if (action.equalsIgnoreCase("Registrazione")){
                    registrazione(request, response);
                }
            }
        }catch (ServletException | IOException e) {
            request.setAttribute(errorMessage, messaggio + e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher(errore);
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

    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UtenteBean admin;
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        HttpSession session = request.getSession(true);
        try {
            admin = utenteModel.login(email, password);
            if (admin != null) {
                session.setAttribute("Nome", admin.getNome());
                session.setAttribute("Cognome", admin.getCognome());
                session.setAttribute("Email", admin.getEmail());
                session.setAttribute("Password", admin.getPassword());
                session.setAttribute("DataDiNascita", admin.getDataDiNascita());
                session.setAttribute("Immagine", admin.getImmagine());
                session.setAttribute("FlagAmm", admin.isFlagAmm());

                RequestDispatcher dispatcher = request.getRequestDispatcher(index);
                dispatcher.forward(request, response);
            }else {
                request.setAttribute("result", "Credenziali sbagliate riprova!");
                RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
                dispatcher.forward(request, response);
            }
        } catch (ServletException | IOException e) {
            request.setAttribute(errorMessage, messaggio + e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher(errore);
            dispatcher.forward(request, response);
        }
    }

    public void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.invalidate();
        request.setAttribute("notifica", "Logout Effettuato! ");
        RequestDispatcher dispatcher = request.getRequestDispatcher(index);
        dispatcher.forward(request, response);
    }

    public void registrazione(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        Date dataDiNascita = Date.valueOf(request.getParameter("dataDiNascita"));
        Part file = request.getPart("immagine");
        String immagine = file.getSubmittedFileName();
        String directory = "Immagini/ImgUtente/";
        String path = request.getServletContext().getRealPath("/");
        String path2 = path + directory + immagine;
        try(FileOutputStream fos = new FileOutputStream(path2);
        InputStream is = file.getInputStream()){
            byte[] data = new byte[is.available()];
            if(is.read(data) > 0)
            {
                fos.write(data);
            }
        }catch(IOException e){
            request.setAttribute(errorMessage, messaggio + e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher(errore);
            dispatcher.forward(request, response);
        }
        utenteModel.registrati(nome, cognome, email, password, dataDiNascita, immagine);
        RequestDispatcher dispatcher = request.getRequestDispatcher(index);
        dispatcher.forward(request, response);
    }
}
