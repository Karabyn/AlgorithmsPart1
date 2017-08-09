package puzzle;

import java.util.Random;
import java.util.Stack;

/**
 * Algorithms and Data Structures Part I.
 * Assignment 4. Puzzle 8.
 * Created by Petro Karabyn on 29-Jul-17.
 */

public class Board {

    private final short length;
    private final int[][] initialBoard;

    public Board(int[][] blocks) {
        if (blocks == null) throw new IllegalArgumentException();
        length = (short) blocks.length;
        initialBoard = new int[length][length];
        for (int i = 0; i < length; i++) {
            initialBoard[i] = blocks[i].clone();
        }
    }

    /**
     * @return board dimension n
     */
    public int dimension() {
        return length;
    }

    /**
     * The number of blocks in the wrong position
     */
    public int hamming() {
        int priority = 0;
        for (int i = 0; i < length; i++)
            for (int j = 0; j < length; j++)
                if (initialBoard[i][j] != (i * length) + (j + 1) && initialBoard[i][j] != 0)
                    priority += 1;
        // hamming = priority;
        return priority;
    }

    /**
     * The sum of the Manhattan distances (sum of the vertical and horizontal distance)
     * from the blocks to their goal positions.
     */
    public int manhattan() {
        int priority = 0;
        for (int i = 0; i < length; i++)
            for (int j = 0; j < length; j++) {
                int currentNumber = initialBoard[i][j];
                if (currentNumber != (i * length) + (j + 1) && currentNumber != 0) {
                    int goalRow = (currentNumber - 1) / length;
                    int goalColumn = (currentNumber - 1) % length;
                    priority += (Math.abs(i - goalRow) + Math.abs(j - goalColumn));
                }
            }
        return priority;
    }

    private int[][] getBoard() {
        return initialBoard;
    }


    public boolean isGoal() {
        return hamming() == 0;
    }

    /**
     * @return a board that is obtained by exchanging any pair of blocks
     */
    public Board twin() {
        boolean suitable;
        int randNum1, randNum2;
        int[] randArray;
        do {
            randArray = getRandomBlocks();
            randNum1 = initialBoard[randArray[0]][randArray[1]];
            randNum2 = initialBoard[randArray[2]][randArray[3]];
            suitable = randNum1 != randNum2 && randNum1 != 0 && randNum2 != 0;

        } while (!suitable);

        int[][] twinBoard = new int[length][];
        for (int i = 0; i < length; i++)
            twinBoard[i] = initialBoard[i].clone();

        // swap pair of blocks
        twinBoard[randArray[0]][randArray[1]] = randNum2;
        twinBoard[randArray[2]][randArray[3]] = randNum1;

        return new Board(twinBoard);
    }

    private int[] getRandomBlocks() {
        Random rand = new Random();
        return new int[]{rand.nextInt(length), rand.nextInt(length), rand.nextInt(length), rand.nextInt(length)};
    }

    @Override
    public boolean equals(Object y) {
        if (!(y instanceof Board)) return false;
        Board x = (Board) y;
        if (x.dimension() != this.dimension()) return false;
        else {
            int[][] boardX = x.getBoard();
            for (int row = 0; row < length; row++)
                for (int column = 0; column < length; column++)
                    if ((initialBoard[row][column] != boardX[row][column])) return false;
        }
        return true;
    }

    public Iterable<Board> neighbors() {
        Stack<Board> boardStack = new Stack<>();
        int x = 0;
        int y = 0;
        int[][] neighborBoard = new int[length][length];

        for (int row = 0; row < length; row++)
            for (int column = 0; column < length; column++) {
                neighborBoard[row][column] = initialBoard[row][column];
                if (initialBoard[row][column] == 0) {
                    x = row;
                    y = column;
                }
            }

        if (x - 1 >= 0) {
            neighborBoard[x][y] = initialBoard[x - 1][y];
            neighborBoard[x - 1][y] = 0;
            boardStack.push(new Board(neighborBoard));
            neighborBoard[x - 1][y] = neighborBoard[x][y];
        }
        if (x + 1 <= length - 1) {
            neighborBoard[x][y] = initialBoard[x + 1][y];
            neighborBoard[x + 1][y] = 0;
            boardStack.push(new Board(neighborBoard));
            neighborBoard[x + 1][y] = neighborBoard[x][y];
        }
        if (y - 1 >= 0) {
            neighborBoard[x][y] = initialBoard[x][y - 1];
            neighborBoard[x][y - 1] = 0;
            boardStack.push(new Board(neighborBoard));
            neighborBoard[x][y - 1] = neighborBoard[x][y];
        }
        if (y + 1 <= length - 1) {
            neighborBoard[x][y] = initialBoard[x][y + 1];
            neighborBoard[x][y + 1] = 0;
            boardStack.push(new Board(neighborBoard));
            neighborBoard[x][y + 1] = neighborBoard[x][y];
        }
        return boardStack;
    }

    // string representation of this board
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(length + "\n");
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                s.append(String.format("%2d ", initialBoard[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    public static void main(String[] args) {
        /*
        int[][] blocks3x3 = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        int[][] blocks3x3Example = {{0, 3, 8}, {4, 1, 2}, {7, 6, 5}};
        int[][] blocks4x4 = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 0}};

        Board board3x3 = new Board(blocks3x3);
        Board board3x3Example = new Board(blocks3x3Example);
        Board board4x4 = new Board(blocks4x4);

        System.out.println("hamming = " + board3x3Example.hamming());
        System.out.println("manhattan = " + board3x3Example.manhattan());
        System.out.println("board3x3Example: " + board3x3Example.toString());

        //System.out.println("board3x3Example.twin(): " + board3x3Example.twin().toString());
        //System.out.println("board3x3Example: " + board3x3Example.toString());
        //System.out.println("board3x3Example.twin(): " + board3x3Example.twin().toString());
        //System.out.println("board3x3Example: " + board3x3Example.toString());

        Stack<Board> stack = (Stack<Board>) board3x3Example.neighbors();
        stack.forEach(board -> System.out.println("Neighbour: " + board.toString()));
        */
    }
}
