package com.example.trinket.Control;

import com.example.trinket.Model.Bean.UtenteBean;
import com.example.trinket.Model.UtenteModel;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.sql.Date;
import java.util.UUID;

@WebServlet(name = "AccessoControl", value = "/AccessoControl")
public class AccessoControl extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final UtenteModel utenteModel = new UtenteModel();

    private static final  String INDEX = "/index.jsp";
    private static final  String ERROR_MESSAGE = "errorMessage";
    private static final  String MESSAGGIO = "Si è verificato un errore: ";
    private static final  String ERRORE = "/error.jsp";
    private static final  String EMAIL = "email";


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
                }else if(action.equalsIgnoreCase("verificaEmail")){
                    verificaEmail(request, response);
                }
            }
        }catch (ServletException | IOException e) {
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

    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UtenteBean admin;
        String email = request.getParameter(EMAIL);
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

                RequestDispatcher dispatcher = request.getRequestDispatcher(INDEX);
                dispatcher.forward(request, response);
            }else {
                request.setAttribute("result", "Credenziali sbagliate riprova!");
                RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
                dispatcher.forward(request, response);
            }
        } catch (ServletException | IOException e) {
            request.setAttribute(ERROR_MESSAGE, MESSAGGIO + e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher(ERRORE);
            dispatcher.forward(request, response);
        }
    }

    public void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.invalidate();
        request.setAttribute("notifica", "Logout Effettuato! ");
        RequestDispatcher dispatcher = request.getRequestDispatcher(INDEX);
        dispatcher.forward(request, response);
    }

    public void registrazione(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");
        String email = request.getParameter(EMAIL);
        String password = request.getParameter("password");
        Date dataDiNascita = Date.valueOf(request.getParameter("dataDiNascita"));
        Part file = request.getPart("immagine");
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
        utenteModel.registrati(nome, cognome, email, password, dataDiNascita, immagine);
        request.setAttribute("notifica", "Registrazione Effettuata! ");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
        dispatcher.forward(request, response);
    }

    public void verificaEmail(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String email = request.getParameter(EMAIL);
        boolean trovato = utenteModel.ricercaEmail(email);
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        if(trovato){
            PrintWriter out = response.getWriter();
            out.print("trovato");
            out.flush();
        }else{
            PrintWriter out = response.getWriter();
            out.print("non trovato");
            out.flush();
        }
    }
}
