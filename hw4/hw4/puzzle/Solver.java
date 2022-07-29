package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import org.w3c.dom.Node;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;


public class Solver {
    int countMove = 0;
    SearchNode goal;
    HashMap<WorldState, Integer> estimate = new HashMap<>();
    Stack<WorldState> sol = new Stack<>();
    public class SearchNode implements Comparable<SearchNode>{
        WorldState curr;
        SearchNode prevNode;
        int moves;
        int est;
        public SearchNode (WorldState in, int move, SearchNode prev, int estimate) {
            curr = in;
            moves = move;
            prevNode = prev;
            est = estimate;

        }

        @Override
        public int compareTo(SearchNode another) {
            Integer CE = (moves + est);
            Integer AE = (another.est + another.moves);
            return CE.compareTo(AE);
        }
        public boolean parentCheck() {
            return ((Word)curr).equals((prevNode.curr));

            //return curr.equals(prevNode.curr);
        }
    }


    public Solver(WorldState initial) {
        MinPQ<SearchNode> PQ = new MinPQ<>();
        int dist = initial.estimatedDistanceToGoal();
        estimate.put(initial, dist);
        PQ.insert(new SearchNode(initial, 0, null, dist));

        while (!PQ.isEmpty()) {
            //System.out.println("move count check : " + countMove);
            SearchNode xx = PQ.delMin();
            if (xx.curr.isGoal()) {
                goal = xx;
                countMove = xx.moves;
                break;
            }
            countMove = 1 + xx.moves;
            for (WorldState i : xx.curr.neighbors()) {
                int jj;
                if (estimate.containsKey(i)) {
                    jj = estimate.get(i);
                }else {
                    jj = i.estimatedDistanceToGoal();
                    estimate.put(i, jj);
                }

                SearchNode temp = new SearchNode(i, countMove, xx, jj);
                //System.out.println("parent check output : " + temp.parentCheck());
                if (!Objects.nonNull(xx.prevNode)) {  // https://docs.oracle.com/javase/8/docs/api/java/util/Objects.html#nonNull-java.lang.Object-
                    PQ.insert(temp);
                }else if (!i.equals(xx.prevNode.curr)) {
                    PQ.insert(temp);
                }

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
