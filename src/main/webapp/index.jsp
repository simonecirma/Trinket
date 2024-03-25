<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page errorPage="error.jsp" %>

<% String notifica = (String)  request.getAttribute("notifica"); %>

<!DOCTYPE html>
<html lang="it">
<head>
  <title>Easy Travel</title>
  <link href="CSS/index.css" rel="stylesheet" type="text/css">
</head>
<body>
<%@ include file="navbar.jsp" %>

<%
  if(email != null && session.getAttribute("notificationShown") == null){
      session.setAttribute("notificationShown", true);
%>
    <div id="loginAlert" class="login">
        Benvenuto <%= nome%>!
    </div>
<%
  }

  if(notifica != null){
%>
    <div id="logoutAlert" class="logout">
      <%= notifica%>
    </div>
<%
  }
%>

<div class="tutto">
    <div class="sfondo">
        <div class="slider-wrapper">
            <div class="slider">
                <img src="Immagini/Sfondi/Cucina.jpg" alt="Errore Immagini">
                <img src="Immagini/Sfondi/Cucina2.jpg" alt="Errore Immagini">
                <img src="Immagini/Sfondi/Romantico.jpg" alt="Errore Immagini">
                <img src="Immagini/Sfondi/Sport.jpg" alt="Errore Immagini">
                <img src="Immagini/Sfondi/Terme_E_Spa.jpg" alt="Errore Immagini">
                <img src="Immagini/Sfondi/Viaggio.jpg" alt="Errore Immagini">
            </div>
        </div>
        <%if(flagAmm == null || !flagAmm){%>
        <button class="animated-button" type="button" onclick="location.href='PacchettoControl?action=OttieniPacchetti'">
            <svg viewBox="0 0 24 24" class="arr-2" xmlns="http://www.w3.org/2000/svg">
                <path
                        d="M16.1716 10.9999L10.8076 5.63589L12.2218 4.22168L20 11.9999L12.2218 19.778L10.8076 18.3638L16.1716 12.9999H4V10.9999H16.1716Z"
                ></path>
            </svg>
            <span class="text">Vai Al Catalogo! </span>
            <span class="circle"></span>
            <svg viewBox="0 0 24 24" class="arr-1" xmlns="http://www.w3.org/2000/svg">
                <path
                        d="M16.1716 10.9999L10.8076 5.63589L12.2218 4.22168L20 11.9999L12.2218 19.778L10.8076 18.3638L16.1716 12.9999H4V10.9999H16.1716Z"
                ></path>
            </svg>
        </button>
        <%}else{%>
            <button class="animated-button" type="button" onclick="location.href='PacchettoControl?action=OttieniPacchettiPerAmm'">
                <svg viewBox="0 0 24 24" class="arr-2" xmlns="http://www.w3.org/2000/svg">
                    <path
                            d="M16.1716 10.9999L10.8076 5.63589L12.2218 4.22168L20 11.9999L12.2218 19.778L10.8076 18.3638L16.1716 12.9999H4V10.9999H16.1716Z"
                    ></path>
                </svg>
                <span class="text">Vai Al Catalogo! </span>
                <span class="circle"></span>
                <svg viewBox="0 0 24 24" class="arr-1" xmlns="http://www.w3.org/2000/svg">
                    <path
                            d="M16.1716 10.9999L10.8076 5.63589L12.2218 4.22168L20 11.9999L12.2218 19.778L10.8076 18.3638L16.1716 12.9999H4V10.9999H16.1716Z"
                    ></path>
                </svg>
            </button>
        <%}%>
    </div>
    <div class="entry">
        <div class="slogan"><p class="slogan-testo">Esperienze uniche, viaggi indimenticabili: solo con Easy Travel.</p></div>
        <div class="proposte">
            <div class="proposte_testo">
                <span class="freccia">&#8595;</span>
                <p class="slogan-testo">Le nostre proposte! </p>
            </div>
            <img src="Immagini/Pacchetto.png" alt="Errore Immagine">
        </div>
    </div>

    <div class="suggerrimenti">
        <label class="domanda">Hai bisogno di un regalo?</label>
        <div class="per-chi">
            <div class="scelte">Per coppia:
                <a href="PacchettoControl?action=OttieniPacchetti">
                    <img src="Immagini/Index/Coppia.jpg" alt="Errore immagine">
                </a>
            </div>
            <div class="scelte">Per lui:
                <a href="PacchettoControl?action=OttieniPacchetti">
                    <img src="Immagini/Index/Lui.jpg" alt="Errore immagine">
                </a>
            </div>
            <div class="scelte">Per lei:
                <a href="PacchettoControl?action=OttieniPacchetti">
                    <img src="Immagini/Index/Lei.jpg" alt="Errore immagine">
                </a>
            </div>
            <div class="scelte">Per famiglia:
                <a href="PacchettoControl?action=OttieniPacchetti">
                    <img src="Immagini/Index/Famiglia.jpg" alt="Errore immagine">
                </a>
            </div>
        </div>
        <label class="domanda">Cerchi Ispirazione?</label>
        <div class="per-cosa">
            <div class="tipo">Terme E Spa:
                <a href="PacchettoControl?action=FiltriIndex&tipo=Terme E Spa">
                    <img src="Immagini/Index/Terme_E_Spa.jpg" alt="Errore immagine">
                </a>
            </div>
            <div class="tipo">Weekend Romantico:
                <a href="PacchettoControl?action=FiltriIndex&tipo=Weekend Romantico">
                    <img src="Immagini/Index/Romantico.jpg" alt="Errore immagine">
                </a>
            </div>
            <div class="tipo">Agriturismi e B&B:
                <a href="PacchettoControl?action=FiltriIndex&tipo=Agriturismi e BeB">
                    <img src="Immagini/Index/Agriturismi_E_B&B.jpg" alt="Errore immagine">
                </a>
            </div>
            <div class="tipo">Soggiorno Classico:
                <a href="PacchettoControl?action=FiltriIndex&tipo=Soggiorno Classico">
                    <img src="Immagini/Index/Classico.jpg" alt="Errore immagine">
                </a>
            </div>
            <div class="tipo">Soggiorno Insolito:
                <a href="PacchettoControl?action=FiltriIndex&tipo=Soggiorno Insolito">
                    <img src="Immagini/Index/Insolito.jpg" alt="Errore immagine">
                </a>
            </div>
            <div class="tipo">Corso di Cucina:
                <a href="PacchettoControl?action=FiltriIndex&tipo=Corso di Cucina">
                    <img src="Immagini/Index/Cucina.jpg" alt="Errore immagine">
                </a>
            </div>
            <div class="tipo">Esperienze Gastronomiche:
                <a href="PacchettoControl?action=FiltriIndex&tipo=Esperienze Gastronomiche">
                    <img src="Immagini/Index/Gourmet.jpg" alt="Errore immagine">
                </a>
            </div>
        </div>
    </div>
</div>


<script>
  document.addEventListener('DOMContentLoaded', function() {
    var notification = document.getElementById('logoutAlert');

    // Mostro la notifica
    notification.classList.add('show');

    // Nascondo la notifica dopo 3 secondi
    setTimeout(function() {
      notification.classList.remove('show');
    }, 3000);
  });


  document.addEventListener('DOMContentLoaded', function() {
      var notification = document.getElementById('loginAlert');

      // Mostro la notifica
      notification.classList.add('show');

      // Nascondo la notifica dopo 3 secondi
      setTimeout(function() {
          notification.classList.remove('show');
      }, 3000);
  });

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
          slides[i].style.display = 'none';
      }
      slides[slideIndex].style.display = 'block';
  }

  function nextSlide() {
      showSlide(slideIndex + 1);
  }

  setInterval(nextSlide, 3000); // Cambio automatico ogni 3 secondi

  showSlide(slideIndex);
</script>

</body>
</html>

