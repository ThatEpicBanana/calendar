package calendar.drawing.layer;

import calendar.drawing.Canvas;
import calendar.drawing.Just;
import calendar.drawing.color.Color;
import calendar.state.State;
import calendar.storage.EditingEvent;

// the popup for adding an event
public class AddEventDrawer extends PopupDrawer {
    private EditingEvent event;

    public AddEventDrawer(int width, EditingEvent event, State state) {
        super(width, state);
        this.event = event;
    }

    public Canvas draw() {
        Canvas canvas = super.draw();
        Canvas inset = canvas.offsetMargin(2);

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
