package bg.fmi.ai.nbc.human;

public class HumanCreator {
  public static Human createHuman(String line) {
    String[] data = line.split(",");

    Gender gender = Gender.getGender(data[0]);
    double[] attributes = new double[]{
        Double.parseDouble(data[1]),
        Double.parseDouble(data[2]),
        Double.parseDouble(data[3])
    };

    return new Human(gender, attributes);
  }
}
