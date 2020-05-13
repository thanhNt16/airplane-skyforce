package server.communicate.handler;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import server.communicate.Server;

public class ClientHandler {
	private DatagramSocket serverSocket;
	private InetAddress address;
    private int port;
	private String client;
	private int roomId;
	private boolean isAdmin = false;
    
    public ClientHandler(DatagramSocket serverSocket, String client, InetAddress address, int port) {
        this.client = client;
        this.serverSocket = serverSocket;
        this.address = address;
        this.port = port;
    }
    
    public void sendMessage(String message) {
    	try {
        	byte[] sendData = new byte[1024];
    		sendData = message.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, address, port);
			serverSocket.send(sendPacket);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public void handleMessage(String message) {
    	try {
    		String[] payload = message.split("__");
        	String command = payload[0];
        	String name;

        	switch (command) {
    	    	case "GET_ROOM":
    				sendMessage(Server.getRoomList());
    				break;
    	    	case "CURRENT_ROOM":
    				sendMessage("" + roomId);
    				break;
        		case "CREATE_ROOM":
        			name = payload[1];
        			roomId = Server.createRoom(client, name);
        			isAdmin = true;
        			break;
        		case "JOIN_ROOM":
        			int id = Integer.parseInt(payload[1]);
        			roomId = id;
        			name = payload[1];
        			Server.joinRoom(client, name, roomId);
        			break;
        		case "LEAVE_ROOM":
        			if (roomId != 0) {
        				Server.leaveRoom(client, roomId);
        				roomId = 0;
        			}
        			break;
        		case "BROADCAST":
        			Server.broadcastToRoom(roomId, payload[1]);
        			break;
        		case "START_GAME":
        			if (roomId != 0 && isAdmin) {
        				Server.broadcastToRoom(roomId, "START_GAME__");
        				Server.getRoom(roomId).start();
        			}
        			break;
        		case "GAME":
        			String action = payload[1];
        			Server.getRoom(roomId).performAction(client, action);
        			break;
        	}
    	} catch (Exception e) {
    		System.out.println(message);
    		e.printStackTrace();
    	}
    }
}
