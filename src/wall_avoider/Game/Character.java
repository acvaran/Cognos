package wall_avoider.Game;

import cognos.GeneticAlgorithm.Genome;
import cognos.GeneticAlgorithm.Individual;

public class Character implements Individual {

	private Genome genome;
	private int numberOfInputs, numberOfOutputs, location, mapValue;
	private double fitness;
	private String name;

	public Character(Individual individual, String name) {
		clone(individual);
		this.name = name;
	}

	public Character(int numberOfInputs, int numberOfOutputs, String name) {
		this.numberOfInputs = numberOfInputs;
		this.numberOfOutputs = numberOfOutputs;
		this.name = name;
		mapValue = 2;
		genome = new Genome(numberOfInputs, numberOfOutputs);
	}

	@Override
	public void mutateIndividual(double connectionMutationChance, double nodeMutationChance) {
		// TODO Auto-generated method stub
		genome.addConnectionMutation(connectionMutationChance);
		genome.addNodeMutation(nodeMutationChance);
	}

	@Override
	public int[] readOutput(int[] inputs) {
		// TODO Auto-generated method stub
		int[] outputs = genome.solveNetwork(inputs);
		genome.clearInputs();
		return outputs;
	}

	@Override
	public void clone(Individual individual) {
		// TODO Auto-generated method stub
		genome = new Genome(individual.getGenome());
		numberOfInputs = individual.getNumberOfInputs();
		numberOfOutputs = individual.getNumberOfOutputs();
		fitness = individual.getFitness();
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		fitness = 0.0;
		mapValue = 2;
	}

	public boolean isDead() {
		if (mapValue == 3)
			return true;
		return false;
	}

	public void move(int[] inputs) {
		int[] outputs = readOutput(inputs);
		int direction;
		if (outputs[0] == 1 && outputs[1] == 0)
			direction = -1;
		else if (outputs[0] == 0 && outputs[1] == 1)
			direction = 1;
		else
			direction = 0;
		if (location + direction != -1 && location + direction != 7) {
			location = location + direction;
			// incrementFitness();
		}
	}

	public String getName() {
		return name;
	}

	public int getMapValue() {
		return mapValue;
	}

	public int getLocation() {
		return location;
	}

	@Override
	public Genome getGenome() {
		// TODO Auto-generated method stub
		return genome;
	}

	@Override
	public int getNumberOfInputs() {
		// TODO Auto-generated method stub
		return numberOfInputs;
	}

	@Override
	public int getNumberOfOutputs() {
		// TODO Auto-generated method stub
		return numberOfOutputs;
	}

	@Override
	public double getFitness() {
		// TODO Auto-generated method stub
		return fitness;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setMapValue(int mapValue) {
		this.mapValue = mapValue;
	}

	public void setLocation(int location) {
		this.location = location;
	}

	@Override
	public void setGenome(Genome genome) {
		// TODO Auto-generated method stub
		this.genome = genome;
	}

	@Override
	public void setNumberOfInputs(int numberOfInputs) {
		// TODO Auto-generated method stub
		this.numberOfInputs = numberOfInputs;
	}

	@Override
	public void setNumberOfOutputs(int numberOfOutputs) {
		// TODO Auto-generated method stub
		this.numberOfOutputs = numberOfOutputs;
	}

	@Override
	public void setFitness(double fitness) {
		// TODO Auto-generated method stub
		this.fitness = fitness;
	}

	public void incrementFitness() {
		fitness++;
	}

	public void decrementFitness() {
		fitness--;
	}

	public String toString() {
		return name + " " + location + " " + mapValue + " " + fitness;
	}
}
