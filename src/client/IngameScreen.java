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
import static common.Constants.*;

import javax.swing.JPanel;

import client.game.LoadImage;

public class IngameScreen extends JPanel implements ActionListener, KeyListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static Canvas canvas;
	private BufferStrategy buffer = null;
	private Graphics g;
    
    public IngameScreen(int width, int height) {

        setSize(width, height);
        setVisible(true);

        renderCanvas();


        ScreenManager.getInstance().getWindow().addKeyListener(this);
        ScreenManager.getInstance().getWindow().setFocusable(true);
    }
    private void renderCanvas() {
        
        canvas = new Canvas();
		canvas.setPreferredSize(new Dimension(500,600));
		canvas.setBackground(new Color(212, 154, 140));
		canvas.setFocusable(false);
        add(canvas);
        
//		g.clearRect(0, 0, IN_GAME_SCREEN_WIDTH,IN_GAME_SCREEN_HEIGHT );
//		g.drawImage(LoadImage.image, 50, 50, IN_GAME_SCREEN_WIDTH, IN_GAME_SCREEN_HEIGHT, null);
        System.out.println("ok ok 123");
        LoadImage.init();
//        ImageLoader.init();
//        renderUI();
    }
    
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if (buffer == null) {
        	canvas.createBufferStrategy(3);
			buffer = canvas.getBufferStrategy();
		}
        g = buffer.getDrawGraphics();
        g.setColor(Color.red);
		g.drawImage(LoadImage.player, 250, 250, 25, 25, null);
		g.setColor(Color.white);
		g.setFont(new Font("arial", Font.BOLD, 8));
		g.drawString("hello", 250, 250);
        
		System.out.println("press okok");
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
