package byog.Core;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;



public class MapGen {
    int WIDTH;
    int HEIGHT;
    private final long SEED;
    Random RANDOM;
    Point Preloc = null;
    Point Location = new Point(5,5,"Right");

    public MapGen(int wid, int height, long seed) {
        WIDTH = wid;
        HEIGHT = height;
        SEED = seed;
        RANDOM = new Random(SEED);

    }



    // background build
    public void BackG(int WIDTH, int HEIGHT) {
        // initialize the tile rendering engine with a window of size WIDTH x HEIGHT
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        // initialize tiles
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
    }


    public void RandomMap(Random RANDOM) {
        Draw pen = new Draw(Location);
        int temp;
        int token = 7;
        temp = RANDOM.nextInt(token);
        switch (temp) {
            case 1: //draw Hallway



        }



        }





}


