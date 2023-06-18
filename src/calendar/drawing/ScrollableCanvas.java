package calendar.drawing;

public class ScrollableCanvas extends OffsetCanvas {
    protected ScrollableCanvas(Canvas other, int offx, int offy, int width, int height) {
        super(other, offx, offy, width, height);
    }
}
