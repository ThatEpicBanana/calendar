package calendar.drawing.layers;

// TODO: remove all this and just replace it with Drawable
public class Layer {
    // 2D array of all of the chars in the layer
    public char[][] chars;

    private int width;
    private int height;

    public Layer(int width, int height) {
        this.width = width;
        this.height = height;

        this.chars = new char[width][height];
        this.fill(' ');
    }

    private void fill(char with) {
        for(int i = 0; i < height; i++)
            for(int j = 0; j < width; j++)
                this.chars[i][j] = with;
    }

    public void print() {
        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++)
                System.out.print(this.chars[i][j]);
            System.out.println();
        }
    }

    public int width() { return width; }
    public int height() { return height; }
}
