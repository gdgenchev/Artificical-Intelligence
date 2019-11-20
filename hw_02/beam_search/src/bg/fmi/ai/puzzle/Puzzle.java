package bg.fmi.ai.puzzle;

import java.util.*;

public class Puzzle {
    private int[][] startState;
    private int[][] finalState;

    public Puzzle(int[][] startState, int[][] finalState) {
        this.startState = startState;
        this.finalState = finalState;
    }

    public int findSolution(int beamWidth) {
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

            PriorityQueue<Node> adjacentStates = new PriorityQueue<>(currentNode.getAdjacentStates());
            int adjacentStatesSize = adjacentStates.size();
            List<Node> beamAdjacentStates = new ArrayList<>(beamWidth);
            int count = 0;
            while (count != beamWidth && count != adjacentStatesSize) {
                beamAdjacentStates.add(adjacentStates.poll());
                count++;
            }

            for (Node state : beamAdjacentStates) {
                if (!closedStates.contains(state)) {
                    openStates.add(state);
                }
            }

            closedStates.add(currentNode);
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
