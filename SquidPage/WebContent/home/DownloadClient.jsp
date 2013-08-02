<%-- 
    Document   : DownloadClient
    Created on : Sep 24, 2012, 1:39:20 AM
    Author     : Aakash
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.sql.*,java.io.*"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Download Client</title>
</head>
<body>
	<%
		String userName = null;
		String password = null;
		for (Cookie c : request.getCookies()) {
			if (c.getName().equals("userName")) {
				userName = c.getValue();
			} else if (c.getName().equals("pass")) {
				password = c.getValue();
			}
		}
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://127.0.0.1/test", "root", "003679");
			//Statement st = con.createStatement();
			String query = "select mac_address from SquidUsers where user_name = ? and password = ? ";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, userName);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			rs.next();
			String macAddress = rs.getString(1);
			String file = userName + ".jar";
			File dir = new File("/home/aakash/Desktop/SquidRMI/test");
			File f = new File(dir, file);
			session.setAttribute("filename", file);
			RequestDispatcher rd = request
					.getRequestDispatcher("RetrieveJar");
			rd.forward(request, response);
		} catch (Exception e) {
			System.out.println("unable to connect");
			System.out.println("<br>");
			System.out.println(e);
			e.printStackTrace();
		}
	%>
</body>
</html>
