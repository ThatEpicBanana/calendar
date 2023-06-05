package calendar.input;

import java.io.BufferedReader;
import java.io.IOException;

// a key inputted to the screen
// includes all alphanumeric keys
// as well as others such as enter or escape
public class Key {
    private int value;

    public Key(int value) { this.value = value; }
    public Key(char value) { this.value = value; }

    public boolean isEscape() { return value == 27; }
    public boolean isEnter() { return value == 13; }
    public boolean isTab() { return value == 9; }
    public boolean isBackspace() { return value == 127; }
    public boolean isSpace() { return toChar() == ' '; }

    public boolean isAscii() { return value > 32 && value < 127; }
    public boolean isAlphaNumeric() { return Character.isLetterOrDigit(value); }
    public char toChar() { return (char) value; }

    public char toLowerCase() { return Character.toLowerCase(toChar()); }

    public boolean isUp()    { return toChar() == 'k' || this.equals(UP); }
    public boolean isDown()  { return toChar() == 'j' || this.equals(DOWN); }
    public boolean isLeft()  { return toChar() == 'h' || this.equals(LEFT); }
    public boolean isRight() { return toChar() == 'l' || this.equals(RIGHT); }

    public boolean isShiftUp()    { return toChar() == 'K' || this.equals(SHIFT_UP); }
    public boolean isShiftDown()  { return toChar() == 'J' || this.equals(SHIFT_DOWN); }
    public boolean isShiftLeft()  { return toChar() == 'H' || this.equals(SHIFT_LEFT); }
    public boolean isShiftRight() { return toChar() == 'L' || this.equals(SHIFT_RIGHT); }


    public boolean equals(Key other) { return this.value == other.value; }
    
    public static Key UNKNOWN = new Key(-1);

    // this ain't elegant
    // but it works
    public static Key UP = new Key(-2);
    public static Key DOWN = new Key(-3);
    public static Key LEFT = new Key(-4);
    public static Key RIGHT = new Key(-5);

    public static Key SHIFT_UP = new Key(-6);
    public static Key SHIFT_DOWN = new Key(-7);
    public static Key SHIFT_LEFT = new Key(-8);
    public static Key SHIFT_RIGHT = new Key(-9);

    public String toString() {
        if(isAscii()) return "Key(" + toChar() + ")";

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
            case -6: return "Key(Shift+Up)";
            case -7: return "Key(Shift+Down)";
            case -8: return "Key(Shift+Left)";
            case -9: return "Key(Shift+Right)";
        }

        return "Key(UNK_" + value + ")";
    }

    // decodes the next key in the stream
    // this may take multiple values in the stream 
    // if an escape key is present, to decode an ANSI escape code
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
        // it is fine to swallow this because esc globally escapes everything 
        if((char) reader.read() != '[')
            return new Key(27);

        // arrow keys
        switch((char) reader.read()) {
            case 'A': return UP;
            case 'B': return DOWN;
            case 'C': return RIGHT;
            case 'D': return LEFT;
            case '1':
                return decodeEscape1(reader);
        }

        return UNKNOWN;
    }

    // shift-arrow keys look like "<esc>[1;2<direction>"
    private static Key decodeEscape1(BufferedReader reader) throws IOException {
        if((char) reader.read() != ';') return UNKNOWN;
        if((char) reader.read() != '2') return UNKNOWN;

        switch((char) reader.read()) {
            case 'A': return SHIFT_UP;
            case 'B': return SHIFT_DOWN;
            case 'C': return SHIFT_RIGHT;
            case 'D': return SHIFT_LEFT;
        }

        return UNKNOWN;
    }
}
