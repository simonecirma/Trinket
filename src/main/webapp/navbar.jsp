<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page errorPage="error.jsp" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <title>Easy Travel</title>
    <link href="CSS/navbar.css" rel="stylesheet" type="text/css">
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
            <button class="bottoneRicerca">
                    <img src="Immagini/Cerca.png" alt="Cerca">
            </button>
            <label class="contenitoreBarra">
                <input type="text" name="text" class="barraRicerca" placeholder="Cerca">
            </label>
        </div>

        <div class="carrello">
            <a href="carrello.jsp">
                <img src="Immagini/Carrello.png" alt="Carrello">
            </a>
        </div>

        <div class="profilo">
            <a href="profilo.jsp">
                <img src="Immagini/Profilo.png" alt="Profilo">
            </a>
        </div>

    </nav>
</body>
</html>
