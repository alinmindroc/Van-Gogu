package app;

import image_utils.ImageUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class VanGogh {
	static Landscape l;
	static List<Tree> trees;

	private static int baseY = 700;
	private static int baseWidth = 200;
	private static int baseHeight = 240;
	private static int trunkHeight = 100;

	// this will be the same for all the trees, we want the wind to have the
	// same direction
	private static int crownAngleDegrees = 0;

	private static void setRandVals() {
		Random rand = new Random();

		baseY = 700 - rand.nextInt(100);
		baseWidth = 200 + rand.nextInt(60) - 30;
		trunkHeight = 70 + rand.nextInt(40);
	}

	// gets a random colorScheme and adds trees objects
	public static void addTres(int colorScheme) {
		// front trees
		setRandVals();
		trees.add(new Tree(new Vector2(160, baseY + 250), baseWidth + 310,
				baseHeight + 360, trunkHeight, 5, 200, 3, crownAngleDegrees,
				colorScheme));

		setRandVals();
		trees.add(new Tree(new Vector2(320, baseY + 220), baseWidth + 300,
				baseHeight + 260, trunkHeight, 5, 200, 3, crownAngleDegrees,
				colorScheme));

		setRandVals();
		trees.add(new Tree(new Vector2(420, baseY + 150), baseWidth + 200,
				baseHeight + 160, trunkHeight, 5, 200, 3, crownAngleDegrees,
				colorScheme));

		setRandVals();
		trees.add(new Tree(new Vector2(480, baseY + 180), baseWidth + 200,
				baseHeight + 60, trunkHeight, 5, 200, 3, crownAngleDegrees,
				colorScheme));

		setRandVals();
		trees.add(new Tree(new Vector2(580, baseY + 180), baseWidth + 100,
				baseHeight + 60, trunkHeight, 5, 200, 3, crownAngleDegrees,
				colorScheme));

		setRandVals();
		trees.add(new Tree(new Vector2(680, baseY + 230), baseWidth + 80,
				baseHeight + 110, trunkHeight, 5, 200, 3, crownAngleDegrees,
				colorScheme));

		setRandVals();
		trees.add(new Tree(new Vector2(780, baseY + 230), baseWidth + 160,
				baseHeight + 170, trunkHeight, 5, 200, 3, crownAngleDegrees,
				colorScheme));

		setRandVals();
		trees.add(new Tree(new Vector2(880, baseY + 150), baseWidth, 250,
				baseHeight - 140, trunkHeight, 200, 3, crownAngleDegrees,
				colorScheme));

		setRandVals();
		trees.add(new Tree(new Vector2(950, baseY + 150), baseWidth + 80,
				baseHeight + 30, trunkHeight, 5, 200, 3, crownAngleDegrees,
				colorScheme));

		setRandVals();
		// back trees
		trees.add(new Tree(new Vector2(40, baseY + 150), baseWidth + 50,
				baseHeight + 110, trunkHeight, 5, 200, 3, crownAngleDegrees,
				colorScheme));

		setRandVals();
		trees.add(new Tree(new Vector2(100, baseY + 150), baseWidth + 50,
				baseHeight + 160, trunkHeight, 5, 200, 3, crownAngleDegrees,
				colorScheme));

		setRandVals();
		trees.add(new Tree(new Vector2(250, baseY + 150), baseWidth + 50,
				baseHeight + 160, trunkHeight, 5, 200, 3, crownAngleDegrees,
				colorScheme));

		setRandVals();
		trees.add(new Tree(new Vector2(820, baseY), baseWidth + 50,
				baseHeight + 40, trunkHeight, 5, 200, 3, crownAngleDegrees,
				colorScheme));

		setRandVals();
		trees.add(new Tree(new Vector2(920, baseY), baseWidth + 50, baseHeight,
				trunkHeight, 5, 200, 3, crownAngleDegrees, colorScheme));

		setRandVals();
		trees.add(new Tree(new Vector2(870, baseY), baseWidth + 50, baseHeight,
				trunkHeight, 5, 200, 3, crownAngleDegrees, colorScheme));

		setRandVals();
		trees.add(new Tree(new Vector2(620, baseY), baseWidth + 50, baseHeight,
				trunkHeight, 5, 200, 3, crownAngleDegrees, colorScheme));

		setRandVals();
		trees.add(new Tree(new Vector2(720, baseY), baseWidth + 50, baseHeight,
				trunkHeight, 5, 200, 3, crownAngleDegrees, colorScheme));

		setRandVals();
		trees.add(new Tree(new Vector2(520, baseY), baseWidth + 50, baseHeight,
				trunkHeight, 5, 200, 3, crownAngleDegrees, colorScheme));

		setRandVals();
		trees.add(new Tree(new Vector2(950, baseY), baseWidth + 250,
				baseHeight, trunkHeight, 5, 200, 3, crownAngleDegrees,
				colorScheme));

		setRandVals();
		trees.add(new Tree(new Vector2(20, baseY), baseWidth + 150, baseHeight,
				trunkHeight, 5, 200, 3, crownAngleDegrees, colorScheme));
	}

	public static void main(String[] args) {

		if (args.length != 1) {
			System.out.println("usage: java VanGogh <colorScheme>");
			System.out.println("0 - spring");
			System.out.println("1 - summer");
			System.out.println("2 - autumn");
			System.out.println("3 - winter (christmas)");
			System.out.println("4 - alien");
			return;
		}

		long start = System.currentTimeMillis();

		int colorScheme = Integer.parseInt(args[0]);

		Random rand = new Random();
		crownAngleDegrees = rand.nextInt(10) - 5;

		// colorSchemes:
		// 0 - spring
		// 1 - summer
		// 2 - autumn
		// 3 - winter (christmas)
		// 4 - alien

		l = new Landscape(colorScheme);

		// generate a leaf shape using bezier curves
		LeafFactory.drawLeaf();

		trees = new ArrayList<>();
		addTres(colorScheme);

		ImageUtils.drawBackground(1000, 900, l);
		ImageUtils.drawTrees(trees);

		System.out.printf("%f seconds taken",
				(System.currentTimeMillis() - start) / 1000.0);

	}
}