package calendar.drawing;

import calendar.drawing.Canvas;

// this is a bit of a complex class, but just cope
public enum Justification {
    Middle {
        public void write(Canvas canvas, String string, int x, int y, int width, int height, int yoffset) {
            int length = string.length();
            int left = (width - length) / 2;

            canvas.drawText(string, x + left, y + yoffset);
        };
    }, 
    Right {
        public void write(Canvas canvas, String string, int x, int y, int width, int height, int yoffset) {
            int length = string.length();
            int right = width - length - 1;

            canvas.drawText(string, x + right, y + yoffset);
        }       
    };

    public abstract void write(Canvas canvas, String string, int x, int y, int width, int height, int yoffset);
}
