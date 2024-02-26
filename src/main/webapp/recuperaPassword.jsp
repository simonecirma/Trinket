<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

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
                <label>
                    <input type="text" name ="nome" class="input-field" placeholder="Nome Utente">
                </label>
            </div>

            <div class="field">
                <label>
                    <input type="text" name ="cognome" class="input-field" placeholder="Cognome">
                </label>
            </div>

            <div class="field">
                <label>
                    <input type="email" name ="email" class="input-field" placeholder="Email">
                </label>
            </div>

            <div class="field">
                <label>
                    <input type="password" name ="password" class="input-field" placeholder=" Nuova Password">
                </label>
            </div>

            <div class="field">
                <label>
                    <input type="password" name ="password2" class="input-field" placeholder="Conferma Nuova Password">
                </label>
            </div>

            <div class="field">
                <label>
                    Data Di Nascita: <input type="date" name ="dataDiNascita" class="input-field" placeholder="Data Di Nascita">
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
        var email = document.registrati.email.value;
        var nome = document.registrati.nome.value;
        var cognome = document.registrati.cognome.value;
        var dataDiNascitaInput = document.registrati.dataDiNascita.value;

        if (password === "") {
            alert("La Password è obbligatoria! ");
            document.registrati.password.focus();
            return false;
        } else if (!passwordPattern.test(password)) {
            alert("La Password è errata! Deve contenere almeno 8 caratteri, una lettera maiuscola, una lettera minuscola, un numero e un carattere speciale!");
            document.registrati.password.focus();
            return false;
        }

        if (password !== password2) {
            alert("Le Password sono diverse! ");
            document.registrati.password2.focus();
            return false;
        }

        if (nome === "") {
            alert("Il nome è obbligatorio! ");
            document.registrati.nome.focus();
            return false;
        }

        if (cognome === "") {
            alert("Il cognome è obbligatorio! ");
            document.registrati.cognome.focus();
            return false;
        }

        if (dataDiNascitaInput === "") {
            alert("La Data Di Nascita è obbligatoria! ");
            document.registrati.dataDiNascita.focus();
            return false;
        }

        if(email === ""){
            alert("La email è obbligatoria! ");
            document.registrati.email.focus();
            return false;
        }

        return true;
    }
</script>
</body>
</html>
