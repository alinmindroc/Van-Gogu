import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

public class Leaf {

	static List<ColorScheme> colorSchemes;

	static BufferedImage img;
	static {
		try {
			img = ImageIO.read(new File("leaf.png"));
		} catch (IOException ex) {
			ex.printStackTrace();
		}
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

	public void draw(Graphics gfx, int colorSchemeIndex) {
		double branchAngle;
		Vector2 adjPosition = position.addNew(new Vector2(0, -10));
		if (closestBranch != null) {
			Vector2 growthDir = adjPosition.subtractNew(closestBranch.position);
			growthDir.add(new Vector2(0, 10));
			growthDir.normalize();

			branchAngle = Math.atan2(growthDir.y, growthDir.x);
		} else {
			branchAngle = 0;
			System.out.println("leaf has no parent branch");
		}

		ColorScheme colorScheme;

		if (colorSchemeIndex == 0) {
			colorScheme = ColorSchemeFactory.spring();
		} else if (colorSchemeIndex == 1) {
			colorScheme = ColorSchemeFactory.summer();
		} else if (colorSchemeIndex == 2) {
			colorScheme = ColorSchemeFactory.autumn();
		} else if (colorSchemeIndex == 3) {
			colorScheme = ColorSchemeFactory.christmas();
		} else {
			colorScheme = ColorSchemeFactory.alien();
		}

		int r, g, b;
		r = colorScheme.r;
		g = colorScheme.g;
		b = colorScheme.b;

		gfx.drawImage(ImageUtils.rotateImage(
				ImageUtils.colorImage(img, r, g, b, 0), branchAngle),
				(int) adjPosition.x, (int) adjPosition.y, 20, 20, null);
	}
}
