<%-- 
    Document   : Registeration
    Created on : Sep 23, 2012, 11:39:07 PM
    Author     : Aakash
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <% String userName = request.getParameter("userName");
            String password = request.getParameter("pass");
            String fullName = request.getParameter("fullName");
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1/test", "root", "003679");
                Statement st = con.createStatement();
                String query = "select count(*) from SquidUsers where user_name = ?";
                PreparedStatement ps = con.prepareStatement(query);
                ps.setString(1, userName);
                ResultSet rs = ps.executeQuery();
                rs.next();
                if (rs.getInt(1) > 0) {
                    session.setAttribute("message", "Username "+userName+" already exists!");
                    response.sendRedirect("Register.jsp");
                } else {
                    query = "insert into SquidUsers values(?,?,?,?)";
                    ps = con.prepareStatement(query);
                    ps.setString(1, userName);
                    ps.setString(2, fullName);
                    ps.setString(3, password);
                    ps.setString(4, "");
                    ps.executeUpdate();
                    session.setAttribute("message", "Account created!");
                    response.sendRedirect("");
                }
                rs.close();
                ps.close();
                st.close();
                con.close();
            } catch (Exception e) {
                out.println(e);
                e.printStackTrace();
            }
        %>
    </body>
</html>
