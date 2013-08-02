<%-- 
    Document   : Home
    Created on : Sep 24, 2012, 12:20:23 AM
    Author     : Aakash
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Squid Home Page</title>
    </head>
    <% String message = (String) session.getAttribute("message");
        out.print("<body><h1>");
        
        if (message == null) {
            out.println(session.getAttribute("homeMessage"));
        } else {
            out.println(message);
        }
        session.setAttribute("message", null);
        out.print("</h1></body>");
    %>
    <body>
        <h1>Squid Home Page</h1>
       <a href="ChangePass.jsp">Change password</a>
       <br>
       <a href="ChangeComputer.jsp">Change Computer/MAC Address</a>
       <br>
       <a href="RetrieveJar">Download Client</a>
       <br>
       <a href="Logout.jsp">Logout</a>
       <br>
       
    </body>
</html>
