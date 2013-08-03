/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models.listener;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 *
 * @author Aakash
	DBMS username and password are to be generalized
 */
public class DBConnectionInit implements ServletContextListener {

    public void contextInitialized(ServletContextEvent e) {
        try {
            ServletContext ctx = e.getServletContext();
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://127.0.0.1/test", "root", "003679");
            ctx.setAttribute("DBConnection", con);
            System.out.println("Database connection created");
        } catch (Exception ex) {
            System.out.println("Unable to create a database connection");
            System.out.println(ex.getMessage());
        }
    }

    public void contextDestroyed(ServletContextEvent sce) {
        ServletContext ctx = sce.getServletContext();
        Connection con = (Connection) ctx.getAttribute("DBConnection");
        con = null;
        ctx.setAttribute("DBConnection", con);
    }
}
