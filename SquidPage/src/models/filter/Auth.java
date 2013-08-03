/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models.filter;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

/**
 * 
 * @author Aakash
 */
public class Auth implements Filter {

	Connection con = null;
	private static final boolean debug = true;
	// The filter configuration object we are associated with. If
	// this value is null, this filter instance is not currently
	// configured.
	private FilterConfig filterConfig = null;

	public Auth() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = (Connection) DriverManager.getConnection(
					"jdbc:mysql://127.0.0.1/test", "root", "003679");
		} catch (Exception e) {
			System.out.println("Unable to connect to database");
		}
	}

	/**
	 * 
	 * @param request
	 *            The servlet request we are processing
	 * @param response
	 *            The servlet response we are creating
	 * @param chain
	 *            The filter chain we are processing
	 * 
	 * @exception IOException
	 *                if an input/output error occurs
	 * @exception ServletException
	 *                if a servlet error occurs
	 */
	private boolean isUserValid(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String userName = null;
		String password = null;
		for (Cookie c : request.getCookies()) {
			if (c.getName().equals("userName")) {
				userName = c.getValue();
			} else if (c.getName().equals("pass")) {
				password = c.getValue();
			}
		}

		String query = "select count(*) from SquidUsers where user_name = ? and password = ? ";
		PreparedStatement ps = (PreparedStatement) con.prepareStatement(query);
		ps.setString(1, userName);
		ps.setString(2, password);
		ResultSet rs = ps.executeQuery();
		rs.next();
		int noOfRecords = rs.getInt(1);
		rs.close();
		ps.close();

		if (noOfRecords == 1) {
			return true;
		} else {
			return false;
		}

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		if (debug) {
			log("Auth:doFilter()");
		}

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		try {
			boolean isValidUser = isUserValid(req, res);
			if (isValidUser) {
				chain.doFilter(request, response);
			} else {
				HttpSession session = req.getSession();
				session.setAttribute("message", "invalid userName and password");
				RequestDispatcher rd = req.getRequestDispatcher("/SquidPage");
				rd.forward(req, res);
			}
		} catch (Exception e) {
		}

	}

	/**
	 * Return the filter configuration object for this filter.
	 */
	public FilterConfig getFilterConfig() {
		return (this.filterConfig);
	}

	/**
	 * Set the filter configuration object for this filter.
	 * 
	 * @param filterConfig
	 *            The filter configuration object
	 */
	public void setFilterConfig(FilterConfig filterConfig) {
		this.filterConfig = filterConfig;
	}

	/**
	 * Destroy method for this filter
	 */
	@Override
	public void destroy() {
	}

	/**
	 * Init method for this filter
	 */
	@Override
	public void init(FilterConfig filterConfig) {
		this.filterConfig = filterConfig;
		if (filterConfig != null) {
			if (debug) {
				log("Auth:Initializing filter");
			}
		}
	}

	
	@Override
	public String toString() {
		if (filterConfig == null) {
			return ("Auth()");
		}
		StringBuffer sb = new StringBuffer("Auth(");
		sb.append(filterConfig);
		sb.append(")");
		return (sb.toString());
	}

	
	public void log(String msg) {
		filterConfig.getServletContext().log(msg);
	}
}
