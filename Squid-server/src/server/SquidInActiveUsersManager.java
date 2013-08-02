package server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

public class SquidInActiveUsersManager implements Runnable {

	public HashMap<String, Long> currentUsers = new HashMap<String, Long>();
	private Connection con = null;
	private ReconfigSquid squid = null;

	public SquidInActiveUsersManager(Connection con, ReconfigSquid squid) {
		this.con = con;
		this.squid = squid;
		try {
			String query = "select * from currentusers";
			PreparedStatement ps = con.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				String ipAddress = rs.getString(2);
				currentUsers.put(ipAddress, System.currentTimeMillis());
				System.out.println(rs.getString(1) + " connected with ip:"
						+ ipAddress);
			}
			ps.close();
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (true) {
			long now = System.currentTimeMillis();
			try {
				String query = "delete from currentusers where ip_address = ?";
				PreparedStatement ps = con.prepareStatement(query);
				for (String ip : currentUsers.keySet()) {
					long lastTime = currentUsers.get(ip);
					if (((now - lastTime) >= 30 * 1000)/*
														 * &&
														 * (ip.equals("192.168.1.6"
														 * )==false)
														 */) {
						ps.setString(1, ip);
						ps.executeUpdate();
						currentUsers.remove(ip);
						System.out
								.println("TimeOut ip:" + ip + " disconnected");
					}
				}
				ps.close();
				squid.reconfigure();
				Thread.sleep(35 * 1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
