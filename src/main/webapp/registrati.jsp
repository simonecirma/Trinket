<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page errorPage="error.jsp" %>

<!DOCTYPE html>
<html lang="it">
<head>
    <title>Easy Travel</title>
    <link href="CSS/registrati.css" rel="stylesheet" type="text/css">
    <script src="https://code.jquery.com/jquery-3.6.4.min.js" integrity="sha384-UG8ao2jwOWB7/oDdObZc6ItJmwUkR/PfMyt9Qs5AwX7PsnYn1CRKCTWyncPTWvaS" crossorigin="anonymous"></script>
</head>
<body>
<%@ include file="navbar.jsp" %>

<div class="card">
    <div class="card2">
        <form class="form" action="AccessoControl?action=Registrazione" method="post" enctype="multipart/form-data" name="registrati" onsubmit="return validate()">
            <p id="heading">Registrazione</p>

            <div class="field">
                <input type="text" name ="nome" class="input-field" placeholder="Nome" required>
            </div>

            <div class="field">
                <input type="text" name ="cognome" class="input-field" placeholder="Cognome" required>
            </div>

            <div class="field">
                <input type="email" name ="email" class="input-field" placeholder="Email" required>
            </div>

            <div class="field">
                <input type="password" name ="password" class="input-field" placeholder="Password" required>
            </div>

            <div class="field">
                <input type="password" name ="password2" class="input-field" placeholder="Conferma Password" required>
            </div>

            <div class="field">
                <label>
                   Data Di Nascita: <input type="date" name ="dataDiNascita" class="input-field" placeholder="Data Di Nascita" required>
                </label>
            </div>

            <div class="field">
                <label>
                   Immagine del Profilo: <input type="file" name ="immagine" class="input-field" id="fileInput" onchange="showFileName()">
                    <button type="button" onclick="document.getElementById('fileInput').click()">Scegli</button>
                    <div id="fileName"></div>
                </label>
            </div>

            <button type="submit" class="button3">Conferma Registrazione!</button>
        </form>
    </div>
</div>

<script>
    function showFileName() {
        var fileInput = document.getElementById('fileInput');
        var fileName = document.getElementById('fileName');
        fileName.textContent = 'File selezionato: ' + fileInput.files[0].name;
    }

    function validate() {
        var email = document.registrati.email.value;
        var emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        var password = document.registrati.password.value;
        var password2 = document.registrati.password2.value;
        var passwordPattern = /^(?=.*[A-Z])(?=.*[a-z])(?=.*\d)(?=.*[!@#$%^&*.-_])(?=.{8,})/;
        var dataDiNascitaInput = document.registrati.dataDiNascita.value;
        var dataDiOggi = new Date();
        var dataLimite = new Date();
        dataLimite.setFullYear(dataDiOggi.getFullYear() - 18);

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

        var dataDiNascita = new Date(dataDiNascitaInput);

        if(dataDiNascita > dataDiOggi){
            alert("Inserisci una data valida");
            document.registrati.dataDiNascita.focus();
            return false;
        }else if(dataDiNascita > dataLimite){
            alert("Per registrarti devi avere almeno 18 anni");
            document.registrati.dataDiNascita.focus();
            return false;
        }

        if(!emailPattern.test(email)){
            alert("Inserire una email valida! ");
            document.registrati.email.focus();
            return false;
        }else{
            $.ajax({
                url: 'AccessoControl?action=verificaEmail',
                type: 'POST',
                data: {email: email},
                success: function(response){
                    if(response === "trovato"){
                        alert("Esiste già un account con questa Email! ");
                    }else {
                        document.registrati.submit();
                    }
                },
                error: function(){
                    alert("Si è verificato un errore durante la verifica della email! ");
                }
            });
            return false;
        }
    }
</script>
</body>
</html>
