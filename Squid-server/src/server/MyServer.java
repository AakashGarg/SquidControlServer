package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class MyServer extends UnicastRemoteObject implements MyRemote {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6330350491079796645L;

	public MyServer() throws RemoteException {
	}

	@Override
	public String getIpAddress() throws RemoteException {
		String ipAddress = null;
		try {
			ipAddress = java.rmi.server.RemoteServer.getClientHost();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ipAddress;
	}
}
