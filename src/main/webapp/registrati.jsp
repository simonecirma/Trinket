<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="it">
<head>
    <title>Easy Travel</title>
    <link href="CSS/registrati.css" rel="stylesheet" type="text/css">
</head>
<body>
<%@ include file="navbar.jsp" %>

<div class="card">
    <div class="card2">
        <form class="form" action="AccessoControl?action=Registrazione" method="post">
            <p id="heading">Registrazione</p>

            <div class="field">
                <label>
                    <input type="text" name ="nome" class="input-field" placeholder="Nome">
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
                    <input type="password" name ="password" class="input-field" placeholder="Password">
                </label>
            </div>

            <div class="field">
                <label>
                    <input type="password" name ="password2" class="input-field" placeholder="Conferma Password">
                </label>
            </div>

            <div class="field">
                <label>
                    <input type="date" name ="dataDiNascita" class="input-field" placeholder="Data Di Nascita">
                </label>
            </div>

            <div class="field">
                <label>
                    <input type="file" name ="immagine" class="input-field" placeholder="Inserisci Immagine Del Profilo">
                </label>
            </div>

            <button class="button3">Conferma Registrazione!</button>
        </form>
    </div>
</div>
</body>
</html>
