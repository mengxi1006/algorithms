/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] grid;
    private final WeightedQuickUnionUF uf;
    private final int N;
    private boolean[] connectFirstRow;
    private boolean[] connectLastRow;

    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException("n should be greater than zero");
        grid = new boolean[n][n]; // initialise an n x n grid with all sites closed
        // StdOut.println(grid);
        uf = new WeightedQuickUnionUF(
                n * n + 2); // initialise a union fnd data structure with size n * n + 2
        // // connect first row with the first site
        // for (int i = 1; i <= n; i++) {
        //     uf.union(i, 0);
        // }
        // // connect last row with the last site
        // for (int i = n * (n - 1) + 1; i <= n * n; i++) {
        //     uf.union(i, n * n + 1);
        // }
        N = n; // maximum row and column size will be n
        connectFirstRow = new boolean[n * n];
        connectLastRow = new boolean[n * n];
    }

    public void open(int row, int col) {
        int ROW = row - 1; // re-index to make it in range 0 and n - 1
        int COL = col - 1;
        if (ROW < 0 || ROW >= N)
            throw new IllegalArgumentException("row index out of bounds");
        if (COL < 0 || COL >= N)
            throw new IllegalArgumentException("column index out of bounds");
        if (!isOpen(row, col)) {
            grid[ROW][COL] = true; // open the site if it is not open yet
            int ind = (row - 1) * N + col; // index of the current site

            if (ROW == 0) uf.union(ind, 0); // connect to the top if the site is in the first row
            if (ROW == N - 1)
                uf.union(ind, N * N + 1); // connect to the botttom if the site is in the last row

            if (COL - 1 >= 0) { // check if there is a left site
                if (isOpen(row, col - 1) && uf.find(ind - 1) != uf
                        .find(ind)) { // if the left site is open and not connected to current one
                    uf.union(ind, ind - 1); // connect the two sites
                }
            }
            if (COL + 1 < N) { // check if there is a right site
                if (isOpen(row, col + 1) && uf.find(ind + 1) != uf
                        .find(ind)) { // if the right site is open and not connected to current one
                    uf.union(ind, ind + 1); // connect the two sites
                }
            }
            if (ROW - 1 >= 0) { // check if there is an upper site
                if (isOpen(row - 1, col) && uf.find(ind - N) != uf
                        .find(ind)) { // if the upper site is open and not connected to current one
                    uf.union(ind, ind - N); // connect the two sites
                }
            }
            if (ROW + 1 < N) { // check if there is a lower site
                if (isOpen(row + 1, col) && uf.find(ind + N) != uf
                        .find(ind)) { // if the lower site is open and not connected to current one
                    uf.union(ind, ind + N); // connect the two sites
                }
            }
            // // check if the current site is connected to first row
            // if (uf.find(ind) == uf.find(0)) connectFirstRow[ind - 1] = true;
            // // check if the current site is connected to last row
            // if (uf.find(ind) == uf.find(N * N + 1)) connectLastRow[ind - 1] = true;
        }
    }

    public boolean isOpen(int row, int col) {
        int ROW = row - 1; // re-index to make it in range 0 and n - 1
        int COL = col - 1;
        if (ROW < 0 || ROW >= N)
            throw new IllegalArgumentException("row index out of bounds: " + ROW);
        if (COL < 0 || COL >= N)
            throw new IllegalArgumentException("column index out of bounds: " + COL);
        return grid[ROW][COL];
    }

    public boolean isFull(int row, int col) {
        int ROW = row - 1; // re-index to make it in range 0 and n - 1
        int COL = col - 1;
        if (ROW < 0 || ROW >= N)
            throw new IllegalArgumentException("row index out of bounds");
        if (COL < 0 || COL >= N)
            throw new IllegalArgumentException("column index out of bounds");

        int ind = (row - 1) * N + col; // index of the current site
        // check if it connects to the top row -- prevents backwash problem
        // if (!connectFirstRow[ind - 1]) return false;
        if (!isOpen(row, col)) return false; // return false if current site is not open
        else {
            return uf.find(ind) == uf
                    .find(0); // if it is open, check if connected to the first virtual site
        }
    }

    public int numberOfOpenSites() {
        int count = 0;
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                if (isOpen(i, j)) count++;
            }
        }
        return count;
    }

    public boolean percolates() {
        return uf.find(N * N + 1) == uf.find(0);
    }

    public static void main(String[] args) {
        // left empty
    }
}
