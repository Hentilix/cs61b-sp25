import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean network[][];
    private int virtualTop;
    private int virtualBottom;
    private WeightedQuickUnionUF percolationUF;
    private WeightedQuickUnionUF fullnessUF;
    private int numberOfOpenSites;

    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException();
        }
        network = new boolean[N][N];
        virtualTop = N * N;
        virtualBottom = N * N + 1;
        percolationUF = new WeightedQuickUnionUF(N * N + 2);
        fullnessUF = new WeightedQuickUnionUF(N * N + 1);
        numberOfOpenSites = 0;
    }

    public void open(int row, int col) {
        if (isOpen(row, col)) {
            return;
        }
        network[row][col] = true;
        int N = network.length;
        if (row == 0) {
            percolationUF.union(virtualTop, row * N + col);
            fullnessUF.union(virtualTop, row * N + col);
        }
        if (row == N - 1) {
            percolationUF.union(virtualBottom, row * N + col);
        }
        for (int i = 0; i < 4; i++) {
            int newRow = row + dx[i], newCol = col + dy[i];
            if (newRow >= 0 && newRow < N && newCol >= 0 && newCol < N && network[newRow][newCol]) {
                percolationUF.union(row * N + col, newRow * N + newCol);
                fullnessUF.union(row * N + col, newRow * N + newCol);
            }
        }
        numberOfOpenSites++;
    }

    public boolean isOpen(int row, int col) {
        return network[row][col];
    }

    public boolean isFull(int row, int col) {
        int N = network.length;
        return network[row][col] && fullnessUF.connected(row * N + col, virtualTop);
    }

    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    public boolean percolates() {
        int N = network.length;
        if (percolationUF.connected(virtualTop, virtualBottom)) {
            return true;
        }
        return false;
    }

    private final int[] dx = {0, 1, 0, -1};
    private final int[] dy = {1, 0, -1, 0};
}
