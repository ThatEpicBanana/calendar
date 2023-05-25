package calendar.drawing.components;

import calendar.drawing.Canvas;
import calendar.drawing.Drawable;

public class MultiGrid implements Drawable {
    public final Grid[] grids;

    // separated from the grids because it's easier
    private final int[] gridx;
    private final int[] gridy;

    private final int width;
    private final int height;

    // large rectangle with overlayed grids
    public MultiGrid(Grid[] grids, int[] gridx, int[] gridy, int width, int height) {
        this.grids = grids;
        this.gridx = gridx;
        this.gridy = gridy;

        this.width = width;
        this.height = height;
    }

    public int height() { return height; }
    public int  width() { return width; }

    public Canvas draw() {
        Canvas canvas = Canvas.rectangle(width, height, false);

        for(int i = 0; i < grids.length; i++)
            canvas.merge(gridx[i], gridy[i], grids[i].draw());

        return canvas;
    }

}
