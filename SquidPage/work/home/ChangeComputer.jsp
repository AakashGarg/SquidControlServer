<%-- 
    Document   : MAC
    Created on : Oct 25, 2012, 1:26:43 AM
    Author     : Aakash
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Change Computer</title>
    </head>
    <% String message = (String) session.getAttribute("message");
        out.print("<body><h1>");
        if (message != null) {
            out.println(message);
        }
        session.setAttribute("message", null);
        out.print("</h1></body>");
    %>
    <body>
        <form action = "ChangeMAC.jsp" method ="post">
            Username : 
            <input type = "text" name = "userName">
            <br>
            Password : 
            <input type = "password" name = "pass">
            <br>
            MAC-Address : 
            <input type = "text" name = "macAddress">
            <br>Example MAC : E8-39-DF-3F-AC-94<br><br>
            <input type = "submit">
            </body>
            </html>
