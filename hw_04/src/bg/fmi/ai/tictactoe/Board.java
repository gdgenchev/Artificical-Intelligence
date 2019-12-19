package bg.fmi.ai.tictactoe;

public class Board {
  public static final int BOARD_SIZE = 3;

  private Symbol[][] cells = new Symbol[BOARD_SIZE][BOARD_SIZE];

  public Board() {
    initInitialBoard();
  }


  private void initInitialBoard() {
    for (int i = 0; i < BOARD_SIZE; i++) {
      for (int j = 0; j < BOARD_SIZE; j++) {
        cells[i][j] = Symbol.EMPTY;
      }
    }
  }

  public Board getCopy() {
    Board copy = new Board();
    for (int i = 0; i < BOARD_SIZE; i++) {
      for (int j = 0; j < BOARD_SIZE; j++) {
        copy.fillCell(i, j, cells[i][j]);
      }
    }

    return copy;
  }

  public Symbol[][] getCells() {
    return cells;
  }

  public boolean checkInput(int x, int y) {
    if (x < 0 || x > 2 || y < 0 || y > 2) {
      System.out.println("Coordinates should be between 1 and 3!");
      return false;
    }

    if (cells[x][y] != Symbol.EMPTY) {
      System.out.println("Cell already taken!");
      return false;
    }

    return true;
  }

  public void fillCell(int row, int col, Symbol symbol) {
    cells[row][col] = symbol;
  }


  public Symbol getCell(int row, int col) {
    return cells[row][col];
  }


  public boolean isGameOver() {
    if (getWinner() != null) {
      return true;
    }

    for (int i = 0; i < BOARD_SIZE; i++) {
      for (int j = 0; j < BOARD_SIZE; j++) {
        if (cells[i][j] == Symbol.EMPTY) {
          return false;
        }
      }
    }

    return true;
  }

  public Symbol getWinner() {
    for (var i = 0; i < cells.length; i++) {
      if (cells[i][0] != Symbol.EMPTY && cells[i][0] == cells[i][1] && cells[i][0] == cells[i][2]) {
        return cells[i][0];
      }


      if (cells[0][i] != Symbol.EMPTY && cells[0][i] == cells[1][i] && cells[0][i] == cells[2][i]) {
        return cells[0][i];
      }
    }

    if (cells[0][0] != Symbol.EMPTY && cells[0][0] == cells[1][1] && cells[0][0] == cells[2][2]) {
      return cells[0][0];
    }
    if (cells[0][2] != Symbol.EMPTY && cells[0][2] == cells[1][1] && cells[0][2] == cells[2][0]) {
      return cells[0][2];
    }

    return null;
  }

  void printBoard() {
    for (int i = 0; i < BOARD_SIZE; i++) {
      for (int j = 0; j < BOARD_SIZE; j++) {
        System.out.print(cells[i][j] + "  ");
      }
      System.out.println();
    }
  }
}
