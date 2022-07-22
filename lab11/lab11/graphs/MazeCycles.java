package lab11.graphs;

/**
 *  @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private boolean targetFound = false;



    public MazeCycles(Maze m) {
        super(m);


    }

    @Override
    public void solve() {
        // TODO: Your code here!



        // dfs recursion

        edgeTo[0] = 0;
        //marked[0] = true;
        dfs_helper(0, 0);




    }

    // Helper methods go here

    public void dfs_helper(int v, int parent) {

        //announce();


        if (targetFound == true) {
            //edgeTo[v] = v;
            return;
        }
        marked[v] = true;
        announce();


        for (int u : maze.adj(v)) {
            System.out.println("u is: " + u);
            if (!marked[u]) {
                dfs_helper(u, v);
                if (targetFound == true) {
                    edgeTo[u] = v;
                    System.out.println("in loop, u is: " + u);
                    //return;

                }

            } else if (parent != u){
                targetFound = true;
                //marked[u] = true;
                edgeTo[v] = u;
                announce();

                return;

                //edgeTo[v] = Integer.MAX_VALUE;
            }


            //edgeTo[v] = Integer.MAX_VALUE;



        }



    }
}

