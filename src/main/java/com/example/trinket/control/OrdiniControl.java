package com.example.trinket.control;

import com.example.trinket.model.OrdiniModel;
import com.example.trinket.model.PacchettoModel;
import com.example.trinket.model.UtenteModel;
import com.example.trinket.model.bean.*;
import com.google.gson.Gson;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet(name = "OrdiniControl", value = "/OrdiniControl")
public class OrdiniControl extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final OrdiniModel ordiniModel = new OrdiniModel();
    private final PacchettoModel pacchettoModel = new PacchettoModel();
    private final UtenteModel utenteModel = new UtenteModel();


    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String MESSAGGIO = "Si è verificato un errore: ";
    private static final String ERRORE = "/error.jsp";
    private static final String EMAIL = "Email";


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        String action = request.getParameter("action");

        try {
            if (action != null) {
                if (action.equalsIgnoreCase("OttieniOrdini")) {
                    ottieniOrdini(request, response);
                } else if (action.equalsIgnoreCase("filtriOrdini")) {
                    filtriOrdini(request, response);
                } else if (action.equalsIgnoreCase("AggiungiAlCarrello")) {
                    aggiungiAlCarrello(request, response);
                } else if (action.equalsIgnoreCase("RimuoviDalCarrello")) {
                    rimuoviDalCarrello(request, response);
                } else if (action.equalsIgnoreCase("AggiornaQuantita")) {
                    aggiornaQuantita(request, response);
                } else if (action.equalsIgnoreCase("OrdiniPerUtenteAdmin")) {
                    ordiniPerUtenteAdmin(request, response);
                } else if (action.equalsIgnoreCase("Checkout")) {
                    checkout(request, response);
                } else if (action.equalsIgnoreCase("VerificaCVV")) {
                    verificaCVV(request, response);
                } else if (action.equalsIgnoreCase("GeneraFattura")) {
                    generaFattura(request, response);
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

    public void ottieniOrdini(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = (String) request.getSession().getAttribute(EMAIL);
        List<OrdineBean> ordini;
        ordini = ordiniModel.ricercaOrdiniUtente(email);
        for (OrdineBean bean : ordini) {
            int id = bean.getIdOrdine();
            List<CompostoBean> composto;
            List<PacchettoBean> pacchettiOrdine = new ArrayList<>();
            List<Integer> quantitaPacchettiOrdine = new ArrayList<>();
            composto = ordiniModel.dettagliOrdine(id);
            for (CompostoBean bean2 : composto) {
                String codice = bean2.getCodSeriale();
                PacchettoBean bean3;
                bean3 = pacchettoModel.getPacchettoById(codice);
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
        String data1 = request.getParameter("dataInizio");
        String data2 = request.getParameter("dataFine");
        if (!data1.isEmpty() && !data2.isEmpty()) {
            Date dataInizio = java.sql.Date.valueOf(data1);
            Date dataFine = java.sql.Date.valueOf(data2);
            ordini = ordiniModel.ordiniPerData(email, dataInizio, dataFine);
        }
        String prezzoMinimo = request.getParameter("prezzoMinimo");
        String prezzoMassimo = request.getParameter("prezzoMassimo");
        if (!prezzoMinimo.isEmpty() && !prezzoMassimo.isEmpty()) {
            float minimo = Float.parseFloat(prezzoMinimo);
            float massimo = Float.parseFloat(prezzoMassimo);
            ordini = ordiniModel.ordiniPerPrezzo(ordini, email, minimo, massimo);
        }
        for (OrdineBean bean : ordini) {
            int id = bean.getIdOrdine();
            List<CompostoBean> composto;
            List<PacchettoBean> pacchettiOrdine = new ArrayList<>();
            List<Integer> quantitaPacchettiOrdine = new ArrayList<>();
            composto = ordiniModel.dettagliOrdine(id);
            for (CompostoBean bean2 : composto) {
                String codice = bean2.getCodSeriale();
                PacchettoBean bean3;
                bean3 = pacchettoModel.getPacchettoById(codice);
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

    public void aggiungiAlCarrello(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String quant = request.getParameter("quantita");
        int i = Integer.parseInt(quant);
        String id = request.getParameter("id");
        HttpSession session = request.getSession();

        CarrelloBean carrello = (CarrelloBean) session.getAttribute("carrello");
        if (carrello == null) {
            carrello = new CarrelloBean();
            session.setAttribute("carrello", carrello);
        }

        List<PacchettoBean> pacchetti = carrello.getPacchetti();
        List<Integer> quantita = carrello.getQuantita();

        boolean flag = false;
        int counter = 0;
        for (PacchettoBean bean : pacchetti) {
            if (bean.getCodSeriale().equals(id)) {
                flag = true;
                break;
            }
            counter++;
        }

        if (flag) {
            int prima = quantita.get(counter);
            prima += i;
            quantita.set(counter, prima);
        } else {
            pacchetti.add(pacchettoModel.getPacchettoById(id));
            quantita.add(i);
        }

        carrello.setPacchetti(pacchetti);
        carrello.setQuantita(quantita);

        session.setAttribute("carrello", carrello);

        List<PacchettoBean> pacchetti2;
        pacchetti2 = pacchettoModel.getPacchetti();
        for (PacchettoBean bean : pacchetti2) {
            List<ImmaginiBean> immagini;
            immagini = pacchettoModel.immaginiPerPacchetto(bean.getCodSeriale());
            bean.setImmagini(immagini);
        }

        request.setAttribute("pacchetti", pacchetti2);
        RequestDispatcher dispatcher = request.getRequestDispatcher("catalogo.jsp");
        dispatcher.forward(request, response);
    }

    public void rimuoviDalCarrello(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String codice = request.getParameter("id");

        CarrelloBean carrello = (CarrelloBean) session.getAttribute("carrello");
        List<PacchettoBean> pacchetti = carrello.getPacchetti();
        List<Integer> quantita = carrello.getQuantita();

        int i;

        for (i = 0; i < pacchetti.size(); i++) {
            PacchettoBean bean = pacchetti.get(i);
            if (bean.getCodSeriale().equals(codice)) {
                pacchetti.remove(i);
                quantita.remove(i);
            }
        }

        carrello.setQuantita(quantita);
        carrello.setPacchetti(pacchetti);

        session.setAttribute("carrello", carrello);

        String email = (String) request.getSession().getAttribute(EMAIL);
        if (email != null) {
            List<IndirizzoBean> indirizzi;
            List<MetodoPagamentoBean> metodi;
            indirizzi = utenteModel.ricercaIndirizzi(email);
            metodi = utenteModel.ricercaMetodoPagamento(email);
            request.setAttribute("indirizzi", indirizzi);
            request.setAttribute("metodi", metodi);
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("carrello.jsp");
        dispatcher.forward(request, response);
    }

    public void aggiornaQuantita(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int index = Integer.parseInt(request.getParameter("index"));
        int nuovaQuantita = Integer.parseInt(request.getParameter("quantita"));

        HttpSession session = request.getSession();
        CarrelloBean carrello = (CarrelloBean) session.getAttribute("carrello");
        List<Integer> quantita = carrello.getQuantita();
        quantita.set(index, nuovaQuantita);
        session.setAttribute("carrello", carrello);
        float prezzoTotale = 0;

        for (int i = 0; i < carrello.getPacchetti().size(); i++) {
            PacchettoBean pacchetto = carrello.getPacchetti().get(i);
            int numero = quantita.get(i);
            prezzoTotale += pacchetto.getPrezzo() * numero;
        }

        String totaleFormattato = String.format("%.2f", prezzoTotale);

        Gson gson = new Gson();
        String json = gson.toJson(totaleFormattato);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
    }

    public void ordiniPerUtenteAdmin(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String email = request.getParameter(EMAIL);
        List<OrdineBean> ordini;
        ordini = ordiniModel.ricercaOrdiniUtente(email);
        for (OrdineBean bean : ordini) {
            int id = bean.getIdOrdine();
            List<CompostoBean> composto;
            List<PacchettoBean> pacchettiOrdine = new ArrayList<>();
            List<Integer> quantitaPacchettiOrdine = new ArrayList<>();
            composto = ordiniModel.dettagliOrdine(id);
            for (CompostoBean bean2 : composto) {
                String codice = bean2.getCodSeriale();
                PacchettoBean bean3;
                bean3 = pacchettoModel.getPacchettoById(codice);
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

    public void checkout(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        CarrelloBean bean = (CarrelloBean) request.getSession().getAttribute("carrello");
        List<PacchettoBean> pacchetti_carrello = bean.getPacchetti();
        List<Integer> quantita = bean.getQuantita();
        String email = (String) request.getSession().getAttribute(EMAIL);
        LocalDate oggi = LocalDate.now();
        Date dataDiOggi = java.sql.Date.valueOf(oggi);
        float prezzo = (float) 0;

        for (int i = 0; i < pacchetti_carrello.size(); i++) {
            PacchettoBean pacchetto = pacchetti_carrello.get(i);
            String codice = pacchetto.getCodSeriale();
            int copie_acquistate = quantita.get(i);
            pacchettoModel.modificaNumPacchetti(copie_acquistate, codice);
            prezzo += pacchetto.getPrezzo() * copie_acquistate;
        }

        int lastId = ordiniModel.getLastID() + 1;
        String fattura = "Fattura" + lastId + ".pdf";

        ordiniModel.aggiungiOrdine(dataDiOggi, fattura, prezzo, email);

        for (int i = 0; i < pacchetti_carrello.size(); i++) {
            PacchettoBean pacchetto = pacchetti_carrello.get(i);
            String codice = pacchetto.getCodSeriale();
            float prezzo_unitario = pacchetto.getPrezzo();
            int copie_acquistate = quantita.get(i);
            ordiniModel.aggiungiComposto(copie_acquistate, codice, prezzo_unitario);
        }

        pacchetti_carrello.clear();
        quantita.clear();
        bean.setPacchetti(pacchetti_carrello);
        bean.setQuantita(quantita);
        request.getSession().setAttribute("carrello", bean);

        List<IndirizzoBean> indirizzi;
        List<MetodoPagamentoBean> metodi;

        indirizzi = utenteModel.ricercaIndirizzi(email);
        metodi = utenteModel.ricercaMetodoPagamento(email);

        request.setAttribute("indirizzi", indirizzi);
        request.setAttribute("metodi", metodi);

        RequestDispatcher dispatcher = request.getRequestDispatcher("carrello.jsp");
        dispatcher.forward(request, response);
    }

    public void verificaCVV(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        int cvv = Integer.parseInt(request.getParameter("cvv"));
        String numeroCarta = request.getParameter("numeroCarta");

        boolean risultato = ordiniModel.verificaCVV(cvv, numeroCarta);

        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");

        if (risultato) {
            PrintWriter out = response.getWriter();
            out.print("giusto");
            out.flush();
        } else {
            PrintWriter out = response.getWriter();
            out.print("sbagliato");
            out.flush();
        }
    }

    public void generaFattura(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        OrdineBean bean = ordiniModel.ricercaOrdiniById(id);
        List<CompostoBean> composto;
        List<PacchettoBean> pacchettiOrdine = new ArrayList<>();
        List<Integer> quantitaPacchettiOrdine = new ArrayList<>();
        composto = ordiniModel.dettagliOrdine(id);
        for (CompostoBean bean2 : composto) {
            String codice = bean2.getCodSeriale();
            PacchettoBean bean3;
            bean3 = pacchettoModel.getPacchettoById(codice);
            pacchettiOrdine.add(bean3);
            int i = bean2.getQuantita();
            quantitaPacchettiOrdine.add(i);
        }
        bean.setPacchetti(pacchettiOrdine);
        bean.setQuantitaPacchetto(quantitaPacchettiOrdine);

        String email = (String) request.getSession().getAttribute(EMAIL);
        String nome = (String) request.getSession().getAttribute("Nome");
        String cognome = (String) request.getSession().getAttribute("Cognome");

        int n = bean.getIdOrdine();
        String file = "Fattura" + n + ".pdf";

        try {
            String servletPath = request.getServletContext().getRealPath("");
            String filePath = servletPath + "Fattura" + File.separator + "Fattura" + n + ".pdf";

            // Carica il template PDF esistente
            File templateFile = new File(servletPath + "Fattura" + File.separator + "TemplateFattura.pdf");
            File templateFile2 = new File(servletPath + "Fattura" + File.separator + "TemplateFattura2.pdf");

            // Posizione delle celle nel template da riempire

            // Posizione Nome Pacchetto
            float xCoordinate1 = 31;
            float yCoordinate1 = 437F;

            //Posizione Quantià Pacchetto
            float xCoordinate2 = 420;
            float yCoordinate2 = 437F;

            // Posizione Prezzo
            float xCoordinate3 = 500;
            float yCoordinate3 = 437F;

            // Posizione Data Acquisto
            float xCoordinate4 = 31;
            float yCoordinate4 = 695;

            // Posizione Descrizione
            float xCoordinate5 = 225;
            float yCoordinate5 = 695;

            // Posizione Id Ordine
            float xCoordinate6 = 280;
            float yCoordinate6 = 695;

            // Posizione Prezzo Totale
            float xCoordinate7 = 500;
            float yCoordinate7 = 144;

            // Posizione data fine
            float xCoordinate8 = 31;
            float yCoordinate8 = 85;

            // Posizione Email
            float xCoordinate9 = 31;
            float yCoordinate9 = 70;

            // Posizione Ricavo per ogni vendita
            float xCoordinate12 = 370;
            float yCoordinate12 = 422.5F;

            //Posizione Soldi Spesi Corrente
            float xCoordinate11 = 475;
            float yCoordinate11 = 446.5F;
            float xCoordinate13 = 475;
            float yCoordinate13 = 192;

            //Posizione Resoconto Finale
            float xCoordinate14 = 420;
            float yCoordinate14 = 160;

            //Posizione Nome Ente Contratto
            float xCoordinate15 = 304;
            float yCoordinate15 = 76;

            //Posizione Costo Medio Unitario
            float xCoordinate16 = 304;
            float yCoordinate16 = 62;

            //Posizione Prezzo Vendita
            float xCoordinate17 = 304;
            float yCoordinate17 = 48;

            int numPacchetti = bean.getPacchetti().size();
            if (numPacchetti <= 10) {
                PDDocument document = PDDocument.load(templateFile);
                // Ottieni la prima pagina del template
                PDPage page = document.getPage(0);

                // Apertura del flusso di contenuto per scrivere nel documento PDF
                PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true, true);

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.newLineAtOffset(xCoordinate4, yCoordinate4);
                contentStream.showText(String.valueOf(bean.getDataAcquisto()));
                contentStream.endText();

                String descrizione1 = "Id Ordine:";

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.newLineAtOffset(xCoordinate5, yCoordinate5);
                contentStream.showText(descrizione1);
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.newLineAtOffset(xCoordinate6, yCoordinate6);
                contentStream.showText(String.valueOf(bean.getIdOrdine()));
                contentStream.endText();

                int i = 0;
                for (PacchettoBean bean2 : bean.getPacchetti()) {

                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA, 12);
                    contentStream.newLineAtOffset(xCoordinate1, yCoordinate1);
                    contentStream.showText(String.valueOf(bean2.getNome()));
                    contentStream.endText();
                    yCoordinate1 -= 29;

                    int quantita = bean.getQuantitaPacchetto().get(i);
                    i++;

                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA, 12);
                    contentStream.newLineAtOffset(xCoordinate2, yCoordinate2);
                    contentStream.showText(String.valueOf(quantita));
                    contentStream.endText();
                    yCoordinate2 -= 29;

                    float prezzo = bean2.getPrezzo();

                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA, 12);
                    contentStream.newLineAtOffset(xCoordinate3, yCoordinate3);
                    contentStream.showText(String.valueOf(prezzo));
                    contentStream.endText();
                    yCoordinate3 -= 29;
                }

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.newLineAtOffset(xCoordinate7, yCoordinate7);
                contentStream.showText(String.valueOf(bean.getPrezzoTotale()));
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                float textWidth1 = PDType1Font.HELVETICA.getStringWidth(String.valueOf(nome)) / 1000f * 12; // Calcola la larghezza approssimativa del testo
                contentStream.newLineAtOffset(xCoordinate8, yCoordinate8);
                contentStream.showText(String.valueOf(nome));
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                float xOffset = textWidth1 + 5; // Aggiungi un margine di 10 punti
                contentStream.newLineAtOffset(xCoordinate8 + xOffset, yCoordinate8);
                contentStream.showText(String.valueOf(cognome));
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.newLineAtOffset(xCoordinate9, yCoordinate9);
                contentStream.showText(String.valueOf(email));
                contentStream.endText();

                contentStream.close();

                document.save(filePath);
                document.close();
            }else{
                PDDocument document2 = PDDocument.load(templateFile2);
                // Ottieni la prima pagina del template
                PDPage page = document2.getPage(0);

                // Apertura del flusso di contenuto per scrivere nel documento PDF
                PDPageContentStream contentStream = new PDPageContentStream(document2, page, PDPageContentStream.AppendMode.APPEND, true, true);

                int maxElem = 10;
                int i = 0;

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.newLineAtOffset(xCoordinate4, yCoordinate4);
                contentStream.showText(String.valueOf(bean.getDataAcquisto()));
                contentStream.endText();

                String descrizione1 = "Id Ordine:";

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.newLineAtOffset(xCoordinate5, yCoordinate5);
                contentStream.showText(descrizione1);
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.newLineAtOffset(xCoordinate6, yCoordinate6);
                contentStream.showText(String.valueOf(bean.getIdOrdine()));
                contentStream.endText();

                int j = 0;
                while(i != maxElem) {

                    PacchettoBean bean2 = bean.getPacchetti().get(j);

                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA, 12);
                    contentStream.newLineAtOffset(xCoordinate1, yCoordinate1);
                    contentStream.showText(String.valueOf(bean2.getNome()));
                    contentStream.endText();
                    yCoordinate1 -= 29;

                    int quantita = bean.getQuantitaPacchetto().get(j);

                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA, 12);
                    contentStream.newLineAtOffset(xCoordinate2, yCoordinate2);
                    contentStream.showText(String.valueOf(quantita));
                    contentStream.endText();
                    yCoordinate2 -= 29;

                    float prezzo = bean2.getPrezzo();

                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA, 12);
                    contentStream.newLineAtOffset(xCoordinate3, yCoordinate3);
                    contentStream.showText(String.valueOf(prezzo));
                    contentStream.endText();
                    yCoordinate3 -= 29;

                    j++;
                    i++;
                    numPacchetti--;
                }

                i = 0;
                yCoordinate1 += 29 * 10;
                yCoordinate2 += 29 * 10;
                yCoordinate3 += 29 * 10;

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                float textWidth1 = PDType1Font.HELVETICA.getStringWidth(String.valueOf(nome)) / 1000f * 12; // Calcola la larghezza approssimativa del testo
                contentStream.newLineAtOffset(xCoordinate8, yCoordinate8);
                contentStream.showText(String.valueOf(nome));
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                float xOffset = textWidth1 + 5; // Aggiungi un margine di 10 punti
                contentStream.newLineAtOffset(xCoordinate8 + xOffset, yCoordinate8);
                contentStream.showText(String.valueOf(cognome));
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.newLineAtOffset(xCoordinate9, yCoordinate9);
                contentStream.showText(String.valueOf(email));
                contentStream.endText();

                contentStream.close();


                while (numPacchetti > 10) {
                    PDDocument document3 = PDDocument.load(templateFile2);
                    PDPage newPage = document3.getPage(0);
                    document2.addPage(newPage);

                    PDPageContentStream contentStream2 = new PDPageContentStream(document2, newPage, PDPageContentStream.AppendMode.APPEND, true, true);

                    contentStream2.beginText();
                    contentStream2.setFont(PDType1Font.HELVETICA, 12);
                    contentStream2.newLineAtOffset(xCoordinate4, yCoordinate4);
                    contentStream2.showText(String.valueOf(bean.getDataAcquisto()));
                    contentStream2.endText();

                    contentStream2.beginText();
                    contentStream2.setFont(PDType1Font.HELVETICA, 12);
                    contentStream2.newLineAtOffset(xCoordinate5, yCoordinate5);
                    contentStream2.showText(descrizione1);
                    contentStream2.endText();

                    contentStream2.beginText();
                    contentStream2.setFont(PDType1Font.HELVETICA, 12);
                    contentStream2.newLineAtOffset(xCoordinate6, yCoordinate6);
                    contentStream2.showText(String.valueOf(bean.getIdOrdine()));
                    contentStream2.endText();

                    while(i != maxElem) {

                        PacchettoBean bean2 = bean.getPacchetti().get(j);

                        contentStream2.beginText();
                        contentStream2.setFont(PDType1Font.HELVETICA, 12);
                        contentStream2.newLineAtOffset(xCoordinate1, yCoordinate1);
                        contentStream2.showText(String.valueOf(bean2.getNome()));
                        contentStream2.endText();
                        yCoordinate1 -= 29;

                        int quantita = bean.getQuantitaPacchetto().get(j);

                        contentStream2.beginText();
                        contentStream2.setFont(PDType1Font.HELVETICA, 12);
                        contentStream2.newLineAtOffset(xCoordinate2, yCoordinate2);
                        contentStream2.showText(String.valueOf(quantita));
                        contentStream2.endText();
                        yCoordinate2 -= 29;

                        float prezzo = bean2.getPrezzo();

                        contentStream2.beginText();
                        contentStream2.setFont(PDType1Font.HELVETICA, 12);
                        contentStream2.newLineAtOffset(xCoordinate3, yCoordinate3);
                        contentStream2.showText(String.valueOf(prezzo));
                        contentStream2.endText();
                        yCoordinate3 -= 29;

                        j++;
                        i++;
                        numPacchetti--;
                    }

                    yCoordinate1 += 29 * 10;
                    yCoordinate2 += 29 * 10;
                    yCoordinate3 += 29 * 10;

                    contentStream2.beginText();
                    contentStream2.setFont(PDType1Font.HELVETICA, 12);
                    contentStream2.newLineAtOffset(xCoordinate8, yCoordinate8);
                    contentStream2.showText(String.valueOf(nome));
                    contentStream2.endText();

                    contentStream2.beginText();
                    contentStream2.setFont(PDType1Font.HELVETICA, 12);
                    contentStream2.newLineAtOffset(xCoordinate8 + xOffset, yCoordinate8);
                    contentStream2.showText(String.valueOf(cognome));
                    contentStream2.endText();

                    contentStream2.beginText();
                    contentStream2.setFont(PDType1Font.HELVETICA, 12);
                    contentStream2.newLineAtOffset(xCoordinate9, yCoordinate9);
                    contentStream2.showText(String.valueOf(email));
                    contentStream2.endText();

                    contentStream2.close();
                }

                PDDocument document = PDDocument.load(templateFile);
                PDPage newPage = document.getPage(0);
                document2.addPage(newPage);
                PDPageContentStream contentStream2 = new PDPageContentStream(document2, newPage, PDPageContentStream.AppendMode.APPEND, true, true);

                contentStream2.beginText();
                contentStream2.setFont(PDType1Font.HELVETICA, 12);
                contentStream2.newLineAtOffset(xCoordinate4, yCoordinate4);
                contentStream2.showText(String.valueOf(bean.getDataAcquisto()));
                contentStream2.endText();

                contentStream2.beginText();
                contentStream2.setFont(PDType1Font.HELVETICA, 12);
                contentStream2.newLineAtOffset(xCoordinate5, yCoordinate5);
                contentStream2.showText(descrizione1);
                contentStream2.endText();

                contentStream2.beginText();
                contentStream2.setFont(PDType1Font.HELVETICA, 12);
                contentStream2.newLineAtOffset(xCoordinate6, yCoordinate6);
                contentStream2.showText(String.valueOf(bean.getIdOrdine()));
                contentStream2.endText();

                while(i != maxElem && numPacchetti != 0) {

                    PacchettoBean bean2 = bean.getPacchetti().get(j);

                    contentStream2.beginText();
                    contentStream2.setFont(PDType1Font.HELVETICA, 12);
                    contentStream2.newLineAtOffset(xCoordinate1, yCoordinate1);
                    contentStream2.showText(String.valueOf(bean2.getNome()));
                    contentStream2.endText();
                    yCoordinate1 -= 29;

                    int quantita = bean.getQuantitaPacchetto().get(j);

                    contentStream2.beginText();
                    contentStream2.setFont(PDType1Font.HELVETICA, 12);
                    contentStream2.newLineAtOffset(xCoordinate2, yCoordinate2);
                    contentStream2.showText(String.valueOf(quantita));
                    contentStream2.endText();
                    yCoordinate2 -= 29;

                    float prezzo = bean2.getPrezzo();

                    contentStream2.beginText();
                    contentStream2.setFont(PDType1Font.HELVETICA, 12);
                    contentStream2.newLineAtOffset(xCoordinate3, yCoordinate3);
                    contentStream2.showText(String.valueOf(prezzo));
                    contentStream2.endText();
                    yCoordinate3 -= 29;

                    j++;
                    i++;
                    numPacchetti--;
                }

                contentStream2.beginText();
                contentStream2.setFont(PDType1Font.HELVETICA, 12);
                contentStream2.newLineAtOffset(xCoordinate7, yCoordinate7);
                contentStream2.showText(String.valueOf(bean.getPrezzoTotale()));
                contentStream2.endText();

                contentStream2.beginText();
                contentStream2.setFont(PDType1Font.HELVETICA, 12);
                contentStream2.newLineAtOffset(xCoordinate8, yCoordinate8);
                contentStream2.showText(String.valueOf(nome));
                contentStream2.endText();

                contentStream2.beginText();
                contentStream2.setFont(PDType1Font.HELVETICA, 12);
                contentStream2.newLineAtOffset(xCoordinate8 + xOffset, yCoordinate8);
                contentStream2.showText(String.valueOf(cognome));
                contentStream2.endText();

                contentStream2.beginText();
                contentStream2.setFont(PDType1Font.HELVETICA, 12);
                contentStream2.newLineAtOffset(xCoordinate9, yCoordinate9);
                contentStream2.showText(String.valueOf(email));
                contentStream2.endText();

                contentStream2.close();

                document2.save(filePath);
                document2.close();
            }
            //Apertura PDF
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                if (desktop.isSupported(Desktop.Action.OPEN)) {
                    desktop.open(new File(filePath));
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
}