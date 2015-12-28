import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Landscape {
	
	public static final int arraySize = 1000;
	
	public int[] terrainArray;
	public Color[] colors;
	
	private int start;
	private int change;
	private int factor;
	private int last;
	private int plus;
	Random rnd = new Random ();
		
	public Landscape() {
		factor = 1;
		terrainArray = new int[arraySize];
		colors = new Color[arraySize];
		generateLandscape();
	}
	
	public void generateLandscape() {
		plus = 1;
		start = Math.abs(300 + (rnd.nextInt() % 20));
		terrainArray[0] = start;
		
		// TODO - add gradient
		int redvalue = 79;
		int greenvalue = 58;
		int bluevalue = 60;
		
		colors[0] = new Color(redvalue, greenvalue, bluevalue);
		for (int i = 1; i < arraySize; i ++) {
			last = terrainArray[i - 1];
			change = Math.abs(rnd.nextInt() % 10);

			if (change > 8) {
				factor = -(factor);
				plus = 1 + Math.abs(rnd.nextInt() % 2);
			}

			if (last > 600 || last < 200) {
				factor = -(factor);
			}
			// TODO - change values for red,green,blue
			colors[i] = new Color(redvalue, greenvalue, bluevalue);
			terrainArray[i] = last + (factor * plus);
		}
	}
	
	public void draw(Graphics g) {
		g.setColor(new Color(168, 243, 255));
		g.fillRect(0, 0, 1000, 1000);
		for (int i = 0; i < arraySize; i++) {
			g.setColor(colors[i]);
			g.drawLine (i, terrainArray[i], i, 1000);
		}
	}
}
