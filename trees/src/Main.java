import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main extends JPanel {
	public void paint(Graphics g1) {
		Graphics2D g = (Graphics2D) g1;
		Tree t = new Tree(new Vector2(400, 600), g);

		//draw leaves
		for (Leaf l : t.leaves) {
			l.draw(g, this);
		}
		
		//grow branches
		for (int i = 0; i < 500; i++) {
			t.grow();
		}

		//draw branches
		for (Branch b : t.branches.values()) {
			b.draw(g);
		}
	}

	public static void main(String[] a) {
		JFrame f = new JFrame();

		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		f.setContentPane(new Main());
		f.setSize(800, 1000);
		f.setVisible(true);
	}
}