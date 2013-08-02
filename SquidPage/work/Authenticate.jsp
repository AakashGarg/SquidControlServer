<%-- 
    Document   : Authenticate
    Created on : Sep 23, 2012, 11:17:34 PM
    Author     : Aakash
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import= "java.sql.*"%>
<!DOCTYPE html>
<% String name = request.getParameter("name");
    String pass = request.getParameter("pass");
    try {
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1/test", "root", "003679");
        Statement st = con.createStatement();
        String query = "select count(*) from SquidUsers where user_name = ? and password = ? ";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, name);
        ps.setString(2, pass);
        ResultSet rs = ps.executeQuery();
        rs.next();
        int noOfRecords = rs.getInt(1);
        if (noOfRecords == 1) {
            query = "select * from SquidUsers where user_name = ? and password = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, name);
            ps.setString(2, pass);
            rs = ps.executeQuery();
            rs.next();
            String userName = rs.getString("user_name");
            String password = rs.getString("password");
            if (name.equals(userName) && password.equals(pass)) {
                session.setAttribute("message","Hi " + rs.getString("name") + "!");
                session.setAttribute("homeMessage","Hi " + rs.getString("name") + "!");
                Cookie[] cookie = new Cookie[2];
                cookie[0] = new Cookie("userName", name);
                cookie[1] = new Cookie("pass", pass);
                response.addCookie(cookie[0]);
                response.addCookie(cookie[1]);
                response.sendRedirect("home");
            }
            out.println("<br>");

        } else {
            out.println("invalid username or password");
        }
        rs.close();
        ps.close();
        st.close();
        con.close();
    } catch (Exception e) {
        out.println("unable to connect");
        out.println("<br>");
        out.println(e);
        e.printStackTrace();
    }
%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
    </body>
</html>
