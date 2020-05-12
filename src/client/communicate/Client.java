package client.communicate;

import java.io.*;
import java.net.*;

import client.communicate.handler.MainHandler;

class Client {
	public static void main(String args[]) throws Exception {
		Start frame = new Start();
		frame.setVisible(true);

//		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
//		DatagramSocket clientSocket = new DatagramSocket();
//		InetAddress IPAddress = InetAddress.getByName("localhost");
//		MainHandler handler = new MainHandler(clientSocket, IPAddress, 9876);
//		Listener listener = new Listener(clientSocket, handler);
//		Thread t = new Thread(listener);
//		t.start();
//		String message = "";
//		while (!message.equals("END")) {
//			message = inFromUser.readLine();
//			handler.sendMessage(message);
//		}
//		listener.terminate();
//		clientSocket.close();
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
				// TODO Auto-generated catch block
				System.out.println("IO error");
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
//				System.out.println("err");
			}
		}
	}
}