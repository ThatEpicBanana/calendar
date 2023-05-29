package calendar.drawing.layers;

import calendar.drawing.Canvas;
import calendar.state.State;

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

        drawText(canvas, " Help ", 0, colors().helpText(), colors().textBackground());

        int margin = 4;
        int boxy = line(2);
        int boxwidth = width() - margin * 2;
        int boxheight = maxLines() - 2;

        // box
        canvas.highlightBox(margin,  boxy, boxwidth, boxheight, colors().helpText(), colors().textBackground());

        for(int i = 0; i < rows.length; i++) {
            String textrow = rows[i];
            int x = margin + (boxwidth - textrow.length()) / 2;
            canvas.drawText(rows[i], x, boxy + i);
        }

        return canvas;
    }
}
