public class Rectangle {
	double x, y, width, height;

	public Rectangle(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public double getLeft() {
		return Math.min(x, x + width);
	}

	public double getRight() {
		return Math.max(x, x + width);
	}

	public double getTop() {
		return Math.min(y, y + height);
	}

	public double getBottom() {
		return Math.max(y, y + height);
	}

	public String toString() {
		return String.format("%f %f %f %f", x, y, width, height);
	}
}
