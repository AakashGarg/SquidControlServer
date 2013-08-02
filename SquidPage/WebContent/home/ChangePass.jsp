<%-- 
    Document   : ChangePassword
    Created on : Sep 24, 2012, 12:31:29 AM
    Author     : Aakash
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Change Password</title>
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
 <form action = "ChangePassword.jsp" method ="post">
        Username : 
        <input type = "text" name = "userName">
        <br>
        Password : 
        <input type = "password" name = "oldPassword">
        <br>
        New Password :
        <input type = "password" name = "newPassword">
        <br>
        Confirm new Password :
        <input type = "password" name = "confirmNewPassword">
        <br>
        <input type = "submit">
   </body>
</html>
