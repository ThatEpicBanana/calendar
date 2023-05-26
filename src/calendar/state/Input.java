package calendar.state;

import java.util.ArrayList;
import java.util.Scanner;

public class InputClass {
    private ArrayList<String> baseLayer;
    private ArrayList<String> popupLayer;
    private ArrayList<String> inputLayer;
    private int currentState;  // 0 - Base, 1 - Popup (Event), 2 - Popup (Section)
    private String title;
    private String timeFrame;
    private ArrayList<String> sections;
    private ArrayList<String> sectionPopupList;
    private int sectionPopupIndex;
    private boolean isTextEditable;

    public InputClass() {
        baseLayer = new ArrayList<>();
        popupLayer = new ArrayList<>();
        inputLayer = new ArrayList<>();
        currentState = 0;
        title = "";
        timeFrame = "";
        sections = new ArrayList<>();
        sectionPopupList = new ArrayList<>();
        sectionPopupIndex = 0;
        isTextEditable = false;
    }

    public void handleInput(String input) {
        switch (currentState) {
            case 0:  // Base Layer
                // No action needed
                break;
            case 1:  // Popup (Event) Layer
                handleEventPopupInput(input);
                break;
            case 2:  // Popup (Section) Layer
                handleSectionPopupInput(input);
                break;
        }
    }

    private void handleEventPopupInput(String input) {
        switch (input) {
            case "j":
            case "down":
                currentState = 2;  // Move to Section Popup
                break;
            case "k":
            case "up":
                // No action needed
                break;
            case "a":
                if (sectionPopupList.size() > 0) {
                    sections.add(sectionPopupList.get(sectionPopupIndex));
                }
                break;
            case "r":
                if (sections.size() > 0) {
                    sections.remove(sections.size() - 1);
                }
                break;
            case "enter":
                isTextEditable = true;
                break;
            case "escape":
                currentState = 0;  // Return to Base Layer
                break;
        }
    }

    private void handleSectionPopupInput(String input) {
        switch (input) {
            case "j":
            case "down":
                sectionPopupIndex = Math.min(sectionPopupIndex + 1, sectionPopupList.size() - 1);
                break;
            case "k":
            case "up":
                sectionPopupIndex = Math.max(sectionPopupIndex - 1, 0);
                break;
            case "enter":
                isTextEditable = true;
                break;
            case "escape":
                currentState = 1;  // Return to Event Popup
                break;
        }
    }

    public void displayInput() {
        clearConsole();
        switch (currentState) {
            case 0:  // Base Layer
                System.out.println("Base Layer:");
                for (String item : baseLayer) {
                    System.out.println(item);
                }
                break;
            case 1:  // Popup (Event) Layer
                System.out.println("Event Layer:");
                System.out.println(title);
                System.out.println(timeFrame);
                System.out.println(sections.get(sectionPopupIndex));
                break;
            case 2:  // Popup (Section) Layer
                System.out.println("Section Layer:");
                for (int i = 0; i < sectionPopupList.size(); i++) {
                    String section = sectionPopupList.get(i);
                    if (i == sectionPopupIndex) {
                        System.out.println("< " + section + " >");
                    } else {
                        System.out.println("  " + section);
                    }
                }
                break;
        }
        System.out.println("Current State: " + currentState);
    }

    private void clearConsole() {
        try {
            final String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else if (os.contains("Mac") || os.contains("Linux")) {
                Runtime.getRuntime().exec("clear");
            } else {
                System.out.println("Console clearing not supported for this operating system.");
            }
        } catch (final Exception e) {
            System.out.println("Error clearing console: " + e.getMessage());
        }
    }
    

    public static void main(String[] args) {
        InputClass inputClass = new InputClass();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            inputClass.displayInput();
            String input = scanner.nextLine();
            inputClass.handleInput(input);
        }
    }
}
