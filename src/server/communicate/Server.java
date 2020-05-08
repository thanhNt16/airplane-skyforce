package server.communicate;

import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import server.communicate.handler.ClientHandler;


public class Server {
	public static final int PORT = 9876;
	private static int roomId = 1;
	private static Hashtable<String, ClientHandler> clients = new Hashtable<String, ClientHandler>();
	private static Hashtable<Integer, ArrayList<String>> rooms = new Hashtable<Integer, ArrayList<String>>();
	
    public static void main(String args[]) throws Exception {
        DatagramSocket serverSocket = new DatagramSocket(PORT);
        
        while (true) {
        	byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            serverSocket.receive(receivePacket);
            String message = new String(receivePacket.getData());
            InetAddress address = receivePacket.getAddress();
            int port = receivePacket.getPort();
            String client = address + ":" + port;
            if (!clients.containsKey(client)) {
            	clients.put(client, new ClientHandler(serverSocket, client, address, port));
            }
            clients.get(client).handleMessage(message);
        }
    }
    
    public static String getRoomList() {
    	String roomList = "";
    	Enumeration<Integer> e = rooms.keys();
    	while (e.hasMoreElements()) {
    		roomList += e.nextElement() + ",";
    	}
    	return roomList;
    }
    
    public static int createRoom(String client) {
    	ArrayList<String> room = new ArrayList<String>();
    	room.add(client);
    	rooms.put(roomId, room);
    	return roomId++;
    }
    
    public static void joinRoom(String client, int room) {
    	if (rooms.containsKey(room)) {
    		rooms.get(room).add(client);
    	}
    }
    
    public static void leaveRoom(String client, int room) {
    	if (rooms.containsKey(room)) {
    		rooms.get(room).remove(client);
    	}
    }
    
    public static void disconnectClient(String client) {
    	clients.remove(client);
    }
    
    public static void broadcastToRoom(int room, String message) {
    	if (rooms.containsKey(room)) {
    		for (String client : rooms.get(room)) {
        		if (clients.containsKey(client)) {
        			clients.get(client).sendMessage(message);
                }
        	}
    	}
    }
}