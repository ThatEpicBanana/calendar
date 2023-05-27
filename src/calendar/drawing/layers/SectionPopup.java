package calendar.drawing.layers;

import java.util.List;

import calendar.drawing.Canvas;
import calendar.state.State;
import calendar.storage.Section;

// TODO: scrolling
public class SectionPopup extends Popup {
    public SectionPopup(int width, State state) {
        super(width, state);
    }

    private List<Section> sections() { return state.calendar.sections(); }

    public Canvas draw() {
        Canvas canvas = super.draw();

        drawText(canvas, " Manage Sections ", 0, null, colors().textBackground());

        int margin = 4;
        int boxy = line(2);
        int boxwidth = width() - margin * 2;
        int boxheight = maxLines() - 4;

        // sections box
        canvas.highlightBox(margin,  boxy, boxwidth, boxheight, null, colors().textBackground());

        List<Section> sections = sections();
        int sectionLength = sections.size();

        for(int i = 0; i < sectionLength; i++) {
            Section section = sections.get(i);
            canvas.highlightBox(margin, boxy + i, boxwidth, 1, colors().highlightText(), section.color());
            drawText(canvas, section.title(), 2 + i);
        }

        // current section
         drawTextLeft(canvas, "←", 2 + state.popupLine(), margin + 1);
        drawTextRight(canvas, "→", 2 + state.popupLine(), margin + 1);

        // info line
        int infoy = 2 + boxheight + 1;
        canvas.highlightBox(margin, line(infoy), boxwidth, 1, colors().helpText2(), colors().textBackground());

         drawTextLeft(canvas, "(a) add", infoy, margin + 1);
        drawTextRight(canvas, "remove (r)", infoy, margin + 1);

        return canvas;
    } 
}
