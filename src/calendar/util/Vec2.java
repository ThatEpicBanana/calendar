package calendar.util;

// a vector of an x and y
public class Vec2 {
    public int x;
    public int y;

    public Vec2(int x, int y) { this.x = x; this.y = y; }

    public String toString() { return String.format("(%d, %d)", x, y); }
}
