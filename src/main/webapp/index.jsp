<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page errorPage="error.jsp" %>
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
  <title>Easy Travel</title>
</head>
<body>
<%@ include file="navbar.jsp" %>
<form method="post" action="AccessoControl?action=Login">
  <label>
    Email: <input type="email" name="email" placeholder="email">
  </label>
  <label>
    Password: <input type="password" name="password" placeholder="password">
  </label>
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