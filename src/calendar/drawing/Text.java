package calendar.drawing;

import java.util.HashMap;

public class Text {
    // map of the basic light unicode box chars
    // each axis represents if the line is present in that direction
    //   up down left right
    // for example, a vertical line is [1][1][0][0]
    //   because up and down are present
    public static final char[][][][] lightBoxChars;
    public static final char[][][][] heavyBoxChars;

    // some commonly used characters
    public static final char LIGHT_VERTICAL;
    public static final char LIGHT_HORIZONTAL;
    public static final char HEAVY_VERTICAL;
    public static final char HEAVY_HORIZONTAL;

    // map between characters and their directions
    private static HashMap<Character, int[]> map;

    // associates a set of directions with a given light and dark box character
    private static void add(
        int up,   int down, 
        int left, int right, 
        char light, char heavy
    ) {
        lightBoxChars[up][down][left][right] = light;
        heavyBoxChars[up][down][left][right] = heavy;

        int[] array = { up, down, left, right };
        map.put(light, array);
        map.put(heavy, array);
    }

    // combines the directions of two seperate characters
    // and outputs the corresponding character with the given darkness
    public static char combine(char a, char b, boolean heavy) {
        int[] a_dirs = map.get(a);
        int[] b_dirs = map.get(b);

        int up    = Math.min(a_dirs[0] + b_dirs[0], 1); // kinda goofy but it works
        int down  = Math.min(a_dirs[1] + b_dirs[1], 1);
        int left  = Math.min(a_dirs[2] + b_dirs[2], 1);
        int right = Math.min(a_dirs[3] + b_dirs[3], 1);
        
        if(heavy)
            return heavyBoxChars[up][down][left][right];
        else
            return lightBoxChars[up][down][left][right];
    }

    static {
        lightBoxChars = new char[2][2][2][2];
        heavyBoxChars = new char[2][2][2][2];
        map = new HashMap<Character, int[]>();

        add(0,0,0,0, ' ', ' ');
        add(0,0,0,1, '╶', '╺');
        add(0,0,1,0, '╴', '╸');
        add(0,0,1,1, '─', '━'); // horizontal!
        add(0,1,0,0, '╷', '╻');
        add(0,1,0,1, '┌', '┏');
        add(0,1,1,0, '┐', '┓');
        add(0,1,1,1, '┬', '┳');
        add(1,0,0,0, '╵', '╹');
        add(1,0,0,1, '└', '┗');
        add(1,0,1,0, '┘', '┛');
        add(1,0,1,1, '┴', '┻');
        add(1,1,0,0, '│', '┃'); // vertical!
        add(1,1,0,1, '├', '┣');
        add(1,1,1,0, '┤', '┫');
        add(1,1,1,1, '┼', '╋');

        LIGHT_VERTICAL = lightBoxChars[1][1][0][0];
        LIGHT_HORIZONTAL = lightBoxChars[0][0][1][1];
        HEAVY_VERTICAL = heavyBoxChars[1][1][0][0];
        HEAVY_HORIZONTAL = heavyBoxChars[0][0][1][1];
    }
}
