package server.game;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class LoadImage {
	public static BufferedImage image;
	public static BufferedImage player;
	public static BufferedImage enemy;
	public static BufferedImage entities;
	
	public static void init() {
		image = imageLoader("/white.jpg");
		player = imageLoader("/player.jpg");
		entities = imageLoader("/airplane.png");
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
