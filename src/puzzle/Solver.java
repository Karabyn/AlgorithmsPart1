package puzzle;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Algorithms and Data Structures Part I.
 * Assignment 4. Puzzle 8.
 * Created by Petro Karabyn on 30-Jul-17.
 */

public class Solver {

    private final Board initialBoard;
    private final ArrayList<Board> solutionBoards;
    private boolean solvable;


    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException();
        initialBoard = initial;
        solutionBoards = new ArrayList<>();
        solvable = false;

        if (isSolvable()) {
            solve();
        }
    }

    private void solve() {
        MinPQ<Node> pq = new MinPQ<>();
        pq.insert(new Node(initialBoard, new Node(), 0));
        Node prevNode = pq.delMin();
        boolean solved = prevNode.thisBoard.isGoal();

        while (!solved) {
            Iterable<Board> iterableNeighbors = prevNode.thisBoard.neighbors();
            for (Board board : iterableNeighbors) {
                if (!board.equals(prevNode.previousNode.thisBoard) && !board.equals(initialBoard))
                    pq.insert(new Node(board, prevNode, prevNode.moves + 1));
            }
            prevNode = pq.delMin();
            if (prevNode.thisBoard.isGoal()) solved = true;
        }

        while (prevNode.previousNode != null) {
            solutionBoards.add(prevNode.thisBoard);
            prevNode = prevNode.previousNode;
        }
        Collections.reverse(solutionBoards);
    }

    /**
     * Get integer values from a string representation of an
     * n-puzzle board
     * @return ArrayList<Integer>
     */
    private ArrayList<Integer> extractInts() {
        ArrayList<Integer> integerBlocks = new ArrayList<>();
        int cut = 3;
        if (initialBoard.dimension() >= 10) cut = 4;
        String[] stringBlocks = initialBoard.toString().substring(cut).replaceAll("\n", "").replaceAll("  ", " ").split(" ");
        for (String str : stringBlocks) {
            // System.out.println(str);
            integerBlocks.add(Integer.parseInt(str));
        }
        // System.out.println(integerBlocks);
        return integerBlocks;
    }

    /**
     * n - puzzle with an odd n is solvable when
     * the number of inversions is an even number
     * @return true or false
     */
    private boolean isSolvableOdd() {
        int inversions = 0;
        ArrayList<Integer> integerBlocks = extractInts();
        int size = integerBlocks.size();
        for (int i = 0; i < size; i++)
            for (int j = i + 1; j < size; j++)
                if (integerBlocks.get(i) > integerBlocks.get(j) && integerBlocks.get(j) != 0)
                    inversions += 1;
        // System.out.println("Inversions: " + inversions);
        return inversions % 2 == 0;
    }

    /**
     * the parity of the number of inversions plus the row of the blank square is invariant:
     * each legal move changes this sum by an even number.
     * If this sum is even, then it cannot lead to the goal board by a sequence of legal moves;
     * if this sum is odd, then it can lead to the goal board by a sequence of legal moves.
     * @return
     */
    private boolean isSolvableEven() {
        int inversions = 0;
        int blankRow = 0;
        ArrayList<Integer> integerBlocks = extractInts();
        int size = integerBlocks.size();
        for (int i = 0; i < size; i++) {
            if (integerBlocks.get(i) == 0)
                blankRow = i / initialBoard.dimension();
            for (int j = i + 1; j < size; j++) {
                if (integerBlocks.get(i) > integerBlocks.get(j) && integerBlocks.get(j) != 0)
                    inversions += 1;
            }
        }
        // System.out.println("blank row: " + blankRow);
        // System.out.println("Inversions: " + inversions);
        int sum = blankRow + inversions;
        return sum % 2 != 0;
    }

    /**
     * is the initial board solvable?
     * @return true/false
     */
    public boolean isSolvable() {
        if (initialBoard.dimension() % 2 != 0) {
            solvable = isSolvableOdd();
            return solvable;
        }
        solvable = isSolvableEven();
        return solvable;
    }

    /**
     * min number of moves to solve initial board
     * @return number of moves
     */
    public int moves() {
        if (!solvable)
            return -1;
        return solutionBoards.size() - 1;
    }

    /**
     * sequence of boards in a shortest solution; null if unsolvable
     */
    public Iterable<Board> solution() {
        if (!solvable)
            return null;
        return solutionBoards;
    }


    public static void main(String[] args) {
        /*
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }


        int[][] blocks3x3 = {{0, 1, 3},
                             {4, 2, 5},
                             {7, 8, 6}};

        int[][] blocks3x3_07 = {{1, 2, 3},
                                {0, 7, 6},
                                {5, 4, 8}};

        int[][] blocks3x3_inversion_Unsolvable = {{1, 2, 3}, // inversions = 3;
                                                  {4, 6, 7},
                                                  {8, 5, 0}};

        int[][] blocks4x4 = {{1, 2, 3, 4}, // blank row = 2, inversions = 3.
                {5, 6, 7, 8},
                {9, 10, 11, 0},
                {13, 14, 15, 12}};
         */
    }

    private class Node implements Comparable<Node> {

        private Board thisBoard;
        private Node previousNode;
        private int manhattan;
        private int moves;
        private int priority;

        public Node() { }

        public Node(Board initial, Node previous, int m) {

            thisBoard = initial;
            previousNode = previous;
            manhattan = thisBoard.manhattan();
            moves = m;
            priority = manhattan + moves;
        }

        @Override
        public int compareTo(Node node) {
            if (this.priority > node.priority) return 1;
            return -1;
        }
    }
}
