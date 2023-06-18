package calendar.drawing.layer;

import calendar.drawing.canvas.Canvas;
import calendar.drawing.Just;
import calendar.drawing.color.Color;
import calendar.state.State;
import calendar.state.layer.AddEventLayer;
import calendar.storage.EditingEvent;

// the popup for adding an event
public class AddEventDrawer extends SelectablePopupDrawer {
    private AddEventLayer layer;

    public AddEventDrawer(int width, State state, AddEventLayer layer) {
        super(width, state, layer);
        this.layer = layer;
    }

    public Canvas draw() {
        Canvas canvas = super.draw();
        Canvas inset = canvas.offsetMargin(2);

        EditingEvent event = this.layer.event;

        Color highlight = event.section().color();

        inset.text("  Add  Task  ", Just.centeredOnRow(0), colors().highlightText(), highlight);

        inset.draw(wid.titledText("Title", Just.centeredOnRow(3), event.title(), highlight, 0));
        inset.draw(wid.titledText("Time Frame", Just.centeredOnRow(6), new String[]{ event.start(), event.end() }, highlight, 1));
        inset.draw(wid.titledText("Section", Just.centeredOnRow(10), event.section().title(), highlight, 3));

        inset.draw(wid.button("Confirm", Just.bottomLeft(), highlight, 4));
        inset.draw(wid.button("Abandon", Just.bottomRight(), highlight, 5));

        return canvas;
    } 
}
