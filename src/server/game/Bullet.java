package server.game;

import server.communicate.Server;

public class Bullet {
	private int x;
	private int y;
	private int speed;
	private String player;
	
	public Bullet(int x, int y, String player) {
		this.x = x;
		this.y = y;
		this.player = player;
		speed = 10;
	}
	
	public int getY() {
		return y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void tick() {
		y -= speed;
	}
	
	public void broadcast(int room) {
		String message = "BULLET__" + x + "__" + y + "__";
		Server.broadcastToRoom(room, message);
	}

	public String getPlayer() {
		return player;
	}	
}

