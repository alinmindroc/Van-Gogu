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
	public int accumulatedGrowCount = 0;

	public Branch(Branch parent, Vector2 position, Vector2 growDirection) {
		this.parent = parent;
		this.position = position;
		this.growDirection = growDirection;
		this.originalGrowDirection = growDirection;
	}

	public void reset() {
		accumulatedGrowCount += growCount;
		growCount = 0;
		growDirection = originalGrowDirection;
	}

	public Point rotatePoint(Point pt, Point center, double angleDeg) {
		if (angleDeg == 0)
			return pt;
		double angleRad = (angleDeg / 180) * Math.PI;
		double cosAngle = Math.cos(angleRad);
		double sinAngle = Math.sin(angleRad);
		double dx = (pt.x - center.x);
		double dy = (pt.y - center.y);

		pt.x = center.x + (int) (dx * cosAngle - dy * sinAngle);
		pt.y = center.y + (int) (dx * sinAngle + dy * cosAngle);
		return pt;
	}

	public void draw(Graphics g, int maxBranchGrowCount) {
		if (parent == null)
			return;

		int x = (int) position.x;
		int y = (int) position.y;

		int px = (int) parent.position.x;
		int py = (int) parent.position.y;

		List<Point> pts = new ArrayList<>();

		int branchCount = accumulatedGrowCount;
		int maxBranches = maxBranchGrowCount;
		int width;

		if (branchCount < maxBranches / 4) {
			width = 4;
		} else if (branchCount < maxBranches / 2) {
			width = 6;
		} else if (branchCount < 3 * maxBranches / 4) {
			width = 8;
		} else {
			width = 10;
		}

		Vector2 dir = parent.position.subtractNew(position);
		double dirAngle = Math.atan2(dir.y, dir.x);

		int dist = (int) position.distance(parent.position);

		int distOffset = dist / 2;
		
		pts.add(new Point(x - distOffset, y));
		pts.add(new Point(x + dist + distOffset, y));
		pts.add(new Point(x + dist + distOffset, y + width));
		pts.add(new Point(x - distOffset, y + width));

		Point center = new Point(x + dist / 2, y + width / 2);

		Polygon branchPol = new Polygon();
		Polygon rotatedBranchPol = new Polygon();

		for (int i = 0; i < pts.size(); i++) {
			Point p = new Point(pts.get(i).x, pts.get(i).y);
			rotatePoint(p, center, 180 * dirAngle / Math.PI);
			rotatedBranchPol.addPoint(p.x, p.y);
		}

		g.fillPolygon(rotatedBranchPol);
		// g.drawLine((int) position.x, (int) position.y, (int)
		// parent.position.x,
		// (int) parent.position.y);
	}

	public void draw2(Graphics g) {
		if (parent == null)
			return;
		g.drawRect((int) position.x, (int) position.y, 5, 5);
		// g.drawLine((int) position.x, (int) position.y, (int)
		// parent.position.x,
		// (int) parent.position.y);
	}

	public String toString() {
		return "B " + position.toString();
	}
}
