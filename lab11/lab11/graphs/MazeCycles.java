package lab11.graphs;

import edu.princeton.cs.algs4.Stack;

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
        /*

        edgeTo[0] = 0;
        //marked[0] = true;
        dfs_helper(0, 0);
        */
        // dfs iteration
        edgeTo[0] = 0;
        dfs_Iter(0);




    }

    // Helper methods go here

    public void dfs_helper(int v, int parent) {
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
                System.out.println("announce checker");
                announce();

                return;

                //edgeTo[v] = Integer.MAX_VALUE;
            }


            //edgeTo[v] = Integer.MAX_VALUE;



        }



    }

    public void dfs_Iter(int v) {
        Stack<Integer> holder = new Stack();
        holder.push(v);
        //marked[v] = true;
        int parent = v;
        while(!holder.isEmpty()) {
            int temp = holder.pop();
            if((targetFound) && (temp == parent)){
                break;
            }
            if (targetFound) {
                int xx = edgeTo[temp];
                announce();
                holder.push(xx);

            }

            if (!marked[temp]) {
                marked[temp] = true;
                announce();
            }
            for(int u : maze.adj(temp)) {
                if (!marked[u]) {
                    holder.push(u);
                    //edgeTo[u] = temp;
                    parent = temp;
                    //announce();
                } else if (u != parent) {
                    targetFound = true;
                    edgeTo[u] = parent;
                    holder.push(temp);
                }
            }


        }


    }
}

