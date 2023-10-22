package calendar.input.layer;

import calendar.input.InputLayer;
import calendar.input.Key;
import calendar.input.LayerChange;
import calendar.input.LayerType;
import calendar.input.component.TextBoxLayer;
import calendar.state.State;
import calendar.state.layer.ScrollableLayer;
import calendar.storage.Section;

// the input layer for the section popup
// allows for the user to add, remove, and change sections
public class SectionInputLayer implements InputLayer {
    private State state;
    private ScrollableLayer layer;

    public SectionInputLayer(State state, ScrollableLayer layer) {
        this.state = state;
        this.layer = layer;

        updateBounds();
    }

    private boolean hasSections() { return state.calendar.sections().size() > 0; }
    private int line() { return layer.selection(); }
    private int maxSession() { return Math.max(state.calendar.sections().size() - 1, 0); }
    private Section section() {
        if(!state.calendar.sections().isEmpty())
            return state.calendar.sections().get(line());
        else
            return null;
    }

    private void updateBounds() {
        int max = maxSession();
        this.layer.setBounds(0, max);
    }

    public LayerChange handle(Key character) {
        // (q) exit
        if(character.toLowerCase() == 'q' || character.toLowerCase() == 's') 
            return LayerChange.exit();

        // (enter) edit
        if(character.isEnter()) 
            return edit();

        switch(character.toLowerCase()) {
            case 'r':
                return remove();
            case 'a':
                return add();
            case '?':
                return toggleHelp();
        }

        if(character.isUp()) 
            up();
        else if(character.isDown()) 
            down();
        else if(character.isLeft()) 
            previousHighlight();
        else if(character.isRight()) 
            nextHighlight();

        return LayerChange.keep();
    }

    private LayerChange edit() {
        Section section = section();
        if(section == null) return LayerChange.keep();

        InputLayer newLayer = new TextBoxLayer(layer, section().title(), value -> section.setTitle(value));
        return LayerChange.switchTo(newLayer);
    }

    private LayerChange remove() {
        if(hasSections()) {
            state.calendar.removeSection(line());
            updateBounds();
            if(line() > maxSession()) layer.setSelection(maxSession());
        }

        return LayerChange.keep();
    }

    private LayerChange add() {
        Section section = state.calendar.addSection("New Section", 0);
        updateBounds();
        layer.setSelection(maxSession());
        InputLayer newLayer = new TextBoxLayer(layer, "", value -> section.setTitle(value));
        return LayerChange.switchTo(newLayer);
    }

    private void up() {
        layer.prevSelection();
    }

    private void down() {
        layer.nextSelection();
    }

    private void nextHighlight() {
        if(section() != null)
            section().addColor(1);
    }

    private void previousHighlight() {
        if(section() != null)
            section().addColor(-1);
    }

    private static String[] help = new String[]{
        "Sections are used",
        "  to group events",
        "  ex: birthdays",
        "",
        "(a) add section",
        "(r) remove section",
        "",
        "(↓,↑) (j,k)", 
        "  move",
        "(←,→) (h,l)", 
        "  change color",
    };

    private LayerChange toggleHelp() {
        state.toggleHelp(help);
        return LayerChange.keep();
    }


    public LayerType type() { return LayerType.Popup; }
}
