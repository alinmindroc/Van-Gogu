import java.awt.Point;

public class Vector2 {
	public double x, y;

	public Vector2(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector2(Point p){
		this.x = p.x;
		this.y = p.y;
	}

	public Vector2 divide(double d) {
		return new Vector2(x / d, y / d);
	}

	public void add(Vector2 v) {
		x += v.x;
		y += v.y;
	}

	public Vector2 addNew(Vector2 v) {
		return new Vector2(x + v.x, y + v.y);
	}

	public Vector2 subtractNew(Vector2 v) {
		return new Vector2(x - v.x, y - v.y);
	}

	public Vector2 multiplyNew(double m) {
		return new Vector2(x * m, y * m);
	}

	public double getLength() {
		return (double) Math.sqrt(x * x + y * y);
	}

	public void normalize() {
		double len = getLength();
		x /= len;
		y /= len;
	}

	public double distance(Vector2 v) {
		return (double) Math.sqrt(Math.pow((v.x - x), 2) + Math.pow((v.y - y), 2));
	}

	public String toString() {
		return String.format("(%f,%f)", x, y);
	}
}
