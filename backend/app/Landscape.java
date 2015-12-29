package app;
import image_utils.CloudGenerator;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Landscape {

	public static final int arraySize = 1000;

	public int[] terrainArray;

	private int start;
	private int change;
	private int factor;
	private int last;
	private int plus;
	private int colorScheme;
	Color gradientStart, gradientMiddle, gradientEnd;

	Random rand = new Random();

	public Landscape(int colorScheme) {
		this.colorScheme = colorScheme;

		factor = 1;
		terrainArray = new int[arraySize];
		generateLandscape();

		switch (colorScheme) {
		case 0:
			gradientStart = new Color(91, 126, 64);
			gradientMiddle = new Color(56, 82, 79);
			gradientEnd = new Color(32, 12, 7);
			break;
		case 1:
			gradientStart = new Color(112, 161, 28);
			gradientMiddle = new Color(35, 63, 65);
			gradientEnd = new Color(32, 12, 7);
			break;
		case 2:
			gradientStart = new Color(131, 154, 40);
			gradientMiddle = new Color(96, 72, 4);
			gradientEnd = new Color(32, 12, 7);
			break;
		case 3:
			gradientStart = new Color(234, 235, 250);
			gradientMiddle = new Color(35, 84, 123);
			gradientEnd = new Color(32, 12, 7);
			break;
		case 4:
			gradientStart = new Color(6, 86, 113);
			gradientMiddle = new Color(180, 0, 0);
			gradientEnd = new Color(32, 12, 7);
			break;
		}
	}

	public void generateLandscape() {
		plus = 1;
		start = Math.abs(200 + (rand.nextInt() % 20));
		terrainArray[0] = start;

		for (int i = 1; i < arraySize; i++) {
			last = terrainArray[i - 1];
			change = Math.abs(rand.nextInt() % 11);

			if (change > 8) {
				factor = -(factor);
				plus = 1 + Math.abs(rand.nextInt() % 2);
			}

			if (last > 750 || last < 100) {
				factor = -(factor);
			}
			// TODO - change values for red,green,blue
			terrainArray[i] = last + (factor * plus);
		}
	}

	public void draw(Graphics g) {
		BufferedImage clouds = CloudGenerator.getCloudTexture(1000, 500,
				colorScheme);
		g.drawImage(clouds, 0, 0, 1000, 500, null);

		Graphics2D g2d = (Graphics2D) g;

		GradientPaint topGradient = new GradientPaint(500f, 0f, gradientStart,
				500f, 600f, gradientMiddle);
		GradientPaint bottomGradient = new GradientPaint(500f, 600f,
				gradientMiddle, 500f, 800f, gradientEnd);

		g2d.setPaint(topGradient);

		for (int i = 0; i < arraySize; i++) {
			g.drawLine(i, terrainArray[i], i, 1000);
		}

		g2d.setPaint(bottomGradient);

		g.fillRect(0, 600, 1000, 1000);
	}
}
