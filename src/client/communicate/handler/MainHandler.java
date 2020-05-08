package client.communicate.handler;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


public class MainHandler {
	private MessageSender sender;
    
    public MainHandler(DatagramSocket clientSocket, InetAddress address, int port) {
        this.sender = new MessageSender(clientSocket, address, port);
    }
    
    public void sendMessage(String message) {
    	sender.send(message);
    }
    
    public void handleMessage(String message) {
    	System.out.println("Received from server: " + message);
    }
}


class MessageSender {
	private DatagramSocket clientSocket;
	private InetAddress address;
    private int port;
    
    MessageSender(DatagramSocket clientSocket, InetAddress address, int port) {
    	this.clientSocket = clientSocket;
        this.address = address;
        this.port = port;
    }
	
	public void send(String message) {
        try {
        	byte[] sendData = new byte[1024];
    		sendData = message.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, address, port);
        	clientSocket.send(sendPacket);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}