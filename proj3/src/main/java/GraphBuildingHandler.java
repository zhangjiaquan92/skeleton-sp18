import org.w3c.dom.Node;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.*;

/**
 *  Parses OSM XML files using an XML SAX parser. Used to construct the graph of roads for
 *  pathfinding, under some constraints.
 *  See OSM documentation on
 *  <a href="http://wiki.openstreetmap.org/wiki/Key:highway">the highway tag</a>,
 *  <a href="http://wiki.openstreetmap.org/wiki/Way">the way XML element</a>,
 *  <a href="http://wiki.openstreetmap.org/wiki/Node">the node XML element</a>,
 *  and the java
 *  <a href="https://docs.oracle.com/javase/tutorial/jaxp/sax/parsing.html">SAX parser tutorial</a>.
 *
 *  You may find the CSCourseGraphDB and CSCourseGraphDBHandler examples useful.
 *
 *  The idea here is that some external library is going to walk through the XML
 *  file, and your override method tells Java what to do every time it gets to the next
 *  element in the file. This is a very common but strange-when-you-first-see it pattern.
 *  It is similar to the Visitor pattern we discussed for graphs.
 *
 *  @author Alan Yao, Maurice Lee
 */
public class GraphBuildingHandler extends DefaultHandler {
    /**
     * Only allow for non-service roads; this prevents going on pedestrian streets as much as
     * possible. Note that in Berkeley, many of the campus roads are tagged as motor vehicle
     * roads, but in practice we walk all over them with such impunity that we forget cars can
     * actually drive on them.
     */
    private static final Set<String> ALLOWED_HIGHWAY_TYPES = new HashSet<>(Arrays.asList
            ("motorway", "trunk", "primary", "secondary", "tertiary", "unclassified",
                    "residential", "living_street", "motorway_link", "trunk_link", "primary_link",
                    "secondary_link", "tertiary_link"));
    private String activeState = "";
    private final GraphDB g;
    //Mapping the vertice ID to integer starting from 0
    //private Map<String, Integer> vertMap = new HashMap<>();
    // mapping integer to the lat and lon of the location
    //private Map<Integer, Double[]> latlonMap = new HashMap<>();
    //List<Long>[] adj;

    private int nodeCount = 0;
    private long wayCur = -1;
    private Stack<long[]> wayPair = new Stack<>();
    private boolean saveCheck = false;
    private boolean adjCheck = false;

    /**
     * Create a new GraphBuildingHandler.
     * @param g The graph to populate with the XML data.
     */
    public GraphBuildingHandler(GraphDB g) {
        this.g = g;
    }

    /**
     * Called at the beginning of an element. Typically, you will want to handle each element in
     * here, and you may want to track the parent element.
     * @param uri The Namespace URI, or the empty string if the element has no Namespace URI or
     *            if Namespace processing is not being performed.
     * @param localName The local name (without prefix), or the empty string if Namespace
     *                  processing is not being performed.
     * @param qName The qualified name (with prefix), or the empty string if qualified names are
     *              not available. This tells us which element we're looking at.
     * @param attributes The attributes attached to the element. If there are no attributes, it
     *                   shall be an empty Attributes object.
     * @throws SAXException Any SAX exception, possibly wrapping another exception.
     * @see Attributes
     */
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes)
            throws SAXException {
        /* Some example code on how you might begin to parse XML files. */
        if (qName.equals("node")) {
            activeState = "node";
            String vertID = attributes.getValue("id");
            long vertLong = Long.parseLong(vertID);

            g.vertMap.put(vertLong, nodeCount);
            String tempLon = attributes.getValue("lon");
            String tempLat = attributes.getValue("lat");
            double Dlon = Double.parseDouble(tempLon);
            double Dlat = Double.parseDouble(tempLat);
            double[] array = new double[]{Dlon, Dlat};

            g.latlonMap.put(nodeCount, array);
            //long vertLong = Long.parseLong(vertID);
            g.verti.add(vertLong);

            nodeCount++;




            /* We encountered a new <node...> tag. */
            /*
            System.out.println("Node id: " + attributes.getValue("id"));
            System.out.println("Node lon: " + attributes.getValue("lon"));
            System.out.println("Node lat: " + attributes.getValue("lat"));

             */

            /* TODO Use the above information to save a "node" to somewhere. */
            /* Hint: A graph-like structure would be nice. */

        } else if (qName.equals("way")) {
            /* We encountered a new <way...> tag. */
            activeState = "way";


//            System.out.println("Beginning a way...");
        } else if (activeState.equals("way") && qName.equals("nd")) {
            /* While looking at a way, we found a <nd...> tag. */
            String temp = attributes.getValue("ref");
            long tempLong = Long.parseLong(temp);



            //long out = g.vertMap.get(tempLong);
            if (wayCur != -1) {
                long[] adding = new long[]{wayCur, tempLong};
                wayPair.push(adding);
            }
            wayCur = tempLong;


            //System.out.println("Id of a node in this way: " + attributes.getValue("ref"));

            /* TODO Use the above id to make "possible" connections between the nodes in this way */
            /* Hint1: It would be useful to remember what was the last node in this way. */
            /* Hint2: Not all ways are valid. So, directly connecting the nodes here would be
            cumbersome since you might have to remove the connections if you later see a tag that
            makes this way invalid. Instead, think of keeping a list of possible connections and
            remember whether this way is valid or not. */

        } else if (activeState.equals("way") && qName.equals("tag")) {
            /* While looking at a way, we found a <tag...> tag. */
            String k = attributes.getValue("k");
            String v = attributes.getValue("v");
            if (k.equals("maxspeed")) {
                //System.out.println("Max Speed: " + v);
                /* TODO set the max speed of the "current way" here. */
            } else if (k.equals("highway")) {
                if(ALLOWED_HIGHWAY_TYPES.contains(v)) {
                    saveCheck = true;
                }
                //System.out.println("Highway type: " + v);
                /* TODO Figure out whether this way and its connections are valid. */
                /* Hint: Setting a "flag" is good enough! */
            } else if (k.equals("name")) {
                //System.out.println("Way Name: " + v);
            }
//            System.out.println("Tag with k=" + k + ", v=" + v + ".");
        } else if (activeState.equals("node") && qName.equals("tag") && attributes.getValue("k")
                .equals("name")) {
            /* While looking at a node, we found a <tag...> with k="name". */
            /* TODO Create a location. */
            /* Hint: Since we found this <tag...> INSIDE a node, we should probably remember which
            node this tag belongs to. Remember XML is parsed top-to-bottom, so probably it's the
            last node that you looked at (check the first if-case). */
//            System.out.println("Node's name: " + attributes.getValue("v"));
        }
    }

    /**
     * Receive notification of the end of an element. You may want to take specific terminating
     * actions here, like finalizing vertices or edges found.
     * @param uri The Namespace URI, or the empty string if the element has no Namespace URI or
     *            if Namespace processing is not being performed.
     * @param localName The local name (without prefix), or the empty string if Namespace
     *                  processing is not being performed.
     * @param qName The qualified name (with prefix), or the empty string if qualified names are
     *              not available.
     * @throws SAXException  Any SAX exception, possibly wrapping another exception.
     */
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equals("way")) {
            /* We are done looking at a way. (We finished looking at the nodes, speeds, etc...)*/
            /* Hint1: If you have stored the possible connections for this way, here's your
            chance to actually connect the nodes together if the way is valid. */
//            System.out.println("Finishing a way...");


            if(!adjCheck) {
                g.adj = new ArrayList[nodeCount];
                //System.out.println("size of adj is :" + adj.length);
                /*for (int v = 0; v < nodeCount; v++) {
                    g.adj[v] = new ArrayList<Long>();

                }

                 */






                adjCheck = true;
            }





            if(saveCheck) {
                while(!wayPair.empty()) {
                    long[] out = wayPair.pop();
                    long friendOne = out[0];
                    long friendTwo = out[1];

                    double friend1lon = g.lon(friendOne);
                    double friend1lat = g.lat(friendOne);
                    double friend2lon = g.lon(friendTwo);
                    double friend2lat = g.lat(friendTwo);
                    /*
                    Integer group1 = g.groupHelper(friend1lon, friend1lat);
                    Integer group2 = g.groupHelper(friend2lon, friend2lat);
                    if(g.closeMap.containsKey(group1)) {
                        ArrayList<Long> temp = g.closeMap.get(group1);
                        temp.add(friendOne);
                        g.closeMap.replace(group1, temp);
                    } else {
                        ArrayList<Long> ttt = new ArrayList<>();
                        ttt.add(friendOne);
                        g.closeMap.put(group1,ttt);
                    }
                    if(g.closeMap.containsKey(group2)) {
                        ArrayList<Long> temp2 = g.closeMap.get(group2);
                        temp2.add(friendTwo);
                        g.closeMap.replace(group2, temp2);
                    } else {
                        ArrayList<Long> ttt2 = new ArrayList<>();
                        ttt2.add(friendTwo);
                        g.closeMap.put(group2,ttt2);
                    }

                     */




                    //System.out.println("friendOne is :" + friendOne);
                    //System.out.println("friendTwo is :" + friendTwo);
                    int friend1Map = g.vertMap.get(friendOne);
                    int friend2Map = g.vertMap.get(friendTwo);
                    if(Objects.isNull(g.adj[friend2Map])){
                        g.adj[friend2Map] = new ArrayList<Long>();
                    }
                    if(Objects.isNull(g.adj[friend1Map])){
                        g.adj[friend1Map] = new ArrayList<Long>();
                    }

                    g.adj[friend1Map].add(friendTwo);
                    g.adj[friend2Map].add(friendOne);
                    //ArrayList<Long> temp1 = g.adj.get(friend1Map);
                    //ArrayList<Long> temp2 = g.adj.get(friend2Map);
                    //temp1.add(friendTwo);
                    //temp2.add(friendOne);
                    //g.adj.set(friend1Map, temp1);
                    //g.adj.set(friend2Map, temp2);




                    //g.adj[(int)out[0]].add(out[1]);
                    //g.adj[(int)out[1]].add(out[0]);
                }
            }else {

                wayPair.clear();
            }
            wayCur = -1;
            saveCheck = false;
            //System.out.println("nodecount is :" + nodeCount);
        }
    }



}
