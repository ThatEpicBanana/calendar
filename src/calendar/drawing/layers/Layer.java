package calendar.drawing.layers;

public class Layer {
    // 2D array of all of the chars in the layer
    // column arrays!
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
        for(int i = 0; i < width; i++)
            for(int j = 0; j < height; j++)
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
