package client.game;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class LoadImage {
	public static BufferedImage image;
	public static BufferedImage player;

	public static BufferedImage player1;
	public static BufferedImage player2;
	public static BufferedImage player3;
	public static BufferedImage player4;

	public static BufferedImage enemy;
	public static BufferedImage entities;
	public static BufferedImage bullet;
	
	public static void init() {
		image = imageLoader("/night.png");
		player1 = imageLoader("/player1.png");
		player2 = imageLoader("/player2.png");
		player3 = imageLoader("/player3.png");
		player4 = imageLoader("/player4.png");
		entities = imageLoader("/airplane.png");
		bullet = imageLoader("/bullet.png");

		crop();
	}
	
	public static BufferedImage imageLoader(String path) {
		try {
			return ImageIO.read(LoadImage.class.getResource(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}
		return null;
	}
	public static void crop() {
		enemy = entities.getSubimage(0, 0,85, 90);
	}

}
