package calendar.drawing;

// An enum that provides methods for justifying some text on a box
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
    },
    Left {
        public void write(Canvas canvas, String string, int x, int y, int width, int height, int yoffset) {
            int left = 1;
            canvas.drawText(string, x + left, y + yoffset);
        }       
    };

    public abstract void write(Canvas canvas, String string, int x, int y, int width, int height, int yoffset);
}
