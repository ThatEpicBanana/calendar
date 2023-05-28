package calendar.input;

import java.io.BufferedReader;
import java.io.IOException;

public class Key {
    private int value;

    public Key(int value) { this.value = value; }

    public boolean isEscape() { return value == 27; }
    public boolean isEnter() { return value == 13; }
    public boolean isTab() { return value == 9; }
    public boolean isBackspace() { return value == 127; }
    public boolean isSpace() { return toChar() == ' '; }

    public boolean isAscii() { return value > 32 && value < 127; }
    public boolean isAlphaNumeric() { return Character.isLetterOrDigit(value); }
    public char toChar() { return (char) value; }

    public char toLowerCase() { return Character.toLowerCase(toChar()); }

    public boolean isUp()    { return toLowerCase() == 'k' || this.equals(UP); }
    public boolean isDown()  { return toLowerCase() == 'j' || this.equals(DOWN); }
    public boolean isLeft()  { return toLowerCase() == 'h' || this.equals(LEFT); }
    public boolean isRight() { return toLowerCase() == 'l' || this.equals(RIGHT); }


    public boolean equals(Key other) { return this.value == other.value; }
    
    public static Key UNKNOWN = new Key(-1);

    public static Key UP = new Key(-2);
    public static Key DOWN = new Key(-3);
    public static Key LEFT = new Key(-4);
    public static Key RIGHT = new Key(-5);

    public String toString() {
        if(isAlphaNumeric()) return "Key(" + toChar() + ")";

        switch(value) {
            case 27: return "Key(Escape)";
            case 13: return "Key(Enter)";
            case 9:  return "Key(Tab)";
            case 127:return "Key(Backspace)";
            case 32: return "Key(Space)";
            case -1: return "Key(Unknown)";
            case -2: return "Key(Up)";
            case -3: return "Key(Down)";
            case -4: return "Key(Left)";
            case -5: return "Key(Right)";
        }

        return "Key(" + value + ")";
    }

    // decodes the next key in the stream
    // this may take multiple values in the stream if an escape key is present
    // to decode an ANSI escape code
    public static Key next(BufferedReader reader) throws IOException {
        int integer = reader.read();

        if(integer == -1) 
            return UNKNOWN;

        Key key = new Key(integer);

        if(key.isEscape())
            return decodeEscape(reader);

        return key;
    }

    // decodes an ANSI escape code if it is present
    // or simply an escape key if it isn't
    // NOTE: if just an escape key is inputted, it might swallow the next input as well
    public static Key decodeEscape(BufferedReader reader) throws IOException {
        if((char) reader.read() != '[')
            return new Key(27);

        switch((char) reader.read()) {
            case 'A':
                return UP;
            case 'B':
                return DOWN;
            case 'C':
                return RIGHT;
            case 'D':
                return LEFT;
        }

        return UNKNOWN;
    }
}
