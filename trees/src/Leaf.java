import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

public class Leaf {
	static BufferedImage img;
	static {
		try {
			img = colorImage(ImageIO.read(new File("leaf.png")));
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	private static BufferedImage colorImage(BufferedImage image) {
		int width = image.getWidth();
		int height = image.getHeight();
		WritableRaster raster = image.getRaster();

		Random rand = new Random();

		int r, g, b;
		r = rand.nextInt(256);
		g = rand.nextInt(256);
		b = rand.nextInt(256);
		
		for (int xx = 0; xx < width; xx++) {
			for (int yy = 0; yy < height; yy++) {
				int[] pixels = raster.getPixel(xx, yy, (int[]) null);
				pixels[0] = r;
				pixels[1] = g;
				pixels[2] = b;
				raster.setPixel(xx, yy, pixels);
			}
		}
		return image;
	}

	public Vector2 position;
	public Branch closestBranch;

	public Leaf(int x, int y) {
		this.position = new Vector2(x, y);
	}

	public Leaf(Vector2 position) {
		this.position = position;
	}

	public String toString() {
		return "L " + position.toString();
	}

	public void draw(Graphics g, JComponent component) {
		g.drawImage(colorImage(img), (int) position.x, (int) position.y, 20, 20, component);
	}
}
