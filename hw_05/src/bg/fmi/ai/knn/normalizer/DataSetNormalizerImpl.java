package bg.fmi.ai.knn.normalizer;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;


/**
 * This class is used for normalizing numeric data.
 * It can be used for any dataset
 * The numeric columns will be normalized
 */
public class DataSetNormalizerImpl implements DatasetNormalizer {

  /**
   * The filename of the original dataset
   */
  private String originalDatasetFilename;

  /**
   * The filename of the newly created normalized dataset
   */
  private String normalizedDatasetFilename;

  /**
   * Pattern used for checking whether a string is numeric;
   */
  private Pattern pattern;

  /**
   * The min and max for each column number
   */
  private Map<Integer, Pair> dataToMinMax;

  public DataSetNormalizerImpl(String originalDatasetFilename, String normalizedDatasetFilename) {
    this.originalDatasetFilename = originalDatasetFilename;
    this.normalizedDatasetFilename = normalizedDatasetFilename;
    this.pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
    this.dataToMinMax = new HashMap<>();
  }

  /**
   * This function normalizes the given dataset.
   * <p>
   * Firstly, we go through the whole file and save the min and max for each column
   * We do not keep the lines in memory as the file might be huge.
   * Instead, when writing the normalized dataset we again read each line from the original dataset,
   * normalize the data using the dataToMinMax map and write the line to normalizedDatasetFilename.
   */
  @Override
  public void normalizeDataSet() {
    createDataToMinMaxMap();
    generateNormalizedDataSet();
  }

  private void createDataToMinMaxMap() {
    try (BufferedReader reader = new BufferedReader(new FileReader(originalDatasetFilename))) {
      String line;
      while ((line = reader.readLine()) != null) {
        String[] lineData = line.split(",");
        for (int i = 0; i < lineData.length; i++) {
          if (isNumeric(lineData[i])) {
            double data = Double.parseDouble(lineData[i]);
            if (!dataToMinMax.containsKey(i)) {
              dataToMinMax.put(i, new Pair(data, data));
            } else {
              dataToMinMax.put(i, new Pair(Double.min(dataToMinMax.get(i).min, data),
                  Double.max(dataToMinMax.get(i).max, data)));
            }
          }
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private boolean isNumeric(String str) {
    if (str == null) {
      return false;
    }
    return pattern.matcher(str).matches();
  }

  private void generateNormalizedDataSet() {
    try (BufferedReader reader = new BufferedReader(new FileReader(originalDatasetFilename));
         BufferedWriter writer = new BufferedWriter(new FileWriter(normalizedDatasetFilename))) {
      //Keep the header line
      writer.write(reader.readLine() + "\n");

      String line;
      while ((line = reader.readLine()) != null) {
        String[] data = line.split(",");
        for (int i = 0; i < data.length; i++) {
          if (isNumeric(data[i])) {
            double normalized = normalizeData(Double.parseDouble(data[i]), i);
            data[i] = String.valueOf(normalized);
          }
        }
        writer.write(String.join(",", data) + "\n");
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private double normalizeData(double data, int column) {
    double minDataFromColumn = dataToMinMax.get(column).min;
    double maxDataFromColumn = dataToMinMax.get(column).max;
    return (data - minDataFromColumn) / (maxDataFromColumn - minDataFromColumn);
  }
}
