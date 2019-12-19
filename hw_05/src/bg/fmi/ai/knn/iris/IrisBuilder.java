package bg.fmi.ai.knn.iris;

public class IrisBuilder {
  private double sepalLength;
  private double sepalWidth;

  private double petalLength;
  private double petalWidth;

  private String species;

  IrisBuilder sepalLength(double sepalLength) {
    this.sepalLength = sepalLength;
    return this;
  }

  IrisBuilder sepalWidth(double sepalWidth) {
    this.sepalWidth = sepalWidth;
    return this;
  }

  IrisBuilder petalLength(double petalLength) {
    this.petalLength = petalLength;
    return this;
  }

  IrisBuilder petalWidth(double petalWidth) {
    this.petalWidth = petalWidth;
    return this;
  }

  IrisBuilder species(String species) {
    this.species = species;
    return this;
  }

  Iris build() {
    return new Iris(sepalLength, sepalWidth, petalLength, petalWidth, species);
  }
}
