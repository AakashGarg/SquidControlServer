<%-- 
    Document   : Register
    Created on : Sep 23, 2012, 11:35:04 PM
    Author     : Aakash
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <% String message = (String) session.getAttribute("message");
            if (message != null) {
                out.print("<h1>");
                out.println(message);
                out.println("</h1><br>");
            }
        %>
        <form action = "Registeration.jsp" method ="post">
            Enter the username : 
            <input type = "text" name = "userName">
            <br>
            Enter the password : 
            <input type = "password" name = "pass">
            <br>
            Enter the Full Name : 
            <input type = "text" name = "fullName">
            <br>

            <input type = "submit">

            </body>
            </html>
