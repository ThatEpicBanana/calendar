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



    static int maxX(Vec2 dims, Vec2 drawingDims) { return maxX(dims, drawingDims, 1); }
    static int maxX(Vec2 dims, Vec2 drawingDims, int off) { return dims.x - drawingDims.x - off; }
    static int maxY(Vec2 dims, Vec2 drawingDims) { return dims.y - drawingDims.y; }

    static int centeredX(Vec2 dims, Vec2 drawingDims) { return (dims.x - drawingDims.x) / 2; }
    static int centeredY(Vec2 dims, Vec2 drawingDims) { return (dims.y - drawingDims.y) / 2; }



    public static Just at(int x, int y) {
        return (dims, drawingDims) -> new Vec2(x, y); }

    public static Just centered() {
        return (dims, drawingDims) -> new Vec2(centeredX(dims, drawingDims), centeredY(dims, drawingDims)); }


    public static Just leftOfRow(int y) {
        return (dims, drawingDims) -> new Vec2(1, y); }

    public static Just rightOfRow(int y) {
        return (dims, drawingDims) -> new Vec2(maxX(dims, drawingDims), y); }

    public static Just centeredOnRow(int y) {
        return (dims, drawingDims) -> new Vec2(centeredX(dims, drawingDims), y); }


    public static Just topLeft() { return offTopLeftBy(1); }
    public static Just topRight() { return offTopRightBy(1); }
    public static Just bottomLeft() { return offBottomLeftBy(1); }
    public static Just bottomRight() { return offBottomRightBy(1); }

    public static Just offTopLeftBy(int off) {
        return (dims, drawingDims) -> new Vec2(off, 0); }

    public static Just offTopRightBy(int off) {
        return (dims, drawingDims) -> new Vec2(maxX(dims, drawingDims, off), 0); }

    public static Just offBottomLeftBy(int off) {
        return (dims, drawingDims) -> new Vec2(off, maxY(dims, drawingDims)); }

    public static Just offBottomRightBy(int off) {
        return (dims, drawingDims) -> new Vec2(maxX(dims, drawingDims, off), maxY(dims, drawingDims)); }


    public static Just centerTop() {
        return (dims, drawingDims) -> new Vec2(centeredX(dims, drawingDims), 0); }

    public static Just centerBottom() {
        return (dims, drawingDims) -> new Vec2(centeredX(dims, drawingDims), maxY(dims, drawingDims)); }

    public static Just centerLeft() {
        return (dims, drawingDims) -> new Vec2(0, centeredY(dims, drawingDims)); }

    public static Just centerRight() {
        return (dims, drawingDims) -> new Vec2(maxX(dims, drawingDims), centeredY(dims, drawingDims)); }


    public static Just offsetFrom(Just other, Vec2 offset) {
        return (dims, drawingDims) -> other.get(dims, drawingDims).add(offset); }

    public static Just rightOf(Just other, Vec2 otherDims) {
        return (dims, drawingDims) -> 
            other.get(dims, otherDims)
                 .addX(1)
                 .addY((otherDims.y - drawingDims.y) / 2);
    }
}
