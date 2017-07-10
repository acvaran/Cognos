package wall_avoider.Game;

import java.io.File;
import java.util.ArrayList;
import java.util.Formatter;

import cognos.GeneticAlgorithm.Individual;
import cognos.GeneticAlgorithm.Topology;

public class Population {

	private int populationSize, numberOfInputs, numberOfOutputs;
	private double selectionPercentage, connectionMutationChance, nodeMutationChance, lowestFitness;
	private Topology topology;
	private ArrayList<Individual> population, selectedPopulation;
	private int number, generation;

	public Population(int populationSize, int numberOfInputs, int numberOfOutputs, double selectionPercentage,
			double connectionMutationChance, double nodeMutationChance, Topology topology) {
		this.population = new ArrayList<>();
		this.selectedPopulation = new ArrayList<>();
		this.populationSize = populationSize;
		this.numberOfInputs = numberOfInputs;
		this.numberOfOutputs = numberOfOutputs;
		this.selectionPercentage = selectionPercentage;
		this.connectionMutationChance = connectionMutationChance;
		this.nodeMutationChance = nodeMutationChance;
		this.topology = topology;
		this.generation = 0;
		number = 0;
		for (int i = 0; i < populationSize; i++, number++)
			population.add(new Character(numberOfInputs, numberOfOutputs, "Individual" + number));
	}

	public void run() {
		selectedPopulation = new ArrayList<>();
		generation++;
		((Game) topology).setGeneration(generation);
		for (int i = 0; i < population.size(); i++)
			population.get(i).reset();
		for (int i = 0; i < population.size(); i++)
			population.get(i).setFitness(topology.test(population.get(i)));
	}

	public void selection() {
		int numberOfFittest = (int) (population.size() * selectionPercentage / 100);
		if (numberOfFittest == 0)
			numberOfFittest++;
		ArrayList<Individual> fittestPop = new ArrayList<>();
		double fitness = 0.0;
		lowestFitness = population.get(0).getFitness();
		for (int i = 0; i < populationSize; i++)
			if (lowestFitness > population.get(i).getFitness())
				lowestFitness = population.get(i).getFitness();
		while (fittestPop.size() != populationSize) {
			for (int i = 0; i < population.size(); i++)
				if (fitness < population.get(i).getFitness())
					fitness = population.get(i).getFitness();

			sorting_loop: for (int i = 0; i < population.size(); i++)
				if (fitness == population.get(i).getFitness()) {
					fittestPop.add(population.get(i));
					population.remove(i);
					break sorting_loop;
				}
			fitness = 0;
		}
		for (int i = 0; i < numberOfFittest; i++) {
			population.add(fittestPop.get(i));
			selectedPopulation.add(fittestPop.get(i));
		}
		populationSize = population.size();
	}

	public void mutate() {
		ArrayList<Individual> tempPop = new ArrayList<>();
		for (int i = 0; i < populationSize; i++, number++) {
			tempPop.add(new Character(population.get(i), "Individual" + number));
			tempPop.get(i).mutateIndividual(connectionMutationChance, nodeMutationChance);
			tempPop.get(i).reset();
		}
		for (int i = 0; i < populationSize; i++)
			population.add(tempPop.get(i));
		populationSize = population.size();
	}

	public String getInfo() {
		double avgFitness = 0;
		double maxFitness = 0;
		int fittest = 0, lowest = 0;
		for (int i = 0; i < population.size(); i++) {
			if (maxFitness < population.get(i).getFitness())
				maxFitness = population.get(i).getFitness();
			avgFitness = avgFitness + population.get(i).getFitness();
		}
		avgFitness = avgFitness / population.size();
		for (int i = 0; i < population.size(); i++)
			if (maxFitness == population.get(i).getFitness()) {
				fittest = i;
				break;
			}
		for (int i = 0; i < population.size(); i++)
			if (lowestFitness == population.get(i).getFitness()) {
				lowest = i;
				break;
			}
		return "Average Fitness: " + avgFitness + "\nMax Fitness: " + maxFitness + "\nLowest Fitness: " + lowestFitness
				+ "\nFittest Genome: \n" + population.get(fittest).getGenome() + "\nLowest Genome: \n"
				+ population.get(lowest).getGenome();
	}

	public void savePopulation() {

	}

	public ArrayList<Individual> getSelectedPopulation() {
		return selectedPopulation;
	}

	public int getPopulationSize() {
		return populationSize;
	}

	public int getNumberOfInputs() {
		return numberOfInputs;
	}

	public int getNumberOfOutputs() {
		return numberOfOutputs;
	}

	public double getAvgFitness() {
		double avgFitness = 0;
		for (int i = 0; i < population.size(); i++)
			avgFitness = avgFitness + population.get(i).getFitness();
		avgFitness = (double) avgFitness / population.size();
		return avgFitness;
	}

	public double getMaxFitness() {
		double maxFitness = 0;
		for (int i = 0; i < population.size(); i++)
			if (maxFitness < population.get(i).getFitness())
				maxFitness = population.get(i).getFitness();
		return maxFitness;
	}

	public double getLowFitness() {
		double lowFitness = population.get(0).getFitness();
		for (int i = 0; i < population.size(); i++)
			if (lowFitness > population.get(i).getFitness())
				lowFitness = population.get(i).getFitness();
		return lowFitness;
	}

	public double getSelectionPercentage() {
		return selectionPercentage;
	}

	public double getConnectionMutationChance() {
		return connectionMutationChance;
	}

	public double getNodeMutationChance() {
		return nodeMutationChance;
	}

	public Topology getTopology() {
		return topology;
	}

	public ArrayList<Individual> getPopulation() {
		return population;
	}

	public int getGeneration() {
		return generation;
	}

	public void setPopulationSize(int populationSize) {
		this.populationSize = populationSize;
	}

	public void setNumberOfInputs(int numberOfInputs) {
		this.numberOfInputs = numberOfInputs;
	}

	public void setNumberOfOutputs(int numberOfOutputs) {
		this.numberOfOutputs = numberOfOutputs;
	}

	public void setSelectionPercentage(double selectionPercentage) {
		this.selectionPercentage = selectionPercentage;
	}

	public void setConnectionMutationChance(double connectionMutationChance) {
		this.connectionMutationChance = connectionMutationChance;
	}

	public void setNodeMutationChance(double nodeMutationChance) {
		this.nodeMutationChance = nodeMutationChance;
	}

	public void setTopology(Topology topology) {
		this.topology = topology;
	}

	public void setPopulation(ArrayList<Individual> population) {
		this.population = population;
	}

	public void setGeneration(int generation) {
		this.generation = generation;
	}
}