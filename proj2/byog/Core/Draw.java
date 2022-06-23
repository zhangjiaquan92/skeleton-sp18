package byog.Core;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

public class Draw {
    // x location on screen





    public Point DrawHallway(TETile[][] way, Point location, int len) {
        switch (location.dir) {
            // to right
            case "Right":
                for (int temp = location.x; temp - location.x < len; temp += 1) {

                    way[temp][location.y + 1] = Tileset.WALL;
                    way[temp][location.y] = Tileset.FLOOR;
                    way[temp][location.y - 1] = Tileset.WALL;


                }
                location.x += len;

                break;
                // to left
            case "Left":
                for (int temp = location.x; location.x - temp < len; temp -= 1) {

                    way[temp][location.y - 1] = Tileset.WALL;
                    way[temp][location.y] = Tileset.FLOOR;
                    way[temp][location.y + 1] = Tileset.WALL;


                }
                location.x -= len;
                break;
                // to up
            case "Up":
                for (int temp = location.y; temp - location.y < len; temp += 1) {

                    way[location.x - 1][temp] = Tileset.WALL;
                    way[location.x][temp] = Tileset.FLOOR;
                    way[location.x + 1][temp] = Tileset.WALL;


                }
                location.y += len;
                break;
            case "Down":
                for (int temp = location.y; location.y - temp < len; temp -= 1) {

                    way[location.x - 1][temp] = Tileset.WALL;
                    way[location.x][temp] = Tileset.FLOOR;
                    way[location.x + 1][temp] = Tileset.WALL;


                }
                location.y -= len;
                break;


        }

        return location;

    }
    public Point DrawCorner(TETile[][] way, Point loc, String turn) {

        loc.getbud();

        switch (turn) {
            // to right
            case "Right":

                //Point temp2 = this.Getbud(loc);
                //this.DrawPix(way, loc, "Wall");
                //this.ClkWise(loc);

                //draw floor of corner
                this.DrawPix(way, loc, "Floor");
                loc.ClkWise();
                this.DrawLine(way,loc, "Floor", 2);
                //draw left bud of corner
                this.DrawLine(way, loc.leftbud, "Wall", 2);
                loc.leftbud.ClkWise();
                this.DrawLine(way, loc.leftbud, "Wall", 3);


                //draw right bud of corner
                this.DrawPix(way, loc.rightbud, "Wall");
                loc.rightbud.ClkWise();

                break;

            // to left
            case "Left":
                //draw floor of corner
                this.DrawPix(way, loc, "Floor");
                loc.CountClkWise();
                this.DrawLine(way, loc, "Floor", 2);

                //draw left bud of corner
                this.DrawPix(way, loc.leftbud, "Wall");
                loc.leftbud.CountClkWise();

                //draw right bud of corner
                this.DrawLine(way, loc.rightbud, "Wall", 2);

                loc.rightbud.CountClkWise();
                this.DrawLine(way, loc.rightbud, "Wall", 3);

                break;
            // to up





        }
        return loc;
    }
    public Point DrawPix(TETile[][] way, Point location, String Type) {
        switch (Type) {
            case "Wall":
                way[location.x][location.y] = Tileset.WALL;
                location.Update();
                break;

            case "Floor":
                way[location.x][location.y] = Tileset.FLOOR;
                location.Update();
                break;


        }


        return location;

    }

    public Point DrawLine(TETile[][] way, Point location, String Type, int len) {
        for (int i = 0; i < len; i++) {
            this.DrawPix(way, location, Type);
        }

        return location;

    }









    public static void main(String[] args) {
        //MapGen tert = new MapGen();
        TERenderer tert = new TERenderer();
        tert.initialize(50, 50);

        // initialize tiles
        TETile[][] world = new TETile[50][50];
        for (int x = 0; x < 50; x += 1) {
            for (int y = 0; y < 50; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        Draw test = new Draw();

        /*



        for (int temp = 15; temp - 15 < 5; temp += 1) {

            world[temp][5] = Tileset.WALL;
            world[temp][5 + 1] = Tileset.FLOOR;
            world[temp][5 + 2] = Tileset.WALL;
        }
        */
        Point location = new Point(20,20, "Up");


        location = test.DrawHallway(world, location, 10);



        location = test.DrawCorner(world, location, "Left");
        location = test.DrawHallway(world, location, 5);


        tert.renderFrame(world);

    }


}
