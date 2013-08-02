package server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import net.java.dev.designgridlayout.DesignGridLayout;

public class Server extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1996487337467894050L;
	private static JTextField ipAddress = new JTextField("192.168.1.6");
	private static JTextField portNo = new JTextField("1099", 20);
	private static JTextField userName = new JTextField("root", 20);
	private static JTextField password = new JTextField("003679", 20);
	private static JButton button;
	private static JTextField messageField = new JTextField();

	public Server() {
		this("Squid Control Server");
	}

	private Server(String tittle) {
		super(tittle);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel top = new JPanel();
		DesignGridLayout layout = new DesignGridLayout(top);
		layout.row().grid(new JLabel("Ip address ")).add(ipAddress);
		layout.row().grid(new JLabel("port")).add(portNo);
		layout.row().grid(new JLabel("Database Details"));
		layout.row().grid(new JLabel("user name")).add(userName);
		layout.row().grid(new JLabel("password")).add(password);
		button = new JButton("Start Server");
		button.addActionListener(new MyButtonListener());
		layout.row().grid().add(button);
		messageField.setFocusable(false);
		JScrollPane scrollPane = new JScrollPane(messageField);
		scrollPane
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setWheelScrollingEnabled(true);
		layout.row().grid(new JLabel("Message : ")).add(scrollPane);
		this.add(top);
		this.pack();
		this.setVisible(true);
	}

	public static void start() {
		System.out.println("Hello");
	}

	@SuppressWarnings("static-access")
	public static void main(String... s) {
		Server server = new Server();
		server.start();
	}

	public static void setMessage(String message) {
		messageField.setText(message);
		messageField.setFocusable(false);
	}

	private class MyButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (button.getText().equals("Start Server")) {
				SquidRegister.main(ipAddress.getText(), portNo.getText(),
						userName.getText(), password.getText());
				button.setText("Stop Server");
			} else if (button.getText().equals("Stop Server")) {
				SquidRegister.stopServer(ipAddress.getText(), portNo.getText(),
						userName.getText(), password.getText());
				button.setText("Start Server");
			}
		}
	}
}
