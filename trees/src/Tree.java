import java.awt.Graphics;
import java.awt.Shape;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class Tree {
	boolean doneGrowing = false;

	Vector2 position = new Vector2(0, 0);

	int leafCount = 400;
	int treeWidth = 200;
	int treeHeight = 300;
	int trunkHeight = 100;
	int minDistance = 4;
	int maxDistance = 40;
	int branchLength = 5;

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
				(int) position.y - treeHeight - trunkHeight, treeWidth,
				treeHeight);

		// g.drawRect((int) crown.x, (int) crown.y, (int) crown.width,
		// (int) crown.height);

		leaves = new ArrayList<Leaf>();

		Random r = new Random();
		Vector2 crownCenter = new Vector2(crown.x + crown.width / 2,
				crown.y + crown.height / 2);
		crownCenter.y += 40;

//		g.drawRect((int)crownCenter.x, (int)crownCenter.y, 20, 20);
		
		// randomly place leaves within our rectangle
		for (int i = 0; i < leafCount; i++) {
			// Vector2 location = new Vector2(
			// r.nextInt((int) (crown.getRight() - crown.getLeft()))
			// + crown.getLeft(), r.nextInt((int) (crown
			// .getBottom() - crown.getTop())) + crown.getTop());

			double randX, randY;

			randX = crown.getLeft() + r.nextDouble()
					* (crown.getRight() - crown.getLeft());
			randY = crown.getTop() + r.nextDouble()
					* (crown.getBottom() - crown.getTop());

			Vector2 location = new Vector2(randX, randY);

			if (location.distance(crownCenter) < 100) {
				Leaf leaf = new Leaf(location);
				leaves.add(leaf);
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