<%@ page import="com.example.trinket.model.UtenteModel" %>
<%@ page import="com.example.trinket.model.bean.UtenteBean" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page errorPage="error.jsp" %>

<% List<UtenteBean> utenti = (List<UtenteBean>) request.getAttribute("utenti");%>

<!DOCTYPE html>
<html lang="it">
<head>
    <title>Easy Travel</title>
    <link href="CSS/elencoUtenti.css" rel="stylesheet" type="text/css">
</head>
<body>
<%@ include file="navbar.jsp" %>
<%if(flagAmm){%>
<div class="tutto">
    <div class="tabella">
        <table class="utenti">
            <caption style="display: none;">Elenco Utenti</caption>
            <thead>
                <tr class="signolo_utente">
                    <th class="campo">Email</th>
                    <th class="campo">Nome</th>
                    <th class="campo">Cognome</th>
                    <th class="campo">Data Di Nascita</th>
                    <th class="campo">Visualizza Ordini</th>
                </tr>
            </thead>
            <%for(UtenteBean utente : utenti){%>
                <tbody>
                    <tr class="signolo_utente">
                        <td class="campo"><%=utente.getEmail()%></td>
                        <td class="campo"><%=utente.getNome()%></td>
                        <td class="campo"><%=utente.getCognome()%></td>
                        <td class="campo"><%=utente.getDataDiNascita()%></td>
                        <td class="campo"><a href="OrdiniControl?action=OrdiniPerUtenteAdmin&Email=<%=utente.getEmail()%>">Ordini</a> </td>
                    </tr>
                </tbody>
            <%}%>
        </table>
    </div>
</div>
<%}%>
</body>
</html>
