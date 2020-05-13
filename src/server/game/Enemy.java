package server.game;

import server.Server;

public class Enemy {
	private int x;
	private int y;
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setX(int x) {
		this.x = x;
	}
	public Enemy(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public void tick() {
		y+=1;
	}
	public void broadcast(int room) {
		String message = "ENEMY__" + x + "__" + y + "__";
		Server.broadcastToRoom(room, message);
	}
}
