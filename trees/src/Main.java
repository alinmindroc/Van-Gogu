import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main extends JPanel {
	public void paint(Graphics g1) {
		Graphics2D g = (Graphics2D) g1;
		g1.setColor(new Color(93, 46, 16));
		Tree t1 = new Tree(new Vector2(500, 900), g);
		t1.draw(this);

//		g1.setColor(Color.blue);
//		Tree t2 = new Tree(new Vector2(360, 700), g);
//		t2.draw(this);
//
//		Tree t3 = new Tree(new Vector2(550, 775), g);
//		t3.draw(this);
	}

	public static void main(String[] a) {
		JFrame f = new JFrame();

		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		f.setContentPane(new Main());
		f.setSize(1000, 1000);
		f.setVisible(true);
	}
}