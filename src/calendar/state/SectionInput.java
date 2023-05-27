package calendar.drawing; //whoever is looking at this i completely forgot how to code halfway through

import java.awt.Color;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class SectionInput {
    private static Theme currentTheme;
    private static int currentIndex;

    public static void main(String[] args) {
        currentTheme = Theme.Latte; // set theme
        currentIndex = 0;

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

    private static void showCurrentValue() {
        Color currentColor = currentTheme.highlights()[currentIndex];
        JOptionPane.showMessageDialog(null, "Current Color: " + currentColor.toString(), "Color Value", JOptionPane.INFORMATION_MESSAGE);
    }
}

enum Theme {
    Latte(Color.WHITE, Color.BLACK), //i might have a very large skill issue and am struggling to pull from theme.java
    Mocha(Color.BLACK, Color.WHITE); // i have a huge skill issue -- i have no idea what i cooked

    private final Color[] highlights;

    Theme(Color... highlights) {
        this.highlights = highlights;
    }

    public Color[] highlights() {
        return highlights;
    }
}
