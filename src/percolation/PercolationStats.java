package percolation;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 * Created by Petro Karabyn on 13-Jul-17.
 */
public class PercolationStats {

    private double[] results;
    private double mean;
    private double stddev;

    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException();

        results = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(n);
            while (!percolation.percolates()) {
                int x = StdRandom.uniform(1, n + 1);
                int y = StdRandom.uniform(1, n + 1);

                percolation.open(x, y);
            }
            results[i] = (double) percolation.numberOfOpenSites() / (n * n);
        }
    }

    public double mean() {
        mean = StdStats.mean(results);
        return mean;
    }

    public double stddev() {
        stddev = StdStats.stddev(results);
        return stddev;
    }

    public double confidenceLo() {
        return mean - (1.96 * stddev / Math.sqrt(results.length));
    }

    public double confidenceHi() {
        return mean + (1.96 * stddev / Math.sqrt(results.length));
    }


    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        PercolationStats percolationStats = new PercolationStats(n, T);
        StdOut.println("mean = " + percolationStats.mean);
        StdOut.println("stddev = " + percolationStats.stddev);
        StdOut.println("95% confidence interval = " + "[" + percolationStats.confidenceHi() + ", " +
                percolationStats.confidenceLo() + "]");

    }
}
