<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isErrorPage="true" %>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Errore</title>
    <link href="CSS/error.css" rel="stylesheet" type="text/css">
</head>
<body>
<div class="error-container">
    <h2>Errore</h2>
    <p><%= request.getAttribute("errorMessage") %></p>
    <p>Torna Alla <a href="index.jsp"> Home</a></p>
</div>
</body>
</html>
