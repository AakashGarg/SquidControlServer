package server;

import java.sql.*;
import java.io.*;

public class ReconfigSquid {

    private static ReconfigSquid instance = null;
    private Connection con = null;

    private ReconfigSquid(Connection con) {
        this.con = con;
    }

    public static ReconfigSquid getInstance(Connection con) {
        if (instance == null) {
            instance = new ReconfigSquid(con);
        }
        return instance;
    }

    public void reconfigure() throws Exception {
        try {
            deleteFile();
            createFile();
            BufferedWriter bw = new BufferedWriter(new FileWriter("/etc/squid/valid_ip.txt"));
            PreparedStatement ps = con.prepareStatement("select ip_address from currentusers");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String ipAddress = rs.getString(1);
                bw.write(ipAddress);
               bw.newLine();
            }
           bw.close();
           execeuteReconfigCmd();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("unable to add ur ip");
        }
    }

    private void execeuteReconfigCmd() throws Exception {
        Process p = Runtime.getRuntime().exec("sudo ./Squid-server/exe/reconfigureSquid");
        p.waitFor();
    }

    private void deleteFile() throws Exception {
        Process p = Runtime.getRuntime().exec("sudo ./Squid-server/exe/deleteFile");
        p.waitFor();
    }

    private void createFile() throws Exception {
        Process p = Runtime.getRuntime().exec("sudo ./Squid-server/exe/createFile");
        p.waitFor();
    }

    public static void main(String... s) throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1/test", "root", "003679");
        ReconfigSquid rc = ReconfigSquid.getInstance(con);
        rc.reconfigure();
    }
}
