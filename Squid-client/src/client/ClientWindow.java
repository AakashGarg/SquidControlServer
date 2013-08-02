package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.ConnectException;
import java.rmi.NotBoundException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import net.java.dev.designgridlayout.DesignGridLayout;

public class ClientWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9005215521886361863L;
	private static JLabel status = new JLabel("disconnected");
	private final JTextField serverIp = new JTextField("192.168.1.6");
	private final JTextField portNo = new JTextField("1099");
	private final JButton button = new JButton("Connect");
	private final JTextField passwordField = new JTextField("password");
	public static JTextField messageField = new JTextField(20);

	private ClientWindow() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel top = new JPanel();
		DesignGridLayout layout = new DesignGridLayout(top);
		layout.row().grid(new JLabel("Server IP : ")).add(serverIp);
		layout.row().grid(new JLabel("port")).add(portNo);
		layout.row().grid(new JLabel("UserName : " + Client.getUserName()));
		layout.row().grid(new JLabel("password")).add(passwordField);
		button.addActionListener(new MyButtonListener());
		ClientWindow.setStatus("disconnected");
		layout.row().grid(status).add(button);
		JScrollPane scrollPane = new JScrollPane(messageField);
		layout.row().grid().add(scrollPane);
		this.add(top);
		this.pack();
		this.setVisible(true);
	}

	public static void main(String s[]) throws Exception {
		@SuppressWarnings("unused")
		ClientWindow window = new ClientWindow();
	}

	public static void setMessage(String message) {
		messageField.setText("Message : ");
		messageField.setText(message);
		messageField.setFocusable(false);
	}

	public static void setStatus(String statusString) {
		status.setText("Status : " + statusString);
	}

	private class MyButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				Client client = new Client();
				if (button.getText().equals("Connect")) {
					try {
						String message = client.operate(serverIp.getText(),
								portNo.getText(), passwordField.getText(),
								"connect");
						ClientWindow.setMessage(message);
						if (!message.equals("Invalid user")
								&& !message.contains("invalid mac")) {
							button.setText("Disconnect");
						}
					} catch (NotBoundException ex) {
						System.out.println("service unavailable");
						ClientWindow.setMessage("service unavailable");
					} catch (ConnectException ce) {
						System.out.println("service unavailable");
						ClientWindow.setMessage("service unavailable");
					}
				} else if (button.getText().equals("Disconnect")) {
					try {
						ClientWindow.setMessage(client.operate(
								serverIp.getText(), portNo.getText(),
								passwordField.getText(), "disconnect"));
						button.setText("Connect");
					} catch (NotBoundException ex) {
						System.out.println("service unavailable");
						ClientWindow.setMessage("service unavailable");
					} catch (ConnectException ce) {
						System.out.println("service unavailable");
						ClientWindow.setMessage("service unavailable");
					}

				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
}
