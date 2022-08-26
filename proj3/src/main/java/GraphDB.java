import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Graph for storing all of the intersection (vertex) and road (edge) information.
 * Uses your GraphBuildingHandler to convert the XML files into a graph. Your
 * code must include the vertices, adjacent, distance, closest, lat, and lon
 * methods. You'll also need to include instance variables and methods for
 * modifying the graph (e.g. addNode and addEdge).
 *
 * @author Alan Yao, Josh Hug
 */
public class GraphDB {
    /** Your instance variables for storing the graph. You should consider
     * creating helper classes, e.g. Node, Edge, etc. */

    /**
     * Example constructor shows how to create and start an XML parser.
     * You do not need to modify this constructor, but you're welcome to do so.
     * @param dbPath Path to the XML file to be parsed.
     */
    //Mapping the vertice ID to integer starting from 0
    Map<Long, Integer> vertMap = new HashMap<>();
    // mapping integer to the lat and lon of the location
    Map<Integer, double[]> latlonMap = new HashMap<>();
    Map<Integer, ArrayList<Long>> closeMap = new HashMap<>();

    List<Long> verti = new ArrayList<>();

    List<Long>[] adj;

    double lonDif = MapServer.ROOT_LRLON - MapServer.ROOT_ULLON;
    double latDif = MapServer.ROOT_ULLAT - MapServer.ROOT_LRLAT;

    public GraphDB(String dbPath) {
        try {
            File inputFile = new File(dbPath);
            FileInputStream inputStream = new FileInputStream(inputFile);
            // GZIPInputStream stream = new GZIPInputStream(inputStream);

            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            GraphBuildingHandler gbh = new GraphBuildingHandler(this);
            //gbhin = gbh;
            saxParser.parse(inputStream, gbh);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        clean();
    }

    /**
     * Helper to process strings into their "cleaned" form, ignoring punctuation and capitalization.
     * @param s Input string.
     * @return Cleaned string.
     */
    static String cleanString(String s) {
        return s.replaceAll("[^a-zA-Z ]", "").toLowerCase();
    }

    /**
     *  Remove nodes with no connections from the graph.
     *  While this does not guarantee that any two nodes in the remaining graph are connected,
     *  we can reasonably assume this since typically roads are connected.
     */
    private void clean() {

        for(int i = 0; i < adj.length; i++) {
            if(adj[i].isEmpty()) {
                verti.set(i, -3L);
            }
        }

        //reference the removeAll method example from
        // https://www.geeksforgeeks.org/arraylist-removeall-method-in-java-with-examples/
        List<Long> delete = new ArrayList<>();
        delete.add(-3L);
        verti.removeAll(delete);
        // TODO: Your code here.


    }


    /**
     * Returns an iterable of all vertex IDs in the graph.
     * @return An iterable of id's of all vertices in the graph.
     */
    Iterable<Long> vertices() {
        //YOUR CODE HERE, this currently returns only an empty list.

        return verti;
    }

    /**
     * Returns ids of all vertices adjacent to v.
     * @param v The id of the vertex we are looking adjacent to.
     * @return An iterable of the ids of the neighbors of v.
     */
    Iterable<Long> adjacent(long v) {
        int vert = vertMap.get(v);
        List<Long> adjList = adj[vert];
        return adjList;
    }

    /**
     * Returns the great-circle distance between vertices v and w in miles.
     * Assumes the lon/lat methods are implemented properly.
     * <a href="https://www.movable-type.co.uk/scripts/latlong.html">Source</a>.
     * @param v The id of the first vertex.
     * @param w The id of the second vertex.
     * @return The great-circle distance between the two locations from the graph.
     */
    double distance(long v, long w) {
        return distance(lon(v), lat(v), lon(w), lat(w));
    }

    static double distance(double lonV, double latV, double lonW, double latW) {
        double phi1 = Math.toRadians(latV);
        double phi2 = Math.toRadians(latW);
        double dphi = Math.toRadians(latW - latV);
        double dlambda = Math.toRadians(lonW - lonV);

        double a = Math.sin(dphi / 2.0) * Math.sin(dphi / 2.0);
        a += Math.cos(phi1) * Math.cos(phi2) * Math.sin(dlambda / 2.0) * Math.sin(dlambda / 2.0);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return (3963 * c);
    }

    /**
     * Returns the initial bearing (angle) between vertices v and w in degrees.
     * The initial bearing is the angle that, if followed in a straight line
     * along a great-circle arc from the starting point, would take you to the
     * end point.
     * Assumes the lon/lat methods are implemented properly.
     * <a href="https://www.movable-type.co.uk/scripts/latlong.html">Source</a>.
     * @param v The id of the first vertex.
     * @param w The id of the second vertex.
     * @return The initial bearing between the vertices.
     */
    double bearing(long v, long w) {
        return bearing(lon(v), lat(v), lon(w), lat(w));
    }

    static double bearing(double lonV, double latV, double lonW, double latW) {
        double phi1 = Math.toRadians(latV);
        double phi2 = Math.toRadians(latW);
        double lambda1 = Math.toRadians(lonV);
        double lambda2 = Math.toRadians(lonW);

        double y = Math.sin(lambda2 - lambda1) * Math.cos(phi2);
        double x = Math.cos(phi1) * Math.sin(phi2);
        x -= Math.sin(phi1) * Math.cos(phi2) * Math.cos(lambda2 - lambda1);
        return Math.toDegrees(Math.atan2(y, x));
    }

    /**
     * Returns the vertex closest to the given longitude and latitude.
     * @param lon The target longitude.
     * @param lat The target latitude.
     * @return The id of the node in the graph closest to the target.
     */
    long closest(double lon, double lat) {
        /*double saveDist = -1;
        long saveID = 0;
        for (int i = 0; i < verti.size(); i++) {
            long temp = verti.get(i);
            int id = vertMap.get(temp);
            double[] mark = latlonMap.get(id);
            double dist = distance(mark[0], mark[1], lon, lat);
            if (dist == 0) {
                return temp;
            }
            if (saveDist == -1) {
                saveDist = dist;
                saveID = temp;
            } else if (saveDist > dist) {
                saveDist = dist;
                saveID = temp;
            }
        }
        System.out.println("in the closest loop");
        return saveID;

         */
        //System.out.println("inside the loop test.");
        double saveDist = -1;
        long saveID = 0;
        Integer tts = groupHelper(lon, lat);
        //System.out.println("array asdfasdfasdf size is : " + tts);

        //ArrayList<Long> tmep = closeMap.get(tts);
        //System.out.println("array asdfasdfasdf size is : " + closeMap.get(tts).isEmpty());
        for(long i : closeMap.get(tts)) {
            double dist = distance(lon, lat, this.lon(i), this.lat(i));
            if (dist == 0) {
                return i;
            }
            if (saveDist == -1) {
                saveDist = dist;
                saveID = i;
            } else if (saveDist > dist) {
                saveDist = dist;
                saveID = i;
            }
        }

        return saveID;


    }
    Integer groupHelper(double lon, double lat) {
        int depth = 1;

        double block = Math.pow(2, depth);


        //System.out.println("Lon is : " + lon);
        //System.out.println("Lat is : " + lat);
        double blockSize = lonDif / block;
        //System.out.println("blocksize is :" + blockSize);
        Integer col = (int) Math.floor(Math.abs(lon - MapServer.ROOT_ULLON) / blockSize);
        //System.out.println("result is :" + result);


        //double block2 = Math.pow(2, depth);
        double blockSize2 = latDif / block;
        Integer row = (int) Math.floor(Math.abs(lat - MapServer.ROOT_ULLAT) / blockSize2);
        //System.out.println("row is : " + row);
        //System.out.println("col is : " + col);

        Integer[] tempo = {row, col};
        return arrayToint(tempo, block);
        //System.out.println("tempo [0] is : " + tempo[0]);
        //System.out.println("tempo [1] is : " + tempo[1]);
        //return tempo;
    }

    private Integer arrayToint(Integer[] array, double size) {
        //double size = Math.pow(2, depth);
        int row = array[0];
        int col = array[1];
        return ((row) * (int) size + col + 1);

    }



    /**
     * Gets the longitude of a vertex.
     * @param v The id of the vertex.
     * @return The longitude of the vertex.
     */
    double lon(long v) {

        int vert = vertMap.get(v);
        double[] lonLat = latlonMap.get(vert);
        return lonLat[0];

    }

    /**
     * Gets the latitude of a vertex.
     * @param v The id of the vertex.
     * @return The latitude of the vertex.
     */
    double lat(long v) {

        int vert = vertMap.get(v);
        double[] lonLat = latlonMap.get(vert);
        return lonLat[1];
    }

}
