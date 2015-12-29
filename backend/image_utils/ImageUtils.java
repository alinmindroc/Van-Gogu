package image_utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import app.Landscape;
import app.Tree;

public class ImageUtils {
	public static void drawBackground(int width, int height, Landscape l) {
		try {
			BufferedImage bi = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_ARGB);

			Graphics2D ig2 = bi.createGraphics();

			l.draw(ig2);

			ImageIO.write(bi, "png", new File("van_gogh.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void drawTrees(List<Tree> trees) {
		try {
			// TYPE_INT_ARGB specifies the image format: 8-bit RGBA packed
			// into integer pixels
			BufferedImage bi = ImageIO.read(new File("van_gogh.png"));

			Graphics2D ig2 = bi.createGraphics();
			ig2.setColor(new Color(93, 46, 16));

			for (int i = trees.size() - 1; i >= 0; i--) {
				trees.get(i).draw(ig2);
			}

			ImageIO.write(bi, "png", new File("van_gogh.png"));
		} catch (IOException ie) {
			ie.printStackTrace();
		}
	}

	public static Point rotatePoint(Point pt, Point center, double angleDeg) {
		if (angleDeg == 0)
			return pt;
		double angleRad = (angleDeg / 180) * Math.PI;
		double cosAngle = Math.cos(angleRad);
		double sinAngle = Math.sin(angleRad);
		double dx = (pt.x - center.x);
		double dy = (pt.y - center.y);

		pt.x = center.x + (int) (dx * cosAngle - dy * sinAngle);
		pt.y = center.y + (int) (dx * sinAngle + dy * cosAngle);
		return pt;
	}

	public static BufferedImage colorImage(BufferedImage image, int r, int g,
			int b, int a) {
		int width = image.getWidth();
		int height = image.getHeight();
		WritableRaster raster = image.getRaster();

		for (int xx = 0; xx < width; xx++) {
			for (int yy = 0; yy < height; yy++) {
				int[] pixels = raster.getPixel(xx, yy, (int[]) null);
				pixels[0] = r;
				pixels[1] = g;
				pixels[2] = b;
				if (a > 0)
					pixels[3] = a;
				raster.setPixel(xx, yy, pixels);
			}
		}
		return image;
	}

	public static BufferedImage rotateImage(BufferedImage bufferedImage,
			double radians) {
		AffineTransform transform = new AffineTransform();
		transform.rotate(radians, bufferedImage.getWidth() / 2,
				bufferedImage.getHeight() / 2);
		try {
			AffineTransformOp op = new AffineTransformOp(transform,
					AffineTransformOp.TYPE_BILINEAR);
			return op.filter(bufferedImage, null);
		} catch (RasterFormatException e) {
			return bufferedImage;
		}
	}
}
