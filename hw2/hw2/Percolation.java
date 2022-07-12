package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    WeightedQuickUnionUF list;
    WeightedQuickUnionUF list2;
    Object[][] array;
    int sizeN;
    int openSize;



    public Percolation(int N) { // create N-by-N grid, with all sites initially blocked
        if (N <= 0) {
            throw new IllegalArgumentException("N <= 0");
        }
        list = new WeightedQuickUnionUF(N * N + 1);
        list2 = new WeightedQuickUnionUF(N * N + 2);

        for (int i = 1; i < N; i++) {
            list.union(0, i);
            list2.union(0, i);
            list2.union(N * N + 2 - i - 1, N * N + 1);
        }

        array = new Object[N][N];
        //array = null;
        sizeN = N;
        openSize = 0;



    }
    public int oneDhelper(int row, int col) {
        return ((row) * sizeN + col);
    }
    public void connectHelper(int row, int col) {
        //up check
        if ((row != 0) && isOpen(row - 1, col)) {
            list.union(oneDhelper(row, col), oneDhelper(row - 1, col));
            list2.union(oneDhelper(row, col), oneDhelper(row - 1, col));

        }

        //left check
        if ((col != 0) && isOpen(row, col - 1)) {
            list.union(oneDhelper(row, col), oneDhelper(row, col - 1));
            list2.union(oneDhelper(row, col), oneDhelper(row, col - 1));
        }

        // right check
        if ((col != sizeN - 1) && isOpen(row, col + 1)) {
            list.union(oneDhelper(row, col), oneDhelper(row, col + 1));
            list2.union(oneDhelper(row, col), oneDhelper(row, col + 1));
        }

        // down check
        if ((row != sizeN - 1) && isOpen(row + 1, col)) {
            list.union(oneDhelper(row, col), oneDhelper(row + 1, col));
            list2.union(oneDhelper(row, col), oneDhelper(row + 1, col));
        }
    }
    public void open(int row, int col) { // open the site (row, col) if it is not open already
        if (!isOpen(row, col)) {
            array[row][col] = 1;
            connectHelper(row, col);
            openSize++;
        }



    }
    public boolean isOpen(int row, int col) { // is the site (row, col) open?
        if (array[row][col] == null) {
            return false;
        }
        return true;

    }
    public boolean isFull(int row, int col) { // is the site (row, col) full?
        if (isOpen(row, col)) {
            int temp = oneDhelper(row, col);
            return list.connected(temp, 0);
        }
        return false;


    }
    public int numberOfOpenSites() {  // number of open sites
        return openSize;

    }
    public boolean percolates() { // does the system percolate?
        return list2.connected(0, sizeN * sizeN + 1);

    }

    public void printarray() {
        int i;
        int j;
        System.out.println("array is: ");
        for (i = 0; i < sizeN; i++) {
            for (j = 0; j < sizeN; j++) {
                System.out.print(" " + array[i][j]);
            }
            System.out.println("");
        }
    }
    public static void main(String[] args) { // use for unit testing (not required)
        int n = 4;
        Percolation temp = new Percolation(4);
        temp.printarray();
        System.out.println(temp.isOpen(1, 1));

        temp.open(1, 1);
        temp.printarray();
        System.out.println(temp.isOpen(1, 1));
        temp.open(1, 3);
        temp.open(2, 3);
        temp.printarray();
        temp.open(0, 3);
        temp.printarray();
        System.out.println("is full(2, 3): " + temp.isFull(2, 3));


        int s = temp.oneDhelper(2, 3);
        System.out.println("row = 2, col = 3, one D is : " + s);
        System.out.println("is full(1, 1): " + temp.isFull(1, 1));
        temp.open(1, 2);
        System.out.println("open (1,2)");
        System.out.println("is full(1, 1): " + temp.isFull(1, 1));
        System.out.println("is porcolate: " + temp.percolates());
        temp.open(3, 3);
        System.out.println("open (3,3)");
        System.out.println("is porcolate: " + temp.percolates());










    }

}
