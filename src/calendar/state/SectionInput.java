import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import calendar.storage.Section;

public class SectionInput implements InputLayer { //use state.java to use Localdate to return what day it's on.
    // find some way to implement the 14 colours here, make sure that when you go past 14 it goes back to 1.
    private ArrayList<String> sections;
    private int currentIndex;
    private State state;

    public SectionInput(State state) {
        this.sections = new ArrayList<>();
        this.currentIndex = 0;
        this.state = state;
    }

    private List<Section> sections() { return this.state.calendar.sections(); } //make a popup that allows you to edit text. you will use
    // just finish, actually edit sections. (popup time) (when you add a section it gives you back a section feat. calendar.java, when you hit enter make a text input layer), use popup selection you idiot

    public InputLayer handleInput(KeyEvent event) {
        int keyCode = event.getKeyCode();

        switch (keyCode) {
            case KeyEvent.VK_A:
                addSection();
                break;
            case KeyEvent.VK_R:
                removeSection();
                break;
            case KeyEvent.VK_J:
            case KeyEvent.VK_LEFT:
                moveLeft();
                break;
            case KeyEvent.VK_K:
            case KeyEvent.VK_RIGHT:
                moveRight();
                break;
        }

        return this;
    }

    private void addSection() {
        sections.add("Section " + (sections.size() + 1));
    }

    private void removeSection() {
        if (!sections.isEmpty()) {
            sections.remove(currentIndex);
            if (currentIndex >= sections.size()) {
                currentIndex = sections.size() - 1;
            }
        }
    }

    private void moveLeft() {
        currentIndex--;
        if (currentIndex < 0) {
            currentIndex = 0;
        }
    }

    private void moveRight() {
        currentIndex++;
        if (currentIndex >= sections.size()) {
            currentIndex = 0;
        }
    }

    public ArrayList<String> getSections() {
        return sections;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }
}
