package hust.ict.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class LoadImage {
	public static BufferedImage image;
	
	public static void init() {
		image = imageLoader("/sky.jpg");
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
}
