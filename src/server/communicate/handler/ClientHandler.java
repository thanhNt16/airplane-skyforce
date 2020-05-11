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
	private int room;
	private GameHandler game;
    
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void handleMessage(String message) {
    	String[] payload = message.split("__");
    	String command = payload[0];

    	switch (command) {
	    	case "GET_ROOM":
				sendMessage(Server.getRoomList());
				break;
	    	case "CURRENT_ROOM":
				sendMessage("" + room);
				break;
    		case "CREATE_ROOM":
    			room = Server.createRoom(client);
    			break;
    		case "JOIN_ROOM":
    			int roomId = Integer.parseInt(payload[1]);
    			room = roomId;
    			Server.joinRoom(client, roomId);
    			break;
    		case "LEAVE_ROOM":
    			if (room != 0) {
    				Server.leaveRoom(client, room);
    				room = 0;
    			}
    			break;
    		case "BROADCAST":
    			Server.broadcastToRoom(room, payload[1]);
    			break;
    		case "START_GAME":
    			if (room != 0) {
    				Server.broadcastToRoom(room, "START_GAME__");
    				game = new GameHandler(room);
    				game.start();
    			}
    			break;
    		case "GAME":
    			String action = payload[1];
    			game.performAction(action);
    			break;
    	}
    }
}
