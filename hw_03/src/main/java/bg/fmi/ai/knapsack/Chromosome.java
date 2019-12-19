package main.java.bg.fmi.ai.knapsack;

public class Chromosome implements Comparable<Chromosome> {
  private boolean[] genes;
  private int weight;
  private int value;

  void setWeight(int weight) {
    this.weight = weight;
  }

  void setValue(int value) {
    this.value = value;
  }

  int getWeight() {
    return weight;
  }

  Chromosome(boolean[] genes, int weight, int value) {
    this.genes = genes;
    this.weight = weight;
    this.value = value;
  }

  int getValue() {
    return value;
  }

  double getFitness() {
    return value;
  }

  @Override
  public int compareTo(Chromosome other) {
    return Double.compare(other.getFitness(), this.getFitness());
  }

  boolean[] getGenes() {
    return genes;
  }

  void setGenes(boolean[] genes) {
    this.genes = genes;
  }
}
