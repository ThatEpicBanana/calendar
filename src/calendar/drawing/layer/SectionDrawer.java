package calendar.drawing.layer;

import java.util.List;

import calendar.drawing.Canvas;
import calendar.drawing.Just;
import calendar.state.State;
import calendar.storage.Section;

// the section editing popup
// TODO: scrolling
public class SectionDrawer extends PopupDrawer {
    public SectionDrawer(int width, State state) {
        super(width, state);
    }

    private List<Section> sections() { return state.calendar.sections(); }

    public Canvas draw() {
        Canvas canvas = super.draw();
        Canvas inset = canvas.offsetMargin(2);

        inset.text(" Manage Sections ", Just.centeredOnRow(0), state.monthColorText(), state.monthColor());

        int sectionsHeight = inset.height() - 4;
        inset.offsetCenteredMargin(2, 2, sectionsHeight)
             .draw(this::drawSections);

        inset.offsetCenteredMargin(sectionsHeight + 3, 2, 1)
             .draw(this::drawInfoLine);

        return canvas;
    }

    private void drawSections(Canvas canvas) {
        canvas.fill(null, colors().textBackground());

        List<Section> sections = sections();
        int sectionLength = Math.min(sections.size(), canvas.height());

        for(int i = 0; i < sectionLength; i++) {
            Section section = sections.get(i);
            String title = sanitize(section.title(), canvas.width() - 6, i);
            
            canvas.draw(wid.rollingSelection(title, Just.centeredOnRow(i), section.color(), i));
        }
    }

    private void drawInfoLine(Canvas canvas) {
        canvas.fill(colors().helpText2(), colors().textBackground())
              .text("(a) add", Just.leftOfRow(0))
              .text("remove (r)", Just.rightOfRow(0));
    }
}
