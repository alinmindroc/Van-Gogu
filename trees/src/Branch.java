import java.awt.Graphics;

public class Branch {
	public Branch parent;
	public Vector2 growDirection;
	public Vector2 originalGrowDirection;
	public int growCount;
	public Vector2 position;

	public Branch(Branch parent, Vector2 position, Vector2 growDirection) {
		this.parent = parent;
		this.position = position;
		this.growDirection = growDirection;
		this.originalGrowDirection = growDirection;
	}

	public void reset() {
		growCount = 0;
		growDirection = originalGrowDirection;
	}

	public void draw(Graphics g) {
		if (parent == null)
			return;
		g.drawLine((int) position.x, (int) position.y, (int) parent.position.x,
				(int) parent.position.y);
	}

	public String toString() {
		return "B " + position.toString();
	}
}
