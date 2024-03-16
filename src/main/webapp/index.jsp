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
        <button class="animated-button">
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
    </div>
    <div class="slogan">Esperienze uniche, viaggi indimenticabili: solo con Easy Travel.</div>
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
</script>
</body>
</html>

