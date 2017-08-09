package percolation;
/**
 * percolation.percolation programming assignment
 * Created by Petro Karabyn on 13-Jul-17.
 */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private int gridSize; // the size of a n-by-n grid
    private int size;
    private boolean[] sitesState; // array to mark sites "open" or "closed"
    private WeightedQuickUnionUF unionFind; // unionFind data structure
    private WeightedQuickUnionUF unionFind2; // unionFind data structure to check fullness and prevent backwashes
    private int nOfOpenSites;

    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException();
        // initialize class variables
        gridSize = n * n;
        size = n;
        sitesState = new boolean[n * n];
        unionFind = new WeightedQuickUnionUF(n * n + 2);
        unionFind2 = new WeightedQuickUnionUF(n * n + 1);
        nOfOpenSites = 0;
        int topVirtualSiteIndex = 0;
        int bottomVirtualSiteIndex = gridSize + 1;

        for (int i = 1; i <= n; i++) { // connect topVirtualSite with the first row
            unionFind.union(topVirtualSiteIndex, i);
            unionFind2.union(topVirtualSiteIndex, i);
        }

        for (int i = gridSize - n + 1; i <= gridSize; i++) // connect bottomVirtualSite with the last row
            unionFind.union(bottomVirtualSiteIndex, i);
    }

    public void open(int row, int col) {
        if (!isValid(row, col))
            throw new IllegalArgumentException();

        int siteIndex = xyto1D(row, col);
        int topNeighbour = xyto1D(row - 1, col);
        int bottomNeighbour = xyto1D(row + 1, col);
        int leftNeighbour = xyto1D(row, col - 1);
        int rightNeighbour = xyto1D(row , col + 1);

        if (!isOpen(row, col)) { // open site
            sitesState[siteIndex - 1] = true;
            nOfOpenSites += 1;
        }
        if (row != 1) // connect site with the topNeighbour
            if (isOpen(row - 1, col)) {
                unionFind.union(siteIndex, topNeighbour);
                unionFind2.union(siteIndex, topNeighbour);
            }
        if (row != size) // connect site with the bottomNeighbour
            if (isOpen(row + 1, col)) {
                unionFind.union(siteIndex, bottomNeighbour);
                unionFind2.union(siteIndex, bottomNeighbour);
            }
        if (col != 1) // connect site with the leftNeighbour
            if (isOpen(row, col - 1)) {
                unionFind.union(siteIndex, leftNeighbour);
                unionFind2.union(siteIndex, leftNeighbour);
            }
        if (col != size) // connect site with the rightNeighbour
            if (isOpen(row, col + 1)) {
                unionFind.union(siteIndex, rightNeighbour);
                unionFind2.union(siteIndex, rightNeighbour);
            }
    }

    /**
     * @param row: x coordinate
     * @param col: y coordinate
     * @return true is the site is open. false otherwise.
     */
    public boolean isOpen(int row, int col) {
        if (!isValid(row, col))
            throw new IllegalArgumentException();

        int siteIndex = xyto1D(row, col);
        return sitesState[siteIndex - 1];
    }

    /**
     * Checks whether the site is full or not.
     * @return true if the site is full. false otherwise
     */
    public boolean isFull(int row, int col) {
        if (!isValid(row, col))
            throw new IllegalArgumentException();
        return unionFind2.connected(0, xyto1D(row, col)) && isOpen(row, col);
    }

    /**
     * @return number of open sites in the system
     */
    public int numberOfOpenSites() {
        return nOfOpenSites;
    }

    /**
     * @return true if the system percolates. false otherwise
     */
    public boolean percolates() {
        return unionFind.connected(0, (size * size) + 1) && nOfOpenSites != 0;
    }

    /**
     * @param row: x coordinate
     * @param column: y coordinate
     * @return true if the coordinate exists in the grid. false otherwise.
     */
    private boolean isValid(int row, int column) {
        int siteIndex = xyto1D(row, column);
        return siteIndex <= gridSize && siteIndex > 0 && column > 0 && column <= size;
    }

    /**
     * @param row: x coordinate
     * @param column: y coordinate
     * @return index of a site
     */
    private int xyto1D(int row, int column) {
        return size * (row - 1) + column;
    }

    public static void main(String[] args) {
        Percolation percolation = new Percolation(2);
        StdOut.println(percolation.percolates());
        percolation.open(1, 1);
        StdOut.println(percolation.percolates());
        percolation.open(2, 2);
        StdOut.println(percolation.percolates());
        percolation.open(1, 2);
        StdOut.println(percolation.percolates());
        StdOut.println(percolation.numberOfOpenSites());
    }
}
