package bg.fmi.ai.nbc.human;

import java.util.Map;

public enum Gender {
  MALE("male"), FEMALE("female");

  private String gender;

  private static final Map<String, Gender> genders = Map.of("male", MALE, "female", FEMALE);

  Gender(String gender) {
    this.gender = gender;
  }

  public static Gender getGender(String gender) {
    return genders.get(gender);
  }

  @Override
  public String toString() {
    return gender;
  }
}
