<%-- 
    Document   : Logout
    Created on : Oct 25, 2012, 3:09:54 PM
    Author     : Aakash
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Logout</title>
    </head>
    <%
        for (Cookie c : request.getCookies()) {
            if (c.getName().equals("userName") || c.getName().equals("pass")) {
                c.setMaxAge(0);
                response.addCookie(c);
            }
        }
        String message = (String) session.getAttribute("message");
        if (message == null) {
            session.setAttribute("message", "You have sucessfully logged out!");
        }
        session.setAttribute("homeMessage", null);
        response.sendRedirect("/SquidPage/index.jsp");
    %>
</html>
