package client.game;

import java.awt.Color;
import java.awt.Graphics;

import hust.ict.graphics.LoadImage;


public class Renderer {
	public Renderer() {
		
	}
	
	public void renderBullet(Graphics g, int x, int y) {
		g.setColor(Color.red);
		g.fillRect(x, y, 6, 10);
	}
	
	public void renderEnemy(Graphics g, int x, int y) {
		g.setColor(Color.black);
		g.drawImage(LoadImage.enemy, x, y, 25, 25, null);
	}
	
	public void renderPlayer(Graphics g, int x, int y) {
		g.setColor(Color.red);
		g.drawImage(LoadImage.player, x, y, 25, 25, null);
	}
}
