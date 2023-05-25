package calendar.drawing;

public class Color {
    public int r;
    public int g;
    public int b;

    public Color(int r, int g, int b) {
        this.r = r; this.g = g; this.b = b;
    }

    public String toString() {
        return String.format("#%h%h%h", r, g, b);
    }
}
