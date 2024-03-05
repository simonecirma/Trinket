<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page errorPage="error.jsp" %>

<% String notifica = (String)  request.getAttribute("notifica"); %>

<!DOCTYPE html>
<html lang="it">
<head>
    <title>Easy Travel</title>
    <link href="CSS/recuperaPassword.css" rel="stylesheet" type="text/css">
</head>
<body>
<%@ include file="navbar.jsp" %>

<%
    if(notifica != null){
%>
<div id="Alert" class="notifica">
    <%= notifica%>
</div>
<%
    }
%>

<div class="card">
    <div class="card2">
        <form class="form" action="AccessoControl?action=RecuperaPassword" method="post" name="registrati" onsubmit="return validate()">
            <p id="heading">Recupera Password</p>

            <div class="field">
                <input type="text" name ="nome" class="input-field" placeholder="Nome Utente" required>
            </div>

            <div class="field">
                <input type="text" name ="cognome" class="input-field" placeholder="Cognome" required>
            </div>

            <div class="field">
                <input type="email" name ="email" class="input-field" placeholder="Email" required>
            </div>

            <div class="field">
                <input type="password" name ="password" class="input-field" placeholder=" Nuova Password" required>
            </div>

            <div class="field">
                <input type="password" name ="password2" class="input-field" placeholder="Conferma Nuova Password" required>
            </div>

            <div class="field">
                <label>
                Data Di Nascita: <input type="date" name ="dataDiNascita" class="input-field" placeholder="Data Di Nascita" required>
                </label>
            </div>

            <button type="submit" class="button3">Conferma Password!</button>
        </form>
    </div>
</div>

<script>
    document.addEventListener('DOMContentLoaded', function() {
        var notification = document.getElementById('Alert');

        // Mostro la notifica
        notification.classList.add('show');

        // Nascondo la notifica dopo 3 secondi
        setTimeout(function() {
            notification.classList.remove('show');
        }, 3000);
    });

    function validate(){
        var password = document.registrati.password.value;
        var password2 = document.registrati.password2.value;
        var passwordPattern = /^(?=.*[A-Z])(?=.*[a-z])(?=.*\d)(?=.*[!@#$%^&*.-_])(?=.{8,})/;

         if (!passwordPattern.test(password)) {
            alert("La Password è errata! Deve contenere almeno 8 caratteri, una lettera maiuscola, una lettera minuscola, un numero e un carattere speciale!");
            document.registrati.password.focus();
            return false;
        }

        if (password !== password2) {
            alert("Le Password sono diverse! ");
            document.registrati.password2.focus();
            return false;
        }

        return true;
    }
</script>
</body>
</html>
