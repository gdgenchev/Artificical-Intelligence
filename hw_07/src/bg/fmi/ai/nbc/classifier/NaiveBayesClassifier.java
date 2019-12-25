package bg.fmi.ai.nbc.classifier;

import bg.fmi.ai.nbc.human.Gender;
import bg.fmi.ai.nbc.human.Human;
import bg.fmi.ai.nbc.human.HumanCreator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

/**
 * Simple Naive Bayes Classifier
 *
 * Coded for the wikipedia example dataset, but can be easily
 * changed to work with other classes.
 */
public class NaiveBayesClassifier {
  /**
   * The filename of the dataset
   */
  private String filename;

  /**
   * The means of each attribute for each gender
   */
  private EnumMap<Gender, double[]> genderToAttributesMean;

  /**
   * The variances of each attribute for each gender
   */
  private EnumMap<Gender, double[]> genderToAttributesVariance;

  /**
   * The number of members of each gender
   */
  private EnumMap<Gender, Integer> genderToCount;

  /**
   * All the date is stored in this list
   */
  private List<Human> humans;

  public NaiveBayesClassifier(String filename) {
    this.filename = filename;
    this.genderToAttributesMean = new EnumMap<>(Gender.class);
    this.genderToAttributesVariance = new EnumMap<>(Gender.class);
    this.genderToCount = new EnumMap<>(Gender.class);
    this.humans = new ArrayList<>();
  }

  /**
   * This method is used to train the model.
   *
   * Firstly, all the date is stored in our Humans list
   * After that, we calculate the means
   * Finally, we calculate the variances
   */
  public void train() {
    fillHumans();
    calculateMeans();
    calculateVariances();
  }

  /**
   * This method fills the Humans list.
   *
   * We traverse over the file line by line, parsing the human and saving it.
   * Moreover, we update the count of all members of each gender
   */
  private void fillHumans() {
    try (var reader = new BufferedReader(new FileReader(filename))) {
      reader.readLine();
      String line;
      while ((line = reader.readLine()) != null) {
        Human human = HumanCreator.createHuman(line);
        humans.add(human);
        genderToCount.put(human.getGender(), genderToCount.getOrDefault(human.getGender(), 0) + 1);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * This method calculates the means of all attributes for each gender.
   *
   * This is done with 2 passes:
   * First, we calculate the sum of each attribute
   * Then, we divide each gender's attributes by the number of members of each gender.
   */
  private void calculateMeans() {
    //Fill sums of each attribute for each gender
    for (Human human : humans) {
      double[] attributes = human.getAttributes();
      double[] attributesMean = genderToAttributesMean.getOrDefault(human.getGender(), new double[]{0.0d, 0.0d, 0.0d});
      attributesMean[0] += attributes[0];
      attributesMean[1] += attributes[1];
      attributesMean[2] += attributes[2];
      genderToAttributesMean.put(human.getGender(), attributesMean);
    }

    //Finalize the means calculation by dividing each sum by the size of the members of each gender
    for (Gender gender : Gender.values()) {
      double[] attributesMean = genderToAttributesMean.get(gender);
      for (int i = 0; i < attributesMean.length; i++) {
        attributesMean[i] /= genderToCount.get(gender);
      }
    }
  }

  /**
   * This method calculates the variances of all attributes for each gender.
   *
   * This is done in 2 passes:
   * First, we calculate the sum of (each attribute - the corresponding mean)
   * Then, we divide the sums by t(he number of members of each gender - 1)
   */
  private void calculateVariances() {
    //Fill sums of each (attribute - mean of attribute) for each gender
    for (Human human : humans) {
      double[] attributes = human.getAttributes();
      var attributesVariance = genderToAttributesVariance.getOrDefault(human.getGender(), new double[]{0.0d, 0.0d, 0.0d});
      double[] means = genderToAttributesMean.get(human.getGender());
      attributesVariance[0] += square(attributes[0] - means[0]);
      attributesVariance[1] += square(attributes[1] - means[1]);
      attributesVariance[2] += square(attributes[2] - means[2]);
      genderToAttributesVariance.put(human.getGender(), attributesVariance);
    }

    //Finalize the variance calculation by dividing each sum by the size of the members of each gender - 1
    for (Gender gender : Gender.values()) {
      double[] variances = genderToAttributesVariance.get(gender);
      for (int i = 0; i < variances.length; i++) {
        variances[i] /= genderToCount.get(gender) - 1;
      }
    }
  }

  /**
   * This method squares a number.
   *
   * @param x the number to be squared
   * @return The square of x
   */
  private double square(double x) {
    return x * x;
  }

  /**
   * This method is used to classify given attributes to a gender.
   *
   * @param attributes the given attributes
   * @return the gender, corresponding to the attributes
   */
  public Gender classify(double[] attributes) {
    double maxProduct = Integer.MIN_VALUE;
    Gender genderToReturn = null;
    for (Gender gender : Gender.values()) {
      double product = 1;
      for (int i = 0; i < attributes.length; i++) {
        product *= gauss(attributes[i], genderToAttributesMean.get(gender)[i], genderToAttributesVariance.get(gender)[i]);
      }

      if (product > maxProduct) {
        maxProduct = product;
        genderToReturn = gender;
      }
    }

    return genderToReturn;
  }

  /**
   *
   * @param v the value tested attribute
   * @param mean the mean of the tested attribute
   * @param variance the variance of the tested attribute
   * @return the chance that v is in some class
   */
  private double gauss(double v, double mean, double variance) {
    return Math.exp(-square(v - mean) / (2 * variance)) / Math.sqrt(2 * Math.PI * variance);
  }
}