import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.Random;

public class ImageUtils {

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

	public static BufferedImage colorImage(BufferedImage image, int r, int g, int b, int a) {
		int width = image.getWidth();
		int height = image.getHeight();
		WritableRaster raster = image.getRaster();


		for (int xx = 0; xx < width; xx++) {
			for (int yy = 0; yy < height; yy++) {
				int[] pixels = raster.getPixel(xx, yy, (int[]) null);
				pixels[0] = r;
				pixels[1] = g;
				pixels[2] = b;
				if(a > 0)
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
		AffineTransformOp op = new AffineTransformOp(transform,
				AffineTransformOp.TYPE_BILINEAR);
		return op.filter(bufferedImage, null);
	}
}
