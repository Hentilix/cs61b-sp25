package game2048logic;

import game2048rendering.Side;
import static game2048logic.MatrixUtils.rotateLeft;
import static game2048logic.MatrixUtils.rotateRight;

/**
 * @author  Josh Hug
 */
public class GameLogic {
    /** Moves the given tile up as far as possible, subject to the minR constraint.
     *
     * @param board the current state of the board
     * @param r     the row number of the tile to move up
     * @param c -   the column number of the tile to move up
     * @param minR  the minimum row number that the tile can land in, e.g.
     *              if minR is 2, the moving tile should move no higher than row 2.
     * @return      if there is a merge, returns the 1 + the row number where the merge occurred.
     *              if no merge occurs, then return 0.
     */
    public static int moveTileUpAsFarAsPossible(int[][] board, int r, int c, int minR) {
        // TODO: Fill this in in tasks 2, 3, 4
        // task 2
        while (r > minR) {
            if (board[r - 1][c] == 0) {
                int temp = board[r][c];
                board[r][c] = 0;
                board[r - 1][c] = temp;
                r -- ;
            } else {
                break;
            }
        }
        // task 3
        if (r > minR) {
            if (board[r - 1][c] == board[r][c]) {
                board[r][c] = 0;
                board[r - 1][c] *= 2;
                return r;
            }
            return 0;
        }
        // task 4 : 已完成，r > 0 -> r > minR
        return 0;
    }

    /**
     * Modifies the board to simulate the process of tilting column c
     * upwards.
     *
     * @param board     the current state of the board
     * @param c         the column to tilt up.
     */
    public static void tiltColumn(int[][] board, int c) {
        // TODO: fill this in in task 5
        int oriMinR = 0;
        for (int i = 0; i < board.length; i ++ ) {
            if (board[i][c] != 0) {
                int mergeRow = moveTileUpAsFarAsPossible(board, i, c, oriMinR);
                if (mergeRow != 0) {
                    oriMinR = mergeRow;
                }
            }
        }
        return;
    }

    /**
     * Modifies the board to simulate tilting all columns upwards.
     *
     * @param board     the current state of the board.
     */
    public static void tiltUp(int[][] board) {
        // TODO: fill this in in task 6
        for (int i = 0; i < board.length; i ++ ) {
            tiltColumn(board, i);
        }
        return;
    }

    /**
     * Modifies the board to simulate tilting the entire board to
     * the given side.
     *
     * @param board the current state of the board
     * @param side  the direction to tilt
     */
    public static void tilt(int[][] board, Side side) {
        // TODO: fill this in in task 7
        if (side == Side.EAST) {
            rotateLeft(board);
            tiltUp(board);
            rotateRight(board);
            return;
        } else if (side == Side.WEST) {
            rotateRight(board);
            tiltUp(board);
            rotateLeft(board);
            return;
        } else if (side == Side.SOUTH) {
            rotateLeft(board);
            rotateLeft(board);
            tiltUp(board);
            rotateRight(board);
            rotateRight(board);
            return;
        } else {
            tiltUp(board);
            return;
        }
    }
}
