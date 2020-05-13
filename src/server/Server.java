package server;

import java.net.*;
import java.util.Enumeration;
import java.util.Hashtable;

import server.handler.ClientHandler;
import server.game.Player;


public class Server {
	public static final int PORT = 9876;
	private static int roomId = 1;
	private static Hashtable<String, ClientHandler> clients = new Hashtable<String, ClientHandler>();
	private static Hashtable<Integer, Room> rooms = new Hashtable<Integer, Room>();
	
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
    
    public static Room getRoom(int id) {
    	return rooms.get(id);
    }
    
    public static int createRoom(String address, String name) {
    	Room room = new Room(roomId);
    	Player player = new Player(address, name);
    	room.addPlayer(player);
    	rooms.put(roomId, room);
    	return roomId++;
    }
    
    public static int joinRoom(String address, String name, int room) {
    	if (rooms.containsKey(room)) {
    		Player player = new Player(address, name);
    		rooms.get(room).addPlayer(player);
    		return room;
    	}
    	return 0;
    }
    
    public static void leaveRoom(String address, int room) {
    	if (rooms.containsKey(room)) {
    		rooms.get(room).removePlayer(address);
    	}
    }
    
    public static void disconnectClient(String client) {
    	clients.remove(client);
    }
    
    public static void broadcastToRoom(int room, String message) {
    	if (rooms.containsKey(room)) {
    		for (Player player : rooms.get(room).getPlayers()) {
    			String address = player.getAddress();
        		if (clients.containsKey(address)) {
        			clients.get(address).sendMessage(message);
                }
        	}
    	}
    }
}