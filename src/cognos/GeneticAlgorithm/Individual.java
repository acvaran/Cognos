package cognos.GeneticAlgorithm;

public interface Individual {

	public void mutateIndividual(double connectionMutationChance, double nodeMutationChance);

	public int[] readOutput(int[] inputs);

	public void clone(Individual individual);

	public void reset();

	public Genome getGenome();

	public int getNumberOfInputs();

	public int getNumberOfOutputs();

	public double getFitness();

	public void setGenome(Genome genome);

	public void setNumberOfInputs(int numberOfInputs);

	public void setNumberOfOutputs(int numberOfOutputs);

	public void setFitness(double fitness);

}
