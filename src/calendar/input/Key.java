package calendar.input;

public class Key {
    private int value;

    public Key(int value) { this.value = value; }

    public boolean isEscape() { return value == 27; }
    public boolean isEnter() { return value == 13; }
    public boolean isTab() { return value == 9; }

    public boolean isAlphaNumeric() { return Character.isAlphabetic(value) || Character.isDigit(value); }
    public char toChar() { return (char) value; }

    public char toLowerCase() { return Character.toLowerCase(toChar()); }
}
