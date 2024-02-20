package com.example.trinket.Control;

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
import java.sql.SQLException;

@WebServlet(name = "AccessoControl", value = "/AccessoControl")
public class AccessoControl extends HttpServlet {

    private static final long serialVersionUID = 1L;

    UtenteModel utenteModel = new UtenteModel();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            if (action != null) {
                if (action.equalsIgnoreCase("Login")) {
                    UtenteBean bean;
                    String email = request.getParameter("email");
                    String password = request.getParameter("password");
                    HttpSession session = request.getSession(true);
                    try {
                        bean = login(email, password, session);
                        if (bean != null) {
                            RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
                            dispatcher.forward(request, response);
                        } else {
                            request.setAttribute("result", "Credenziali sbagliate riprova!");
                            RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
                            dispatcher.forward(request, response);
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }catch (ServletException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    public UtenteBean login(final String email, final String pass, final HttpSession session) throws SQLException {
        UtenteBean admin = new UtenteBean();
        admin = utenteModel.login(email, pass);
        if (admin != null) {
            session.setAttribute("ID", admin.getId());
            session.setAttribute("Nome", admin.getNome());
            session.setAttribute("Cognome", admin.getCognome());
            session.setAttribute("Email", admin.getEmail());
            session.setAttribute("Password", admin.getPassword());
            session.setAttribute("Indirizzo", admin.getIndirizzo());
            session.setAttribute("NumeroCivico", admin.getNumeroCivico());
            session.setAttribute("CAP", admin.getCap());
            session.setAttribute("Città", admin.getCitta());
            session.setAttribute("Provincia", admin.getProvincia());
            session.setAttribute("DataDiNascita", admin.getDataDiNascita());
            session.setAttribute("NumeroTelefono", admin.getNumeroTelefono());
            session.setAttribute("Immagine", admin.getImmagine());
            session.setAttribute("FlagAmm", admin.isFlagAmm());
        }
        return admin;
    }
}
