package app;
import java.util.Random;

public class ColorSchemeFactory {
	static int r, g, b;
	static Random rand;

	public static ColorScheme spring() {
		rand = new Random();

		int p = rand.nextInt(3);

		if (p > 0) {
			r = 25 + rand.nextInt(20);
			g = 105 + rand.nextInt(80);
			b = 25 + rand.nextInt(20);
		} else {
			r = 235 + rand.nextInt(10);
			g = 185 + rand.nextInt(40);
			b = 185 + rand.nextInt(40);
		}

		return new ColorScheme(r, g, b);
	}

	public static ColorScheme summer() {
		rand = new Random();

		r = 25 + rand.nextInt(20);
		g = 105 + rand.nextInt(80);
		b = 25 + rand.nextInt(20);

		return new ColorScheme(r, g, b);
	}

	public static ColorScheme autumn() {
		rand = new Random();

		r = 200 + rand.nextInt(55);
		g = 70 + rand.nextInt(120);
		b = 10 + rand.nextInt(20);

		return new ColorScheme(r, g, b);
	}

	public static ColorScheme christmas() {
		rand = new Random();

		int p = rand.nextInt(40);

		if (p < 2) {
			r = 200;
			g = 0;
			b = 0;
		} else if (p < 4) {
			r = 0;
			g = 0;
			b = 200;
		} else if (p < 6) {
			r = 200;
			g = 200;
			b = 0;
		} else {
			r = 20 + rand.nextInt(30);
			g = 105 + rand.nextInt(40);
			b = 20 + rand.nextInt(30);
		}

		return new ColorScheme(r, g, b);

	}

	public static ColorScheme alien() {
		rand = new Random();

		r = 55 + rand.nextInt(200);
		g = 45 + rand.nextInt(40);
		b = 105 + rand.nextInt(150);

		return new ColorScheme(r, g, b);
	}

}
