package calendar.drawing;

import java.util.List;

import calendar.drawing.canvas.Canvas;
import calendar.util.Vec2;

// represents an offset from another justification
public interface JustOffset {
    Vec2 get(Vec2 canvas, Vec2 self, Vec2 other, Vec2 otherPost);

    public static JustOffset by(int x, int y) {
        return (canvas, self, other, otherPos) -> otherPos.add(new Vec2(x, y)); }

    public static JustOffset centered() {
        return (canvas, self, other, otherPos) -> otherPos.add(other.sub(self).div(2)); }


    public static JustOffset rightBy(int x) {
        return (canvas, self, other, otherPos) -> 
            otherPos.addX(other.x + x).addY((other.y - self.y) / 2); }

    public static JustOffset leftBy(int x) {
        return (canvas, self, other, otherPos) -> 
            otherPos.subX(self.x + x).addY((other.y - self.y) / 2); }


    public interface DrawableWithOffset extends Drawable {
        JustOffset offset();
    }

    // iteratively draw each drawer
    public static Canvas.Drawer drawAll(List<DrawableWithOffset> drawers, Just firstJust) {
        // simple ones
        if(drawers.size() == 0) return (canvas) -> {};
        else if(drawers.size() == 1) 
            return (canvas) -> canvas.overlay(drawers.get(0).draw(), firstJust);

        // multiple layers, have to do special stuff
        // break up the list
        DrawableWithOffset first = drawers.get(0);
        List<DrawableWithOffset> rest = drawers.subList(1, drawers.size());

        return (canvas) -> {
            // draw first using the given justification
            Vec2 prevDims = first.dims();
            Vec2 prevPos = firstJust.get(canvas.dims(), prevDims);
            canvas.overlay(first.draw(), firstJust);

            // draw the rest all offset from the one before
            for(DrawableWithOffset drawer : rest) {
                // update the next positions
                prevPos = drawer.offset().get(canvas.dims(), drawer.dims(), prevDims, prevPos);
                prevDims = drawer.dims();

                canvas.overlay(prevPos.x, prevPos.y, drawer.draw());
            }
        };
    }
}
