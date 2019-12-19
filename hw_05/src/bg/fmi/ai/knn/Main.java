package bg.fmi.ai.knn;

import bg.fmi.ai.knn.normalizer.DataSetNormalizerImpl;
import bg.fmi.ai.knn.normalizer.DatasetNormalizer;

public class Main {
  public static final String DATASET_FILENAME = "iris.csv";
  public static final String NORMALIZED_DATASET_FILENAME = "iris_normalized.csv";

  public static void main(String[] args) {
    //Already normalized
    DatasetNormalizer dataSetNormalizer = new DataSetNormalizerImpl(DATASET_FILENAME, NORMALIZED_DATASET_FILENAME);
    dataSetNormalizer.normalizeDataSet();

    KNNAlgorithm algorithm = new KNNAlgorithm(NORMALIZED_DATASET_FILENAME);
    algorithm.train();

    //Should be Iris-virginica
    String species = algorithm.classify(new double[]{0.677,0.52166666,0.7566101694915254,0.9});
    System.out.println(species);
  }
}
