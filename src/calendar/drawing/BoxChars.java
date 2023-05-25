package calendar.drawing;

import java.util.HashMap;

public class BoxChars {
    // map of the basic light unicode box chars
    // each axis represents if the line is present in that direction
    //   up down left right
    // for example, a vertical line is [1][1][0][0]
    //   because up and down are present
    public static final char[][][][] light;
    public static final char[][][][] heavy;

    // some commonly used characters
    public static final char LIGHT_VERTICAL;
    public static final char LIGHT_HORIZONTAL;
    public static final char HEAVY_VERTICAL;
    public static final char HEAVY_HORIZONTAL;

    // map between characters and their directions
    private static HashMap<Character, int[]> map;
    private static HashMap<Character, Boolean> isHeavy;

    // associates a set of directions with a given light and dark box character
    private static void add(
        int up,   int down, 
        int left, int right, 
        char newlight, char newheavy
    ) {
        light[up][down][left][right] = newlight;
        heavy[up][down][left][right] = newheavy;

        int[] array = { up, down, left, right };
        map.put(newlight, array);
        map.put(newheavy, array);

        isHeavy.put(newlight, false);
        isHeavy.put(newheavy, true);
    }

    // combines the directions of two seperate characters
    // and outputs the corresponding character
    // darkness is first determined by a, or b if a is empty
    // if one of the characters is not a box character, then it returns a
    public static char combine(char a, char b) {
        int[] a_dirs = map.get(a);
        int[] b_dirs = map.get(b);

        if(a_dirs == null || b_dirs == null) return a;

        boolean make_heavy = a != ' ' ? isHeavy.get(a) : isHeavy.get(b);

        int up    = Math.min(a_dirs[0] + b_dirs[0], 1); // kinda goofy but it works
        int down  = Math.min(a_dirs[1] + b_dirs[1], 1);
        int left  = Math.min(a_dirs[2] + b_dirs[2], 1);
        int right = Math.min(a_dirs[3] + b_dirs[3], 1);
        
        if(make_heavy)
            return heavy[up][down][left][right];
        else
            return light[up][down][left][right];
    }

    static {
        light = new char[2][2][2][2];
        heavy = new char[2][2][2][2];
        map = new HashMap<Character, int[]>();
        isHeavy = new HashMap<Character, Boolean>();

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

        LIGHT_VERTICAL = light[1][1][0][0];
        LIGHT_HORIZONTAL = light[0][0][1][1];
        HEAVY_VERTICAL = heavy[1][1][0][0];
        HEAVY_HORIZONTAL = heavy[0][0][1][1];
    }
}
