/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats {
    private double[] frac;
    private double avgFrac, stdFrac, confiLo, confiHi;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) throw new IllegalArgumentException("invalid input number");

        frac = new double[trials]; // fraction of open sites in each trial
        boolean[] sites = new boolean[n * n];

        for (int t = 0; t < trials; t++) { // loop over trials
            Percolation perc = new Percolation(n); // initialise a percolation data structure
            int row = StdRandom.uniform(1, n + 1);
            int col = StdRandom.uniform(1, n + 1);
            while (!perc.percolates()) {
                while (perc.isOpen(row, col)) {
                    row = StdRandom.uniform(1, n + 1);
                    col = StdRandom.uniform(1, n + 1);
                }
                perc.open(row, col);
                // StdOut.println(perc.percolates());
            }
            frac[t] = (double) perc.numberOfOpenSites() / (n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        avgFrac = 0;
        for (int i = 0; i < frac.length; i++) {
            avgFrac += frac[i];
        }
        avgFrac /= frac.length;
        return avgFrac;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        stdFrac = 0;
        for (int i = 0; i < frac.length; i++) {
            stdFrac += (frac[i] - avgFrac) * (frac[i] - avgFrac);
        }
        stdFrac /= frac.length - 1;
        stdFrac = Math.sqrt(stdFrac);
        return stdFrac;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        confiLo = avgFrac - 1.96 * stdFrac / Math.sqrt(frac.length);
        return confiLo;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        confiHi = avgFrac + 1.96 * stdFrac / Math.sqrt(frac.length);
        return confiHi;
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        PercolationStats percStats = new PercolationStats(n, trials);
        StdOut.println("mean                    = " + percStats.mean());
        StdOut.println("stddev                  = " + percStats.stddev());
        StdOut.println("95% confidence interval = [" + percStats.confidenceLo() + ", " + percStats
                .confidenceHi() + "]");
    }
}
