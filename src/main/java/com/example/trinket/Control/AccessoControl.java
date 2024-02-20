package com.example.trinket.Control;

import com.example.trinket.GestioneErrori;
import com.example.trinket.Model.Bean.UtenteBean;
import com.example.trinket.Model.UtenteModel;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "AccessoControl", value = "/AccessoControl")
public class AccessoControl extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final UtenteModel utenteModel = new UtenteModel();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        String action = request.getParameter("action");
        try {
            if (action != null) {
                if (action.equalsIgnoreCase("Login")) {
                    login(request, response);
                }
            }
        } catch (GestioneErrori e) {
            try {
                throw new GestioneErrori("Errore durante il login");
            } catch (GestioneErrori ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        doGet(request, response);
    }

    public void login(HttpServletRequest request, HttpServletResponse response) throws GestioneErrori {
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

                RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
                dispatcher.forward(request, response);
            }else {
                request.setAttribute("result", "Credenziali sbagliate riprova!");
                RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
                dispatcher.forward(request, response);
            }
        } catch (ServletException | IOException e) {
            throw new GestioneErrori("Errore Durante Il Login");
        }
    }
}
