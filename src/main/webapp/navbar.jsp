<%@ page import="java.util.Date" %>
<%@ page import="com.example.trinket.model.bean.CarrelloBean" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page errorPage="error.jsp" %>

<%!
    Boolean flagAmm = false;
    String email = null;
    String nome = "";
    String cognome = "";
    Date dataDiNascita;
    String password = "";
    String immagine = "";
    String result = "";
    CarrelloBean carrello = null;
%>

<%
    synchronized (session) {
        flagAmm = (Boolean) session.getAttribute("FlagAmm");
        immagine = (String) session.getAttribute("Immagine");
        cognome = (String) session.getAttribute("Cognome");
        nome = (String) session.getAttribute("Nome");
        password = (String) session.getAttribute("Password");
        email = (String) session.getAttribute("Email");
        dataDiNascita = (Date) session.getAttribute("DataDiNascita");
        carrello = (CarrelloBean) session.getAttribute("carrello");
    }

    result = (String) request.getAttribute("result");

%>

<!DOCTYPE html>
<html lang="it">
<head>
    <title>Easy Travel</title>
    <link href="CSS/navbar.css" rel="stylesheet" type="text/css">
    <script src="https://code.jquery.com/jquery-3.6.4.min.js" integrity="sha384-UG8ao2jwOWB7/oDdObZc6ItJmwUkR/PfMyt9Qs5AwX7PsnYn1CRKCTWyncPTWvaS" crossorigin="anonymous"></script>
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
            <form class="ricerca" action="PacchettoControl?action=Ricerca" method="post">
                <button type="submit" class="bottoneRicerca">
                        <img src="Immagini/Cerca.png" alt="Cerca">
                </button>
                <label class="contenitoreBarra">
                    <input type="search" class="barraRicerca" placeholder="Cerca" name="ricerca" list="suggerimentiProdotti">
                    <datalist id="suggerimentiProdotti" class="suggerimentiProdotti"></datalist>
                </label>
            </form>
        </div>
        <%if(flagAmm == null || !flagAmm){%>
            <div class="carrello">
                <a href="UtenteControl?action=Carrello">
                    <img src="Immagini/Carrello.png" alt="Carrello">
                </a>
            </div>
        <%}%>
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
                    <img src="Immagini/Profilo.png" alt="Profilo" id="profilo">z5
                    <div class="menuTendina" id="menuTendina">
                        <%if(!flagAmm){%>
                        <!-- Contenuto del menu a tendina -->
                            <a href="profilo.jsp">Profilo</a>
                            <a href="OrdiniControl?action=OttieniOrdini">I Miei Ordini</a>
                            <a href="AccessoControl?action=Logout">Logout</a>
                        <%}else{%>
                            <a href="profilo.jsp">Profilo</a>
                            <a href="AdminControl?action=VisualizzaUtenti">Utenti</a>
                            <a href="AccessoControl?action=Logout">Logout</a>
                        <%}%>
                    </div>
                </div>
        <%
            }
        %>

    </nav>

    <script>
        $(document).ready(function(){
            $('.barraRicerca').keyup(function (){
                var ricerca = $(this).val();
                if(ricerca !== ''){
                    $.ajax({
                        url: 'PacchettoControl?action=RicercaSuggerimenti',
                        method: 'POST',
                        data: {ricerca: ricerca},
                        dataType: 'json',
                        success: function (response) {
                            var suggerimenti = response.suggerimenti;
                            var suggerimentiHTML = '';
                            for(var i = 0; i < suggerimenti.length; i++) {
                                suggerimentiHTML += '<option value="' + suggerimenti[i] + '">';
                            }
                            $('.suggerimentiProdotti').html(suggerimentiHTML);
                        }
                    })
                }else {
                    $('.suggerimentiProdotti').html('');
                }
            })
        });

        // Ottieni il link del profilo e il menu a tendina
        var profilo = document.getElementById("profilo");
        var menuTendina = document.getElementById("menuTendina");

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
