package calendar;

import calendar.drawing.layers.Layer;

public class Main {
    public static void main(String[] args) {
        Layer layer = new Layer(16, 16);

        layer.chars[5][10] = 'a';

        layer.print();
    }
}
