package calendar.drawing.color;

// represents a simple color
public class Color {
    public int r;
    public int g;
    public int b;

    public Color(int r, int g, int b) {
        this.r = r; this.g = g; this.b = b;
    }

    // determines whether this color's transparency 
    // overwites other colors when overlaying
    // (such as with a popup with a transparent background)
    public boolean forceTransparent;

    private Color(boolean forceTransparent) {
        this.forceTransparent = forceTransparent;
    }

    public static final Color FORCE_TRANSPARENT = new Color(true);

    public String toString() {
        return String.format("#%h%h%h", r, g, b);
    }
}
