package app;
import image_utils.ImageUtils;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.List;

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

	public void draw(Graphics g, Tree t) {
//		if(parent != null)
//		g.drawLine((int)position.x, (int)position.y, (int)parent.position.x, (int)parent.position.y);
//		/*
		if (parent == null)
			return;

		int x = (int) position.x;
		int y = (int) position.y;

		List<Point> pts = new ArrayList<>();

		int width = 2;

		Vector2 crownBase = t.position.addNew(new Vector2(0, -t.trunkHeight));

		double trunkDistance = position.distance(crownBase);
		double normalizedDistanceToTrunk = 1.6 - (1.4 * trunkDistance / (t.treeHeight));

		int maxWidth = 4;
		width = (int) (normalizedDistanceToTrunk * maxWidth);
		
		Vector2 dir = parent.position.subtractNew(position);
		double dirAngle = Math.atan2(dir.y, dir.x);

		int dist = (int) position.distance(parent.position);

		int distOffset = dist;

		pts.add(new Point(x - distOffset, y));
		pts.add(new Point(x + dist + distOffset, y));
		pts.add(new Point(x + dist + distOffset, y + width));
		pts.add(new Point(x - distOffset, y + width));

		Point center = new Point(x + dist / 2, y + width / 2);

		Polygon branchPol = new Polygon();

		for (int i = 0; i < pts.size(); i++) {
			Point p = new Point(pts.get(i).x, pts.get(i).y);
			ImageUtils.rotatePoint(p, center, 180 * dirAngle / Math.PI);
			branchPol.addPoint(p.x, p.y);
		}

		g.fillPolygon(branchPol);
//		*/
	}

	public String toString() {
		return "B " + position.toString();
	}
}
