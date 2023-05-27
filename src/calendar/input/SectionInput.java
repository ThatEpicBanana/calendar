package calendar.input;

import java.awt.Color;
import java.util.Scanner;
import javax.swing.JOptionPane;
import calendar.state.State;
import calendar.state.InputState;
import calendar.state.Event;

public class SectionInput {
    private static Theme currentTheme;
    private static int currentIndex;
    private static State state;

    public static void main(String[] args) {
        currentTheme = Theme.Latte; // set theme
        currentIndex = 0;
        state = new State(null, currentTheme, new Coord(0, 0), new Coord(0, 0)); // Create an instance of State with desired parameters

        userInput();
    }

    private static void userInput() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            // Get input from the user
            String userInput = scanner.nextLine();

            if (userInput.equalsIgnoreCase("a")) {
                addIndex();
            } else if (userInput.equalsIgnoreCase("r")) {
                removeIndex();
            } else if (userInput.equalsIgnoreCase("j") || userInput.equalsIgnoreCase("left")) {
                moveLeft();
            } else if (userInput.equalsIgnoreCase("k") || userInput.equalsIgnoreCase("right")) {
                moveRight();
            } else if (userInput.equalsIgnoreCase("p")) {
                createPopupAndInputState();
            }
        }
    }

    private static void addIndex() {
        currentIndex++;
        if (currentIndex >= currentTheme.highlights().length) {
            currentIndex = 0;
        }
        showCurrentValue();
    }

    private static void removeIndex() {
        currentIndex--;
        if (currentIndex < 0) {
            currentIndex = currentTheme.highlights().length - 1;
        }
        showCurrentValue();
    }

    private static void moveLeft() {
        currentIndex--;
        if (currentIndex < 0) {
            currentIndex = currentTheme.highlights().length - 1;
        }
        showCurrentValue();
    }

    private static void moveRight() {
        currentIndex++;
        if (currentIndex >= currentTheme.highlights().length) {
            currentIndex = 0;
        }
        showCurrentValue();
    }

    private static void createPopupAndInputState() {
        Event event = new Event(); // Create an instance of the Event class with desired details
        InputState inputState = state.createPopupAndInputState(event);
        // Perform any desired operations with the inputState and event
        // ...

        // Show the current value after creating the popup and input state
        showCurrentValue();
    }

    private static void showCurrentValue() {
        Color currentColor = currentTheme.highlights()[currentIndex];
        JOptionPane.showMessageDialog(null, "Current Color: " + currentColor.toString(), "Color Value", JOptionPane.INFORMATION_MESSAGE);
    }
}

enum Theme {
    Latte(Color.WHITE, Color.BLACK),
    Mocha(Color.BLACK, Color.WHITE);

    private final Color[] highlights;

    Theme(Color... highlights) {
        this.highlights = highlights;
    }

    public Color[] highlights() {
        return highlights;
    }
}
