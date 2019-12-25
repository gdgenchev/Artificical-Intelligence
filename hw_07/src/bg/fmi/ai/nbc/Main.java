package bg.fmi.ai.nbc;

import bg.fmi.ai.nbc.classifier.NaiveBayesClassifier;
import bg.fmi.ai.nbc.human.Gender;

public class Main {
  public static void main(String[] args) {
    var classifier = new NaiveBayesClassifier("data.csv");
    classifier.train();

    //male
    Gender gender1 = classifier.classify(new double[]{6.4, 150, 12});
    System.out.println(gender1);

    //female
    Gender gender2 = classifier.classify(new double[]{6, 130, 8});
    System.out.println(gender2);
  }
}
