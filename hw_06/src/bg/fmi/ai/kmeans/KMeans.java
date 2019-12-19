package bg.fmi.ai.kmeans;

import bg.fmi.ai.kmeans.iris.Iris;
import bg.fmi.ai.kmeans.iris.IrisCreator;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class KMeans {
    private List<Iris> irisList;

    private List<Iris> centroids;
    private List<Iris> previousCentroids;
    private List<List<Iris>> clusters;

    public KMeans(String datasetFilename, int k) {
        this.irisList = new ArrayList<>();
        readData(datasetFilename);

        this.centroids = new ArrayList<>(k);
        this.previousCentroids = new ArrayList<>(k);
        this.clusters = new ArrayList<>(k);

        //Or use 3 random !but different! species
        for (int i = 0; i < k; i++) {
            centroids.add(new Iris(irisList.get(new Random().nextInt(irisList.size() / k) * (i + 1))));
            previousCentroids.add(new Iris(centroids.get(i)));
            clusters.add(new ArrayList<>());
        }
    }

    private void readData(String datasetFilename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(datasetFilename))) {
            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                Iris iris = IrisCreator.createIris(line);
                irisList.add(iris);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cluster(int maxIterations) {
        boolean isOver = false;
        while (!isOver) {
            clearClusters();
            addPointsToCluster();
            moveCentroids();

            maxIterations--;
            isOver = !haveCentroidsMoved();
        }
    }

    private void clearClusters() {
        for (var cluster : clusters) {
            cluster.clear();
        }
    }

    private void addPointsToCluster() {
        for (var iris : irisList) {
            clusters.get(findClosestCentroidIndex(iris)).add(iris);
        }
    }

    private int findClosestCentroidIndex(Iris iris) {
        double minDistance = Double.MAX_VALUE;
        int closestCentroidIndex = 0;
        for (int i = 0; i < centroids.size(); i++) {
            double currentDistance = centroids.get(i).getDistance(iris.getFeatures());
            if (currentDistance < minDistance) {
                minDistance = currentDistance;
                closestCentroidIndex = i;
            }
        }

        return closestCentroidIndex;
    }

    private void moveCentroids() {
        for (int i = 0; i < clusters.size(); i++) {
            double[] averageFeatures = {0, 0, 0, 0};
            for (var iris : clusters.get(i)) {
                double[] features = iris.getFeatures();
                for (int j = 0; j < 4; j++) {
                    averageFeatures[j] += features[j];
                }
            }
            for (int j = 0; j < 4; j++) {
                averageFeatures[j] /= clusters.get(i).size();
            }

            previousCentroids.set(i, new Iris(centroids.get(i)));
            centroids.set(i, new Iris(averageFeatures, "Centroid"));
        }
    }

    private boolean haveCentroidsMoved() {
        for (int i = 0; i < centroids.size(); ++i) {
            if (!centroids.get(i).equals(previousCentroids.get(i))) {
                return true;
            }
        }
        return false;


    }

    public void printClusters() {
        for (int i = 0; i < clusters.size(); ++i) {
            System.out.println("Cluster " + (i + 1));
            for (int j = 0; j < clusters.get(i).size(); ++j) {
                System.out.println(clusters.get(i).get(j));
            }
            System.out.println("-------------------------------------------------------------------------");
        }
    }

}
