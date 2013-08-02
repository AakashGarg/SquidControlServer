<%-- 
    Document   : index
    Created on : Sep 8, 2012, 1:40:02 AM
    Author     : Aakash
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Squid Page</title>
    </head>
    <% String message = (String) session.getAttribute("message");
        out.print("<body><h1>");
        if (message == null) {
            out.println("Welcome !");
        } else {
            out.println(message);
        }
        session.setAttribute("message", null);
        out.print("</h1></body>");
    %>
    <body>
        <form action = "Authenticate.jsp" method ="post">
            Enter the username : 
            <input type = "text" name = "name">
            <br>
            Enter the password : 
            <input type = "password" name = "pass">
            <br>
            <input type = "submit">

            <a href="Register.jsp">Register</a>
    </body>
</html>
