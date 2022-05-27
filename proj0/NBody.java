import java.io.File;

public class NBody {
    public static double readRadius(String path){
        In in = new In(path);
        int num = in.readInt();
        double rad = in.readDouble();

        return rad;

    }
    public static Planet[] readPlanets(String path){
        /*In tt = new In(path);
        int i = 0;
        while(!tt.isEmpty()){
            String test = tt.readLine();
            i++;

        }

         */
        In in = new In(path);
        int num = in.readInt();
        double rad = in.readDouble();


        Planet[] temp = new Planet[num];


        for(int j = 0; j<temp.length;j++) {
            double xx = in.readDouble();
            double yy = in.readDouble();
            double Vxx = in.readDouble();
            double Vyy = in.readDouble();
            double ms = in.readDouble();
            String name = in.readString();
            temp[j]= new Planet(xx,yy,Vxx,Vyy,ms,name);

            

        }
    return temp;
    }

    public static void main(String[] args) {
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];
        Planet[] Planets = readPlanets(filename);

        StdDraw.setScale(-readRadius(filename), readRadius(filename));
        StdDraw.clear();
        StdDraw.picture(0, 0, "images/starfield.jpg");


        for(Planet i:Planets){
            i.draw();
        }
        StdDraw.show();

        StdDraw.enableDoubleBuffering();
        double time = 0;
        while(time <= T){
            double[] xForce = new double[Planets.length];
            double[] yForce = new double[Planets.length];
            for(int k=0; k < Planets.length;k++){
                xForce[k] = Planets[k].calcNetForceExertedByX(Planets);
                yForce[k] = Planets[k].calcNetForceExertedByY(Planets);
                Planets[k].update(dt,xForce[k],yForce[k]);


            }
            StdDraw.picture(0, 0, "images/starfield.jpg");
            for(int g=0; g < Planets.length;g++){
                Planets[g].update(dt,xForce[g],yForce[g]);
                Planets[g].draw();

            }

            StdDraw.show();
            StdDraw.pause(10);
            time = time + dt;
        }





    }
}
