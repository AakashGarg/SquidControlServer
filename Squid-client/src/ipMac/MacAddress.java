package ipMac;

import java.net.InetAddress;
import java.net.NetworkInterface;

public class MacAddress {

	public static void main(String[] args) {
		try {
			System.out.println("Current IP address : "
					+ IPAddress.getIpAddressString());
			System.out.println("mac Address : " + getHardwareAddressString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static byte[] getHardwareAddress() throws Exception {
		InetAddress ip = IPAddress.getIpAddress();
		NetworkInterface network = NetworkInterface.getByInetAddress(ip);
		byte[] mac = network.getHardwareAddress();
		return mac;
	}

	public static String getHardwareAddressString() throws Exception {
		byte[] mac = getHardwareAddress();
		System.out.println(mac);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < mac.length; i++) {
			sb.append(String.format("%02X%s", mac[i],
					(i < mac.length - 1) ? "-" : ""));
		}
		return (sb.toString());
	}
}
