package main.java.bg.fmi.ai.knapsack;

import java.util.*;

public class Population {
    private Queue<Chromosome> population;
    private Set<Chromosome> populationSet;

    private int fitness;
    
    public Population(){
        population = new PriorityQueue<>();
        populationSet = new HashSet<>();
        fitness = 0;
    }

    public Queue<Chromosome> getPopulation(){
        return population;
    }
    

    public void setFitness(int populationFitness){
        this.fitness = populationFitness;
    }
    

    public int getFitness(){
        return fitness;
    }
    

    public void addChromosome(Chromosome chromosome){
        population.add(chromosome);
        populationSet.add(chromosome);
    }
    

    public boolean conatins(Chromosome individual){
        return populationSet.contains(individual);
    }

}