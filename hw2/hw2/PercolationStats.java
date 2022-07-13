package hw2;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
//import java.lang.Math;

public class PercolationStats {
    private Percolation temp;
    private int sizeN;
    private int timeT;
    private double[] xlist;


    private int randomX;
    private int randomY;




    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException("N or T <= 0");
        }

        xlist = new double[T];

        for (int i = 0; i < T; i++) {
            temp = pf.make(N);
            while (!temp.percolates()) {
                randomX = StdRandom.uniform(N);
                randomY = StdRandom.uniform(N);
                if (!temp.isOpen(randomX, randomY)) {
                    temp.open(randomX, randomY);
                }

            }

            xlist[i] = temp.numberOfOpenSites() / (N * N);



        }

        temp = pf.make(N);


        sizeN = N;
        timeT = T;


    }
    public double mean() {  // sample mean of percolation threshold
        return StdStats.mean(xlist);

    }
    public double stddev() { // sample standard deviation of percolation threshold

        return StdStats.stddev(xlist);

    }
    public double confidenceLow() { // low endpoint of 95% confidence interval
        return (mean() - 1.96 * stddev() / Math.pow(timeT, 0.5));

    }
    public double confidenceHigh() {  // high endpoint of 95% confidence interval
        return (mean() + 1.96 * stddev() / Math.pow(timeT, 0.5));

    }


}
