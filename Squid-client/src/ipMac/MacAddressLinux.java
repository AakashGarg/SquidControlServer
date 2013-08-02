package ipMac;

import java.net.InetAddress;
import java.net.NetworkInterface;

public class MacAddressLinux {

	public static void main(String[] args) {
		try { // System.out.println("mac Address : "+getHardwareAddressString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static byte[] getHardwareAddress(String ipAddress) throws Exception {

		InetAddress ip = InetAddress.getByName(ipAddress);
		NetworkInterface network = NetworkInterface.getByInetAddress(ip);
		byte[] mac = network.getHardwareAddress();
		return mac;
	}

	public static String getHardwareAddressString(String ipAddress)
			throws Exception {
		byte[] mac = getHardwareAddress(ipAddress);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < mac.length; i++) {
			sb.append(String.format("%02X%s", mac[i],
					(i < mac.length - 1) ? "-" : ""));
		}
		return (sb.toString());
	}
}
