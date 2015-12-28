import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main extends JPanel {
	static Tree t1, t2;

	public void paint(Graphics g1) {
		// long start = System.currentTimeMillis();

		Graphics2D g = (Graphics2D) g1;
		g1.setColor(new Color(93, 46, 16));

		System.out.println("here");
		t1.draw(g, this);
		t2.draw(g, this);

		// g1.setColor(Color.blue);
		// Tree t2 = new Tree(new Vector2(360, 700), g);
		// t2.draw(this);
		//
		// Tree t3 = new Tree(new Vector2(550, 775), g);
		// t3.draw(this);
		// System.out.println(System.currentTimeMillis() - start);
	}

	public static void main(String[] a) {
		long start = System.currentTimeMillis();
		Random rand = new Random();
		int randVal = rand.nextInt(5);

		t1 = new Tree(new Vector2(500, 900), randVal);
		t2 = new Tree(new Vector2(700, 900), randVal);

		JFrame f = new JFrame();

		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		f.setContentPane(new Main());
		f.setSize(1000, 1000);
		f.setVisible(true);
		
		System.out.println(System.currentTimeMillis() - start);
	}
}