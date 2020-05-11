package client.communicate.handler;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import client.game.GameSetup;


public class MainHandler implements KeyListener {
	private DatagramSocket clientSocket;
	private InetAddress address;
    private int port;
    private GameSetup game;
    
    public MainHandler(DatagramSocket clientSocket, InetAddress address, int port) {
    	this.clientSocket = clientSocket;
        this.address = address;
        this.port = port;
        game = new GameSetup("Airplane skyforce", 500, 600);
//		game.start();
        game.init(this);
    }
    
    public void sendMessage(String message) {
    	try {
        	byte[] sendData = new byte[1024];
    		sendData = message.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, address, port);
        	clientSocket.send(sendPacket);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void handleMessage(String message) {
    	String[] payload = message.split("__");
    	String command = payload[0];
    	int x;
    	int y;

    	switch (command) {
	    	case "START_GAME":
//	    		game = new GameSetup("Airplane skyforce", 500, 600);
	    		game.prepare();
				break;
	    	case "BULLET":
	    		x = Integer.parseInt(payload[1]);
	    		y = Integer.parseInt(payload[2]);
	    		game.renderBullet(x, y);
				break;
	    	case "ENEMY":
	    		x = Integer.parseInt(payload[1]);
	    		y = Integer.parseInt(payload[2]);
	    		game.renderEnemy(x, y);
				break;
	    	case "PLAYER":
	    		x = Integer.parseInt(payload[1]);
	    		y = Integer.parseInt(payload[2]);
	    		game.renderPlayer(x, y);
				break;
	    	case "END_FRAME":
//	    		game.clear();
	    		game.prepare();
				break;
    		default:
    			System.out.println("Received from server: " + message);
    	}
    }

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int source = e.getKeyCode();
		if (source == KeyEvent.VK_LEFT) {
			sendMessage("GAME__LEFT__");
		}
		if (source == KeyEvent.VK_RIGHT) {
			sendMessage("GAME__RIGHT__");
		}
		if (source == KeyEvent.VK_SPACE) {
			sendMessage("GAME__SPACE__");
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int source = e.getKeyCode();
		if (source == KeyEvent.VK_LEFT) {
			sendMessage("GAME__NOT_LEFT__");
		}
		if (source == KeyEvent.VK_RIGHT) {
			sendMessage("GAME__NOT_RIGHT__");
		}
		if (source == KeyEvent.VK_SPACE) {
			sendMessage("GAME__NOT_SPACE__");
		}
	}
}
