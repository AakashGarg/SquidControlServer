package client;

import server.SquidRemote;

public class BackActiveThread extends Thread {

	private server.SquidRemote m;

	public BackActiveThread(SquidRemote m) {
		this.m = m;
	}

	@Override
	public void run() {
		try {
			while (m != null) {
				m.iAmActive();
				Thread.sleep(5 * 1000);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setSquidRemote(SquidRemote m) {
		this.m = m;
	}
}
