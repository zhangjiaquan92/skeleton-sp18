package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;
import org.w3c.dom.Node;


public class Solver {
    int countMove;
    public class SearchNode {
        WorldState curr;
        SearchNode prevNode;
        int moves;
        public SearchNode (WorldState in, int move, SearchNode prev) {
            curr = in;
            moves = move;
            prevNode = prev;

        }

    }
    public void solveHelper(SearchNode) {
        if

    }

    public Solver(WorldState initial) {
        MinPQ<SearchNode> temp = new MinPQ<>();
        temp.insert(new SearchNode(initial, 0,  null));
        SearchNode xx = temp.delMin();






    }
    public int moves(){
        return countMove;
    }
    public Iterable<WorldState> solution(){

    }

}
