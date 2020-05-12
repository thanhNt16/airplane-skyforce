package client.communicate;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingUtilities;

import client.communicate.handler.MainHandler;

public class Start extends JFrame {
	private static JTextField address;
	private static JTextField username;
	JLabel label;

	public Start() {
		setSize(1024, 768);
		SpringLayout springLayout = new SpringLayout();
		getContentPane().setLayout(springLayout);

		username = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, username, 301, SpringLayout.NORTH, getContentPane());
		getContentPane().add(username);
		username.setColumns(10);

		JLabel lblngNhp = new JLabel("Skywar airforce");
		springLayout.putConstraint(SpringLayout.SOUTH, lblngNhp, -58, SpringLayout.NORTH, username);
		springLayout.putConstraint(SpringLayout.WEST, username, 0, SpringLayout.WEST, lblngNhp);
		lblngNhp.setFont(new Font("Tahoma", Font.BOLD, 25));
		springLayout.putConstraint(SpringLayout.WEST, lblngNhp, 51, SpringLayout.WEST, getContentPane());
		getContentPane().add(lblngNhp);

		// Label username
		JLabel lblNewLabel = new JLabel("Username");
		springLayout.putConstraint(SpringLayout.WEST, lblNewLabel, 0, SpringLayout.WEST, username);
		springLayout.putConstraint(SpringLayout.SOUTH, lblNewLabel, -6, SpringLayout.NORTH, username);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Address Connection");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		springLayout.putConstraint(SpringLayout.NORTH, lblNewLabel_1, 21, SpringLayout.SOUTH, username);
		springLayout.putConstraint(SpringLayout.WEST, lblNewLabel_1, 0, SpringLayout.WEST, username);
		getContentPane().add(lblNewLabel_1);
		
		
		address = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, address, 6, SpringLayout.SOUTH, lblNewLabel_1);
		springLayout.putConstraint(SpringLayout.EAST, address, 0, SpringLayout.EAST, username);
		getContentPane().add(address);
		address.setColumns(10);
		
		JButton btnLogin = new JButton("Start Game");
		springLayout.putConstraint(SpringLayout.NORTH, btnLogin, 31, SpringLayout.SOUTH, address);
		springLayout.putConstraint(SpringLayout.WEST, btnLogin, 0, SpringLayout.WEST, username);
		getContentPane().add(btnLogin);
	
		btnLogin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
				String name = username.getText();
				String IP = address.getText();
				System.out.println(name + " " + IP);
				
					
				BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
				DatagramSocket clientSocket;
				
					clientSocket = new DatagramSocket();
				
				InetAddress IPAddress = InetAddress.getByName("localhost");
				MainHandler handler = new MainHandler(clientSocket, IPAddress, 9876);
				Listener listener = new Listener(clientSocket, handler);
				Thread t = new Thread(listener);
				t.start();
				String message = "";
				while (!message.equals("END")) {
					message = inFromUser.readLine();
					handler.sendMessage(message);
				}
				listener.terminate();
				clientSocket.close();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

	}
	
}
