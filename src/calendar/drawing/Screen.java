package calendar.drawing;

import calendar.drawing.color.Theme;
import calendar.drawing.layers.Month;
import calendar.drawing.layers.Popup;
import calendar.drawing.layers.SectionPopup;
import calendar.state.State;

public class Screen implements Drawable {
    public Month month;
    public Popup popup;

    private int width;
    private int height;

    private int cellWidth;
    private int cellHeight;

    private State state;

    public Screen(int width, int height, int cellWidth, int cellHeight, State state) {
        this.month = new Month(state, cellWidth, cellHeight);
        this.popup = null;

        this.state = state;

        this.width = width;
        this.height = height;
        this.cellWidth = cellWidth;
        this.cellHeight = cellHeight;
    }

    public Theme colors() { return state.colors(); }

    public int width() { return this.width; }
    public int height() { return this.height; }

    public void addGenericPopup() { this.popup = new Popup(cellWidth * 3 - 4, state); }

    public boolean addSectionPopup() { 
        if(this.popup == null) {
            this.popup = new SectionPopup(cellWidth * 3 - 4, state);
            return true;
        } else return false;
    }

    public Canvas draw() {
        Canvas canvas = new Canvas(width, height, null, colors().background());

        center(canvas, month.draw(), 0);
        if(popup != null)
            center(canvas, popup.draw(), 2);

        return canvas;
    }

    private void center(Canvas self, Canvas other, int offset) {
        int x = (width() - other.width()) / 2;
        int y = (height() - other.height()) / 2 + offset;
        self.overlay(x, y, other);
    }
}
