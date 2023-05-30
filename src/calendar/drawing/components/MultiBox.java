package calendar.drawing.components;

import calendar.drawing.Canvas;
import calendar.drawing.Drawable;

// a set of boxes or grids, all merged together
public class MultiBox implements Drawable {
    public final Drawable[] boxes;

    // separated from the grids because it's easier
    public final int[] boxx;
    public final int[] boxy;

    private final int width;
    private final int height;

    // large rectangle with overlayed grids
    public MultiBox(Drawable[] boxes, int[] boxx, int[] boxy, int width, int height) {
        this.boxes = boxes;
        this.boxx = boxx;
        this.boxy = boxy;

        this.width = width;
        this.height = height;
    }

    public int height() { return height; }
    public int  width() { return width; }

    public Canvas draw() {
        Canvas canvas = Canvas.rectangle(width, height, false);

        for(int i = 0; i < boxes.length; i++)
            canvas.merge(boxx[i], boxy[i], boxes[i].draw());

        return canvas;
    }

}
