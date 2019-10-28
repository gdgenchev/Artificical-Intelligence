package bg.fmi.ai.puzzle;

import bg.fmi.ai.util.Position;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Node implements Comparable<Node> {
  private int[][] grid;
  private int size;
  private int cost;
  private int numberOfMoves;
  private Position emptyPosition;

  Node(int[][] grid) {
    this(grid, 0);
    this.emptyPosition = getEmptyPosition();
  }

  private Node(int[][] grid, int numberOfMoves) {
    this.grid = grid;
    this.size = grid.length;
    this.numberOfMoves = numberOfMoves;
    this.cost = calculateFullCost();
  }

  private int calculateFullCost() {
    return numberOfMoves + manhattanSum();
  }

  private int manhattanSum() {
    int sum = 0;
    for (int row = 0; row < size; ++row) {
      for (int column = 0; column < size; ++column) {
        int value = grid[row][column];
        if (value != 0) {
          int expectedX = (value - 1) / size;
          int expectedY = (value - 1) % size;
          sum += manhattan(expectedX, expectedY, row, column);
        }
      }
    }

    return sum;
  }


  private int manhattan(int expectedX, int expectedY, int currentX, int currentY) {
    return Math.abs(expectedX - currentX) + Math.abs(expectedY - currentY);
  }

  private Position getEmptyPosition() {
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        if (grid[i][j] == 0) {
          return new Position(i, j);
        }
      }
    }

    return null;
  }


  List<Node> getAdjacentStates() {
    List<Node> childStates = new ArrayList<>();
    int adjByRow[] = {0, 1, 0, -1};
    int adjByCol[] = {1, 0, -1, 0};

    for (int i = 0; i < 4; i++) {
      Position adjacentPosition = new Position(emptyPosition.x + adjByRow[i], emptyPosition.y + adjByCol[i]);
      if (isValid(adjacentPosition)) {
        int[][] gridCopy = this.copyGrid();
        //swap the empty tile with an adjacent tile
        gridCopy[emptyPosition.x][emptyPosition.y] = gridCopy[adjacentPosition.x][adjacentPosition.y];
        gridCopy[adjacentPosition.x][adjacentPosition.y] = 0;

        Node child = new Node(gridCopy, numberOfMoves + 1);
        child.emptyPosition = adjacentPosition;
        childStates.add(child);
      }
    }
    return childStates;
  }

  private boolean isValid(Position position) {
    return position.x >= 0 && position.x < size
        && position.y >= 0 && position.y < size;
  }

  private int[][] copyGrid() {
    int[][] copyState = new int[size][size];

    for (int i = 0; i < size; ++i) {
      System.arraycopy(grid[i], 0, copyState[i], 0, size);
    }

    return copyState;
  }

  int[][] getGrid() {
    return grid;
  }

  int getNumberOfMoves() {
    return numberOfMoves;
  }

  @Override
  public int hashCode() {
    return Arrays.deepHashCode(grid);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    Node other = (Node) obj;
    return Arrays.deepEquals(this.grid, other.grid);
  }

  @Override
  public int compareTo(Node other) {
    return Integer.compare(this.cost, other.cost);
  }
}
