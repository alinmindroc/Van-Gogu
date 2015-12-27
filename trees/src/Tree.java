import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class Tree {
	public Point rotatePoint(Point pt, Point center, double angleDeg) {
		if(angleDeg == 0)
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

	boolean doneGrowing = false;

	Vector2 position = new Vector2(0, 0);

	int leafCount = 200;
	int treeWidth = 200;
	int treeHeight = 300;
	int trunkHeight = 100;
	int minDistance = 4;
	int maxDistance = 40;
	int branchLength = 2;

	Branch root;
	List<Leaf> leaves;
	HashMap<Vector2, Branch> branches;

	Rectangle crown;
	Graphics g;

	public Tree(Vector2 position, Graphics g) {
		this.position = position;
		this.g = g;
		generateCrown();
		generateTrunk();
	}

	public void print() {
		System.out.println(root);
		System.out.println(leaves);
		System.out.println(branches);
	}

	private void generateCrown() {
		crown = new Rectangle((int) position.x - treeWidth / 2,
				(int) position.y - treeHeight - trunkHeight + 20, treeWidth,
				treeHeight);

		List<Point> pts = new ArrayList<>();

		pts.add(new Point(crown.x, crown.y + 50));
		pts.add(new Point(crown.x + crown.width / 2, crown.y - 20 + 10));
		pts.add(new Point(crown.x + crown.width, crown.y + 50));
		pts.add(new Point(crown.x + crown.width - 50, crown.y + crown.height - 30));
		pts.add(new Point(crown.x + 50, crown.y + crown.height - 30));

		Polygon crownPol = new Polygon();

		for (int i = 0; i < pts.size(); i++) {
			crownPol.addPoint(pts.get(i).x, pts.get(i).y);
		}

		Point center = new Point((int) crownPol.getBounds().getCenterX(),
				(int) crownPol.getBounds().getCenterY());

		List<Point> rotatedPts = new ArrayList<>();

		Polygon rotatedCrownPol = new Polygon();

		
		int rotateDegree = 0;
		
		for (Point p : pts) {
			Point r = rotatePoint(p, center, rotateDegree);
			rotatedCrownPol.addPoint(r.x, r.y);
		}

//		g.drawPolygon(rotatedCrownPol);
		
		// g.drawPolygon(crownPol);

		// g.drawRect((int) crown.x, (int) crown.y, (int) crown.width,
		// (int) crown.height);

		leaves = new ArrayList<Leaf>();

		Random r = new Random();
		// Vector2 crownCenter = new Vector2(crown.x + crown.width / 2, crown.y
		// + crown.height / 2);
		// crownCenter.y += 40;

		// g.drawRect((int)crownCenter.x, (int)crownCenter.y, 20, 20);

		while (leaves.size() < leafCount) {
			int randX, randY;

			randX = r.nextInt(crown.getRight() - crown.getLeft() + 100)
					+ crown.getLeft() - 100;
			randY = r.nextInt(crown.getBottom() - crown.getTop() + 100)
					+ crown.getTop() - 100;

			if (rotatedCrownPol.contains(randX, randY)) {
				Leaf leaf = new Leaf(randX, randY);
				leaves.add(leaf);
			}
		}

		/*
		 * // randomly place leaves within our rectangle for (int i = 0; i <
		 * leafCount; i++) { // Vector2 location = new Vector2( //
		 * r.nextInt((int) (crown.getRight() - crown.getLeft())) // +
		 * crown.getLeft(), r.nextInt((int) (crown // .getBottom() -
		 * crown.getTop())) + crown.getTop());
		 * 
		 * int randX, randY;
		 * 
		 * randX = r.nextInt(crown.getRight() - crown.getLeft()) +
		 * crown.getLeft(); randY = r.nextInt(crown.getBottom() -
		 * crown.getTop()) + crown.getTop();
		 * 
		 * Vector2 location = new Vector2(randX, randY);
		 * 
		 * if (location.distance(crownCenter) < 100) { Leaf leaf = new
		 * Leaf(location); leaves.add(leaf); } }
		 */
	}

	private void generateTrunk() {
		branches = new HashMap<Vector2, Branch>();

		root = new Branch(null, position, new Vector2(0, -1));
		branches.put(root.position, root);

		Branch current = new Branch(root, new Vector2(position.x, position.y
				- branchLength), new Vector2(0, -1));
		branches.put(current.position, current);

		// Keep growing trunk upwards until we reach a leaf
		while ((root.position.subtractNew(current.position).getLength()) < trunkHeight) {
			Branch trunk = new Branch(current, new Vector2(current.position.x,
					current.position.y - branchLength), new Vector2(0, -1));
			branches.put(trunk.position, trunk);
			current = trunk;
		}
	}

	public void grow() {
		if (doneGrowing)
			return;

		// If no leaves left, we are done
		if (leaves.size() == 0) {
			doneGrowing = true;
			return;
		}

		// process the leaves
		for (int i = 0; i < leaves.size(); i++) {
			boolean leafRemoved = false;

			leaves.get(i).closestBranch = null;
			Vector2 direction = new Vector2(0, 0);

			// Find the nearest branch for this leaf
			for (Branch b : branches.values()) {
				direction = leaves.get(i).position.subtractNew(b.position); // direction
																			// to
																			// branch
																			// from
																			// leaf
				float distance = (float) Math.round(direction.getLength()); // distance
																			// to
																			// branch
																			// from
																			// leaf
				direction.normalize();

				if (distance <= minDistance) // Min leaf distance reached,
												// we remove it
				{
					leaves.remove(i);
					i--;
					leafRemoved = true;
					break;
				} else if (distance <= maxDistance) // branch in range,
													// determine if it is
													// the nearest
				{
					if (leaves.get(i).closestBranch == null)
						leaves.get(i).closestBranch = b;
					else if ((leaves.get(i).position
							.subtractNew(leaves.get(i).closestBranch.position))
							.getLength() > distance)
						leaves.get(i).closestBranch = b;
				}
			}

			// if the leaf was removed, skip
			if (!leafRemoved) {
				// Set the grow parameters on all the closest branches that
				// are in range
				if (leaves.get(i).closestBranch != null) {
					Vector2 dir = leaves.get(i).position.subtractNew(leaves
							.get(i).closestBranch.position);
					dir.normalize();
					leaves.get(i).closestBranch.growDirection.add(dir); // add
																		// to
																		// grow
																		// direction
																		// of
																		// branch
					leaves.get(i).closestBranch.growCount++;
				}
			}
		}

		// Generate the new branches
		HashSet<Branch> newBranches = new HashSet<Branch>();
		for (Branch b : branches.values()) {
			if (b.growCount > 0) // if at least one leaf is affecting the
									// branch
			{
				Vector2 avgDirection = b.growDirection.divide(b.growCount);
				if (avgDirection.getLength() == 0)
					continue;
				avgDirection.normalize();

				Branch newBranch = new Branch(b, b.position.addNew(avgDirection
						.multiplyNew(branchLength)), avgDirection);

				newBranches.add(newBranch);
				b.reset();
			}
		}

		// Add the new branches to the tree
		boolean branchAdded = false;
		for (Branch b : newBranches) {
			// Check if branch already exists. These cases seem to happen
			// when leaf is in specific areas
			if (!branches.containsKey(b)) {
				branches.put(b.position, b);
				branchAdded = true;
			}
		}

		// if no branches were added - we are done
		// this handles issues where leaves equal out each other, making
		// branches grow without ever reaching the leaf
		if (!branchAdded)
			doneGrowing = true;
	}
}