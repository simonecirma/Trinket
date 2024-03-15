<%@ page import="com.example.trinket.model.bean.PacchettoBean" %>
<%@ page import="com.example.trinket.model.bean.ImmaginiBean" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page errorPage="error.jsp" %>

<% PacchettoBean pacchetto = (PacchettoBean) request.getAttribute("pacchetto");%>

<!DOCTYPE html>
<html lang="it">
<head>
    <title>Easy Travel</title>
    <link href="CSS/dettagliPacchetto.css" rel="stylesheet" type="text/css">
</head>
<body>
<%@ include file="navbar.jsp" %>
<div class="tutto">
    <div class="slider-wrapper">
        <div class="slider">
            <% for(ImmaginiBean bean : pacchetto.getImmagini()){
            %>
                    <img src="Immagini/Pacchetti/<%=bean.getNome()%>" alt="Errore Immagini">
            <%
                }
            %>
        </div>
        <div class="slider-nav">
            <button class="slider-nav-btn" onclick="prevSlide()">&#10094;</button>
            <button class="slider-nav-btn" onclick="nextSlide()">&#10095;</button>
        </div>
    </div>
    <div class="destra">
        <div class="Informazioni-pacchetto">
            <div class="card">
                <div class="Nome"><span class="testo-nome"><%=pacchetto.getNome()%></span></div>
                <div class="Informazioni-pacchetto2">
                    <div class="Tipo"><span class="esperienza">Esperienza: </span><span class="testo-tipo"><%=pacchetto.getTipo()%></span> </div>
                    <div class="Tipo"><span class="esperienza">Durata: </span><span class="testo-tipo"><%=pacchetto.getNumGiorni()%> Giorni</span> </div>
                </div>
                <div class="Prezzo">
                    <span class="testo-prezzo"><span class="esperienza">Prezzo: </span><%=pacchetto.getPrezzo()%>€</span>
                    <div class="main-section">
                        <button class="first-button"> Pronto ad Iniziare? </button>
                        <button class="second-button"> <svg viewBox="0 0 24 24" width="20" height="20" stroke="#ffd300" stroke-width="2" fill="none" stroke-linecap="round" stroke-linejoin="round" class="css-i6dzq1"><circle cx="9" cy="21" r="1"></circle><circle cx="20" cy="21" r="1"></circle><path d="M1 1h4l2.68 13.39a2 2 0 0 0 2 1.61h9.72a2 2 0 0 0 2-1.61L23 6H6"></path></svg> Aggiungi al carrello!</button>
                    </div>
                </div>
            </div>
        </div>
        <div class="descrizione-pacchetto">
            <div class="card">
                <div class="descrizione-ridotta">
                    <span class="titolo">Che cosa è incluso?</span>
                    <span class="testo_descrizione_piccola"><%=pacchetto.getDescrizioneRidotta()%></span>
                    <span id="mostra-altro">Mostra Altro...</span>
                </div>

                <div class="descrizione-estesa" id="descrizione-estesa">
                    <span class="testo_descrizione" id="testo_descrizione" style="display: none;"><%=pacchetto.getDescrizione()%></span>
                </div>
            </div>
        </div>
    </div>

</div>

<script>
    let slideIndex = 0;
    const slides = document.querySelectorAll('.slider img');

    function showSlide(n) {
        if (n >= slides.length) {
            slideIndex = 0;
        } else if (n < 0) {
            slideIndex = slides.length - 1;
        } else {
            slideIndex = n;
        }

        for (let i = 0; i < slides.length; i++) {
            slides[i].style.transform = 'translateX(' + (-slideIndex * 100) + '%)';
        }
    }

    function nextSlide() {
        showSlide(slideIndex + 1);
    }

    function prevSlide() {
        showSlide(slideIndex - 1);
    }

    showSlide(slideIndex);


    var mostraAltroSpan = document.getElementById('mostra-altro');
    var descrizioneEstesaDiv = document.getElementById('descrizione-estesa');
    var testoDescrizioneSpan = document.getElementById('testo_descrizione');

    mostraAltroSpan.addEventListener('click', function() {
        if (descrizioneEstesaDiv.style.display === 'none') {
            descrizioneEstesaDiv.style.display = 'block';
            testoDescrizioneSpan.style.display = 'inline'; // Mostra il testo della descrizione
            mostraAltroSpan.textContent = 'Nascondi'; // Cambia il testo dello span
        } else {
            descrizioneEstesaDiv.style.display = 'none';
            mostraAltroSpan.textContent = 'Mostra Altro...'; // Cambia il testo dello span
        }
    });

</script>
</body>
</html>
