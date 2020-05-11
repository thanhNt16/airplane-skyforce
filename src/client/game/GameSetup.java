package client.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import hust.ict.graphics.LoadImage;


public class GameSetup implements Runnable  {
	private String title;
	private int width;
	private int height;
	private Thread thread;
	private boolean running;
	private BufferStrategy buffer;
	private Graphics g;
	private Renderer renderer;
	
	private Display display;
	public static final int gameWidth = 400;
	public static final int gameHeight = 400;
	
	public GameSetup(String title, int width, int height) {
		this.title = title;
		this.width = width;
		this.height = height;
	}
	
	public void init() {
		display = new Display(title, width, height);
		LoadImage.init();
	}
	
	public synchronized void start() {
		if (running) {
			return;
		}
		running = true;
		if (thread == null) {
			thread = new Thread(this);
			thread.start();
		}
		
	}
	public synchronized void stop() {
		if (!running) {
			return;
		}
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
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
	
	@Override
	public void run() {
		init();
		
		int fps = 50;
		double timePerTick = 1000000000/fps;
		double delta = 0;
		long current = System.nanoTime();
		
		
		while(running) {
			delta = delta + (System.nanoTime() - current)/timePerTick;
			current = System.nanoTime();
			
			if (delta >= 1) {
				render();
				delta--;
			}
		}
	}
}