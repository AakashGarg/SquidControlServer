package ipMac;

import java.net.InetAddress;

public class IPAddress {

	public static void main(String[] args) {
		try {
			System.out.println("Current IP address : "
					+ IPAddress.getIpAddressString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String getIpAddressString() throws Exception {
		InetAddress ip = getIpAddress();
		String ipAddress = ip.getHostAddress();
		return ipAddress;
	}

	public static InetAddress getIpAddress() throws Exception {
		InetAddress ip = InetAddress.getLocalHost();
		return ip;
	}
}
