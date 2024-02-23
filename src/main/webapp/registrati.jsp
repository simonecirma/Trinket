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
        <form class="form" action="AccessoControl?action=Registrazione" method="post" enctype="multipart/form-data">
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
                   Data Di Nascita: <input type="date" name ="dataDiNascita" class="input-field" placeholder="Data Di Nascita">
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
</script>
</body>
</html>
