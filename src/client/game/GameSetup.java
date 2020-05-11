package client.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;

import client.communicate.handler.MainHandler;
import hust.ict.graphics.LoadImage;


public class GameSetup {
	private String title;
	private int width;
	private int height;
	private BufferStrategy buffer;
	private Graphics g;
	
	private Display display;
	public static final int gameWidth = 400;
	public static final int gameHeight = 400;
	
	public GameSetup(String title, int width, int height) {
		this.title = title;
		this.width = width;
		this.height = height;
	}
	
	public void init(MainHandler handler) {
		display = new Display(title, width, height);
		LoadImage.init();
		display.frame.addKeyListener(handler);
	}
	
	public void renderBullet(int x, int y) {
		buffer = display.getCanvas().getBufferStrategy();
		if (buffer == null) {
			display.getCanvas().createBufferStrategy(3);
			return;
		}
		g = buffer.getDrawGraphics();
//		g.clearRect(0, 0, width, height);
		//draw
		g.setColor(Color.red);
		g.fillRect(x, y, 6, 10);
		//end of draw
		buffer.show();
//		g.dispose();
	}
	
	public void renderEnemy(int x, int y) {
		buffer = display.getCanvas().getBufferStrategy();
		if (buffer == null) {
			display.getCanvas().createBufferStrategy(3);
			return;
		}
		g = buffer.getDrawGraphics();
//		g.clearRect(0, 0, width, height);
		//draw
		g.setColor(Color.black);
		g.drawImage(LoadImage.enemy, x, y, 25, 25, null);
		//end of draw
		buffer.show();
//		g.dispose();
	}
	
	public void renderPlayer(int x, int y) {
		buffer = display.getCanvas().getBufferStrategy();
		if (buffer == null) {
			display.getCanvas().createBufferStrategy(3);
			return;
		}
		g = buffer.getDrawGraphics();
//		g.clearRect(0, 0, width, height);
		//draw
		g.setColor(Color.red);
		g.drawImage(LoadImage.player, x, y, 25, 25, null);
		//end of draw
		buffer.show();
//		g.dispose();
	}
	
	public void prepare() {
		if (buffer == null) {
			display.getCanvas().createBufferStrategy(3);
			return;
		}
		g = buffer.getDrawGraphics();
		g.clearRect(0, 0, width, height);
		//draw
		g.drawImage(LoadImage.image, 50, 50, gameWidth, gameHeight, null);
		buffer.show();
		g.dispose();
	}
	
	public void clear() {
		buffer.show();
		g.dispose();
	}
	
	public void render() {
		buffer = display.getCanvas().getBufferStrategy();
		if (buffer == null) {
			display.getCanvas().createBufferStrategy(3);
			return;
		}
		g = buffer.getDrawGraphics();
		g.clearRect(0, 0, width, height);
		//draw
		g.drawImage(LoadImage.image, 50, 50, gameWidth, gameHeight, null);
		//end of draw
		buffer.show();
//		g.dispose();
	}
}