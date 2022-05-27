

public class Planet {

    /* its current x position **/
    public double xxPos;
    /* its current y position **/
    public double yyPos;
    /* its current velocity in the x direction **/
    public double xxVel;
    /* its current velocity in y direction **/
    public double yyVel;

    /* its mass **/
    public double mass;
    /* the name of the file that corresponds to the image that depicts the planet */
    public String imgFileName;
    /* unit is Nm^2/kg^2 for G **/
    private static final double Gravi = 6.67e-11;
    public Planet(double xP, double yP, double xV,
                  double yV, double m, String img){
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;

    }
    public Planet(Planet p){
        xxPos = p.xxPos;
        yyPos = p.yyPos;
        xxVel = p.xxVel;
        yyVel = p.yyVel;
        mass = p.mass;
        imgFileName = p.imgFileName;

    }
    public double calcDistance(Planet p){
        double dist;
        dist = Math.sqrt((p.xxPos - xxPos)*(p.xxPos - xxPos)+(p.yyPos-yyPos)*(p.yyPos-yyPos));

        return dist;

    }

    public double calcForceExertedBy(Planet p){
        double dist;
        double force;
        dist = calcDistance(p);
        force = Gravi * mass * p.mass/dist/dist;
        return force;

    }
    public double calcForceExertedByX(Planet p){
        double fx = (p.xxPos - xxPos) * calcForceExertedBy(p)/calcDistance(p);
        return fx;
    }

    public double calcForceExertedByY(Planet p){
        double fy = (p.yyPos - yyPos) * calcForceExertedBy(p)/calcDistance(p);
        return fy;
    }

    public double calcNetForceExertedByX(Planet allPlanets[]){
        double forcex = 0;
        for (Planet p : allPlanets) {
            if(this.equals(p)){
                continue;
            }
            else{
                forcex = forcex + calcForceExertedByX(p);
            }
        }
        return forcex;
    }
    public double calcNetForceExertedByY(Planet allPlanets[]){
        double forceY = 0;
        for (Planet p : allPlanets) {
            if(this.equals(p)){
                continue;
            }
            else{
                forceY = forceY + calcForceExertedByY(p);
            }
        }
        return forceY;
    }
    public void update(double dt, double fX, double fY){
        double ax = fX/mass;
        double ay = fY/mass;
        xxVel = xxVel + dt*ax;
        yyVel = yyVel + dt*ay;
        xxPos = xxPos + dt*xxVel;
        yyPos = yyPos + dt*yyVel;


    }
    public void draw(){
        String filepath = "images/"+imgFileName;
        StdDraw.picture(xxPos,yyPos,filepath);
    }

}
