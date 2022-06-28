package byog.Core;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

public class Draw {
    Point location = null;
    Point backup = new Point(1,1,"Up");
    boolean Boundcheck = false;
    public Draw (Point loca) {
        location = loca;


    }
    public Point Door(TETile[][] way) {


        location.getbud();

        this.DrawLine(way, location, "Floor", 1);
        this.DrawLine(way, location.leftbud, "Door", 1);
        this.DrawLine(way, location.rightbud, "Wall", 1);

        return location;
    }


    //return Point will be the start location of the drawing
    public Point DrawHallway(TETile[][] way, int len) {
        if (Boundcheck) {
            return location;
        }
        backup.CopyLoc(location);
        location.getbud();

        this.DrawLine(way, location, "Floor", len);
        this.DrawLine(way, location.leftbud, "Wall", len);
        this.DrawLine(way, location.rightbud, "Wall", len);

        return backup;
    }

    public Point ReverseHallway(TETile[][] way) {
        location.ReverseDir();
        /*System.out.println(!(location.x == backup.x && location.y == backup.y));
        System.out.println(location.x);
        System.out.println(backup.x);
        System.out.println(location.y);
        System.out.println(backup.y);*/

        switch (location.dir) {
            case "Up":
            case "Down":
                while (!(location.y == backup.y - 1)) {
                    //System.out.println("testsetes");
                    location.getbud();
                    this.DrawPix(way, location, "Nothing");
                    this.DrawPix(way, location.leftbud, "Nothing");
                    this.DrawPix(way, location.rightbud, "Nothing");
                }
                break;

            case "Left":
            case "Right":
                while (!(location.x == backup.x - 1)) {
                    //System.out.println("testsetes");
                    location.getbud();
                    this.DrawPix(way, location, "Nothing");
                    this.DrawPix(way, location.leftbud, "Nothing");
                    this.DrawPix(way, location.rightbud, "Nothing");
                }
                break;

        }


        location.ReverseDir();
        Boundcheck = false;
        location.Update();
        return location;
    }



    // will not use DrawCorner most of the time, use the BranchUp instead to leave backdoor
    // bound hitting
    public Point DrawCorner(TETile[][] way, String turn) {
        if (Boundcheck) {
            return location;
        }

        location.getbud();

        switch (turn) {
            // to right
            case "Right":

                //draw floor of corner
                this.DrawPix(way, location, "Floor");
                location.ClkWise();
                this.DrawLine(way,location, "Floor", 2);
                //draw left bud of corner
                this.DrawLine(way, location.leftbud, "Wall", 2);
                location.leftbud.ClkWise();
                this.DrawLine(way, location.leftbud, "Wall", 3);


                //draw right bud of corner
                this.DrawPix(way, location.rightbud, "Wall");
                location.rightbud.ClkWise();

                break;

            // to left
            case "Left":
                //draw floor of corner
                this.DrawPix(way, location, "Floor");
                location.CountClkWise();
                this.DrawLine(way, location, "Floor", 2);

                //draw left bud of corner
                this.DrawPix(way, location.leftbud, "Wall");
                location.leftbud.CountClkWise();

                //draw right bud of corner
                this.DrawLine(way, location.rightbud, "Wall", 2);
                location.rightbud.CountClkWise();
                this.DrawLine(way, location.rightbud, "Wall", 3);

                break;

        }
        return location;
    }

    //DrawPix method will return false if location hit bound of map
    public Boolean DrawPix(TETile[][] way, Point location, String Type) {

            if (Type.equals("Nothing")) {
                way[location.x][location.y] = Tileset.NOTHING;
                location.Update();
                return true;

            }
            if (location.x >= (way.length - 3) || location.y >= (way[0].length - 3) || location.x < 3 || location.y < 3) {


                way[location.x][location.y] = Tileset.WALL;
                if (location.dir.equals("Up") || location.dir.equals("Down")){
                    location.Update();
                }
                //if location hit bound of map, return false.
                Boundcheck = true;
                return false;
            }
            //if pix is filled, update the location only
            if (!(way[location.x][location.y].description().equals("nothing"))) {
                location.Update();
                //Boundcheck = true;
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
    public Point DrawRoom(TETile[][] way, String Side, int sizel, int sizer, int len) {
        if (Boundcheck) {
            return location;
        }
        backup.CopyLoc(location);

        //Point templ;
        //Point tempr;
        Point out = null;
        out = this.BranchUp(way, Side);

            this.DrawHallway(way, 1);
            this.DrawOway(way, sizel, sizer, len);

            this.BranchEnd(way,out);
        location.CopyLoc(out);

            if (Side.equals("Righthand")) {
                this.DrawHallway(way, sizel + 1);
            }else if(Side.equals("Lefthand")) {
                this.DrawHallway(way, sizer + 1);
            }






        return backup;

    }

    public Point BranchUp(TETile[][] way, String Side) {

        backup.CopyLoc(location);
        location.getbud();

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
        if(Boundcheck) {
            location.CopyLoc(out);
            this.ReverseHallway(way);
        }
        //this.DrawHallway(way, 2);
        //location = new Point(out.x, out.y, out.dir);
        backup.CopyLoc(out);

        return out;
    }
    public Point BranchEnd (TETile[][] way, Point output){
        this.DrawPix(way, location, "Wall");
        //output is the location after branch finish, which is the output of BranchUp.
        location.CopyLoc(output);
        Boundcheck = false;
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
    public Point DrawOway(TETile[][] way, int sizel, int sizer, int len) {
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



        Point location = new Point(74,10, "Right");
        Draw test = new Draw(location);

       test.DrawHallway(world, 1);
        //tert.renderFrame(world);






        //test.DrawCorner(world, "Right");
        //test.DrawHallway(world, 5);
        //test.DrawRoom(world, "Lefthand", 0,2, 0);
        //test.DrawHallway(world, 5);
        //test.DrawRoom(world, "Lefthand", 0,1, 0);
        //System.out.println("case 0 snake ");
        //test.DrawCorner(world,"Left");
        //test.DrawCorner(world, "Right");
       test.BranchUp(world, "Righthand");
        test.DrawHallway(world, 2);
        test.BranchEnd(world, location);
       //test.DrawHallway(world, 3);
        //test.DrawHallway(world, 3);


        //test.DrawHallway(world, location, 3);

/*
        test.DrawOway(world, 3,2, 2);

        System.out.println(location.x);
        System.out.println(location.y);
        System.out.println(location.dir);
        System.out.println(test.backup.x);
        System.out.println(test.backup.y);
        System.out.println(test.backup.dir);
        location = test.DrawHallway(world, 3);
        System.out.println(location.x);
        System.out.println(location.y);
        System.out.println(location.dir);
        System.out.println(test.backup.x);
        System.out.println(test.backup.y);
        System.out.println(test.backup.dir);
        Point next = test.BranchUp(world, "Righthand");



        //test.DrawOway(world, location, 3,2, 2);

        location = test.DrawOway(world, 3,1, 2);
        next = test.BranchEnd(world, next);
        //location = new Point(next.x,next.y, next.dir);
        location = test.DrawHallway(world, 5);
        test.Door(world);
        //location = test.ReverseHallway(world, location, next);
        //location = test.DrawHallway(world, location, 2);

        //tert.renderFrame(world);

        //print out the world with command line


*/


        tert.renderFrame(world);


    }


}

