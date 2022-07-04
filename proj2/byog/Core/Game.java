package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;

public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
    }
    public long inputhelper(String s) {
        long seed;
        String temp;
        //System.out.println("s is :" + s);
        //System.out.println("s is (0) :" + s.substring(0,1));
        //System.out.println("s is (0) check :" + s.substring(0,1).equals("N"));
        if (s.substring(0,1).equals("N") || s.substring(0,1).equals("n")) {
            //System.out.println("in to the loop");
            temp = s.substring(1, s.length());
            //System.out.println(temp);
        } else {
            temp = s;
        }
        if (temp.substring(temp.length() - 2).equals(":")) {
            temp = temp.substring(0, temp.length() - 2);
            //seed = Long.parseLong(temp.substring(0, temp.length() - 2));


        }
        if (temp.substring(temp.length() - 1).equals("S") || temp.substring(temp.length() - 1).equals("s")){
            temp = temp.substring(0, temp.length() - 1);
            //seed = Long.parseLong(temp.substring(0, temp.length()));
        }
        seed = Long.parseLong(temp);
        return seed;
    }

    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        //
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().

        long seed = inputhelper(input);

        //System.out.println(seed);

        MapGen result = new MapGen(WIDTH, HEIGHT, seed);
        result.randomMap();
        TERenderer tert = new TERenderer();
        tert.initialize(WIDTH, HEIGHT);
        tert.renderFrame(result.world);



        return result.world;

    }
}
