public class Rectangle {
	int x, y, width, height;

	public Rectangle(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public int getLeft() {
		return Math.min(x, x + width);
	}

	public int getRight() {
		return Math.max(x, x + width);
	}

	public int getTop() {
		return Math.min(y, y + height);
	}

	public int getBottom() {
		return Math.max(y, y + height);
	}

	public String toString() {
		return String.format("%f %f %f %f", x, y, width, height);
	}
}
