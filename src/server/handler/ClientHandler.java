package server.handler;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import server.Server;

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
        		// GET_ROOM__
    	    	case "GET_ROOM":
    				sendMessage("GET_ROOM_RESPONSE__" + Server.getRoomList() + "__");
    				break;
				// CURRENT_ROOM__
    	    	case "CURRENT_ROOM":
    				sendMessage("CURRENT_ROOM_RESPONSE__" + roomId + "__");
    				break;
    			// CREAT_ROOM__duc__
        		case "CREATE_ROOM":
        			name = payload[1];
        			roomId = Server.createRoom(client, name);
        			isAdmin = true;
        			sendMessage("CURRENT_ROOM_RESPONSE__" + roomId + "__");
        			break;
    			// JOIN_ROOM__1__duc__
        		case "JOIN_ROOM":
        			int id = Integer.parseInt(payload[1]);
        			name = payload[2];
        			roomId = Server.joinRoom(client, name, id);
        			sendMessage("CURRENT_ROOM_RESPONSE__" + roomId + "__");
        			break;
        		// LEAVE_ROOM__
        		case "LEAVE_ROOM":
        			if (roomId != 0) {
        				Server.leaveRoom(client, roomId);
        				roomId = 0;
        			}
        			break;
        		// GET_PLAYERS__
        		case "GET_PLAYERS":
        			sendMessage("GET_PLAYERS_RESPONSE__" + Server.getPlayers(roomId) + "__");
        			break;
    			// BROADCAST__abc__
        		case "BROADCAST":
        			Server.broadcastToRoom(roomId, payload[1]);
        			break;
        		// START_GAME__
        		case "START_GAME":
        			if (roomId != 0 && isAdmin) {
        				Server.broadcastToRoom(roomId, "START_GAME__");
        				Server.getRoom(roomId).start();
        			}
        			break;
        		// GAME__*
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
