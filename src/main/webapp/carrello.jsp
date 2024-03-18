<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page errorPage="error.jsp" %><!DOCTYPE html>


<!DOCTYPE html>
<html lang="it">
<head>
    <title>Easy Travel</title>
    <link href="CSS/carrello.css" rel="stylesheet" type="text/css">
</head>
<body>
<%@ include file="navbar.jsp" %>

<div class="tutto">
    <div class="pacchetti">

    </div>
    <div class="checkout">
        <%if(email != null){%>

        <%}else{%>
            <div class="avviso">
                <span> Effettua il <a href="login.jsp">login</a> per procedere con l'acquisto.</span>
            </div>
        <%}%>
    </div>
</div>

</body>
</html>
