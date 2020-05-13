package server.game;

import java.util.ArrayList;
import java.util.Random;

import server.communicate.Server;


public class GameManager {
	public static ArrayList<Bullet> bullets;
	private ArrayList<Enemy> enemies;
	
	private long delay;
	private long current;
	
	private int roomId;
	
	public GameManager(int roomId) {
		this.roomId = roomId;
	}
	
	public void init() {
		for (Player player : Server.getRoom(roomId).getPlayers()) {
			player.setX((GameSetup.gameWidth/2) + 50);
			player.setY((GameSetup.gameHeight - 30) + 50);
			player.init();
		}
		
		bullets = new ArrayList<Bullet>();
		enemies = new ArrayList<Enemy>();
		current = System.nanoTime();
		delay = 800;
	}
	
	public void tick() {
		for (Player player : Server.getRoom(roomId).getPlayers()) {
			player.tick();
		}
		
		for (Bullet bu : bullets) {
            bu.tick();
        }

		long breaks = (System.nanoTime() - current) /1000000;
		if (breaks > delay) {

			for (int i = 0 ; i < 2; i++) {
				Random rand = new Random();
				int randX = rand.nextInt(450);
				int randY = rand.nextInt(450);
				enemies.add(new Enemy(randX, -randY));
			}
			current = System.nanoTime();
		}
		
		// enemies
		for (int i = 0; i < enemies.size(); i++) {
			enemies.get(i).tick();
		}
	}
	
	public void broadcast() {
		for (Player player : Server.getRoom(roomId).getPlayers()) {
			player.broadcast(roomId);
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
