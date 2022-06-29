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
    Point preloc = null;
    Point location = new Point(5, 10, "Right");
    TETile[][] world;

    public MapGen(int wid, int height, long seed) {
        WIDTH = wid;
        HEIGHT = height;
        SEED = seed;
        RANDOM = new Random(seed);
        world = new TETile[WIDTH][HEIGHT];

    }



    // background build
    public void backG() {
        // initialize the tile rendering engine with a window of size WIDTH x HEIGHT
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        // initialize tiles


    }


    public void randomMap() {
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        Draw pen = new Draw(location);
        int temp;
        int token = 9;
        pen.drawPix(world, location, "Wall");
        for (int i = 0; i <= 17; i++) {
            System.out.println("draw loop : " + i);
            temp = RANDOM.nextInt(token);
            System.out.println("temp = "+temp);
            switch (temp) {
                default: break;
                case 1: //draw Hallway
                    pen.drawHallway(world, 4);
                    break;
                case 2: //draw room
                    int check = RANDOM.nextInt(2);
                    int sizel = RANDOM.nextInt(3);
                    int sizer = RANDOM.nextInt(3);
                    int len = RANDOM.nextInt(4);
                    if (check == 0) {
                        pen.drawRoom(world, "Righthand", sizel + 1, sizer + 1, len + 1);
                    } else {
                        pen.drawRoom(world, "Lefthand", sizel + 1, sizer + 1, len  + 1);
                    }
                    break;

                case 3: // draw Oway
                    pen.drawOway(world, RANDOM.nextInt(2) + 1,
                            RANDOM.nextInt(2) + 1, RANDOM.nextInt(4) + 1);
                    break;
                case 4: // draw corner
                    pen.drawCorner(world, "Right");
                    pen.drawCorner(world, "Left");
                    break;
                case 6:
                case 5:// branch up
                    Point ttt;
                    if (RANDOM.nextInt(2) == 0) {
                        ttt = pen.branchUp(world, "Lefthand");
                        System.out.println("Lefthand");
                    } else {
                        ttt = pen.branchUp(world, "Righthand");
                        System.out.println("Righthand");
                    }
                    int branchcheck;
                    for (int j = 0; j < 5; j++) {
                        System.out.println("Boundcheck " + pen.boundCheck);
                        if (pen.boundCheck) {
                            break;
                        }
                        branchcheck = RANDOM.nextInt(2);
                        switch (branchcheck) {
                            default: break;
                            case 0:
                                System.out.println("Hallway");
                                pen.drawHallway(world, 2); break;
                            case 1:
                                System.out.println("Oway");
                                System.out.println("location y : "+location.y);
                                pen.drawOway(world, RANDOM.nextInt(4) + 1, RANDOM.nextInt(4) + 1,
                                        RANDOM.nextInt(4) + 1);
                                break;
                        }
                    }
                    pen.branchEnd(world, ttt);
                    pen.drawHallway(world, 3); break;
                case 0: // draw snake
                    pen.drawCorner(world, "Left");
                    pen.drawCorner(world, "Right"); break;
                case 8:
                case 7: // draw door
                    pen.door(world);
                    token -= 2; break;
            }
        }
        if (token == 8) {
            pen.door(world);
        }
        pen.branchEnd(world, location);
    }


}


