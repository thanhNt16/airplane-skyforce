package server;

import java.util.ArrayList;
import java.util.List;

import server.game.GameSetup;
import server.game.Player;

public class Room {
	private int id;
	private GameSetup game;
	private List<Player> players;
	public boolean isRunning;
	
	public Room(int id) {
		super();
		this.id = id;
		game = new GameSetup(id);
		this.players = new ArrayList<Player>();
	}
	
	public int getId() {
		return id;
	}
	
	public void start() {
		reset();
		isRunning = true;
		game.start();
	}
	
	public void stop() {
		isRunning = false;
		game.stop();
	}
	
	public void addPlayer(Player player) {
		players.add(player);
	}
	
	public void removePlayer(String address) {
		for (Player player : players) {
			if (player.getAddress().equals(address)) {
				players.remove(player);
				return;
			}
		}
	}
	
	public List<Player> getPlayers() {
		return players;
	}
	
	public void reset() {
		for (Player player : players) {
			player.setHealth(3);
		}
	}
	
	public void performAction(String address, String action) {
		for (Player player : players) {
			if (player.getAddress().equals(address)) {
				switch (action) {
					case "LEFT":
						player.setLeft(true);
						break;
					case "NOT_LEFT":
						player.setLeft(false);
						break;
					case "RIGHT":
						player.setRight(true);
						break;
					case "NOT_RIGHT":
						player.setRight(false);
						break;
					case "SPACE":
						player.setFire(true);
						break;
					case "NOT_SPACE":
						player.setFire(false);
						break;
				}
				return;
			}
		}
	}
}
