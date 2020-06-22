package client;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static common.Constants.*;

import javax.swing.JButton;
import javax.swing.JPanel;

import client.game.LoadImage;
import client.game.PlayerInfo;

public class IngameScreen extends JPanel implements ActionListener, KeyListener {
	private static final long serialVersionUID = 1L;
	public static Canvas canvas;
	private static BufferStrategy buffer;
	private static Graphics g;
	
	private static List<String> queue = new ArrayList<String>();
	private static List<PlayerInfo> players = new ArrayList<PlayerInfo>();
	
	private JButton startGameBtn;
    
    public IngameScreen(int width, int height) {
        setSize(width, height);
        setVisible(true);

        renderCanvas();

        ScreenManager.getInstance().getWindow().addKeyListener(this);
        ScreenManager.getInstance().getWindow().setFocusable(true);
    }
    
    public void renderStartBtn() {
    	startGameBtn = new JButton("Start");
    	startGameBtn.addActionListener(this);
    	add(startGameBtn);
    }
    
    private void renderCanvas() {
        
        canvas = new Canvas();
		canvas.setPreferredSize(new Dimension(500,600));
//		canvas.setBackground(new Color(212, 154, 140));
		canvas.setFocusable(false);
        add(canvas);
        LoadImage.init();
        
    }
    
    public static void pushToQueue(String message) {
		queue.add(message);
	}
	
	public static void flushQueue() {
		if (buffer == null) {
			canvas.createBufferStrategy(3);
			buffer = canvas.getBufferStrategy();
		}
		g = buffer.getDrawGraphics();
		g.clearRect(0, 0, IN_GAME_SCREEN_WIDTH, IN_GAME_SCREEN_HEIGHT);
		g.drawImage(LoadImage.image, 50, 50, IN_GAME_SCREEN_WIDTH, IN_GAME_SCREEN_HEIGHT, null);	
		
		for (String message : queue) {
			String[] payload = message.split("__");
			int x;
			int y;
			boolean added;
			switch (payload[0]) {
				// BULLET__10__20__	
		    	case "BULLET":
		    		x = Integer.parseInt(payload[1]);
		    		y = Integer.parseInt(payload[2]);
//		    		g.setColor(Color.red);
		    		g.drawImage(LoadImage.bullet, x, y, 6, 10, null);
//		    		g.fillRect(x, y, 6, 10);
					break;
				// ENEMY__10__20__
		    	case "ENEMY":
		    		x = Integer.parseInt(payload[1]);
		    		y = Integer.parseInt(payload[2]);
		    		g.setColor(Color.black);
		    		g.drawImage(LoadImage.enemy, x, y, 25, 25, null);
					break;
				// PLAYER__10__20__localhost:8000__duc__0__
		    	case "PLAYER":
		    		x = Integer.parseInt(payload[1]);
		    		y = Integer.parseInt(payload[2]);
		    		g.setColor(Color.red);
		    		g.drawImage(LoadImage.player, x, y, 25, 25, null);
		    		g.setColor(Color.white);
					g.setFont(new Font("arial", Font.BOLD, 8));
					g.drawString(payload[4], x, y);
					break;
				// SCORE__10__localhost:8000__duc__
		    	case "SCORE":
		    		added = false;
		    		int score = Integer.parseInt(payload[1]);
		    		for (PlayerInfo p : players) {
		    			if (p.getAddress().equals(payload[2])) {
		    				p.setScore(score);
		    				added = true;
		    				break;
		    			}
		    		}
		    		if (!added) {
		    			PlayerInfo p = new PlayerInfo(payload[2], payload[3]);
		    			p.setScore(score);
		    			players.add(p);
		    		}
		    		break;
	    		// HEALTH__10__localhost:8000__
		    	case "HEALTH":
		    		added = false;
		    		int health = Integer.parseInt(payload[1]);
		    		for (PlayerInfo p : players) {
		    			if (p.getAddress().equals(payload[2])) {
		    				p.setHealth(health);
		    				added = true;
		    				break;
		    			}
		    		}
		    		if (!added) {
		    			PlayerInfo p = new PlayerInfo(payload[2], payload[3]);
		    			p.setHealth(health);
		    			players.add(p);
		    		}
		    		break;
			}
		}
		int startY = 470;
		Collections.sort(players);
		Collections.reverse(players);
		for (PlayerInfo p : players) {
			g.setColor(Color.white);
    		g.setFont(new Font("arial", Font.BOLD, 12));
    		g.drawString(p.getName() + "- score: " + p.getScore() + ", health: " + p.getHealth(), 70, startY);
    		startY += 20;
		}
		buffer.show();
		g.dispose();
		queue.clear();
	}
    
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		int source = e.getKeyCode();
		if (source == KeyEvent.VK_LEFT) {
			Client.handler.sendMessage("GAME__LEFT__");
		}
		if (source == KeyEvent.VK_RIGHT) {
			Client.handler.sendMessage("GAME__RIGHT__");
		}
		if (source == KeyEvent.VK_UP) {
			Client.handler.sendMessage("GAME__UP__");
		}
		if (source == KeyEvent.VK_DOWN) {
			Client.handler.sendMessage("GAME__DOWN__");
		}
		if (source == KeyEvent.VK_SPACE) {
			Client.handler.sendMessage("GAME__SPACE__");
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int source = e.getKeyCode();
		if (source == KeyEvent.VK_LEFT) {
			Client.handler.sendMessage("GAME__NOT_LEFT__");
		}
		if (source == KeyEvent.VK_RIGHT) {
			Client.handler.sendMessage("GAME__NOT_RIGHT__");
		}
		if (source == KeyEvent.VK_UP) {
			Client.handler.sendMessage("GAME__NOT_UP__");
		}
		if (source == KeyEvent.VK_DOWN) {
			Client.handler.sendMessage("GAME__NOT_DOWN__");
		}
		if (source == KeyEvent.VK_SPACE) {
			Client.handler.sendMessage("GAME__NOT_SPACE__");
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == startGameBtn) {
			Client.handler.sendMessage("START_GAME__");
			remove(startGameBtn);
        }
	}
}
