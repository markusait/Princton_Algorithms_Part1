import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private final double mean;
    private final double stddev;
    private final double confidenceLo;
    private final double confidenceHi;

    // perform trials independent experiments on an n-by-n grid
    public PercolationStats (int n, int trials) throws IllegalArgumentException, IllegalAccessException {
        if (n <= 0 || trials <= 0) throw new IllegalArgumentException();
        double[] percolationThresholds = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation p = new Percolation(n);
            int runs = 0;
            while (!p.percolates()) {
                int column;
                int row;
                do {
                    column = 1 + StdRandom.uniform(n);
                    row = 1 + StdRandom.uniform(n);
                } while (p.isOpen(row, column));
                p.open(row, column);
                runs++;
            }
            percolationThresholds[i] = runs / (double) (n * n);
        }
        mean = StdStats.mean(percolationThresholds);
        stddev = StdStats.stddev(percolationThresholds);
        double confidenceFraction = (1.96 * stddev) / Math.sqrt(trials);
        confidenceLo = mean - confidenceFraction;
        confidenceHi = mean + confidenceFraction;
    }
    public double mean() {  // sample mean of percolation threshold
        return mean;
    }

    public double stddev() {    // sample standard deviation of percolation threshold
        return stddev;
    }

    public double confidenceLo() {  // low  endpoint of 95% confidence interval
        return confidenceLo;
    }

    public double confidenceHi() {   // high endpoint of 95% confidence interval
        return confidenceHi;
    }



}
