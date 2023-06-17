package calendar.drawing.component;

import calendar.drawing.Canvas;
import calendar.drawing.Drawable;
import calendar.util.Vec2;

public class Grid implements Drawable {
    private Vec2 gridDims;
    private Vec2 realDims;
    private Vec2 cellDims;

    private CellDrawer drawer;

    private boolean heavy;

    public interface CellDrawer {
        public void draw(Canvas canvas, Vec2 gridCoord, Vec2 cellDims);
    }

    public Grid(int cellWidth, int cellHeight, int columns, int rows, boolean heavy, CellDrawer drawer) {
        this(new Vec2(cellWidth, cellHeight), new Vec2(columns, rows), heavy, drawer);
    }

    public Grid(Vec2 cellDims, Vec2 gridDims, boolean heavy, CellDrawer drawer) {
        this.gridDims = gridDims;
        this.cellDims = cellDims;
        this.realDims = gridPosToReal(gridDims);

        this.drawer = drawer;

        this.heavy = heavy;
    } 

    public int width() { return realDims.x; }
    public int height() { return realDims.y; }
    public int columns() { return gridDims.x; }
    public int rows() { return gridDims.y; }

    public static Vec2 gridPosToReal(Vec2 gridPos, Vec2 cellDims) 
        { return gridPos.multElements(cellDims.add(1)).add(1); }

    public Vec2 gridPosToReal(Vec2 gridPos) 
        { return gridPosToReal(gridPos, this.cellDims); }

    public Canvas draw() {
        Canvas canvas = Canvas.fromGrid(cellDims.x, cellDims.y, gridDims.x, gridDims.y, heavy);

        for(int column = 0; column < columns(); column++) {
            for(int row = 0; row < rows(); row++) {
                Vec2 grid = new Vec2(column, row);
                Vec2 real = gridPosToReal(grid);

                Canvas inset = canvas.offset(real.x, real.y, cellDims.x, cellDims.y);
                drawer.draw(inset, grid, cellDims);
            }
        }

        return canvas;
    }
}
