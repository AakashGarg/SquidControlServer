package server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface SquidRemote extends Remote {

	public String registerMyIp(String userName, String password)
			throws RemoteException;

	public void iAmActive() throws RemoteException;

	public String disconnect(String userName, String password)
			throws RemoteException;
}
