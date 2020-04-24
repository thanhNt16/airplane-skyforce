package hust.ict.main;

import hust.ict.setup.GameSetup;

public class Main {
	public static void main(String[]args) {
		GameSetup game = new GameSetup("Airplane skyforce", 500, 600);
		game.start();
	}
}
