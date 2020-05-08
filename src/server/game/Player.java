package server.game;

import java.awt.Color;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import server.communicate.Server;

public class Player implements KeyListener {
	private int x;
	private int y;
	private boolean left;
	private boolean right;
	private boolean fire;
	
	private long current;
	private long delay;
	private int health;
	
	private int room;
	
	public Player(int x, int y, int room) {
		 this.x = x;
		 this.y = y;
		 this.room = room;
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
		Display.frame.addKeyListener(this);
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
					Server.broadcastToRoom(room, "pew");
				}
				
			}
		}
	}
	public void render(Graphics g) {
		if (!(health <=0)) {
			g.setColor(Color.red);
			g.drawImage(LoadImage.player, x, y, 25, 25, null);	
		}
	}
	public void keyPressed(KeyEvent e) {
		int source = e.getKeyCode();
		if (source == KeyEvent.VK_LEFT) {
			 left = true;
		}
		if (source == KeyEvent.VK_RIGHT) {
			 right = true;
		}
		if (source == KeyEvent.VK_SPACE) {
			 fire = true;
		}
	}
	public void keyReleased(KeyEvent e) {
		int source = e.getKeyCode();
		if (source == KeyEvent.VK_LEFT) {
			 left = false;
		}
		if (source == KeyEvent.VK_RIGHT) {
			 right = false;
		}
		if (source == KeyEvent.VK_SPACE) {
			 fire = false;
		}
	}


	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}