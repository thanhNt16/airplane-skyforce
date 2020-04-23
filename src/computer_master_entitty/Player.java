package computer_master_entitty;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import computer_master_bullets.Bullet;
import computer_master_display.Display;
import computer_master_manager.GameManager;

public class Player implements KeyListener {
	private int x;
	private int y;
	private boolean left;
	private boolean right;
	private boolean fire;
	
	private long current;
	private long delay;
	
	public Player(int x, int y) {
		 this.x = x;
		 this.y = y;
	}
	public void init() {
		Display.frame.addKeyListener(this);
		current = System.nanoTime();
		delay = 100;
	}
	public void tick() {
		if (left) {
			if (x>=50) {
				x-=1;	
			}

		}
		if (right) {
			if (x<= 450-30) {
				x+=1;	
			}
		}
		if (fire) {
			long breaks = (System.nanoTime() - current)/1000000;
			if (breaks > delay) {
				GameManager.bullet.add(new Bullet(x+11, y+10));	
				current = System.nanoTime();
			}
			
		}
	}
	public void render(Graphics g) {
		g.setColor(Color.red);
		g.fillRect(x, y, 30, 30);
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
