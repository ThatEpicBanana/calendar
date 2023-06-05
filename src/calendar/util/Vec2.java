package calendar.util;

// a vector of an x and y
public class Vec2 {
    public int x;
    public int y;

    public Vec2(int x, int y) { this.x = x; this.y = y; }

    public String toString() { return String.format("(%d, %d)", x, y); }

    public Vec2 add(Vec2 other) { return new Vec2(this.x + other.x, this.y + other.y); }
    public Vec2 add(int by) { return new Vec2(this.x + by, this.y + by); }

    public Vec2 sub(Vec2 other) { return new Vec2(this.x - other.x, this.y - other.y); }
    public Vec2 sub(int by) { return new Vec2(this.x - by, this.y - by); }

    public Vec2 mult(int by) { return new Vec2(this.x * by, this.y * by); }
    public Vec2 multElements(Vec2 other) { return new Vec2(this.x * other.x, this.y * other.y); }

    public double len() { return Math.sqrt(x*x + y*y); }

    public Vec2 withX(int x) { return new Vec2(x, this.y); }
    public Vec2 withY(int y) { return new Vec2(this.x, y); }

    public Vec2 addX(int x) { return new Vec2(this.x + x, y); }
    public Vec2 addY(int y) { return new Vec2(x, this.y + y); }
}
