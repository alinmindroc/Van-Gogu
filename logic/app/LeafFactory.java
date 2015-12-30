package app;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class LeafFactory {
	public static void drawLeaf() {
		try {
			BufferedImage bi = new BufferedImage(100, 100,
					BufferedImage.TYPE_INT_ARGB);

			Graphics2D g = bi.createGraphics();

			GeneralPath path = new GeneralPath();
			g.setColor(Color.BLACK);

			path.moveTo(10, 30);
			path.curveTo(20, 10, 20, 10, 60, 10);
			path.curveTo(60, 20, 20, 40, 90, 50);
			path.curveTo(20, 70, 60, 80, 60, 90);
			path.curveTo(20, 90, 20, 90, 10, 70);
			path.curveTo(15, 50, 15, 50, 10, 30);
			path.closePath();

			g.fill(path);

			ImageIO.write(bi, "png", new File("leaf.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
