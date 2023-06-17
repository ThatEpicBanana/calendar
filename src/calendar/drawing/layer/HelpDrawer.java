package calendar.drawing.layer;

import calendar.drawing.Canvas;
import calendar.drawing.Just;
import calendar.state.State;

// a generic help popup
// see input.layer.MonthLayer for month help 
public class HelpDrawer extends PopupDrawer {
    private String[] rows;

    public HelpDrawer(int width, State state, String[] rows) {
        super(width, state);
        this.rows = rows;
    }

    public HelpDrawer(int width, State state, String rows) {
        super(width, state);

        this.rows = rows.split("\n");
    }

    public Canvas draw() {
        Canvas canvas = super.draw();
        Canvas inset = canvas.offsetMargin(2);

        inset.text(" Help ", Just.centeredOnRow(0), state.monthColorText(), state.monthColor());

        Canvas box = inset.offsetCenteredMargin(2, 2, inset.height() - 2);
        box.fill(colors().helpText(), colors().textBackground());

        for(int i = 0; i < rows.length; i++)
            box.text(rows[i], Just.leftOfRow(i));

        return canvas;
    }
}
