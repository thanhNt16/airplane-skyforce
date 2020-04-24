package hust.ict.enemy;

import java.awt.Color;
import java.awt.Graphics;

import hust.ict.graphics.LoadImage;

public class Enemy {
	private int x;
	private int y;
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setX(int x) {
		this.x = x;
	}
	public Enemy(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public void tick() {
		y+=1;
	}
	public void render(Graphics g) {
		g.setColor(Color.black);
		g.drawImage(LoadImage.enemy, x, y, 25, 25, null);
	}
}
