package byog.Core;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

public class Draw {

    public Point DrawHallway(TETile[][] way, Point location, int len) {
        location.getbud();
        this.DrawLine(way, location, "Floor", len);
        this.DrawLine(way, location.leftbud, "Wall", len);
        this.DrawLine(way, location.rightbud, "Wall", len);
        return location;
    }



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
    public Point DrawRoom(TETile[][] way, Point location, String Side, int sizel, int sizer, int len) {
        location.getbud();
        Point templ;
        Point tempr;
        Point out = null;
        out = this.BranchUp(way, location, Side);

            this.DrawHallway(way, location, 3);
            this.DrawOway(way, location, sizel, sizer, len);
            location.UndoUpdate();
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
        return out;
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
        Point out = this.DrawLine(way,location, "Floor", len + 2);
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
        tert.initialize(50, 50);

        // initialize tiles
        TETile[][] world = new TETile[50][50];
        for (int x = 0; x < 50; x += 1) {
            for (int y = 0; y < 50; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        Draw test = new Draw();

        Point location = new Point(20,20, "Up");

        location = test.DrawHallway(world, location, 10);




        location = test.DrawCorner(world, location, "Right");
        location = test.DrawHallway(world, location, 5);
        location = test.DrawRoom(world, location, "Lefthand", 2,4, 2);
        location = test.DrawHallway(world, location, 3);
        System.out.println(location.x);
        System.out.println(location.y);
        System.out.println(location.dir);
        location = test.DrawOway(world, location, 3,2, 2);
        System.out.println(location.x);
        System.out.println(location.y);
        System.out.println(location.dir);
        location = test.DrawHallway(world, location, 3);
        Point next = test.BranchUp(world, location, "Righthand");
        test.DrawHallway(world, location, 5);
        test.DrawOway(world, location, 3,2, 2);
        test.DrawOway(world, next, 3,2, 2);


        tert.renderFrame(world);

    }


}
