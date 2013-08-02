package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SquidServer extends UnicastRemoteObject implements SquidRemote {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4767225652355860052L;
	private Connection con = null;
	private ReconfigSquid squid = null;
	private SquidInActiveUsersManager inActiveUsersManager;

	public SquidServer(String userName, String password) throws RemoteException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			this.con = DriverManager.getConnection(
					"jdbc:mysql://127.0.0.1/test", userName, password);
			this.squid = ReconfigSquid.getInstance(con);
			this.inActiveUsersManager = new SquidInActiveUsersManager(con,
					squid);
			Thread manageUsers = new Thread(inActiveUsersManager);
			manageUsers.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public PreparedStatement getInsertStatement(String userName,
			String ipAddress) throws Exception {
		PreparedStatement ps = con
				.prepareStatement("insert into currentusers values(?,?)");
		ps.setString(1, userName);
		ps.setString(2, ipAddress);
		return ps;
	}

	public PreparedStatement getRemoveStatement(String ipAddress)
			throws Exception {
		PreparedStatement ps = con
				.prepareStatement("delete from currentusers where ip_address = ?");
		ps.setString(1, ipAddress);
		return ps;
	}

	public boolean isUserValid(String userName, String password,
			String ipAddress) throws Exception {
		boolean found = false;
		String query = "select count(*) from SquidUsers where user_name = ? and password = ?";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setString(1, userName);
		ps.setString(2, password);
		ResultSet rs = ps.executeQuery();
		rs.next();
		int noOfRecords = rs.getInt(1);
		if (noOfRecords == 1) { // A AUTHENTICATED USER
			query = "select count(*) from currentusers where ip_address = ?";
			ps = con.prepareStatement(query);
			ps.setString(1, ipAddress);
			rs = ps.executeQuery();
			rs.next();
			noOfRecords = rs.getInt(1);
			if (noOfRecords > 0) {
				query = "select user_name from currentusers where ip_address = ?";
				ps = con.prepareStatement(query);
				ps.setString(1, ipAddress);
				rs = ps.executeQuery();
				rs.next();
				String user_name = rs.getString(1);
				if (user_name.equals(userName)) {
					throw new Exception("already connected " + userName);
				} else {
					throw new Exception("invalid IpAddress " + ipAddress);
				}
			}
			found = true;
		} else {
			throw new Exception("Invalid user");
		}
		rs.close();
		ps.close();
		return found;
	}

	@Override
	public String registerMyIp(String userName, String password)
			throws RemoteException {
		String message = null;
		try {
			String ipAddress = getIpAddress();
			if (isUserValid(userName, password, ipAddress)) {
				PreparedStatement ps = getInsertStatement(userName, ipAddress);
				ps.executeUpdate();
				ps.close();
				squid.reconfigure();
				System.out.println("user " + userName + " connected with ip : "
						+ ipAddress);
				message = "registered your IP Address";
				inActiveUsersManager.currentUsers.put(ipAddress,
						System.currentTimeMillis());
			} else {
				System.out.println("invalid user :" + userName + " password : "
						+ password + " ipAddress : " + ipAddress);
			}
		} catch (Exception e) {
			message = e.getMessage();
			System.out.println(message);
		}
		return message;
	}

	@Override
	public void iAmActive() throws RemoteException {
		try {
			String ipAddress = getIpAddress();
			if (inActiveUsersManager.currentUsers.containsKey(ipAddress)) {
				Long lastTime = inActiveUsersManager.currentUsers
						.get(ipAddress);
				System.out.print("ip : " + ipAddress + ", " + lastTime
						+ " ---> ");
				lastTime = inActiveUsersManager.currentUsers.put(ipAddress,
						System.currentTimeMillis());
				lastTime = inActiveUsersManager.currentUsers.get(ipAddress);
				System.out.println(lastTime);
			}
		} catch (Exception e) {
		}
	}

	public String getIpAddress() throws Exception {
		return (java.rmi.server.RemoteServer.getClientHost().toString());
	}

	@Override
	public String disconnect(String userName, String password) {
		String message = null;
		String ipAddress = null;

		try {
			ipAddress = getIpAddress();
			if (isUserValid(userName, password, ipAddress) == true) {
				message = "User " + userName + " is not connected";
				return message;
			}
		} catch (Exception e) {
			message = e.getMessage();
			if (message.equals("already connected " + userName)) {
				try {
					PreparedStatement ps = getRemoveStatement(ipAddress);
					ps.executeUpdate();
					ps.close();
					squid.reconfigure();
					message = "user " + userName + " disconnected with ip : "
							+ ipAddress;
					// inActiveUsersManager.currentUsers.put(ipAddress,
					// System.currentTimeMillis());
					inActiveUsersManager.currentUsers.remove(ipAddress);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			} else {
				message = "invalid user :" + userName + " password : "
						+ password + " ipAddress : " + ipAddress;
			}
			System.out.println(message);
		}
		return message;
	}
}
