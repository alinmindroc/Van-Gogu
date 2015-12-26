import java.awt.Graphics;

public class Leaf {
	public Vector2 position;
	public Branch closestBranch;

	public Leaf(Vector2 position) {
		this.position = position;
	}
	
	public String toString(){
		return "L " + position.toString();
	}
	
	public void draw(Graphics g){
		g.drawRect((int) position.x, (int) position.y, 3, 3);
	}
}
