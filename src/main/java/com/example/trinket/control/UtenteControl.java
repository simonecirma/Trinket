package com.example.trinket.control;

import com.example.trinket.model.UtenteModel;
import com.example.trinket.model.bean.MetodoPagamentoBean;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.List;
import java.util.UUID;

import com.google.gson.Gson;

@WebServlet(name = "UtenteControl", value = "/UtenteControl")
public class UtenteControl extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final UtenteModel utenteModel = new UtenteModel();

    private static final  String ERROR_MESSAGE = "errorMessage";
    private static final  String MESSAGGIO = "Si è verificato un errore: ";
    private static final  String ERRORE = "/error.jsp";
    private static final  String EMAIL = "Email";


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        String action = request.getParameter("action");

        try {
            if (action != null) {
                if (action.equalsIgnoreCase("ImmagineProfilo")) {
                    inserisciImmagine(request, response);
                }else if (action.equalsIgnoreCase("ModificaInformazioni")) {
                    modificaInformazioni(request, response);
                }else if(action.equalsIgnoreCase("OttieniMetodiPagamento")){
                    metodiPagamento(request, response);
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

    public void inserisciImmagine(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Part file = request.getPart("immagineProfilo");
        String email = (String) request.getSession().getAttribute(EMAIL);
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

    public void modificaInformazioni(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        Date dataDiNascita = Date.valueOf(request.getParameter("dataDiNascita"));
        String email2 = (String) request.getSession().getAttribute(EMAIL);
        utenteModel.modificaInformazioni(nome, cognome, email, password, dataDiNascita, email2);
        request.getSession().setAttribute("Nome", nome);
        request.getSession().setAttribute("Cognome", cognome);
        request.getSession().setAttribute(EMAIL, email);
        request.getSession().setAttribute("Password", password);
        request.getSession().setAttribute("dataDiNascita", dataDiNascita);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/profilo.jsp");
        dispatcher.forward(request, response);
    }

    public void metodiPagamento( HttpServletRequest request, HttpServletResponse response) throws IOException {
        String email = (String) request.getSession().getAttribute(EMAIL);
        List<MetodoPagamentoBean> carte;
        carte = utenteModel.ricercaMetodoPagamento(email);

        Gson gson = new Gson();
        String json = gson.toJson(carte);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
    }
}
