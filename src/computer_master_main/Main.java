package computer_master_main;

import computer_master_display.Display;
import computer_master_setup.GameSetup;

public class Main {
	public static void main(String[]args) {
		GameSetup game = new GameSetup("Airplane skyforce", 500, 600);
		game.start();
	}
}
