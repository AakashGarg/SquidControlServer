<%-- 
    Document   : ChangePassword
    Created on : Oct 25, 2012, 2:49:01 PM
    Author     : Aakash
--%>

<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Change Password</title>
    </head>
    <body>
        <%
            String userName = (String) request.getParameter("userName");
            String password = (String) request.getParameter("oldPassword");
            String newPassword = (String) request.getParameter("newPassword");
            String confirmNewPassword = (String) request.getParameter("confirmNewPassword");

            if (newPassword.compareTo(confirmNewPassword) != 0) {
                session.setAttribute("message", "new password fields do not match! " + newPassword + ", " + confirmNewPassword);
                response.sendRedirect("ChangePass.jsp");
            }
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
                    query = "update SquidUsers set password = ? where user_name = ? and password = ?";
                    ps = con.prepareStatement(query);
                    ps.setString(1, newPassword);
                    ps.setString(2, userName);
                    ps.setString(3, password);
                    ps.executeUpdate();
                    session.setAttribute("message", "Password Sucessfully changed. Login Again!");
                    response.sendRedirect("/SquidPage");
                } else {
                    session.setAttribute("message", "invalid username or password");
                    response.sendRedirect("ChangePass.jsp");
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
    </body>
</html>
