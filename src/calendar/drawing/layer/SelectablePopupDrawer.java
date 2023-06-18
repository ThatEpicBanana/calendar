package calendar.drawing.layer;

import calendar.drawing.canvas.Widgets;
import calendar.state.State;
import calendar.state.layer.SelectionsLayer;

public class SelectablePopupDrawer extends PopupDrawer {
    private SelectionsLayer layer;

    public SelectablePopupDrawer(int width, State state, SelectionsLayer layer) {
        super(width, state, new Widgets(state, layer));
        this.layer = layer;
    }

    public int selection() { return layer.selection(); }
    public boolean selected(int y) { return layer.selected(y); }

    public String sanitize(String text, int maxWidth, int line) {
        int len = text.length();
        if(selected(line) && layer.editing())
            // from the end
            return text.substring(len - Math.min(len, maxWidth));
        else
            // from the beginning
            return text.substring(0, Math.min(len, maxWidth));
    }
}
