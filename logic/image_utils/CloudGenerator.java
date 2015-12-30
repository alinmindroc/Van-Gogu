package image_utils;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.Random;

public class CloudGenerator {

	private static double[][] noise;

	private static void generateNoise(int w, int h) {
		Random rand = new Random();
		for (int x = 0; x < w; x++)
			for (int y = 0; y < h; y++) {
				noise[x][y] = (rand.nextDouble());
			}
	}

	static double smoothNoise(double x, double y, int w, int h) {
		// get fractional part of x and y
		double fractX = x - (int) (x);
		double fractY = y - (int) (y);

		// wrap around
		int x1 = ((int) (x) + w) % w;
		int y1 = ((int) (y) + h) % h;

		// neighbor values
		int x2 = (x1 + w - 1) % w;
		int y2 = (y1 + h - 1) % h;

		// smooth the noise with bilinear interpolation
		double value = 0.0;
		value += fractX * fractY * noise[x1][y1];
		value += fractX * (1 - fractY) * noise[x1][y2];
		value += (1 - fractX) * fractY * noise[x2][y1];
		value += (1 - fractX) * (1 - fractY) * noise[x2][y2];

		return value;
	}

	static double turbulence(double x, double y, double size, int w, int h) {
		double value = 0.0, initialSize = size;

		while (size >= 1) {
			value += smoothNoise(x / size, y / size, w, h) * size;
			size /= 2.0;
		}

		return (128.0 * value / initialSize);
	}

	private static Color HSLtoRGB(double h, double s, double l) {
		double r, g, b; // this function works with floats between 0 and 1
		double temp1, temp2, tempr, tempg, tempb;
		h /= 256.0;
		s /= 256.0;
		l /= 256.0;

		// If saturation is 0, the color is a shade of grey
		if (s == 0)
			r = g = b = l;
		// If saturation > 0, more complex calculations are needed
		else {
			// set the temporary values
			if (l < 0.5)
				temp2 = l * (1 + s);
			else
				temp2 = (l + s) - (l * s);
			temp1 = 2 * l - temp2;
			tempr = h + 1.0 / 3.0;
			if (tempr > 1.0)
				tempr--;
			tempg = h;
			tempb = h - 1.0 / 3.0;
			if (tempb < 0.0)
				tempb++;

			// red
			if (tempr < 1.0 / 6.0)
				r = temp1 + (temp2 - temp1) * 6.0 * tempr;
			else if (tempr < 0.5)
				r = temp2;
			else if (tempr < 2.0 / 3.0)
				r = temp1 + (temp2 - temp1) * ((2.0 / 3.0) - tempr) * 6.0;
			else
				r = temp1;

			// green
			if (tempg < 1.0 / 6.0)
				g = temp1 + (temp2 - temp1) * 6.0 * tempg;
			else if (tempg < 0.5)
				g = temp2;
			else if (tempg < 2.0 / 3.0)
				g = temp1 + (temp2 - temp1) * ((2.0 / 3.0) - tempg) * 6.0;
			else
				g = temp1;

			// blue
			if (tempb < 1.0 / 6.0)
				b = temp1 + (temp2 - temp1) * 6.0 * tempb;
			else if (tempb < 0.5)
				b = temp2;
			else if (tempb < 2.0 / 3.0)
				b = temp1 + (temp2 - temp1) * ((2.0 / 3.0) - tempb) * 6.0;
			else
				b = temp1;
		}

		Color colorRGB = new Color((int) (r * 255.0), (int) (g * 255.0),
				(int) (b * 255.0));
		return colorRGB;
	}

	/*
	 * Generates a cloud texture based on
	 * http://lodev.org/cgtutor/randomnoise.html
	 */
	public static BufferedImage getCloudTexture(int width, int height,
			int colorScheme) {
		double levelStart;
		double hue;
		if (colorScheme < 3) {
			// spring, summer, autumn
			levelStart = 220;
			hue = 150;
		} else if (colorScheme == 3) {
			// winter
			levelStart = 222;
			hue = 160;
		} else {
			// alien
			levelStart = 222;
			hue = 20;
		}

		noise = new double[width][height];
		generateNoise(width, height);

		BufferedImage img = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_ARGB);
		Graphics ig = img.createGraphics();

		ig.setColor(Color.BLACK);
		ig.fillRect(0, 0, width, height);
		WritableRaster raster = img.getRaster();

		double l;

		for (int xx = 0; xx < width; xx++) {
			for (int yy = 0; yy < height; yy++) {
				int[] pixels = raster.getPixel(xx, yy, (int[]) null);
				l = levelStart
						+ ((int) (turbulence(xx, yy, 64, width, height))) / 5;
				l = Math.min(l, 250);
				Color color = HSLtoRGB(hue, 255.0, l);

				pixels[0] = color.getRed();
				pixels[1] = color.getGreen();
				pixels[2] = color.getBlue();

				raster.setPixel(xx, yy, pixels);
			}
		}

		return img;
	}
}
