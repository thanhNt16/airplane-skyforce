package server.game;

import java.util.ArrayList;
import java.util.Random;

import static common.Constants.GAME_HEIGHT;
import static common.Constants.GAME_WIDTH;

import server.Server;


public class GameManager {
	private ArrayList<Bullet> bullets;
	private ArrayList<Enemy> enemies;
	
	private long delay;
	private long current;
	
	private int roomId;
	
	public GameManager(int roomId) {
		this.roomId = roomId;
	}
	
	public void addBullet(Bullet bullet) {
		bullets.add(bullet);
	}
	
	public void init() {
		for (Player player : Server.getRoom(roomId).getPlayers()) {
			player.setX((GAME_WIDTH / 2) + 50);
			player.setY((GAME_HEIGHT - 30) + 50);
			player.init(this);
		}
		
		bullets = new ArrayList<Bullet>();
		enemies = new ArrayList<Enemy>();
		current = System.nanoTime();
		delay = 800;
	}
	
	public void tick() {
		for (Player player : Server.getRoom(roomId).getPlayers()) {
			if (player.getGame() == null) {
				player.setX((GAME_WIDTH / 2) + 50);
				player.setY((GAME_HEIGHT - 30) + 50);
				player.init(this);
			}
			player.tick();
		}
		
		for (Bullet bu : bullets) {
            bu.tick();
        }

		long breaks = (System.nanoTime() - current) / 1000000;
		if (breaks > delay) {
			for (int i = 0 ; i < 2; i++) {
				Random rand = new Random();
				int randX = rand.nextInt(GAME_WIDTH);
				int randY = rand.nextInt(GAME_WIDTH);
				int randSpeed = rand.nextInt(9) + 1;
				enemies.add(new Enemy(randX, -randY, randSpeed));
			}
			current = System.nanoTime();
		}
		
		// enemies
		for (int i = 0; i < enemies.size(); i++) {
			enemies.get(i).tick();
		}
	}
	
	public void broadcast() {
		boolean ended = true;
		for (Player player : Server.getRoom(roomId).getPlayers()) {	
			if (player.getHealth() > 0) {
				player.broadcast(roomId);
				ended = false;
			}
		}
		
		if (ended) {
			Server.broadcastToRoom(roomId, "END_GAME__");
			Server.getRoom(roomId).stop();
			return;
		}
		
		for (int i = 0; i<bullets.size(); i++) {
			bullets.get(i).broadcast(roomId);
		}
		for (int i = 0 ; i < bullets.size(); i++) {
			if (bullets.get(i).getY() <= 50) {
				bullets.remove(i);
				i--;
			}
		}
		
		// enemies
		for (int i = 0; i < enemies.size(); i++) {
			if (!(enemies.get(i).getX() <= 50  || enemies.get(i).getX() >= 450 - 30
					|| enemies.get(i).getY() >=450 - 30)) {
				if (enemies.get(i).getY() >= 50) {
					enemies.get(i).broadcast(roomId);	
				}
			}
			
		}
		for (int i = 0; i < enemies.size(); i++) {
			int ex = enemies.get(i).getX();
			int ey = enemies.get(i).getY();
			
			// Check player hit
			for (Player player : Server.getRoom(roomId).getPlayers()) {
				if (player.getHealth() > 0) {
					int px = player.getX();
					int py = player.getY();
					if (px <= ex + 25 && px + 25 >= ex && 
							py <= ey + 25 && py + 25 >= ey) {
						enemies.remove(i);
						i--;
						player.setHealth(player.getHealth() - 1);
						player.broadcastHealth(roomId);
					}
				}
			}
			
			// Check bullet hit
			for (int j = 0; j < bullets.size(); j++) {
				Bullet b = bullets.get(j);
				int by = b.getY();
				int bx = b.getX();
				// collision
				if (ex < bx + 6 && ex + 25 > bx && ey < by + 6  && ey + 25 > by) {
					enemies.remove(i);
					i--;
					
					bullets.remove(j);
					j--;
					for (Player p : Server.getRoom(roomId).getPlayers()) {	
						if (p.getAddress().equals(b.getPlayer())) {
							p.setScore(p.getScore() + 1);
							p.broadcastScore(roomId);
						}
					}
				}
			}
		}
	}
}
