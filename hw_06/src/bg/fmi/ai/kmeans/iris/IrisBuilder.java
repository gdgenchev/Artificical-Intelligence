package bg.fmi.ai.kmeans.iris;

public class IrisBuilder {
  private double sepalLength;
  private double sepalWidth;

  private double petalLength;
  private double petalWidth;

  private String species;

  public IrisBuilder sepalLength(double sepalLength) {
    this.sepalLength = sepalLength;
    return this;
  }

  public IrisBuilder sepalWidth(double sepalWidth) {
    this.sepalWidth = sepalWidth;
    return this;
  }

  public IrisBuilder petalLength(double petalLength) {
    this.petalLength = petalLength;
    return this;
  }

  public IrisBuilder petalWidth(double petalWidth) {
    this.petalWidth = petalWidth;
    return this;
  }

  public IrisBuilder species(String species) {
    this.species = species;
    return this;
  }

  public Iris build() {
    return new Iris(sepalLength, sepalWidth, petalLength, petalWidth, species);
  }
}
