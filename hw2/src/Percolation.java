import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {
    private final int N;
    private final boolean[][] open;
    private final WeightedQuickUnionUF uf;
    private final WeightedQuickUnionUF fullUf;

    private final int virtualTop;
    private final int virtualBottom;

    private int numberOfOpenSites;

    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException();
        }

        this.N = N;
        this.open = new boolean[N][N];
        this.numberOfOpenSites = 0;

        this.virtualTop = N * N;
        this.virtualBottom = N * N + 1;

        this.uf = new WeightedQuickUnionUF(N * N + 2);

        this.fullUf = new WeightedQuickUnionUF(N * N + 1);
    }

    public void open(int row, int col) {
        validate(row, col);

        if (isOpen(row, col)) {
            return;
        }

        open[row][col] = true;
        numberOfOpenSites++;

        int current = xyTo1D(row, col);

        // 第一行连接 Virtual Top
        if (row == 0) {
            uf.union(current, virtualTop);
            fullUf.union(current, virtualTop);
        }

        // 最后一行连接 Virtual Bottom
        if (row == N - 1) {
            uf.union(current, virtualBottom);
        }

        // 上
        if (row > 0 && isOpen(row - 1, col)) {
            int neighbor = xyTo1D(row - 1, col);
            uf.union(current, neighbor);
            fullUf.union(current, neighbor);
        }

        // 下
        if (row < N - 1 && isOpen(row + 1, col)) {
            int neighbor = xyTo1D(row + 1, col);
            uf.union(current, neighbor);
            fullUf.union(current, neighbor);
        }

        // 左
        if (col > 0 && isOpen(row, col - 1)) {
            int neighbor = xyTo1D(row, col - 1);
            uf.union(current, neighbor);
            fullUf.union(current, neighbor);
        }

        // 右
        if (col < N - 1 && isOpen(row, col + 1)) {
            int neighbor = xyTo1D(row, col + 1);
            uf.union(current, neighbor);
            fullUf.union(current, neighbor);
        }
    }

    public boolean isOpen(int row, int col) {
        validate(row, col);
        return open[row][col];
    }

    public boolean isFull(int row, int col) {
        validate(row, col);

        if (!isOpen(row, col)) {
            return false;
        }

        int current = xyTo1D(row, col);

        return fullUf.connected(current, virtualTop);
    }

    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    public boolean percolates() {
        return uf.connected(virtualTop, virtualBottom);
    }

    private int xyTo1D(int row, int col) {
        return row * N + col;
    }

    private void validate(int row, int col) {
        if (row < 0 || row >= N || col < 0 || col >= N) {
            throw new IllegalArgumentException();
        }
    }
}
