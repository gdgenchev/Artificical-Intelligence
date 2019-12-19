package bg.fmi.ai.kmeans;

import bg.fmi.ai.kmeans.normalizer.DataSetNormalizer;

public class Main {
    public static final String ORIGINAL_DATASET_FILENAME = "iris.csv";
    public static final String NORMALIZED_DATASET_FILENAME = "iris_normalized.csv";
    public static void main(String[] args) {
        DataSetNormalizer normalizer = new DataSetNormalizer(ORIGINAL_DATASET_FILENAME, NORMALIZED_DATASET_FILENAME);
        normalizer.normalizeDataset();
        KMeans kMeans = new KMeans(NORMALIZED_DATASET_FILENAME, 3);
        kMeans.cluster(0);
        kMeans.printClusters();
    }
}
