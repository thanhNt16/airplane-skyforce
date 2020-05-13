package server.game;

import java.util.ArrayList;
import java.util.Random;

import server.communicate.Server;


public class GameManager {
	public static ArrayList<Bullet> bullet;
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
		
		bullet = new ArrayList();
		
		enemies = new ArrayList();
		current = System.nanoTime();
		delay = 800;
	}
	
	public void tick() {
		for (Player player : Server.getRoom(roomId).getPlayers()) {
			player.tick();
		}
		
		for (Bullet bu : bullet) {
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
	
	public void broadcast(int room) {
		for (Player player : Server.getRoom(roomId).getPlayers()) {
			player.broadcast(room);
		}
		
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
			
			for (Player player : Server.getRoom(roomId).getPlayers()) {
				int px = player.getX();
				int py = player.getY();
				if (px <= ex + 25 && px + 25 >= ex && 
						py <= ey + 25 && py + 25 >= ey) {
					enemies.remove(i);
					i--;
					player.setHealth(player.getHealth() - 1);
					System.out.println("hit " + player.getName());
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
					}
				}
			}
		}
	}
}
