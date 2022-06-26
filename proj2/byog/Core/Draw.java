package byog.Core;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

public class Draw {
    Point loc = null;
    public Draw (Point location) {
        loc = location;

    }
    public Point Door(TETile[][] way, Point location) {


        location.getbud();

        this.DrawLine(way, location, "Floor", 1);
        this.DrawLine(way, location.leftbud, "LOCKED_DOOR", 1);
        this.DrawLine(way, location.rightbud, "Wall", 1);

        return location;
    }


    //return Point will be the start location of the drawing
    public Point DrawHallway(TETile[][] way, Point location, int len) {



        location.getbud();

        this.DrawLine(way, location, "Floor", len);
        this.DrawLine(way, location.leftbud, "Wall", len);
        this.DrawLine(way, location.rightbud, "Wall", len);

        return location;
    }

    public Point ReverseHallway(TETile[][] way, Point location, Point start) {
        location.ReverseDir();
        System.out.println(!(location.x == start.x && location.y == start.y));
        System.out.println(location.x);
        System.out.println(start.x);
        System.out.println(location.y);
        System.out.println(start.y);

        while (!(location.x == start.x && location.y == start.y)) {
            System.out.println("testsetes");
            location.getbud();
            this.DrawPix(way, location, "Nothing");
            this.DrawPix(way, location.leftbud, "Nothing");
            this.DrawPix(way, location.rightbud, "Nothing");
        }
        location.ReverseDir();
        return location;
    }



    // will not use DrawCorner most of the time, use the BranchUp instead to leave backdoor
    // bound hitting
    public Point DrawCorner(TETile[][] way, Point loc, String turn) {

        loc.getbud();

        switch (turn) {
            // to right
            case "Right":

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

        }
        return loc;
    }

    //DrawPix method will return false if location hit bound of map
    public Boolean DrawPix(TETile[][] way, Point location, String Type) {
        if (Type.equals("Nothing")) {
            way[location.x][location.y] = Tileset.NOTHING;
            location.Update();
            return true;

        }
        if (location.x >= way.length || location.y >= way[0].length || location.x < 0 || location.y < 0) {
            location.UndoUpdate();
            //if location hit bound of map, return false.
            return false;
        }
        //if pix is filled, update the location only
        if (!(way[location.x][location.y].description().equals("nothing"))){
            location.Update();
            return true;
        }
        switch (Type) {
            case "Wall":
                way[location.x][location.y] = Tileset.WALL;
                location.Update();
                break;

            case "Floor":
                way[location.x][location.y] = Tileset.FLOOR;
                location.Update();
                break;

            case "Door":
                way[location.x][location.y] = Tileset.LOCKED_DOOR;
                location.Update();
                break;


        }

        return true;

    }
    public Point DrawRoom(TETile[][] way, Point location, String Side, int sizel, int sizer, int len) {
        location.getbud();
        Point templ;
        Point tempr;
        Point out = null;
        out = this.BranchUp(way, location, Side);

            this.DrawHallway(way, location, 3);
            this.DrawOway(way, location, sizel, sizer, len);

            this.DrawPix(way, location, "Wall");



        return out;

    }

    public Point BranchUp(TETile[][] way, Point location, String Side) {
        //out return the location after branch finish
        Point out = null;
        switch (Side) {
            case "Lefthand":
                this.DrawLine(way, location, "Floor", 3);

                this.DrawLine(way, location.rightbud, "Wall", 3);
                out = new Point(location.x, location.y, location.dir);
                location.UndoUpdate();
                location.UndoUpdate();
                location.CountClkWise();
                location.Update();
                break;


            case "Righthand":
                this.DrawLine(way, location, "Floor", 3);

                this.DrawLine(way, location.leftbud, "Wall", 3);
                out = new Point(location.x, location.y, location.dir);
                location.UndoUpdate();
                location.UndoUpdate();
                location.ClkWise();
                location.Update();
                break;

        }
        this.DrawHallway(way, location, 2);

        return out;
    }
    public Point BranchEnd (TETile[][] way, Point location, Point output){
        this.DrawPix(way, location, "Wall");
        //output is the location after branch finish, which is the output of BranchUp.
        return output;
    }

    public Point DrawSwch(TETile[][] way, Point location, String Bread, String Meat, int len, Point store) {
        Point temp = new Point(store.x,store.y, store.dir);
        this.DrawPix(way, location, Bread);

        for (int i = 0; i < len; i++) {
            this.DrawPix(way, location, Meat);
        }
        this.DrawPix(way, location, Bread);
        return temp;



    }
    public Point DrawOway(TETile[][] way, Point location, int sizel, int sizer, int len) {
        Point templ = new Point(location.x,location.y, location.dir);
        Point tempr = new Point(location.x,location.y, location.dir);
        Point out = this.DrawLine(way,location, "Floor", len + 1);
        for (int i = 0; i < sizel; i++) {
            templ.getbud();
            templ = this.DrawSwch(way, templ.leftbud, "Wall", "Floor", len, templ.leftbud);

        }
        templ.getbud();
        this.DrawLine(way, templ.leftbud, "Wall", len + 2);

        for (int j = 0; j < sizer; j++) {
            tempr.getbud();
            tempr = this.DrawSwch(way, tempr.rightbud, "Wall", "Floor", len, tempr.rightbud);
            //tempr = new Point(tempr.rightbud.x, tempr.rightbud.y, tempr.rightbud.dir);

        }
        tempr.getbud();
        this.DrawLine(way, tempr.rightbud, "Wall", len + 2);

        return out;
    }


    public Point DrawLine(TETile[][] way, Point location, String Type, int len) {
        for (int i = 0; i < len; i++) {
            this.DrawPix(way, location, Type);
        }

        return location;

    }


    public static void main(String[] args) {

        TERenderer tert = new TERenderer();
        tert.initialize(80, 30);


        // initialize tiles
        TETile[][] world = new TETile[80][30];
        for (int x = 0; x < 80; x += 1) {
            for (int y = 0; y < 30; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }



        Point location = new Point(10,10, "Up");
        Draw test = new Draw(location);

       test.DrawHallway(world, location, 5);
        //tert.renderFrame(world);




        test.DrawCorner(world, location, "Right");
        test.DrawHallway(world, location, 5);
        location = test.DrawRoom(world, location, "Lefthand", 2,4, 2);


        //test.DrawHallway(world, location, 3);

        //System.out.println(location.x);
        //System.out.println(location.y);
        //System.out.println(location.dir);
        test.DrawOway(world, location, 3,2, 2);

        //System.out.println(location.x);
        //System.out.println(location.y);
        //System.out.println(location.dir);
        location = test.DrawHallway(world, location, 3);
        Point next = test.BranchUp(world, location, "Righthand");



        //test.DrawOway(world, location, 3,2, 2);

        location = test.DrawOway(world, location, 3,1, 2);
        //next = test.BranchEnd(world, location, next);
        //location = new Point(next.x,next.y, next.dir);
        //location = test.DrawHallway(world, location, 5);
        //location = test.ReverseHallway(world, location, next);
        //location = test.DrawHallway(world, location, 2);

        //tert.renderFrame(world);

        //print out the world with command line





        tert.renderFrame(world);


    }


}

