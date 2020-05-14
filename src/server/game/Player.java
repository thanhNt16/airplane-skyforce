package server.game;

import server.Server;

public class Player {
	private int x;
	private int y;
	private int health;
	private int score;
	private boolean left;
	private boolean right;
	private boolean fire;
	
	private long current;
	private long delay;
	
	private String name;

	private String address;
	
	private GameManager game;
	
	public GameManager getGame() {
		return game;
	}

	public void setGame(GameManager game) {
		this.game = game;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public String getAddress() {
		return address;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public void setFire(boolean fire) {
		this.fire = fire;
	}
	
	public Player(String address, String name) {
		 this.name = name;
		 this.address = address;
		 this.health = 2;
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

	public void init(GameManager game) {
		current = System.nanoTime();
		delay = 100;
		health = 3;
		this.game = game;
	}
	public void tick() {
		if (!(health <= 0)) {
			if (left) {
				if (x >= 60) {
					x -= 4;	
				}

			}
			if (right) {
				if (x <= 450 - 40) {
					x += 4;	
				}
			}
			if (fire) {
				long breaks = (System.nanoTime() - current) / 1000000;
				if (breaks > delay) {
					game.addBullet(new Bullet(x + 11, y + 12, address));	
					current = System.nanoTime();
				}
				
			}
		}
	}
	public void broadcast(int room) {
		if (health > 0) {
			String message = "PLAYER__" + x + "__" + y + "__" + address + "__" + name + "__";
			Server.broadcastToRoom(room, message);
		}
	}
	
	public void broadcastScore(int room) {
		String message = "SCORE__" + score + "__" + address + "__" + name + "__";
		Server.broadcastToRoom(room, message);
	}
	
	public void broadcastHealth(int room) {
		String message = "HEALTH__" + health + "__" + address + "__" + name + "__";
		Server.broadcastToRoom(room, message);
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
}