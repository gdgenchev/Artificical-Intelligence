package bg.fmi.ai.knn.iris;

public class Iris {
  private double sepalLength;
  private double sepalWidth;

  private double petalLength;
  private double petalWidth;

  private String species;

  Iris(double sepalLength, double sepalWidth, double petalLength, double petalWidth, String species) {
    this.sepalLength = sepalLength;
    this.sepalWidth = sepalWidth;
    this.petalLength = petalLength;
    this.petalWidth = petalWidth;
    this.species = species;
  }

  public double getDistance(double[] features) {
    return Math.sqrt(Math.pow(sepalLength - features[0], 2)
        + Math.pow(sepalWidth - features[1], 2)
        + Math.pow(petalLength - features[2], 2)
        + Math.pow(petalWidth - features[3], 2));
  }

  public String getSpecies() {
    return species;
  }

  public double[] getFeatures() {
    return new double[]{sepalLength, sepalLength, petalLength, petalLength};
  }
}
