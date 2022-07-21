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
        dfs_helper(0);









    }

    // Helper methods go here

    public void dfs_helper(int v) {
        marked[v] = true;
        //announce();

        if (targetFound == true) {
            return;
        }


        for (int w : maze.adj(v)) {
            if (!marked[w]) {
                marked[w] = true;
                edgeTo[w] = v;
                //edgeTo[v] = Integer.MAX_VALUE;




                dfs_helper(w);
                //announce();

            } else if (edgeTo[v] != w){
                targetFound = true;
                marked[w] = true;
                edgeTo[w] = v;

                announce();

                return;

                //edgeTo[v] = Integer.MAX_VALUE;



            }
            //edgeTo[v] = Integer.MAX_VALUE;



        }



    }
}

