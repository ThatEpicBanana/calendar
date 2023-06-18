package calendar.state.layer;

import java.util.function.IntSupplier;

import calendar.state.State;

public class ScrollableLayer extends SelectionsLayer {
    private int scroll = 0;
    private int windowHeight;
    private IntSupplier fullHeight;

    public ScrollableLayer(State state) {
        super(state);
    }

    public ScrollableLayer(State state, IntSupplier fullHeight) {
        super(state);
        this.fullHeight = fullHeight;
    }

    public int fullHeight() { return fullHeight.getAsInt(); }
    public void setHeightSupplier(IntSupplier supplier) { this.fullHeight = supplier; }

    // window height automatically gets set once a ScrollableCanvas is created
    public int windowHeight() { return this.windowHeight; }
    public void setWindowHeight(int windowHeight) { this.windowHeight = windowHeight; boundSelection(); }

    public int scroll() { return this.scroll; }
    public void setScroll(int scroll) { this.scroll = scroll; boundSelection(); }

    public void setSelection(int i) {
        this.selection = i;
        boundSelection();
        state.updateScreen();
    }

    private void boundSelection() {
        int sel = this.selection;
        if(sel < scroll)
            this.scroll = sel;
        else if(sel > scroll + windowHeight - 1)
            this.scroll = sel - windowHeight + 1;
    }
}
