package hust.ict.display;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Display {
	private String title;
	private int width;
	private int height;
	
	public static JFrame frame;
	private Canvas canvas;
	
	public Display(String title, int width, int height) {
		this.width = width;
		this.title = title;
		this.height = height;
		createDisplay();
	}
	
	public void createDisplay() {
		frame = new JFrame(title);
		frame.setSize(width, height);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		canvas = new Canvas();
		canvas.setPreferredSize(new Dimension(width,height));
		canvas.setBackground(new Color(212, 154, 140));
		canvas.setFocusable(false);
		frame.add(canvas);
		frame.pack();
	}
	public Canvas getCanvas() {
		return canvas;
	}
}
