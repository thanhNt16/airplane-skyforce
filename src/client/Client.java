package client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import client.handler.MainHandler;

class Client {
	public static MainHandler handler;
	
	public static void main(String args[]) throws Exception {
        ScreenManager.getInstance().display();
        connect("localhost", 9876);
	}
	
	public static void connect(String host, int port) throws Exception {
		DatagramSocket clientSocket = new DatagramSocket();
		InetAddress IPAddress = InetAddress.getByName(host);
		handler = new MainHandler(clientSocket, IPAddress, port);
		Listener listener = new Listener(clientSocket, handler);
		Thread t = new Thread(listener);
		t.start();
	}
}

class Listener implements Runnable {
	private DatagramSocket clientSocket;
	private boolean terminated = false;
	private MainHandler handler;

	public Listener(DatagramSocket clientSocket, MainHandler handler) {
		this.clientSocket = clientSocket;
		this.handler = handler;
	}

	public void terminate() {
		terminated = true;
	}

	@Override
	public void run() {
		while (!terminated) {
			try {
				byte[] receiveData = new byte[1024];
				DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
				clientSocket.receive(receivePacket);
				String message = new String(receivePacket.getData());
				handler.handleMessage(message);
			} catch (IOException e) {
				System.out.println("IO error");
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}