<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%!
  String email = "";
  String result = "";
%>
<%
  synchronized (session) {
    session = request.getSession();
    email = (String) session.getAttribute("Email");
  }

  result = (String) request.getAttribute("result");
%>

<!DOCTYPE html>
<html lang="it">
<head>
  <title>Trinket</title>
</head>
<body>
<form method="post" action="AccessoControl?action=Login">
  <input type="email" name="email" placeholder="email">
  <input type="password" name="password" placeholder="password">
  <input type="submit" value="Accedi">
</form>
<%
  if(email != null){
%>
<h1>CIAO</h1>
<%
  }
  if (result != null) {
%>
<h3><%=result%></h3>
<%
  }
%>
</body>
</html>