<%-- 
    Document   : ChangeMAC
    Created on : Sep 24, 2012, 12:31:40 AM
    Author     : Aakash
--%>

<%@page import="java.sql.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Change Computer</title>
    </head>
    <body>
        <h1>Change MAC address!</h1>
    </body>
    <%
        String userName = request.getParameter("userName");
        String password = request.getParameter("pass");
        String mac_address = request.getParameter("macAddress").toUpperCase();
        try {
            ServletContext ctx = getServletContext();
            Connection con = (Connection) ctx.getAttribute("DBConnection");
            String query = "select count(*) from SquidUsers where user_name = ? and password = ? ";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, userName);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            rs.next();
            int noOfRecords = rs.getInt(1);
            if (noOfRecords == 1) {
                query = "update SquidUsers set mac_address = ? where user_name = ? and password = ?";
                ps = con.prepareStatement(query);
                ps.setString(1, mac_address);
                ps.setString(2, userName);
                ps.setString(3, password);
                ps.executeUpdate();
                session.setAttribute("message", "Computer Sucessfully changed!");
                response.sendRedirect("index.jsp");
            } else {
                session.setAttribute("message", "invalid username or password");
                response.sendRedirect("ChangeComputer.jsp");
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            out.println("unable to connect");
            out.println("<br>");
            out.println(e);
            e.printStackTrace();
        }
    %>
</html>
