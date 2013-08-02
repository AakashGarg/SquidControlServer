/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import ipMac.GetOs;
import ipMac.IPAddress;
import ipMac.MacAddressLinux;

import java.rmi.Naming;
import java.rmi.NotBoundException;

import server.SquidRemote;

/**
 * 
 * @author Aakash
 */
public class Client {

	private static String macAddress = "78-DD-08-C9-94-3A";
	private static String userName = "aakashgarg";
	private static SquidRemote m;
	private static BackActiveThread thread;
	private String mac;

	public String operate(String serverIp, String port, String password,
			String operation) throws NotBoundException, Exception {
		String message = null;
		String osName = GetOs.getOSName();
		String ipAddress = null;
		if (osName.equals("Linux")) {
			server.MyRemote ipGetter = (server.MyRemote) Naming.lookup("rmi://"
					+ serverIp + ":" + port + "/getIp");
			ipAddress = ipGetter.getIpAddress();
		} else {
			ipAddress = IPAddress.getIpAddressString();
		}
		ClientWindow.setMessage("your ipAddress : " + ipAddress);
		System.out.println("your ipAddress : " + ipAddress);
		mac = MacAddressLinux.getHardwareAddressString(ipAddress).toUpperCase();
		ClientWindow.setMessage("mac address : " + mac);
		if (mac.equals(macAddress)) {
			if (operation.equals("connect")) {
				System.out.println("connecting to server");
				m = (server.SquidRemote) Naming.lookup("rmi://" + serverIp
						+ ":" + port + "/aakash");
				message = m.registerMyIp(userName, password);
				thread = new BackActiveThread(m);
				thread.start();
				System.out.println(message);
				if (message.equals("registered your IP Address")) {
					ClientWindow.setStatus("connected");
				}
			} else {
				System.out.println("disconnecting");
				message = m.disconnect(userName, password);
				m = null;
				thread.setSquidRemote(m);
				ClientWindow.setStatus("disconnected");
				ClientWindow.setMessage(message);
			}
		} else {
			System.out.println("invalid mac : " + mac);
			ClientWindow.setMessage("invalid mac : " + mac);
		}
		return message;
	}

	public static String getUserName() {
		return userName;
	}
}
