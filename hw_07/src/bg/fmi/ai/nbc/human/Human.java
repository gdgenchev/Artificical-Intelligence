package bg.fmi.ai.nbc.human;

public class Human {
  private Gender gender;
  private double[] attributes;

  Human(Gender gender, double[] attributes) {
    this.gender = gender;
    this.attributes = attributes;
  }

  public Gender getGender() {
    return gender;
  }

  public double[] getAttributes() {
    return attributes;
  }
}
