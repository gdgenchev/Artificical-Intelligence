package bg.fmi.ai.tictactoe;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    Board board = new Board();
    board.printBoard();

    Scanner scanner = new Scanner(System.in);

    boolean hasEnded = board.isGameOver();
    while (!hasEnded) {
      System.out.println("Enter your move(x y):");
      String input = scanner.nextLine();
      String[] inputData = input.split(" ");
      int x = Integer.parseInt(inputData[0]);
      int y = Integer.parseInt(inputData[1]);
      if (!board.checkInput(x, y)) {
        continue;
      }
      board.fillCell(x, y, Symbol.HUMAN);
      board.printBoard();
      hasEnded = board.isGameOver();

      if (!hasEnded) {
        System.out.println();
        System.out.println("Computer's next move:");
        board = getBestNextState(board, Symbol.COMPUTER);
        board.printBoard();
        System.out.println();
        hasEnded = board.isGameOver();
      }
    }
    Symbol winner;
    if ((winner = board.getWinner()) != null) {
      System.out.println("Winner is " + winner);
    } else {
      System.out.println("It's a draw");
    }
  }

  private static Board getBestNextState(Board board, Symbol symbol) {
    int alpha = Integer.MIN_VALUE;
    int beta = Integer.MAX_VALUE;

    List<Board> states = getNextStates(board, symbol);

    Board bestNextState = null;

    for (Board state : states) {
      int result = minimax(state, alpha, beta, true, 1);
      if (alpha < result) {
        bestNextState = state;
        alpha = result;
      }
    }

    return bestNextState;
  }

  private static List<Board> getNextStates(Board board, Symbol symbol) {
    List<Board> states = new ArrayList<>(9);

    for (int i = 0; i < Board.BOARD_SIZE; ++i) {
      for (int j = 0; j < Board.BOARD_SIZE; ++j) {
        if (board.getCell(i, j) == Symbol.EMPTY) {
          Board state = board.getCopy();
          state.fillCell(i, j, symbol);
          states.add(state);
        }
      }
    }

    return states;
  }


  private static int minimax(Board board, int alpha, int beta, boolean isMin, int depth) {
    if (board.isGameOver()) {
      Symbol winner;
      if ((winner = board.getWinner()) != null) {
        if (winner == Symbol.COMPUTER) {
          return Integer.MAX_VALUE / depth;
        } else {
          return Integer.MIN_VALUE;
        }
      } else {
        return 0;
      }
    }

    List<Board> children = getNextStates(board, isMin ? Symbol.HUMAN : Symbol.COMPUTER);
    int returnValue = isMin ? Integer.MAX_VALUE : Integer.MIN_VALUE;

    for (Board child : children) {
      int result = minimax(child, alpha, beta, !isMin, depth + 1);
      if (isMin) {
        beta = Math.min(beta, result);
        returnValue = Math.min(returnValue, result);
      } else {
        alpha = Math.max(alpha, result);
        returnValue = Math.max(returnValue, result);
      }

      if (alpha >= beta) {
        break;
      }
    }

    return returnValue;
  }
}
