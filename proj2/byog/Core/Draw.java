package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;



public class Draw {
    Point location = null;
    Point backup = new Point(1, 1, "Up");
    boolean boundCheck = false;
    public Draw(Point loca) {
        location = loca;


    }
    public Point door(TETile[][] way) {


        location.getbud();

        this.drawLine(way, location, "Floor", 1);
        this.drawLine(way, location.leftbud, "Door", 1);
        this.drawLine(way, location.rightbud, "Wall", 1);

        return location;
    }


    //return Point will be the start location of the drawing
    public Point drawHallway(TETile[][] way, int len) {
        if (boundCheck) {
            return location;
        }
        backup.copyLoc(location);
        location.getbud();

        this.drawLine(way, location, "Floor", len);
        this.drawLine(way, location.leftbud, "Wall", len);
        this.drawLine(way, location.rightbud, "Wall", len);

        return backup;
    }

    public Point reverseHallway(TETile[][] way) {
        location.reverseDir();
        /*System.out.println(!(location.x == backup.x && location.y == backup.y));
        System.out.println(location.x);
        System.out.println(backup.x);
        System.out.println(location.y);
        System.out.println(backup.y);*/

        switch (location.dir) {
            default:
                break;
            case "Up":
            case "Down":
                while (!(location.y == backup.y - 1)) {
                    //System.out.println("testsetes");
                    location.getbud();
                    this.drawPix(way, location, "Nothing");
                    this.drawPix(way, location.leftbud, "Nothing");
                    this.drawPix(way, location.rightbud, "Nothing");
                }
                break;

            case "Left":
            case "Right":
                while (!(location.x == backup.x - 1)) {
                    //System.out.println("testsetes");
                    location.getbud();
                    this.drawPix(way, location, "Nothing");
                    this.drawPix(way, location.leftbud, "Nothing");
                    this.drawPix(way, location.rightbud, "Nothing");
                }
                break;

        }


        location.reverseDir();
        boundCheck = false;
        location.update();
        return location;
    }



    // will not use DrawCorner most of the time, use the BranchUp instead to leave backdoor
    // bound hitting
    public Point drawCorner(TETile[][] way, String turn) {
        if (boundCheck) {
            return location;
        }

        location.getbud();

        switch (turn) {
            default:
                break;
            // to right
            case "Right":

                //draw floor of corner
                this.drawPix(way, location, "Floor");
                location.clkWise();
                this.drawLine(way, location, "Floor", 2);
                //draw left bud of corner
                this.drawLine(way, location.leftbud, "Wall", 2);
                location.leftbud.clkWise();
                this.drawLine(way, location.leftbud, "Wall", 3);


                //draw right bud of corner
                this.drawPix(way, location.rightbud, "Wall");
                location.rightbud.clkWise();

                break;

            // to left
            case "Left":
                //draw floor of corner
                this.drawPix(way, location, "Floor");
                location.countClkWise();
                this.drawLine(way, location, "Floor", 2);

                //draw left bud of corner
                this.drawPix(way, location.leftbud, "Wall");
                location.leftbud.countClkWise();

                //draw right bud of corner
                this.drawLine(way, location.rightbud, "Wall", 2);
                location.rightbud.countClkWise();
                this.drawLine(way, location.rightbud, "Wall", 3);

                break;

        }
        return location;
    }

    //DrawPix method will return false if location hit bound of map
    public Boolean drawPix(TETile[][] way, Point loc, String type) {

        if (type.equals("Nothing")) {
            way[loc.x][loc.y] = Tileset.NOTHING;
            loc.update();
            return true;

        }
        if (loc.x >= (way.length - 3) || loc.y
                >= (way[0].length - 3) || loc.x < 3 || loc.y < 3) {


            way[loc.x][loc.y] = Tileset.WALL;
            if (loc.dir.equals("Up") || loc.dir.equals("Down")) {
                loc.update();
            }
                //if location hit bound of map, return false.
            boundCheck = true;
            return false;
        }
            //if pix is filled, update the location only
        if (!(way[loc.x][loc.y].description().equals("nothing"))) {
            loc.update();
                //Boundcheck = true;
            return true;
        }
        switch (type) {
            default:
                break;
            case "Wall":
                way[loc.x][loc.y] = Tileset.WALL;
                loc.update();
                break;

            case "Floor":
                way[loc.x][loc.y] = Tileset.FLOOR;
                loc.update();
                break;

            case "Door":
                way[loc.x][loc.y] = Tileset.LOCKED_DOOR;
                loc.update();
                break;


        }

        return true;

    }
    public Point drawRoom(TETile[][] way, String side, int sizel, int sizer, int len) {
        if (boundCheck) {
            return location;
        }
        backup.copyLoc(location);

        //Point templ;
        //Point tempr;
        Point out = null;
        out = this.branchUp(way, side);

        this.drawHallway(way, 1);
        this.drawOway(way, sizel, sizer, len);

        this.branchEnd(way, out);
        location.copyLoc(out);

        if (side.equals("Righthand")) {
            this.drawHallway(way, sizel + 1);
        } else if (side.equals("Lefthand")) {
            this.drawHallway(way, sizer + 1);
        }

        return backup;

    }

    public Point branchUp(TETile[][] way, String side) {

        backup.copyLoc(location);
        location.getbud();

        //out return the location after branch finish
        Point out = null;
        switch (side) {
            default:
                break;
            case "Lefthand":
                this.drawLine(way, location, "Floor", 3);

                this.drawLine(way, location.rightbud, "Wall", 3);
                out = new Point(location.x, location.y, location.dir);
                location.undoUpdate();
                location.undoUpdate();
                location.countClkWise();
                location.update();
                break;


            case "Righthand":
                this.drawLine(way, location, "Floor", 3);

                this.drawLine(way, location.leftbud, "Wall", 3);
                out = new Point(location.x, location.y, location.dir);
                location.undoUpdate();
                location.undoUpdate();
                location.clkWise();
                location.update();
                break;

        }
        if (boundCheck) {
            location.copyLoc(out);
            this.reverseHallway(way);
        }
        //this.DrawHallway(way, 2);
        //location = new Point(out.x, out.y, out.dir);
        backup.copyLoc(out);

        return out;
    }
    public Point branchEnd(TETile[][] way, Point output) {
        this.drawPix(way, location, "Wall");
        //output is the location after branch finish, which is the output of BranchUp.
        location.copyLoc(output);
        boundCheck = false;
        return output;
    }

    public Point drawSwch(TETile[][] way, Point loc,
                          String bread, String meat, int len, Point store) {
        Point temp = new Point(store.x, store.y, store.dir);
        this.drawPix(way, loc, bread);

        for (int i = 0; i < len; i++) {
            this.drawPix(way, loc, meat);
        }
        this.drawPix(way, loc, bread);
        return temp;



    }
    public Point drawOway(TETile[][] way, int sizel, int sizer, int len) {
        Point templ = new Point(location.x, location.y, location.dir);
        Point tempr = new Point(location.x, location.y, location.dir);
        Point out = this.drawLine(way, location, "Floor", len + 1);
        for (int i = 0; i < sizel; i++) {
            templ.getbud();
            templ = this.drawSwch(way, templ.leftbud, "Wall", "Floor", len, templ.leftbud);

        }
        templ.getbud();
        this.drawLine(way, templ.leftbud, "Wall", len + 2);

        for (int j = 0; j < sizer; j++) {
            tempr.getbud();
            tempr = this.drawSwch(way, tempr.rightbud, "Wall", "Floor", len, tempr.rightbud);
            //tempr = new Point(tempr.rightbud.x, tempr.rightbud.y, tempr.rightbud.dir);

        }
        tempr.getbud();
        this.drawLine(way, tempr.rightbud, "Wall", len + 2);

        return out;
    }


    public Point drawLine(TETile[][] way, Point loc, String type, int len) {
        for (int i = 0; i < len; i++) {
            this.drawPix(way, loc, type);
        }

        return loc;

    }

/*
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

       test.drawHallway(world, 1);
        //tert.renderFrame(world);






        //test.DrawCorner(world, "Right");
        //test.DrawHallway(world, 5);
        //test.DrawRoom(world, "Lefthand", 0,2, 0);
        //test.DrawHallway(world, 5);
        //test.DrawRoom(world, "Lefthand", 0,1, 0);
        //System.out.println("case 0 snake ");
        //test.DrawCorner(world,"Left");
        //test.DrawCorner(world, "Right");
       test.branchUp(world, "Righthand");
        test.drawHallway(world, 2);
        test.branchEnd(world, location);
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





        tert.renderFrame(world);


    }
*/

}

