package models.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.rmi.Naming;
import java.sql.DriverManager;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import server.clientCompiler.CompilerRemote;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

public class RetrieveJar extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6386977880497629254L;
	static String serverIpAndPort = "127.0.0.1:1099";	//change this ip if compiler server is on other computer

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/jar");
		HttpSession session = request.getSession();
		if (session.getAttribute("filename") == null) {
			session.setAttribute("filename", "aakash.jar");
		}
		String fileName = "/home/aakash/workspace/Squid-client/test/"
				+ session.getAttribute("fileName");
		Utilities util = new Utilities();
		ServletContext ctx = getServletContext();
		InputStream is = ctx.getResourceAsStream(fileName);
		OutputStream os = response.getOutputStream();
		String result = util.streamToStream(is, os, 1024);
		request.setAttribute("result", result);
		RequestDispatcher view = request.getRequestDispatcher("result.jsp");
		view.forward(request, response);
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String output = null;
		try {
			output = getUserNameAndMac(request, response);
		} catch (NullPointerException e) {
		}
		if (output == null || output.contains("null")) {
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out.println("error retriving data from database");
			return;
		}
		String userName = output.split("/")[0];
		String macAddress = output.split("/")[1];
		String fileName = userName + ".jar";

		response.setContentType("application/jar");
		response.setHeader("Content-Disposition", "attachment;filename="
				+ fileName);
		// HttpSession session = request.getSession();

		try {
			CompilerRemote compiler = (server.clientCompiler.CompilerRemote) Naming
					.lookup("rmi://" + serverIpAndPort + "/compileAndJar");
			serverIpAndPort = compiler.compileAndJar(userName, macAddress);
		} catch (Exception e) {
			System.err.println("unknown exception");
			e.printStackTrace();
		}

		String dir = "/home/aakash/workspace/Squid-client/test";
		File file = new File(dir, fileName);
		System.out.println("file " + fileName + " exists : " + file.exists());
		Utilities util = new Utilities();
		// ServletContext ctx = getServletContext();
		FileInputStream is = new FileInputStream(file);
		ServletOutputStream os = response.getOutputStream();
		String result = util.streamToStream(is, os, 1024);
		request.setAttribute("result", result);
		System.out.println(result);
		os.flush();
		os.close();
	}

	public String getUserNameAndMac(HttpServletRequest request,
			HttpServletResponse response) {
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
			Connection con = (Connection) DriverManager.getConnection(
					"jdbc:mysql://127.0.0.1/test", "root", "003679");
			con.createStatement();
			String query = "select mac_address from SquidUsers where user_name = ? and password = ? ";
			PreparedStatement ps = (PreparedStatement) con
					.prepareStatement(query);
			ps.setString(1, userName);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			rs.next();
			String macAddress = rs.getString(1);
			return userName + "/" + macAddress;
		} catch (Exception e) {
			System.out.println("unable to connect");
			System.out.println("<br>");
			System.out.println(e);
			e.printStackTrace();
			return null;
		}
	}
}
