import java.awt.event.KeyEvent;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import calendar.storage.Section;

public class SectionInput implements InputLayer {
    private ArrayList<String> sections;
    private int currentIndex;
    private State state;

    public SectionInput(State state) {
        this.sections = new ArrayList<>();
        this.currentIndex = 0;
        this.state = state;
    }

    private List<Section> sections() {
        return this.state.calendar.sections();
    }

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
            currentIndex = sections.size() - 1;
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

    public LocalDate getCurrentDate() {
        return state.calendar.getCurrentDate();
    }
}
