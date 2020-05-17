package client;

import java.util.ArrayList;

public class AppState {
	private static String name;
	private static int room;
	private static ArrayList<Integer> roomList = new ArrayList<Integer>();
	private static ArrayList<String> playerList = new ArrayList<String>();

	public static ArrayList<String> getPlayerList() {
		return playerList;
	}

	public static void setPlayerList(ArrayList<String> playerList) {
		AppState.playerList = playerList;
	}

	public static ArrayList<Integer> getRoomList() {
		return roomList;
	}

	public static void setRoomList(ArrayList<Integer> roomList) {
		AppState.roomList = roomList;
	}

	public static String getName() {
		return name;
	}

	public static void setName(String name) {
		AppState.name = name;
	}
	
	public static int getRoom() {
		return room;
	}

	public static void setRoom(int room) {
		AppState.room = room;
	}
}
