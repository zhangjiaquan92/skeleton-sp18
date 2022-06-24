package byog.Core;

public class Point {
    int x;
    int y;
    String dir;
    Point leftbud;
    Point rightbud;

    public Point (int xx, int yy, String dirction) {
        x = xx;
        y = yy;
        dir = dirction;

    }
    public void getbud(){
        leftbud = new Point(x,y,dir);
        rightbud = new Point(x,y,dir);
        switch (dir) {
            case "Up":
                leftbud.x -= 1;
                rightbud.x += 1;

                break;
            case "Down":
                leftbud.x += 1;
                rightbud.x -= 1;
                break;

            case "Left":
                leftbud.y -= 1;
                rightbud.y += 1;
                break;
            case "Right":
                leftbud.y += 1;
                rightbud.y -= 1;
                break;


        }
    }
    public void Update() {
        switch (dir) {
            case "Up":
                y += 1;
                break;

            case "Down":
                y -= 1;
                break;

            case "Left":
                x -= 1;
                break;

            case "Right":
                x += 1;
                break;

        }

    }

    public void UndoUpdate() {
        switch (dir) {
            case "Up":
                y -= 1;
                break;

            case "Down":
                y += 1;
                break;

            case "Left":
                x += 1;
                break;

            case "Right":
                x -= 1;
                break;

        }
    }
    public void CountClkWise() {
        switch (dir) {
            case "Up":
                dir = "Left";
                break;


            case "Right":
                dir = "Up";
                break;

            case "Down":
                dir = "Right";
                break;

            case "Left":
                dir = "Down";
                break;


        }

    }

    public void ClkWise() {
        switch (dir) {
            case "Up":
                dir = "Right";
                break;


            case "Right":
                dir = "Down";
                break;

            case "Down":
                dir = "Left";
                break;

            case "Left":
                dir = "Up";
                break;


        }

    }
}
