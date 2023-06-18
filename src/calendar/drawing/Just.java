package calendar.drawing;

import calendar.drawing.canvas.Canvas;
import calendar.util.Vec2;

// some justification / positioning
public interface Just {
    // get the justified location
    // using the dimensions of the canvas
    // and the dimensions of what's getting drawn
    Vec2 get(Vec2 dims, Vec2 drawingDims);

    default Canvas getCanvas(Canvas source, Vec2 dims) {
        Vec2 pos = this.get(source.dims(), dims);
        return source.offset(pos.x, pos.y, dims.x, dims.y);
    }



    static int maxX(Vec2 dims, Vec2 drawingDims) {
        return dims.x - drawingDims.x - 1;
    }

    static int maxY(Vec2 dims, Vec2 drawingDims) {
        return dims.y - drawingDims.y;
    }

    static int centeredX(Vec2 dims, Vec2 drawingDims) {
        return (dims.x - drawingDims.x) / 2;
    }

    static int centeredY(Vec2 dims, Vec2 drawingDims) {
        return (dims.y - drawingDims.y) / 2;
    }



    public static Just at(int x, int y) {
        return (dims, drawingDims) -> new Vec2(x, y);
    }


    public static Just leftOfRow(int y) {
        return (dims, drawingDims) -> new Vec2(1, y);
    }

    public static Just rightOfRow(int y) {
        return (dims, drawingDims) -> new Vec2(maxX(dims, drawingDims), y);
    }

    public static Just centeredOnRow(int y) {
        return (dims, drawingDims) -> new Vec2(centeredX(dims, drawingDims), y);
    }


    public static Just topLeft() {
        return (dims, drawingDims) -> new Vec2(1, 1);
    }

    public static Just topRight() {
        return (dims, drawingDims) -> new Vec2(maxX(dims, drawingDims), 1);
    }

    public static Just bottomLeft() {
        return (dims, drawingDims) -> new Vec2(1, maxY(dims, drawingDims));
    }

    public static Just bottomRight() {
        return (dims, drawingDims) -> new Vec2(maxX(dims, drawingDims), maxY(dims, drawingDims));
    }


    public static Just centerTop() {
        return (dims, drawingDims) -> new Vec2(centeredX(dims, drawingDims), 0);
    }

    public static Just centerBottom() {
        return (dims, drawingDims) -> new Vec2(centeredX(dims, drawingDims), maxY(dims, drawingDims));
    }

    public static Just centerLeft() {
        return (dims, drawingDims) -> new Vec2(0, centeredY(dims, drawingDims));
    }

    public static Just centerRight() {
        return (dims, drawingDims) -> new Vec2(maxX(dims, drawingDims), centeredY(dims, drawingDims));
    }
}
