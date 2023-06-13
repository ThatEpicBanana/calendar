package calendar.state;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import calendar.drawing.color.Color;
import calendar.drawing.color.Theme;

// set of settings editable by the dialogue
// stuff that shouldn't just be assumed, such as the Theme
public class Config implements Serializable {
    private transient State state;

    private Theme colors;
    private boolean colorfulMonths;
    private int selectedDayColor;

    public Config(State state) {
        this.state = state;
        this.colors = Theme.Latte;
        this.colorfulMonths = true;
        this.selectedDayColor = 5; // teal 
    }

    public Theme colors() { return this.colors; }
    public boolean colorfulMonths() { return this.colorfulMonths; }
    public Color selectedDayColor() { return this.colors.highlights()[selectedDayColor]; }

    public void toggleColorfulMonths() { this.colorfulMonths = !this.colorfulMonths; state.updateScreen(); }
    public void setColorfulMonths(boolean colorfulMonths) { this.colorfulMonths = colorfulMonths; state.updateScreen(); }
    public void setSelectedDayColor(int index) { this.selectedDayColor = index; state.updateScreen(); }
    public void changeSelectedDayColor(int by) {
        int max = Theme.HIGHLIGHT_COUNT;
        setSelectedDayColor((((selectedDayColor + by) % max) + max) % max);
    }

    public int dayColorIndex() { return this.selectedDayColor; }

    public void setTheme(Theme theme) {
        this.colors = theme; 
        // state.screen.month.reinitialize();
        state.updateScreen();
    }

    private void populate(State state) {
        this.state = state;
    }

    public static Config deserialize(String file, State state) {
        if(new File(file).exists()) {
            try {
                try(FileInputStream fileInput = new FileInputStream(file)) {
                    try(ObjectInputStream objectInput = new ObjectInputStream(fileInput)) {
                        Config config = (Config) objectInput.readObject();
                        config.populate(state);
                        return config;
                    }
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
        }

        // TODO: some logging or something
        return new Config(state);
    } 

    public void serialize(String file) {
        try {
            try(FileOutputStream fileOut = new FileOutputStream(file)) {
                try(ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
                    out.writeObject(this);
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
