package server.game;

import server.Server;
import static common.Constants.GAME_HEIGHT;
import static common.Constants.GAME_WIDTH;

public class Player {
	private int x;
	private int y;
	private int health;
	private int score;
	private boolean left;
	private boolean right;
	private boolean up;
	private boolean down;

	private boolean fire;
	
	private long current;
	private long delay;
	
	private String name;
	private String address;
	
	private GameManager game;
	
	public boolean isUp() {
		return up;
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public boolean isDown() {
		return down;
	}

	public void setDown(boolean down) {
		this.down = down;
	}
	
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
				if (x >= 50) {
					x -= 4;	
				}

			}
			if (right) {
				if (x <= GAME_WIDTH - 50) {
					x += 4;	
				}
			}
			if (up) {
				if (y >= 60) {
					y -= 4;	
				}

			}
			if (down) {
				if (y <= GAME_HEIGHT + 20) {
					y += 4;	
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
	public void broadcast(int room, int index) {
		if (health > 0) {
			String message = "PLAYER__" + x + "__" + y + "__" + address + "__" + name + "__" + index + "__";
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