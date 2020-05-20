package client;

//import skyforce.client.ui.homescreen.HomeScreen;
//import skyforce.client.ui.waitingroomscreen.WaitingRoomScreen;

import javax.swing.*;

import static common.Constants.*;

public class ScreenManager {
	private static ScreenManager instance;
	private JFrame window;
	private PrepareScreen prepareScreen;
	private HomeScreen homeScreen;
	private RoomScreen roomScreen;
	private IngameScreen ingameScreen;

	private ScreenManager() {
		window = new JFrame("Skyforce");
		window.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		window.setLocationRelativeTo(null);
		window.setResizable(false);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(false);

		navigate(PREPARE_SCREEN);
	}

	public void display() {
		window.setVisible(true);
	}

	public static ScreenManager getInstance() {
		if (instance == null) {
			synchronized (ScreenManager.class) {
				if (null == instance) {
					instance = new ScreenManager();
				}
			}
		}
		return instance;
	}

	public void navigate(String screenName) {
		window.getContentPane().removeAll();

		switch (screenName) {
			case PREPARE_SCREEN:
				if (prepareScreen == null) {
					prepareScreen = new PrepareScreen(SCREEN_WIDTH, SCREEN_HEIGHT);
				}
				window.getContentPane().add(prepareScreen);
				break;
			case HOME_SCREEN:
				if (homeScreen == null) {
					homeScreen = new HomeScreen(SCREEN_WIDTH, SCREEN_HEIGHT);
				}
				window.getContentPane().add(homeScreen);
				break;
			case ROOM_SCREEN:
				if (roomScreen == null) {
					roomScreen = new RoomScreen(SCREEN_WIDTH, SCREEN_HEIGHT);
				}
				window.getContentPane().add(roomScreen);
				break;
			case INGAME_SCREEN:
				if (ingameScreen == null) {
					ingameScreen = new IngameScreen(IN_GAME_SCREEN_WIDTH, IN_GAME_SCREEN_HEIGHT);
				}
				window.getContentPane().add(ingameScreen);
				ingameScreen.renderStartBtn();
		}

		window.revalidate();
		window.repaint();
	}

	public JFrame getWindow() {
		return this.window;
	}
}
