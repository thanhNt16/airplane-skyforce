package client;



//import skyforce.client.ui.homescreen.HomeScreen;
//import skyforce.client.ui.waitingroomscreen.WaitingRoomScreen;

import javax.swing.*;

import static common.Constants.*;


public class ScreenManager {
  private static ScreenManager instance;
  private JFrame window;

  private ScreenManager() {
      window = new JFrame("Skyforce");
      window.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
      window.setLocationRelativeTo(null);
      window.setResizable(false);
      window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      window.setVisible(false);

      navigate(HOME_SCREEN);
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
          case HOME_SCREEN:
              window.getContentPane().add( new HomeScreen(SCREEN_WIDTH, SCREEN_HEIGHT));
              break;
          case INGAME_SCREEN:
        	  window.getContentPane().add(getIngameScreen());
//          case WAITING_ROOM_SCREEN:
//              window.getContentPane().add( new WaitingRoomScreen(SCREEN_WIDTH, SCREEN_HEIGHT));
//              break;
      }

      window.revalidate();
      window.repaint();
  }
  public JFrame getWindow() {
      return this.window;
  }
  private synchronized JPanel getIngameScreen() {
      return new IngameScreen(IN_GAME_SCREEN_WIDTH, IN_GAME_SCREEN_HEIGHT);
  }
}
