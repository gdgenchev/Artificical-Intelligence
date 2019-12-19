package bg.fmi.ai.tictactoe;

public enum Symbol {
  HUMAN("X"), COMPUTER("O"), EMPTY("_");

  private String symbolString;

  Symbol(String symbolString) {
    this.symbolString = symbolString;
  }

  @Override
  public String toString() {
    return this.symbolString;
  }
}
