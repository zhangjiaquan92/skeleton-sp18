package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import org.w3c.dom.Node;

import java.util.Iterator;


public class Solver {
    int countMove = 0;
    SearchNode goal;
    Stack<WorldState> sol = new Stack<>();
    public class SearchNode implements Comparable<SearchNode>{
        WorldState curr;
        SearchNode prevNode;
        int moves;
        int est;
        public SearchNode (WorldState in, int move, SearchNode prev) {
            curr = in;
            moves = move;
            prevNode = prev;
            est = in.estimatedDistanceToGoal();

        }

        @Override
        public int compareTo(SearchNode another) {
            Integer CE = (moves + est);
            Integer AE = (another.est + another.moves);
            return CE.compareTo(AE);
        }
    }


    public Solver(WorldState initial) {
        MinPQ<SearchNode> PQ = new MinPQ<>();
        PQ.insert(new SearchNode(initial, 0, null));

        while (!PQ.isEmpty()) {
            //System.out.println("move count check : " + countMove);
            SearchNode xx = PQ.delMin();
            if (xx.curr.isGoal()) {
                goal = xx;
                break;
            }
            countMove += 1;
            int s = 0;
            for (WorldState i : xx.curr.neighbors()) {

                PQ.insert(new SearchNode(i, countMove, xx));
            }

        }

        int j;
        for (j = 0; j <= countMove; j += 1) {

            sol.push(goal.curr);
            goal = goal.prevNode;
        }

        return;



    }
    public int moves(){
        return countMove;
    }
    public Iterable<WorldState> solution(){
        return sol;

    }

}
