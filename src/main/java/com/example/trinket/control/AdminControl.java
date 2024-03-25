package com.example.trinket.control;

import com.example.trinket.model.AccessoModel;
import com.example.trinket.model.PacchettoModel;
import com.example.trinket.model.UtenteModel;
import com.example.trinket.model.bean.ImmaginiBean;
import com.example.trinket.model.bean.PacchettoBean;
import com.example.trinket.model.bean.UtenteBean;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@WebServlet(name = "AdminControl", value = "/AdminControl")
public class AdminControl extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final PacchettoModel pacchettoModel = new PacchettoModel();
    private final UtenteModel utenteModel = new UtenteModel();


    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String MESSAGGIO = "Si è verificato un errore: ";
    private static final String ERRORE = "/error.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        String action = request.getParameter("action");

        try {
            if (action != null) {
                if (action.equalsIgnoreCase("AggiungiPacchetto")) {
                    aggiungiPacchetto(request, response);
                } else if (action.equalsIgnoreCase("ModificaPacchetto")) {
                    modificaPacchetto(request, response);
                } else if (action.equalsIgnoreCase("RimuoviPacchetto")) {
                    rimuoviPacchetto(request, response);
                } else if (action.equalsIgnoreCase("VisualizzaUtenti")) {
                    visualizzaUtenti(request, response);
                }
            }
        } catch (ServletException | IOException e) {
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

    public void aggiungiPacchetto(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String codSeriale = request.getParameter("codSeriale");
        String nome = request.getParameter("Nome");
        String pr = request.getParameter("Prezzo");
        Float prezzo = Float.parseFloat(pr);
        String descrizione = request.getParameter("Descrizione");
        String descrizioneRidotta = request.getParameter("DescrizioneRidotta");
        String tipo = request.getParameter("Tipo");
        String dur = request.getParameter("Durata");
        int numGiorni = Integer.parseInt(dur);
        String num = request.getParameter("NumPacchetti");
        int numPacchetti = Integer.parseInt(num);

        pacchettoModel.aggiungiPacchetto(codSeriale, nome, prezzo, descrizione, descrizioneRidotta, tipo, numGiorni, numPacchetti);

        Collection<Part> immaginiParts = request.getParts();
        String directory = "Immagini/Pacchetti/";
        String path = request.getServletContext().getRealPath("/") + directory;

        for (Part part : immaginiParts) {
            if (part.getName().equals("Immagini")) {
                // Genera un nome univoco per l'immagine
                String nome_immagine = String.valueOf(UUID.randomUUID());
                String pathImmagine = path + nome_immagine;

                try (FileOutputStream fos = new FileOutputStream(pathImmagine);
                     InputStream is = part.getInputStream()) {
                    // Leggi i dati dell'immagine e scrivi sul disco
                    byte[] data = new byte[is.available()];
                    if (is.read(data) > 0) {
                        fos.write(data);
                    }
                } catch (IOException e) {
                    request.setAttribute(ERROR_MESSAGE, MESSAGGIO + e.getMessage());
                    RequestDispatcher dispatcher = request.getRequestDispatcher(ERRORE);
                    dispatcher.forward(request, response);
                }
                pacchettoModel.aggiungiImmagini(codSeriale, nome_immagine);
            }
        }

        Part copertina = request.getPart("Copertina");
        String nome_copertina = String.valueOf(UUID.randomUUID());
        String path2 = path + nome_copertina;

        try (FileOutputStream fos = new FileOutputStream(path2);
             InputStream is = copertina.getInputStream()) {
            byte[] data = new byte[is.available()];
            if (is.read(data) > 0) {
                fos.write(data);
            }
        } catch (IOException e) {
            request.setAttribute(ERROR_MESSAGE, MESSAGGIO + e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher(ERRORE);
            dispatcher.forward(request, response);
        }

        pacchettoModel.aggiungiCopertina(codSeriale, nome_copertina);
        List<PacchettoBean> pacchetti = pacchettoModel.getPacchettiPerAmministratore();
        for (PacchettoBean bean : pacchetti) {
            List<ImmaginiBean> immagini;
            immagini = pacchettoModel.immaginiPerPacchetto(bean.getCodSeriale());
            bean.setImmagini(immagini);
        }

        List<String> tipi = pacchettoModel.getTipo();
        List<Integer> durata = pacchettoModel.getDurata();
        request.setAttribute("tipi", tipi);
        request.setAttribute("durata", durata);

        request.setAttribute("pacchetti", pacchetti);
        RequestDispatcher dispatcher = request.getRequestDispatcher("catalogo.jsp");
        dispatcher.forward(request, response);
    }

    public void modificaPacchetto(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String codice = request.getParameter("id");
        String pre = request.getParameter("Prezzo");
        Float prezzo = Float.parseFloat(pre);
        String aggiungi = request.getParameter("Rifornimento");
        int rifornimento = 0;

        if (!aggiungi.isEmpty()) {
            rifornimento = Integer.parseInt(aggiungi);
        }

        List<PacchettoBean> pacchetti = pacchettoModel.getPacchettiPerAmministratore();
        for (PacchettoBean bean : pacchetti) {
            List<ImmaginiBean> immagini;
            immagini = pacchettoModel.immaginiPerPacchetto(bean.getCodSeriale());
            bean.setImmagini(immagini);
        }

        List<String> tipi = pacchettoModel.getTipo();
        List<Integer> durata = pacchettoModel.getDurata();
        request.setAttribute("tipi", tipi);
        request.setAttribute("durata", durata);
        request.setAttribute("pacchetti", pacchetti);

        pacchettoModel.modificaPacchetto(prezzo, rifornimento, codice);
        RequestDispatcher dispatcher = request.getRequestDispatcher("catalogo.jsp");
        dispatcher.forward(request, response);
    }

    public void rimuoviPacchetto(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String codice = request.getParameter("id");

        pacchettoModel.rimuoviPacchetto(codice);

        List<PacchettoBean> pacchetti = pacchettoModel.getPacchettiPerAmministratore();
        for (PacchettoBean bean : pacchetti) {
            List<ImmaginiBean> immagini;
            immagini = pacchettoModel.immaginiPerPacchetto(bean.getCodSeriale());
            bean.setImmagini(immagini);
        }

        List<String> tipi = pacchettoModel.getTipo();
        List<Integer> durata = pacchettoModel.getDurata();
        request.setAttribute("tipi", tipi);
        request.setAttribute("durata", durata);
        request.setAttribute("pacchetti", pacchetti);

        RequestDispatcher dispatcher = request.getRequestDispatcher("catalogo.jsp");
        dispatcher.forward(request, response);
    }

    public void visualizzaUtenti(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<UtenteBean> utenti = utenteModel.getUtenti();

        request.setAttribute("utenti", utenti);
        RequestDispatcher dispatcher = request.getRequestDispatcher("elencoUtenti.jsp");
        dispatcher.forward(request, response);
    }
}