package server.game;

import server.communicate.Server;

public class Player {
	private int x;
	private int y;
	private boolean left;
	private boolean right;
	private boolean fire;
	
	public void setLeft(boolean left) {
		this.left = left;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public void setFire(boolean fire) {
		this.fire = fire;
	}
	private long current;
	private long delay;
	private int health;
	
	public Player(int x, int y) {
		 this.x = x;
		 this.y = y;
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public void init() {
		current = System.nanoTime();
		delay = 100;
		health = 3;
	}
	public void tick() {
		if (!(health <= 0)) {
			if (left) {
				if (x>=60) {
					x-=4;	
				}

			}
			if (right) {
				if (x<= 450-40) {
					x+=4;	
				}
			}
			if (fire) {
				long breaks = (System.nanoTime() - current)/1000000;
				if (breaks > delay) {
					GameManager.bullet.add(new Bullet(x+11, y+12));	
					current = System.nanoTime();
				}
				
			}
		}
	}
	public void broadcast(int room) {
		String message = "PLAYER__" + x + "__" + y + "__";
		Server.broadcastToRoom(room, message);
	}
}