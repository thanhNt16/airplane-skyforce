package server.communicate.handler;

import server.game.GameSetup;

public class GameHandler {
	private int room;
	private GameSetup game;
	
	public GameHandler(int room) {
		this.room = room;
	}
	
	public void start() {
		game = new GameSetup(room);
		game.start();
	}
	
	public void performAction(String action) {
		switch (action) {
			case "LEFT":
				game.setLeft(true);
				break;
			case "NOT_LEFT":
				game.setLeft(false);
				break;
			case "RIGHT":
				game.setRight(true);
				break;
			case "NOT_RIGHT":
				game.setRight(false);
				break;
			case "SPACE":
				game.setFire(true);
				break;
			case "NOT_SPACE":
				game.setFire(false);
				break;
		}
	}
}
