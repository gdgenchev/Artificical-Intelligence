package bg.fmi.ai.kmeans.iris;

public class IrisCreator {
    public static Iris createIris(String line) {
        String[] data = line.split(",");
        return new IrisBuilder()
                .sepalLength(Double.parseDouble(data[0]))
                .sepalWidth(Double.parseDouble(data[1]))
                .petalLength(Double.parseDouble(data[2]))
                .petalWidth(Double.parseDouble(data[3]))
                .species(data[4])
                .build();
    }
}
