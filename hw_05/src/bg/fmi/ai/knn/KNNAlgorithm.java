package bg.fmi.ai.knn;

import bg.fmi.ai.knn.iris.Iris;
import bg.fmi.ai.knn.iris.IrisBuilder;
import bg.fmi.ai.knn.iris.IrisCreator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class KNNAlgorithm {
  private String datasetFilename;
  private List<Iris> irisTrainList;

  public KNNAlgorithm(String datasetFilename) {
    this.datasetFilename = datasetFilename;
    irisTrainList = new ArrayList<>();
  }

  /**
   * Here we can train with 70% of the data and test with 30%
   * but we should be careful - need to choose from all species
   */
  public void train() {
    try (BufferedReader reader = new BufferedReader(new FileReader(datasetFilename))) {
      reader.readLine();
      String line;
      while ((line = reader.readLine()) != null) {
        Iris iris = IrisCreator.createIris(line);
        irisTrainList.add(iris);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public String classify(double[] features) {
    irisTrainList.sort(Comparator.comparingDouble(iris -> iris.getDistance(features)));
    int k = (int) Math.sqrt(irisTrainList.size());
    Map<String, Integer> speciesToCount = new HashMap<>();
    for (int i = 0; i < k; i++) {
      String species = irisTrainList.get(i).getSpecies();
      speciesToCount.put(species, speciesToCount.getOrDefault(species, 0) + 1);
    }

    String mostCommonSpecies = null;
    int maxCount = -1;
    for (var entry : speciesToCount.entrySet()) {
      if (maxCount < entry.getValue()) {
        maxCount = entry.getValue();
        mostCommonSpecies = entry.getKey();
      }
    }

    return mostCommonSpecies;
  }
}
