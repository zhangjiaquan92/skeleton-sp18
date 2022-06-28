package byog.Core;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;



public class MapGen {
    int WIDTH;
    int HEIGHT;
    private final long SEED = 922345984;
    Random RANDOM;
    Point Preloc = null;
    Point Location = new Point(5,10,"Right");
    TETile[][] world;

    public MapGen(int wid, int height, long seed) {
        WIDTH = wid;
        HEIGHT = height;
        //SEED = seed;
        RANDOM = new Random(SEED);
        world = new TETile[WIDTH][HEIGHT];

    }



    // background build
    public void BackG(int WIDTH, int HEIGHT) {
        // initialize the tile rendering engine with a window of size WIDTH x HEIGHT
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        // initialize tiles
        TETile[][] world = new TETile[WIDTH][HEIGHT];

    }


    public void RandomMap() {

        for (int x = 0; x < WIDTH; x += 1) {

            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        Draw pen = new Draw(Location);
        int temp;
        int token = 9;
        pen.DrawPix(world, Location, "Wall");

        for (int i = 0; i <= 17; i++){
            System.out.println("cycle: "+i);
            temp = RANDOM.nextInt(token);
            System.out.println("temp is " + temp);
            switch (temp) {
                case 1: //draw Hallway
                    System.out.println("case 1 Hallway");
                    pen.DrawHallway(world, 4);
                    break;

                case 2: //draw room

                    System.out.println("case 2 draw room");
                    int check = RANDOM.nextInt(2);
                    System.out.println("check is: "+ check);
                    int sizel = RANDOM.nextInt(3);
                    int sizer = RANDOM.nextInt(3);
                    int len = RANDOM.nextInt(4);
                    System.out.println("sizel is: "+ sizel);
                    System.out.println("sizer is: "+ sizer);
                    System.out.println("len is: "+ len);

                    if (check == 0) {

                        pen.DrawRoom(world, "Righthand", sizel + 1, sizer + 1, len + 1);
                    } else {
                        pen.DrawRoom(world, "Lefthand", sizel + 1, sizer + 1, len  + 1);
                    }
                    break;

                case 3: // draw Oway
                    System.out.println("case 3 Oway");
                    pen.DrawOway(world, RANDOM.nextInt(2) + 1, RANDOM.nextInt(2) + 1, RANDOM.nextInt(4) + 1);
                    break;

                case 4: // draw corner
                    System.out.println("case 4 snake ");
                    pen.DrawCorner(world,"Right");
                    pen.DrawCorner(world, "Left");
                    break;



                case 6:
                case 5:// branch up
                    System.out.println("case 5 branchup");
                    Point ttt;

                    if (RANDOM.nextInt(2) == 0) {



                        ttt = pen.BranchUp(world, "Lefthand");


                    } else {
                        ttt = pen.BranchUp(world, "Righthand");

                    }
                    int branchcheck;

                    for (int j = 0; j < 5; j++) {
                        System.out.println("Boundcheck " + pen.Boundcheck);
                        if (pen.Boundcheck) {
                            //pen.DrawHallway(world,1);
                            //pen.BranchEnd(world, ttt);
                            break;
                        }

                        System.out.println("branch loop: "+ j);
                        branchcheck = RANDOM.nextInt(2);
                        System.out.println("branchup check is: " + branchcheck);
                        switch (branchcheck) {
                            case 0:
                                System.out.println("branch case 0");
                                pen.DrawHallway(world, 2);
                                break;



                            case 1:
                                System.out.println("branch case 1");
                                pen.DrawOway(world, RANDOM.nextInt(4) + 1, RANDOM.nextInt(4) + 1, RANDOM.nextInt(4) + 1);
                                break;


                                /*
                                String side = "Lefthand";
                                System.out.println("branch case 2");
                                if (RANDOM.nextInt(2) == 0) {
                                    side = "Lefthand";
                                } else {
                                    side = "Righthand";
                                }
                                pen.DrawRoom(world, side, RANDOM.nextInt(3), RANDOM.nextInt(3), RANDOM.nextInt(3));

                                break;*/

                        }




                    }

                    pen.BranchEnd(world, ttt);
                    pen.DrawHallway(world, 3);

                    break;


                case 0: // draw snake
                    System.out.println("case 0 snake ");
                    pen.DrawCorner(world,"Left");
                    pen.DrawCorner(world, "Right");


                break;




                case 8:
                case 7: // draw door
                    System.out.println("case 6 door");
                    pen.Door(world);
                    token -= 2;
                break;




            }



        }
        if(token == 8) {

            pen.Door(world);

        }

        //pen.Door(world);
        pen.BranchEnd(world, Location);




        }


    public static void main(String[] args) {
        TERenderer tert = new TERenderer();
        tert.initialize(80, 30);
        long seed = 123;

        MapGen result = new MapGen(80, 30, seed);
        result.RandomMap();
        tert.renderFrame(result.world);

    }


}


