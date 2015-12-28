import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main extends JPanel {
	static Tree t1, t2;

	@Override
	public void paint(Graphics g1) {
		Graphics2D g = (Graphics2D) g1;
		g.setColor(new Color(93, 46, 16));

		t1.draw(g);
		t2.draw(g);
	}

	public static void main(String[] a) {
		long start = System.currentTimeMillis();
		Random rand = new Random();
		int randVal = rand.nextInt(5);
		randVal = 0;

		t1 = new Tree(new Vector2(500, 900), randVal);
		t2 = new Tree(new Vector2(700, 900), randVal);

		ImageUtils.drawToFile(1000, 1000, t1, t2);
		
		/*
		JFrame f = new JFrame();

		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		f.setContentPane(new Main());
		f.setSize(1000, 1000);
		f.setVisible(true);
		*/
		System.out.println(System.currentTimeMillis() - start);
	}
}