package server.communicate.handler;

import server.game.GameSetup;

public class GameHandler {
	private int room;
	
	public GameHandler(int room) {
		this.room = room;
	}
	
	public void start() {
		GameSetup game = new GameSetup("Airplane skyforce", 500, 600, room);
		game.start();
	}
}
