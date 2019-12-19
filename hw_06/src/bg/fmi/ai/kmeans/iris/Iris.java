package bg.fmi.ai.kmeans.iris;

public class Iris {
    private double sepalLength;
    private double sepalWidth;

    private double petalLength;
    private double petalWidth;

    private String species;

    public Iris(double sepalLength, double sepalWidth, double petalLength, double petalWidth, String species) {
        this.sepalLength = sepalLength;
        this.sepalWidth = sepalWidth;
        this.petalLength = petalLength;
        this.petalWidth = petalWidth;
        this.species = species;
    }

    public Iris(double[] features, String species) {
        this(features[0], features[1], features[2], features[3], species);
    }

    public Iris(Iris other) {
        this.sepalLength = other.sepalLength;
        this.sepalWidth = other.sepalWidth;
        this.petalLength = other.petalLength;
        this.petalWidth = other.petalWidth;
        this.species = other.species;
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
        return new double[]{sepalLength, sepalWidth, petalLength, petalWidth};
    }

    @Override
    public String toString() {
        return sepalLength + "," + sepalWidth + "," + petalLength + "," + petalWidth + "," + species;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        Iris otherIris = (Iris) other;
        return Double.compare(otherIris.sepalLength, sepalLength) == 0 &&
                Double.compare(otherIris.sepalWidth, sepalWidth) == 0 &&
                Double.compare(otherIris.petalLength, petalLength) == 0 &&
                Double.compare(otherIris.petalWidth, petalWidth) == 0 &&
                otherIris.species.equals(species);
    }
}