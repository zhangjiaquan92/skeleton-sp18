import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.*;

/**
 * This class provides a shortestPath method for finding routes between two points
 * on the map. Start by using Dijkstra's, and if your code isn't fast enough for your
 * satisfaction (or the autograder), upgrade your implementation by switching it to A*.
 * Your code will probably not be fast enough to pass the autograder unless you use A*.
 * The difference between A* and Dijkstra's is only a couple of lines of code, and boils
 * down to the priority you use to order your vertices.
 */
public class Router {
    /**
     * Return a List of longs representing the shortest path from the node
     * closest to a start location and the node closest to the destination
     * location.
     * @param g The graph to use.
     * @param stlon The longitude of the start location.
     * @param stlat The latitude of the start location.
     * @param destlon The longitude of the destination location.
     * @param destlat The latitude of the destination location.
     * @return A list of node id's in the order visited on the shortest path.
     */
    public static List<Long> shortestPath(GraphDB g, double stlon, double stlat,
                                          double destlon, double destlat) {
        long startID = g.closest(stlon, stlat);
        long endID = g.closest(destlon, destlat);
        int startInt = g.vertMap.get(startID);
        int endInt = g.vertMap.get(endID);
        double actLon = g.lon(startID);
        double actLat = g.lat(startID);
        Map<Integer, Double> best = new HashMap<>(g.vertMap.size());
        boolean[] marking = new boolean[g.vertMap.size()];

        //parentMap is mapping from son to parent
        Map<Long, Long> parentMap = new HashMap<>();
        for (int i = 0; i < g.vertMap.size(); i++) {
            best.put(i, 999999999.9);
            marking[i] = false;
        }
        List<Long> sol = new ArrayList<>();

        class locNode implements Comparable<locNode> {

            long IDin;
            double priority;
            locNode parentNode;


            public locNode (long ID, double pri, locNode prev) {

                IDin = ID;
                priority = pri;
                parentNode = prev;
            }

            @Override
            public int compareTo(locNode in) {
                Double CE = priority;
                Double AE = in.priority;
                return CE.compareTo(AE);

            }
        }

        PriorityQueue<locNode> pq = new PriorityQueue<>();


        pq.add(new locNode(startID, 999999999, null));
        best.put(g.vertMap.get(startID), 0.0);
        marking[g.vertMap.get(startID)] = true;
        //sol.add(startID);
        while (!pq.isEmpty()) {
            locNode temp = pq.poll();
            long tempID = temp.IDin;
            if (tempID == endID) {
                //sol.push(tempID);
                break;


            }
            double dsv = best.get(g.vertMap.get(tempID));
            //System.out.println("dsv = " + dsv);
            for (long x: g.adjacent(tempID)){
                if (marking[g.vertMap.get(x)]){
                    continue;
                }else {
                    double edvw = g.distance(tempID, x);
                    double hw = g.distance(x, endID);
                    if((dsv + edvw) <= best.get(g.vertMap.get(x))) {
                        best.put(g.vertMap.get(x),  (dsv + edvw));
                        parentMap.put(x,tempID);
                        //addPq is the priority distance equals to d(s, v) + ed(v, w) + h(w)
                        double addPq = dsv + edvw + hw;
                        pq.add(new locNode(x, addPq, temp));
                        //sol.add(x);
                    }
                }
                //double dsv = g.distance(startID, tempID);

            }
        }
        long tts = endID;
        while(tts != startID) {
            sol.add(0, tts);
            tts = parentMap.get(tts);
        }
        sol.add(0, startID);
        return sol;
    }

    /**
     * Create the list of directions corresponding to a route on the graph.
     * @param g The graph to use.
     * @param route The route to translate into directions. Each element
     *              corresponds to a node from the graph in the route.
     * @return A list of NavigatiionDirection objects corresponding to the input
     * route.
     */
    public static List<NavigationDirection> routeDirections(GraphDB g, List<Long> route) {
        return null; // FIXME
    }


    /**
     * Class to represent a navigation direction, which consists of 3 attributes:
     * a direction to go, a way, and the distance to travel for.
     */
    public static class NavigationDirection {

        /** Integer constants representing directions. */
        public static final int START = 0;
        public static final int STRAIGHT = 1;
        public static final int SLIGHT_LEFT = 2;
        public static final int SLIGHT_RIGHT = 3;
        public static final int RIGHT = 4;
        public static final int LEFT = 5;
        public static final int SHARP_LEFT = 6;
        public static final int SHARP_RIGHT = 7;

        /** Number of directions supported. */
        public static final int NUM_DIRECTIONS = 8;

        /** A mapping of integer values to directions.*/
        public static final String[] DIRECTIONS = new String[NUM_DIRECTIONS];

        /** Default name for an unknown way. */
        public static final String UNKNOWN_ROAD = "unknown road";
        
        /** Static initializer. */
        static {
            DIRECTIONS[START] = "Start";
            DIRECTIONS[STRAIGHT] = "Go straight";
            DIRECTIONS[SLIGHT_LEFT] = "Slight left";
            DIRECTIONS[SLIGHT_RIGHT] = "Slight right";
            DIRECTIONS[LEFT] = "Turn left";
            DIRECTIONS[RIGHT] = "Turn right";
            DIRECTIONS[SHARP_LEFT] = "Sharp left";
            DIRECTIONS[SHARP_RIGHT] = "Sharp right";
        }

        /** The direction a given NavigationDirection represents.*/
        int direction;
        /** The name of the way I represent. */
        String way;
        /** The distance along this way I represent. */
        double distance;

        /**
         * Create a default, anonymous NavigationDirection.
         */
        public NavigationDirection() {
            this.direction = STRAIGHT;
            this.way = UNKNOWN_ROAD;
            this.distance = 0.0;
        }

        public String toString() {
            return String.format("%s on %s and continue for %.3f miles.",
                    DIRECTIONS[direction], way, distance);
        }

        /**
         * Takes the string representation of a navigation direction and converts it into
         * a Navigation Direction object.
         * @param dirAsString The string representation of the NavigationDirection.
         * @return A NavigationDirection object representing the input string.
         */
        public static NavigationDirection fromString(String dirAsString) {
            String regex = "([a-zA-Z\\s]+) on ([\\w\\s]*) and continue for ([0-9\\.]+) miles\\.";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(dirAsString);
            NavigationDirection nd = new NavigationDirection();
            if (m.matches()) {
                String direction = m.group(1);
                if (direction.equals("Start")) {
                    nd.direction = NavigationDirection.START;
                } else if (direction.equals("Go straight")) {
                    nd.direction = NavigationDirection.STRAIGHT;
                } else if (direction.equals("Slight left")) {
                    nd.direction = NavigationDirection.SLIGHT_LEFT;
                } else if (direction.equals("Slight right")) {
                    nd.direction = NavigationDirection.SLIGHT_RIGHT;
                } else if (direction.equals("Turn right")) {
                    nd.direction = NavigationDirection.RIGHT;
                } else if (direction.equals("Turn left")) {
                    nd.direction = NavigationDirection.LEFT;
                } else if (direction.equals("Sharp left")) {
                    nd.direction = NavigationDirection.SHARP_LEFT;
                } else if (direction.equals("Sharp right")) {
                    nd.direction = NavigationDirection.SHARP_RIGHT;
                } else {
                    return null;
                }

                nd.way = m.group(2);
                try {
                    nd.distance = Double.parseDouble(m.group(3));
                } catch (NumberFormatException e) {
                    return null;
                }
                return nd;
            } else {
                // not a valid nd
                return null;
            }
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof NavigationDirection) {
                return direction == ((NavigationDirection) o).direction
                    && way.equals(((NavigationDirection) o).way)
                    && distance == ((NavigationDirection) o).distance;
            }
            return false;
        }

        @Override
        public int hashCode() {
            return Objects.hash(direction, way, distance);
        }
    }
}
