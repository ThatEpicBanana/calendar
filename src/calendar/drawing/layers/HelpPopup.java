package calendar.drawing.layers;

import calendar.drawing.Canvas;
import calendar.state.State;

// a generic help popup
// see input.layer.MonthLayer for month help 
public class HelpPopup extends Popup {
    private String[] rows;

    public HelpPopup(int width, State state, String[] rows) {
        super(width, state);
        this.rows = rows;
    }

    public HelpPopup(int width, State state, String rows) {
        super(width, state);

        this.rows = rows.split("\n");
    }

    public Canvas draw() {
        Canvas canvas = super.draw();
        Canvas inset = canvas.offsetMargin(2);

        inset.textCentered(" Help ", 0, state.monthColorText(), state.monthColor());

        int margin = 2;
        int boxy = 2;
        int boxwidth = inset.width() - margin * 2;
        int boxheight = inset.height() - 2;

        // box
        inset.highlightBox(margin, boxy, boxwidth, boxheight, colors().helpText(), colors().textBackground());

        for(int i = 0; i < rows.length; i++) {
            String textrow = rows[i];
            int x = margin + (boxwidth - textrow.length()) / 2;
            inset.text(rows[i], x, boxy + i);
        }

        return canvas;
    }
}
