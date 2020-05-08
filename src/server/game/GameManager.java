package server.game;

import java.util.ArrayList;
import java.util.Random;


public class GameManager {
	private Player player;
	public static ArrayList<Bullet> bullet;
	private ArrayList<Enemy> enemies;
	
	private long delay;
	private long current;
	private int health;
	private int score = 0;
	
	public GameManager() {

	}
	
	public void init() {
		player = new Player((GameSetup.gameWidth/2) + 50, (GameSetup.gameHeight - 30) + 50);
		player.init();
		bullet = new ArrayList();
		
		enemies = new ArrayList();
		current = System.nanoTime();
		delay = 800;
		health = player.getHealth();
	}
	
	public void tick() {
		player.tick();
		for (Bullet bu : bullet) {
            bu.tick();
        }

		long breaks = (System.nanoTime() - current) /1000000;
		if (breaks > delay) {

			for (int i = 0 ; i < 2; i++) {
				Random rand = new Random();
				int randX = rand.nextInt(450);
				int randY = rand.nextInt(450);
				if (health > 0) {
					enemies.add(new Enemy(randX, -randY));	
				}
				
			}
			current = System.nanoTime();
		}
		
		// enemies
		for (int i = 0; i < enemies.size(); i++) {
			enemies.get(i).tick();
		}
	}
	
	public void broadcast(int room) {
		player.broadcast(room);
		for (int i = 0; i<bullet.size(); i++) {
			bullet.get(i).broadcast(room);
		}
		for (int i = 0 ; i < bullet.size(); i++) {
			if (bullet.get(i).getY() <= 50) {
				bullet.remove(i);
				i--;
			}
		}
		
		// enemies
		for (int i = 0; i < enemies.size(); i++) {
			if (!(enemies.get(i).getX() <= 50  || enemies.get(i).getX() >= 450  - 30
					|| enemies.get(i).getY() >=450 - 30)) {
				if (enemies.get(i).getY() >= 50) {
					enemies.get(i).broadcast(room);	
				}
			}
			
		}
		for (int i = 0; i<enemies.size(); i++) {
			int ex = enemies.get(i).getX();
			int ey = enemies.get(i).getY();
			
			int px = player.getX();
			int py = player.getY();
			if (px <= ex + 25 && px + 25 >= ex && 
					py <= ey + 25 && py + 25 >= ey) {
				enemies.remove(i);
				i--;
				health--;
				System.out.println("health " + health);
				if (health <= 0) {
					enemies.removeAll(enemies);
					player.setHealth(0);
				}
			}
			
			for (int j = 0; j<bullet.size(); j++) {
				int by = bullet.get(j).getY();
				int bx = bullet.get(j).getX();
				// collision
				if (ex < bx + 6 && ex + 25 > bx && ey < by + 6  && ey + 25 > by) {
					enemies.remove(i);
					i--;
					
					bullet.remove(j);
					j--;
					score += 1;
				}
			}
		}
	}
}
