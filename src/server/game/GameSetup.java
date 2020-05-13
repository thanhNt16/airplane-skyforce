package server.game;

import server.communicate.Server;

public class GameSetup implements Runnable {
	private Thread thread;
	private boolean running;
	
	private GameManager manager;
	public static final int gameWidth = 400;
	public static final int gameHeight = 400;
	
	private int roomId;
	
	public GameSetup(int roomId) {
		this.roomId = roomId;
	}
	
	public void init() {
		manager = new GameManager(roomId);
		manager.init();
	}
	
	public synchronized void start() {
		if (running ) {
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

	public void tick() {
		manager.tick();
		
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		init();
		
		int fps = 50;
		double timePerTick = 1000000000/fps;
		double delta = 0;
		long current = System.nanoTime();
		
		
		while(running) {
			delta = delta + (System.nanoTime() - current)/timePerTick;
			current = System.nanoTime();
			
			if (delta >= 1) {
				tick();
				manager.broadcast(roomId);
				Server.broadcastToRoom(roomId, "END_FRAME__");
				delta--;
			}
		}
	}
}