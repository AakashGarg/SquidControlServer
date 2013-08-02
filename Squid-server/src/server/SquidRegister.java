package server;

import java.rmi.Naming;

public class SquidRegister {
	private static java.rmi.registry.Registry reg;

	public static void startServer(String ipAddress, String port,
			String userName, String password) {
		System.out.println("starting server on : " + ipAddress + ":" + port);
		try {
			reg = java.rmi.registry.LocateRegistry.createRegistry(1099);
			Server.setMessage("RMI registry ready.");
			System.out.println("RMI registry ready.");

			server.SquidServer m = new server.SquidServer(userName, password);
			Naming.rebind("rmi://" + ipAddress + ":" + port + "/aakash", m);

			server.MyServer ipGetter = new server.MyServer();
			Naming.rebind("rmi://" + ipAddress + ":" + port + "/getIp",
					ipGetter);

			Server.setMessage("listening on " + ipAddress + ":" + port);
			System.out.println("listening on " + ipAddress + ":" + port);

			System.out.println("Setting up Compiler Service : ");
			server.clientCompiler.CompilerServer compiler = new server.clientCompiler.CompilerServer(
					ipAddress, port);
			Naming.rebind("rmi://" + ipAddress + ":" + port + "/compileAndJar",
					compiler);
			System.out.println("done");

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static void main(String... s) {
		String ipAddress = s[0];
		String port = s[1];
		String userName = s[2];
		String password = s[3];

		startServer(ipAddress, port, userName, password);
	}

	public static void stopServer(String ipAddress, String port,
			String userName, String password) {
		try {
			Server.setMessage("stopping Server");
			System.out.println("stopping server");
			Naming.unbind("rmi://" + ipAddress + ":" + port + "/aakash");
			Naming.unbind("rmi://" + ipAddress + ":" + port + "/getIp");

			java.rmi.server.UnicastRemoteObject.unexportObject(reg, true);

			System.out.println("server stopped at " + ipAddress + ":" + port);
			Server.setMessage("server stopped at " + ipAddress + ":" + port);
		} catch (Exception ex) {
			Server.setMessage("exception");
			ex.printStackTrace();
		}
	}
}
