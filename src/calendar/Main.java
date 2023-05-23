package calendar;

import calendar.drawing.Text;
import calendar.drawing.layers.Layer;

public class Main {
    public static void main(String[] args) {
        System.out.println("BELLOOOOO yesyes");

        Layer layer = new Layer(16, 16);

        layer.chars[0][1] = Text.HORIZONTAL;
        layer.chars[0][2] = Text.HORIZONTAL;
        layer.chars[0][3] = Text.HORIZONTAL;

        layer.chars[0][0] = Text.lightBoxChars[1][0][0][1];

        layer.chars[1][0] = Text.VERTICAL;
        layer.chars[2][0] = Text.VERTICAL;
        layer.chars[3][0] = Text.VERTICAL;

        layer.print();
    }
}
