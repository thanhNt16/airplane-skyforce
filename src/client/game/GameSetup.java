package client.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.List;

import client.communicate.handler.MainHandler;
import hust.ict.graphics.LoadImage;


public class GameSetup {
	private String title;
	private int width;
	private int height;
	private BufferStrategy buffer;
	private Graphics g;
	
	private Display display;
	public static final int gameWidth = 400;
	public static final int gameHeight = 400;
	
	private List<String> queue = new ArrayList<String>();
	
	public GameSetup(String title, int width, int height) {
		this.title = title;
		this.width = width;
		this.height = height;
	}
	
	public void init(MainHandler handler) {
		display = new Display(title, width, height);
		LoadImage.init();
		Display.frame.addKeyListener(handler);
		if (buffer == null) {
			display.getCanvas().createBufferStrategy(3);
			buffer = display.getCanvas().getBufferStrategy();
		}
	}
	
	public void pushToQueue(String message) {
		queue.add(message);
	}
	
	public void flushQueue() {
		g = buffer.getDrawGraphics();
		g.clearRect(0, 0, width, height);
		g.drawImage(LoadImage.image, 50, 50, gameWidth, gameHeight, null);	
		for (String message : queue) {
			String[] payload = message.split("__");
			int x = Integer.parseInt(payload[1]);
    		int y = Integer.parseInt(payload[2]);
			switch (payload[0]) {
		    	case "BULLET":
		    		g.setColor(Color.red);
		    		g.fillRect(x, y, 6, 10);
					break;
		    	case "ENEMY":
		    		g.setColor(Color.black);
		    		g.drawImage(LoadImage.enemy, x, y, 25, 25, null);
					break;
		    	case "PLAYER":
		    		g.setColor(Color.red);
		    		g.drawImage(LoadImage.player, x, y, 25, 25, null);
					break;
			}
		}
		buffer.show();
		g.dispose();
		queue.clear();
	}
}