package hust.ict.bullet;

import java.awt.Color;
import java.awt.Graphics;

public class Bullet {
	private int x;
	private int y;
	private int speed;
	
	public Bullet(int x, int y) {
		this.x = x;
		this.y = y;
		speed = 10;
	}
	
	public int getY() {
		return y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void tick() {
		y -= speed;
	}
	public void render(Graphics g) {
		g.setColor(Color.red);
		g.fillRect(x, y, 6, 10);
	}
	
	
}

