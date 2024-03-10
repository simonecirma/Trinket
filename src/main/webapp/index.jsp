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

</script>
</body>
</html>

