package client.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.List;

import client.communicate.handler.MainHandler;


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
	private List<PlayerInfo> players = new ArrayList<PlayerInfo>();
	
	public GameSetup(String title, int width, int height) {
		this.title = title;
		this.width = width;
		this.height = height;
	}
	
	public void init(MainHandler handler) {
		display = new Display(title, width, height);
		LoadImage.init();
		
		Display.frame.setFocusable(true);
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
			int x;
			int y;
			boolean added;
			switch (payload[0]) {
				// BULLET__10__20__	
		    	case "BULLET":
		    		x = Integer.parseInt(payload[1]);
		    		y = Integer.parseInt(payload[2]);
		    		g.setColor(Color.red);
		    		g.fillRect(x, y, 6, 10);
					break;
				// ENEMY__10__20__
		    	case "ENEMY":
		    		x = Integer.parseInt(payload[1]);
		    		y = Integer.parseInt(payload[2]);
		    		g.setColor(Color.black);
		    		g.drawImage(LoadImage.enemy, x, y, 25, 25, null);
					break;
				// PLAYER__10__20__localhost:8000__duc__
		    	case "PLAYER":
		    		x = Integer.parseInt(payload[1]);
		    		y = Integer.parseInt(payload[2]);
		    		g.setColor(Color.red);
		    		g.drawImage(LoadImage.player, x, y, 25, 25, null);
		    		g.setColor(Color.blue);
					g.setFont(new Font("arial", Font.BOLD, 8));
					g.drawString(payload[4], x, y);
					break;
				// SCORE__10__localhost:8000__duc__
		    	case "SCORE":
		    		added = false;
		    		int score = Integer.parseInt(payload[1]);
		    		for (PlayerInfo p : players) {
		    			if (p.getAddress().equals(payload[2])) {
		    				p.setScore(score);
		    				added = true;
		    				break;
		    			}
		    		}
		    		if (!added) {
		    			PlayerInfo p = new PlayerInfo(payload[2], payload[3]);
		    			p.setScore(score);
		    			players.add(p);
		    		}
		    		break;
	    		// HEALTH__10__localhost:8000__
		    	case "HEALTH":
		    		added = false;
		    		int health = Integer.parseInt(payload[1]);
		    		for (PlayerInfo p : players) {
		    			if (p.getAddress().equals(payload[2])) {
		    				p.setHealth(health);
		    				added = true;
		    				break;
		    			}
		    		}
		    		if (!added) {
		    			PlayerInfo p = new PlayerInfo(payload[2], payload[3]);
		    			p.setHealth(health);
		    			players.add(p);
		    		}
		    		System.out.println(health);
		    		break;
			}
		}
		int startY = 470;
		for (PlayerInfo p : players) {
			g.setColor(Color.blue);
    		g.setFont(new Font("arial", Font.BOLD, 12));
    		g.drawString(p.getName() + "- score: " + p.getScore() + ", health: " + p.getHealth(), 70, startY);
    		startY += 20;
		}
		buffer.show();
		g.dispose();
		queue.clear();
	}
}

class PlayerInfo {
	private String address;
	private String name;
	private int score;
	private int health;
	
	public PlayerInfo(String address, String name) {
		this.address = address;
		this.name = name;
		this.score = 0;
		this.health = 3;
	}
	
	public String getAddress() {
		return address;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getName() {
		return name;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}
}