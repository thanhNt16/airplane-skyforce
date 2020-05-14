package client;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PrepareScreen extends JPanel  {
	private JTextField host, port;
	private JButton connectBtn;
	
	public PrepareScreen() {
		host = new JTextField("Host address: ");
		port = new JTextField("Port address: ");
		connectBtn = new JButton("Connect");
		
		add(host);
		add(port);
		add(connectBtn);
	}
}
