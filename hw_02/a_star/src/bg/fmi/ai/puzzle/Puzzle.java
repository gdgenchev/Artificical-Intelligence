package bg.fmi.ai.puzzle;

import java.util.Arrays;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

public class Puzzle {
    private int[][] startState;
    private int[][] finalState;

    public Puzzle(int[][] startState, int[][] finalState) {
        this.startState = startState;
        this.finalState = finalState;
    }

    public int findSolution() {
        PriorityQueue<Node> openStates = new PriorityQueue<>();
        Set<Node> closedStates = new HashSet<>();
        Node startNode = new Node(startState);
        openStates.add(startNode);
        openStates.add(startNode);

        while(!openStates.isEmpty()){
            Node currentNode = openStates.remove();

            if(Arrays.deepEquals(currentNode.getGrid(), finalState)) {
                return currentNode.getNumberOfMoves();
            }

            for (Node node : currentNode.getAdjacentStates()) {
                if (!closedStates.contains(node)) {
                    openStates.add(node);
                    closedStates.add(node);
                }
            }
        }

        return -1;
    }

    public boolean isSolvable() {
        int numberOfInversions = 0;
        int[] startStateArr = convertToArray(startState);
        for (int i = 0; i < startStateArr.length; i++) {
            for (int j = i + 1; j < startStateArr.length; j++) {
                if (startStateArr[j] > 0 && startStateArr[i] > 0 && startStateArr[j] < startStateArr[i]) {
                    numberOfInversions++;
                }
            }
        }

        return numberOfInversions % 2 == 0;
    }

    private int[] convertToArray(int[][] matrix) {
        int[] arr = new int[matrix.length * matrix.length];
        int k = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                arr[k++] = matrix[i][j];
            }
        }

        return arr;
    }
}
