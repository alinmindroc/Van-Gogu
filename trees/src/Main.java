import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Point2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main extends JPanel {
	public void paint(Graphics g1) {
		Graphics2D g = (Graphics2D) g1;

		Tree t = new Tree(new Vector2(400, 600), g);

		for (int i = 0; i < 400; i++) {

			for (Branch b : t.branches.values()) {
				b.draw(g);
			}

			for (Leaf l : t.leaves) {
				l.draw(g);
			}
			
			t.grow();
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