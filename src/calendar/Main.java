package calendar;

import calendar.drawing.Text;
import calendar.drawing.layers.Layer;

public class Main {
    public static void main(String[] args) {
        System.out.println("BELLOOOOO yesyes");

        Layer layer = new Layer(16, 16);

        layer.chars[0][1] = Text.LIGHT_HORIZONTAL;
        layer.chars[0][2] = Text.LIGHT_HORIZONTAL;
        layer.chars[0][3] = Text.LIGHT_HORIZONTAL;

        layer.chars[0][0] = Text.lightBoxChars[0][1][0][1];

        layer.chars[1][0] = Text.LIGHT_VERTICAL;
        layer.chars[2][0] = Text.LIGHT_VERTICAL;
        layer.chars[3][0] = Text.LIGHT_VERTICAL;

        layer.print();

        char up = Text.lightBoxChars[1][0][0][0];
        char right = Text.lightBoxChars[0][0][0][1];

        System.out.println("up char: '" + up + "', right char: '" + right + "'");
        System.out.println("combined: '" + Text.combine(up, right, false) + "'");
    }
}
