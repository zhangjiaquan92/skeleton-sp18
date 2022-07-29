package hw4.puzzle;
import edu.princeton.cs.algs4.Queue;
public class Board {
    int size;
    int[][] memory;
    int hammingCount = 0;
    int manCounter = 0;


    /** Returns the string representation of the board. 
      * Uncomment this method. */
    public Board(int[][] tiles) {
        size = tiles.length;
        memory = new int[size][size];
        int temp = 1;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                memory[i][j] = tiles[i][j];
                if (memory[i][j] != temp && memory[i][j] != 0) {
                    hammingCount = hammingCount + 1;
                    manCounter += manHelper(memory[i][j], i, j);

                }
                temp = temp + 1;
            }



        }
    }
    public int manHelper(int number, int i, int j) {
        int row = number / size;
        int col = number % size;

        return (row - i + col - j);

    }
    public int tileAt(int i, int j) {
        return memory[i - 1][j - 1];

    }
    public int size() {
        return size;

    }
    public Iterable<WorldState> neighbors() { //http://joshh.ug/neighbors.html
        Queue<WorldState> neighbors = new Queue<>();
        int hug = size();
        int bug = -1;
        int zug = -1;
        for (int rug = 0; rug < hug; rug++) {
            for (int tug = 0; tug < hug; tug++) {
                if (tileAt(rug, tug) == 0) {
                    bug = rug;
                    zug = tug;
                }
            }
        }
        int[][] ili1li1 = new int[hug][hug];
        for (int pug = 0; pug < hug; pug++) {
            for (int yug = 0; yug < hug; yug++) {
                ili1li1[pug][yug] = tileAt(pug, yug);
            }
        }
        for (int l11il = 0; l11il < hug; l11il++) {
            for (int lil1il1 = 0; lil1il1 < hug; lil1il1++) {
                if (Math.abs(-bug + l11il) + Math.abs(lil1il1 - zug) - 1 == 0) {
                    ili1li1[bug][zug] = ili1li1[l11il][lil1il1];
                    ili1li1[l11il][lil1il1] = 0;
                    Board neighbor = new Board(ili1li1);
                    neighbors.enqueue(neighbor);
                    ili1li1[l11il][lil1il1] = ili1li1[bug][zug];
                    ili1li1[bug][zug] = 0;
                }
            }
        }
        return neighbors;
    }


    public int hamming() {
        return hammingCount;

    }
    public int manhattan() {
        return manCounter;

    }
    public int estimatedDistanceToGoal() {

    }
    public boolean equals(Object y) {

    }
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i,j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

}
