package bg.fmi.ai;

import bg.fmi.ai.puzzle.Puzzle;

public class Main {
  public static final int[][] START = {
      {6, 5, 3},
      {2, 4, 8},
      {7, 0, 1}
  };

  public static final int[][] GOAL = {
      {1, 2, 3},
      {4, 5, 6},
      {7, 8, 0}
  };

  public static void main(String[] args) {
    Puzzle puzzle = new Puzzle(START, GOAL);
    if (puzzle.isSolvable()) {
      long start = System.currentTimeMillis();
      int numberOfMoves = puzzle.findSolution();
      long elapsed = System.currentTimeMillis() - start;
      System.out.println("Elapsed time " + elapsed + "ms.");
      System.out.println("Number of moves: " + numberOfMoves);
    } else {
      System.out.println("Unsolvable puzzle!");
    }
  }

}
