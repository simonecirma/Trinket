<%@ page import="java.util.Date" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page errorPage="error.jsp" %>

<%!
    Boolean flagAmm;
    String email = null;
    String nome = "";
    String cognome = "";
    Date dataDiNascita;
    String password = "";
    String immagine = "";
    String result = "";
%>

<%
    synchronized (session) {
        session = request.getSession();
        flagAmm = (Boolean) session.getAttribute("FlagAmm");
        immagine = (String) session.getAttribute("Immagine");
        cognome = (String) session.getAttribute("Cognome");
        nome = (String) session.getAttribute("Nome");
        password = (String) session.getAttribute("Password");
        email = (String) session.getAttribute("Email");
        dataDiNascita = (Date) session.getAttribute("DataDiNascita");
    }

    result = (String) request.getAttribute("result");

%>

<!DOCTYPE html>
<html lang="it">
<head>
    <title>Easy Travel</title>
    <link href="CSS/navbar.css" rel="stylesheet" type="text/css">
</head>
<body>
    <nav class="navbar">

        <div class="logo">
            <a href="index.jsp">
                <img src="Immagini/logo.png" alt="Logo">
            </a>
        </div>


        <div class="cerca">
            <div class="ombre"></div>
            <button class="bottoneRicerca">
                    <img src="Immagini/Cerca.png" alt="Cerca">
            </button>
            <label class="contenitoreBarra">
                <input type="text" name="text" class="barraRicerca" placeholder="Cerca">
            </label>
        </div>

        <div class="carrello">
            <a href="carrello.jsp">
                <img src="Immagini/Carrello.png" alt="Carrello">
            </a>
        </div>
        <%
            if(email == null){
        %>
                <div class="profilo">
                    <a href="login.jsp">
                        <img src="Immagini/Profilo.png" alt="Profilo">
                    </a>
                </div>
        <%
            }else{
        %>
                <div class="profilo">
                    <img src="Immagini/Profilo.png" alt="Profilo" id="profilo">
                    <div class="card" id="card">
                        <!-- Contenuto del menu a tendina -->
                        <a href="profilo.jsp">Profilo</a>
                        <a href="#">I Miei Ordini</a>
                        <a href="AccessoControl?action=Logout">Logout</a>
                    </div>
                </div>
        <%
            }
        %>

    </nav>

    <script>
        // Ottieni il link del profilo e il menu a tendina
        var profilo = document.getElementById("profilo");
        var menuTendina = document.getElementById("card");

        // Aggiungi un gestore di eventi al clic sul link del profilo
        profilo.addEventListener("click", function(e) {
            // Impedisci il comportamento predefinito del link
            e.preventDefault();
            // Toggle della visibilità del menu a tendina
            if (menuTendina.style.display === "block") {
                menuTendina.style.display = "none";
            } else {
                menuTendina.style.display = "block";
            }
        });

        // Chiudi il menu a tendina quando si fa clic altrove nella pagina
        document.addEventListener("click", function(e) {
            if (e.target !== profilo && e.target.closest(".menuTendina") !== menuTendina) {
                menuTendina.style.display = "none";
            }
        });

    </script>
</body>
</html>
