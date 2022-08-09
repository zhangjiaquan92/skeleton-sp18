import java.util.HashMap;
import java.util.Map;

/**
 * This class provides all code necessary to take a query box and produce
 * a query result. The getMapRaster method must return a Map containing all
 * seven of the required fields, otherwise the front end code will probably
 * not draw the output correctly.
 */
public class Rasterer {
    /*private double lrlon;
    private double ullon;
    private double w;
    private double h;
    private double ullat;
    private double lrlat;


*/
    //private double w;
    private double[] feetppCon = new double[8];



    final double lonStart = -122.2998046875;
    final double lonEnd = -122.2119140625;
    final double lanStart = 37.892195547244356;
    final double lanEnd = 37.82280243352756;
    final double lonDif = lonEnd - lonStart;
    final double lanDif = lanStart - lanEnd;


    // 288200 is only for latitude around 38 degrees north
    final double lonCon = 288200.0;

    public Rasterer() {

        // YOUR CODE HERE
        // d0: (abs(122.29980 - 122.21191) * 288200








    }

    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     *
     *     The grid of images must obey the following properties, where image in the
     *     grid is referred to as a "tile".
     *     <ul>
     *         <li>The tiles collected must cover the most longitudinal distance per pixel
     *         (LonDPP) possible, while still covering less than or equal to the amount of
     *         longitudinal distance per pixel in the query box for the user viewport size. </li>
     *         <li>Contains all tiles that intersect the query bounding box that fulfill the
     *         above condition.</li>
     *         <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     *     </ul>
     *
     * @param params Map of the HTTP GET request's query parameters - the query box and
     *               the user viewport width and height.
     *
     * @return A map of results for the front end as specified: <br>
     * "render_grid"   : String[][], the files to display. <br>
     * "raster_ul_lon" : Number, the bounding upper left longitude of the rastered image. <br>
     * "raster_ul_lat" : Number, the bounding upper left latitude of the rastered image. <br>
     * "raster_lr_lon" : Number, the bounding lower right longitude of the rastered image. <br>
     * "raster_lr_lat" : Number, the bounding lower right latitude of the rastered image. <br>
     * "depth"         : Number, the depth of the nodes of the rastered image <br>
     * "query_success" : Boolean, whether the query was able to successfully complete; don't
     *                    forget to set this to true on success! <br>
     */
    public Map<String, Object> getMapRaster(Map<String, Double> params) {
        // System.out.println(params);
        Map<String, Object> results = new HashMap<>();
        //System.out.println("Since you haven't implemented getMapRaster, nothing is displayed in "
         //                  + "your browser.");

        double lrlon = params.get("lrlon");
        double ullon = params.get("ullon");
        double wtemp = params.get("w");
        double h = params.get("h");
        double ullat = params.get("ullat");
        double lrlat = params.get("lrlat");
        // using the formula on slides, and 288200 is only for latitude around 38 degrees north

        double feetPp = Math.abs(ullon - lrlon) * lonCon / wtemp;
        int depthReturn = depthupdate(feetPp);

        // calculate the start and end of the grid
        int rowStart = lonCal(ullon, depthReturn);
        int colStart = lanCal(ullat, depthReturn);
        //int rowEnd = (int)Math.floor(wtemp / 256) + rowStart;
        //int colEnd = (int)Math.floor(h / 256) + colStart;

        int rowEnd = lonCal(lrlon, depthReturn);
        int colEnd = lanCal(lrlat, depthReturn);
        System.out.println("rowStart is:" + rowStart);
        System.out.println("colStart is:" + colStart);
        System.out.println("rowEnd is:" + rowEnd);
        System.out.println("colEnd is:" + colEnd);


        String[][] gridOut = new String[colEnd - colStart + 1][rowEnd - rowStart + 1];
        for (int i = 0; i <= (colEnd - colStart); i++) {
            for (int j = 0; j <= (rowEnd - rowStart); j++) {
                gridOut[i][j] = picName(j + rowStart,i + colStart,depthReturn);
            }
        }
        //lonLat[0] is ul_lon, lonLant[1] is lr_lon, lonLat[2] is ul_lat, lonLat[3] is lr_lat
        double lonLat[] = rasterCal(depthReturn, rowStart, rowEnd, colStart, colEnd);

        results.put("raster_ul_lon", lonLat[0]);
        results.put("depth", depthReturn);
        results.put("raster_lr_lon", lonLat[1]);
        results.put("raster_lr_lat", lonLat[3]);
        results.put("render_grid", gridOut);
        results.put("raster_ul_lat", lonLat[2]);
        results.put("query_success", true);




        return results;
    }



    private int depthupdate(double feetPp) {
        double temp = lonDif;
        for(int i = 0; i < 8; i++) {
            feetppCon[i] = temp * lonCon / 256;
            if (feetPp > feetppCon[i]) {
                System.out.println("feetPp is :" + feetPp);
                System.out.println("feetppCon [" + i + "] is :" + feetppCon[i]);
                return i;
            }
            //System.out.println("temp is :" + temp);
            temp = temp / 2;
        }
        return 7;

    }

    private int lonCal(double lonIn, int depth) {
        double block = Math.pow(2, depth);
        System.out.println("block is :" + block);
        double blockSize = lonDif / block;
        System.out.println("blocksize is :" + blockSize);
        int result = (int) Math.floor(Math.abs(lonIn - lonStart) / blockSize);
        System.out.println("result is :" + result);
        if(result >= block) {
            result = (int)block - 1;
        }
        return result;

    }

    private int lanCal(double lanIn, int depth) {
        double block = Math.pow(2, depth);
        double blockSize = lanDif / block;
        int result = (int) Math.floor(Math.abs(lanIn - lanStart) / blockSize);
        if(result >= block) {
            result = (int)block - 1;
        }
        return result;
    }

    private String picName(int row, int col, int depth) {
        String result = "d" + depth + "_x" + row + "_y" + col + ".png";
        return result;
    }

    private double[] rasterCal(int depth, int rowStart, int rowEnd, int colStart, int colEnd) {
        double block = Math.pow(2, depth);
        double lanStep = lanDif / block;
        double lonStep = lonDif / block;
        double result[] = new double[4];
        result[0] = lonStep * rowStart + lonStart;
        result[1] = lonStep * (rowEnd + 1) + lonStart;
        result[2] = lanStep * colStart + lanStart;
        result[3] = lanStep * (colEnd + 1) + lanStart;
        return result;



    }

    public static void main(String[] args) {
        Rasterer out = new Rasterer();
        double w = 892.0;
        double temp = Math.abs(122.29980 - 122.21191) * 288200.0 / w;
        int output = out.depthupdate(26);
        System.out.println("depth = " + output);

        int lenth = out.lonCal(-122.24053, 7);
        System.out.println("lonCal result: " + lenth);
        int lanth = out.lanCal(37.87548, 7);
        System.out.println("lonCal result: " + lanth);


        String filename = out.picName(3,5,4);
        System.out.println("filename is: " + filename);
    }

}
