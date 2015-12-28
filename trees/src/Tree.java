import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import javax.swing.JComponent;

public class Tree {
	Vector2 position;

	int leafCount;
	int treeWidth;
	int treeHeight;
	int trunkHeight;

	int minDistance;
	int maxDistance;
	int branchLength;

	int crownRotationDegrees;

	int colorSchemeIndex;

	boolean doneGrowing = false;

	Branch root;
	List<Leaf> leaves;
	List<Leaf> leavesCopy;
	HashMap<Vector2, Branch> branches;

	Rectangle crown;

	public Tree(Vector2 position, int leafCount, int treeWidth, int treeHeight,
			int trunkHeight, int minDistance, int maxDistance,
			int branchLength, int crownRotationDegrees, int colorSchemeIndex) {
		this.position = position;
		this.leafCount = leafCount;
		this.treeWidth = treeWidth;
		this.treeHeight = treeHeight;
		this.trunkHeight = trunkHeight;
		this.minDistance = minDistance;
		this.maxDistance = maxDistance;
		this.branchLength = branchLength;
		this.crownRotationDegrees = crownRotationDegrees;
		this.colorSchemeIndex = colorSchemeIndex;

		generateCrown();
		generateTrunk();

		// grow branches
		for (int i = 0; i < 1000; i++) {
			grow();
		}
	}

	public Tree(Vector2 position) {
		this(position, 800, 500, 500, 100, 5, 800, 4, 0, 0);
	}

	public Tree(Vector2 position, int colorSchemeIndex) {
		this(position, 800, 500, 500, 100, 5, 800, 4, 0, colorSchemeIndex);
	}

	public void draw(Graphics g, JComponent comp) {
		// draw branches
		for (Branch b : branches.values()) {
			b.draw(g, this);
		}

		// draw leaves
		for (Leaf l : leavesCopy) {
			l.draw(g, comp, colorSchemeIndex);
		}
	}

	private void generateCrown() {
		crown = new Rectangle((int) position.x - treeWidth / 2,
				(int) position.y - treeHeight - trunkHeight + 20, treeWidth,
				treeHeight);

		List<Point> pts = new ArrayList<>();

		pts.add(new Point(crown.x + crown.width / 4, crown.y + 200));
		pts.add(new Point(crown.x + crown.width / 2, crown.y - 20 + 10));
		pts.add(new Point(crown.x + 3 * crown.width / 4, crown.y + 200));
		pts.add(new Point(crown.x + 4 * crown.width / 5, crown.y + crown.height
				- 50));
		pts.add(new Point(crown.x + crown.width / 2, crown.y + crown.height));
		pts.add(new Point(crown.x + crown.width / 5, crown.y + crown.height
				- 50));

		Polygon crownPol = new Polygon();

		for (int i = 0; i < pts.size(); i++) {
			crownPol.addPoint(pts.get(i).x, pts.get(i).y);
		}

		Point center = new Point((int) crownPol.getBounds().getCenterX(),
				(int) crownPol.getBounds().getMaxY());

		Polygon rotatedCrownPol = new Polygon();

		for (Point p : pts) {
			Point r = ImageUtils.rotatePoint(p, center, crownRotationDegrees);
			rotatedCrownPol.addPoint(r.x, r.y);
		}

		leaves = new ArrayList<Leaf>();
		leavesCopy = new ArrayList<Leaf>();

		Random r = new Random();

		while (leaves.size() < leafCount) {
			int randX, randY;

			randX = (int) (crown.getLeft() + r.nextDouble()
					* (crown.getRight() - crown.getLeft()));
			randY = (int) (crown.getTop() + r.nextDouble()
					* (crown.getBottom() - crown.getTop()));

			if (rotatedCrownPol.contains(randX, randY)) {
				Leaf leaf = new Leaf(randX, randY);
				leaves.add(leaf);
				leavesCopy.add(leaf);
			}
		}
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
			leavesCopy.get(i).closestBranch = null;
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
					if (leaves.get(i).closestBranch == null) {
						leaves.get(i).closestBranch = b;
						leavesCopy.get(i).closestBranch = b;
					} else if ((leaves.get(i).position.subtractNew(leaves
							.get(i).closestBranch.position)).getLength() > distance) {
						leaves.get(i).closestBranch = b;
						leavesCopy.get(i).closestBranch = b;
					}
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
					leavesCopy.get(i).closestBranch.growDirection.add(dir);
					leavesCopy.get(i).closestBranch.growCount++;
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