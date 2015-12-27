import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
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
			img = ImageIO.read(new File("leaf.png"));
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
		r = 150 + rand.nextInt(100);
		g = 80 + rand.nextInt(80);
		b = rand.nextInt(256);
//		b = 0;

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

	public BufferedImage rotate(BufferedImage bufferedImage, double radians) {
		AffineTransform transform = new AffineTransform();
		transform.rotate(radians, bufferedImage.getWidth() / 2,
				bufferedImage.getHeight() / 2);
		AffineTransformOp op = new AffineTransformOp(transform,
				AffineTransformOp.TYPE_BILINEAR);
		return op.filter(bufferedImage, null);
	}

	public void draw(Graphics g, JComponent component) {
		double branchAngle;
		if (closestBranch != null) {
			Vector2 avgDirection = closestBranch.originalGrowDirection
					.divide(closestBranch.growCount);

			branchAngle = Math.atan2(avgDirection.y, avgDirection.x);
		} else {
			branchAngle = 0;
			System.out.println("leaf has no parent branch");
		}
		g.drawImage(rotate(colorImage(img), branchAngle),
				(int) position.x - 10, (int) position.y - 10, 20, 20, component);
		// g.drawRect((int) position.x, (int) position.y, 5, 5);
	}
}
