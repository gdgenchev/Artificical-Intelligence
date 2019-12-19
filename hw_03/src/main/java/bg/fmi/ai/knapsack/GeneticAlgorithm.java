package main.java.bg.fmi.ai.knapsack;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

public class GeneticAlgorithm {
  private List<Item> items;
  private int numberOfItems;
  private int knapsackCapacity;
  private int maxIterations;
  private int populationSize;

  private Population population;

  private int maxValue = 0;
  private int maxWeight = 0;

  public GeneticAlgorithm(List<Item> items, int numberOfItems, int knapsackCapacity, int populationSize) {
    this.items = items;
    this.numberOfItems = numberOfItems;
    this.knapsackCapacity = knapsackCapacity;
    this.maxIterations = 10000;
    this.population = new Population();
    this.populationSize = populationSize;
  }

  public void solve() {
    generateInitialPopulation();

    Chromosome parent1;

    for (int i = 0; i < maxIterations; ++i) {
      parent1 = getMostProbableChromosome();
      System.out.println(parent1.getFitness());
      Chromosome[] children = crossover(parent1, selectParent());
      population.addChromosome(children[0]);
      population.addChromosome(children[1]);
    }

    System.out.println("MAX VALUE " + maxValue);
    System.out.println("MAX WEIGHT " + maxWeight);

  }

  private Chromosome getMostProbableChromosome() {
    return population.getPopulation().peek();
  }

  private void generateInitialPopulation() {
    int populationFitness = 0;
    for (int i = 0; i < populationSize; i++) {
      Chromosome chromosome = generateChromosome();

      if (!population.conatins(chromosome)) {
        while (chromosome.getWeight() > knapsackCapacity) {
          mutate(chromosome);
        }

        if (!population.conatins(chromosome)) {
          population.addChromosome(chromosome);
          populationFitness += chromosome.getFitness();
          if (chromosome.getValue() > maxValue) {
            maxValue = chromosome.getValue();
          }
          if (chromosome.getWeight() > maxWeight) {
            maxWeight = chromosome.getWeight();
          }
        }
      }
    }
    population.setFitness(populationFitness);
  }

  private void mutate(Chromosome chromosome) {
    boolean[] genes = chromosome.getGenes();
    int value = chromosome.getValue();
    int weight = chromosome.getWeight();

    Random random = new Random();
    int indexToMutate = random.nextInt(numberOfItems);
    boolean geneToBeChanged = genes[indexToMutate];

    genes[indexToMutate] = !genes[indexToMutate];

    if (geneToBeChanged) {
      value -= items.get(indexToMutate).getValue();
      weight -= items.get(indexToMutate).getWeight();

    } else {
      value += items.get(indexToMutate).getValue();
      weight += items.get(indexToMutate).getWeight();
    }


    chromosome.setGenes(genes);
    chromosome.setValue(value);
    chromosome.setWeight(weight);
  }

  private Chromosome generateChromosome() {
    int value = 0;
    int weight = 0;

    boolean[] genes = new boolean[numberOfItems];
    Random random = new Random();

    for (int i = 0; i < numberOfItems; ++i) {
      Item item = items.get(i);

      genes[i] = random.nextBoolean();

      if (genes[i]) {
        value += item.getValue();
        weight += item.getWeight();
      }
    }

    return new Chromosome(genes, weight, value);
  }

  public static void main(String[] args) {
    List<Item> items = new ArrayList<>();
    try {
      Scanner scanner = new Scanner(new FileInputStream("items.txt"));
      while (scanner.hasNext()) {
        scanner.useDelimiter("\\d");
        String name = scanner.next();
        name = name.trim();
        scanner.reset();
        int price = scanner.nextInt();
        int value = scanner.nextInt();
        items.add(new Item(name, price, value));

        if (scanner.hasNext()) {
          scanner.skip("\r\n");
        }
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(items, items.size(), 3000, 100);
    geneticAlgorithm.solve();
  }

  Chromosome[] crossover(Chromosome parent1Chromosome, Chromosome parent2Chromosome) {
    boolean[] parent1Genes = parent1Chromosome.getGenes();
    boolean[] parent2Genes = parent2Chromosome.getGenes();

    boolean[] child1Genes = new boolean[parent1Genes.length];
    boolean[] child2Genes = new boolean[parent2Genes.length];

    int child1Value = 0;
    int child1Weight = 0;

    int child2Value = 0;
    int child2Weight = 0;

    int crossoverPoint = new Random().nextInt(numberOfItems);


    for (int i = 0; i < numberOfItems; ++i) {
      if (i <= crossoverPoint) {
        child1Genes[i] = parent1Genes[i];
        child2Genes[i] = parent2Genes[i];
      } else {
        child1Genes[i] = parent2Genes[i];
        child2Genes[i] = parent1Genes[i];
      }

      if (child1Genes[i]) {
        child1Value += items.get(i).getValue();
        child1Weight += items.get(i).getWeight();
      }

      if (child2Genes[i]) {
        child2Value += items.get(i).getValue();
        child2Weight += items.get(i).getWeight();
      }
    }

    return new Chromosome[]{
        new Chromosome(child1Genes, child1Weight, child1Value),
        new Chromosome(child2Genes, child2Weight, child2Value)
    };
  }

  private Chromosome selectParent() {
    Queue<Chromosome> chromosomes = population.getPopulation();
    int populationFitness = population.getFitness();
    double rouletteWheelPosition = Math.random() * populationFitness;
    int spinWheel = 0;

    for (Chromosome chromosome : chromosomes) {
      spinWheel += chromosome.getFitness();

      if (spinWheel >= rouletteWheelPosition) {
        return chromosome;
      }
    }

    return getMostProbableChromosome();
  }
}


