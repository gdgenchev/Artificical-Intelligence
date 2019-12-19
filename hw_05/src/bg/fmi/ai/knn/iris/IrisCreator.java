package bg.fmi.ai.knn.iris;

public class IrisCreator {
  public static Iris createIris(String irisData) {
    String[] data = irisData.split(",");

    return new IrisBuilder()
        .sepalLength(Double.parseDouble(data[0]))
        .sepalWidth(Double.parseDouble(data[1]))
        .petalLength(Double.parseDouble(data[2]))
        .petalWidth(Double.parseDouble(data[3]))
        .species(data[4])
        .build();
  }
}
