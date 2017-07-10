package cognos.GeneticAlgorithm;

import java.util.ArrayList;

import cognos.NeuralNetwork.Connection;
import cognos.NeuralNetwork.Node;

public class Genome {

	private ArrayList<Gene> genome;
	private int innovationNumber, numberOfInputs, numberOfOutputs;
	private double defaultThreshold = 0.0;
	private ArrayList<Node> inputNodes, outputNodes, hiddenNodes;

	public Genome(Genome genome) {
		cloneGenome(genome);
		try {
			genome.finalize();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Genome(int numberOfInputs, int numberOfOutputs) {
		genome = new ArrayList<>();
		inputNodes = new ArrayList<>();
		hiddenNodes = new ArrayList<>();
		outputNodes = new ArrayList<>();
		innovationNumber = 0;
		this.numberOfInputs = numberOfInputs;
		this.numberOfOutputs = numberOfOutputs;
		createInputNodes();
		createOutputNodes();
		createFirstGenGenes();
	}

	public void createInputNodes() {
		for (int i = 0; i < numberOfInputs; i++) {
			Node temp = new Node(0);
			temp.setThreshold(0);
			temp.setName("Input" + i);
			inputNodes.add(temp);
		}
	}

	public void createOutputNodes() {
		for (int i = 0; i < numberOfOutputs; i++) {
			Node temp = new Node(2, defaultThreshold);
			temp.setName("Output" + i);
			outputNodes.add(temp);
		}
	}

	public void createFirstGenGenes() {
		double connectionProbability = 0.5, random;
		Connection connection;
		for (int i = 0; i < numberOfInputs; i++)
			for (int j = 0; j < numberOfOutputs; j++) {
				random = Math.random();
				if (random < connectionProbability) {
					innovationNumber++;
					connection = new Connection(inputNodes.get(i), outputNodes.get(j), (Math.random() * 2) - 1);
					genome.add(new Gene(connection, innovationNumber, true));
				}
			}
	}

	@SuppressWarnings("unused")
	public void addConnectionMutation(double connectionMutationChance) {
		double r = Math.random();
		if (r < connectionMutationChance) {
			double numberOfIHNodes = inputNodes.size() + hiddenNodes.size();
			double numberOfHONodes = hiddenNodes.size() + outputNodes.size();
			int randomINode = 0, randomONode = 0, changedConnection = 0;
			Node randINode, randONode;
			boolean connectionExists = false;
			r = Math.random();

			random_inputnode_loop: {
				while (r > (double) randomINode * (1 / numberOfIHNodes))
					randomINode++;
				if (randomINode <= inputNodes.size())
					randINode = inputNodes.get(randomINode - 1);
				else
					randINode = hiddenNodes.get(randomINode - inputNodes.size() - 1);
			}
			r = Math.random();

			randONode = randINode;
			while (randINode.getName().equals(randONode.getName())) {
				random_outputnode_loop: {
					randomONode = 0;
					while (r > (double) randomONode * (1 / numberOfHONodes))
						randomONode++;
					if (randomONode <= outputNodes.size())
						randONode = outputNodes.get(randomONode - 1);
					else
						randONode = hiddenNodes.get(randomONode - outputNodes.size() - 1);
				}
				r = Math.random();
			}
			choosing_connection_loop: for (int i = 0; i < genome.size(); i++)
				if (genome.get(i).getConnection().getNode1().getName().equals(randINode.getName()))
					if (genome.get(i).getConnection().getNode2().getName().equals(randONode.getName())) {
						changedConnection = i;
						connectionExists = true;
						break;
					}

			if (connectionExists) {
				genome.get(changedConnection).getConnection().setWeight((Math.random() * 2) - 1);
				if (!genome.get(changedConnection).isActive())
					genome.get(changedConnection).setActive(true);
			} else {
				innovationNumber++;
				genome.add(new Gene(new Connection(randINode, randONode, (Math.random() * 2) - 1), innovationNumber,
						true));
			}
		}
	}

	public void addNodeMutation(double nodeMutationChance) {
		double r = Math.random();
		int randGene = 0;
		Node hNode;
		if (r < nodeMutationChance) {
			r = Math.random();
			while (r >= (double) randGene * (1 / (double) genome.size()))
				randGene++;
			if (!genome.get(randGene - 1).isActive())
				addNodeMutation(nodeMutationChance);
			else {
				hNode = new Node(1, defaultThreshold);
				hNode.setName("Hidden" + hiddenNodes.size());
				hiddenNodes.add(hNode);
				innovationNumber++;
				genome.add(new Gene(new Connection(genome.get(randGene - 1).getConnection().getNode1(), hNode,
						(Math.random() * 2) - 1), innovationNumber, true));
				innovationNumber++;
				genome.add(new Gene(new Connection(hNode, genome.get(randGene - 1).getConnection().getNode2(),
						(Math.random() * 2) - 1), innovationNumber, true));
				genome.get(randGene - 1).setActive(false);
			}
		}
	}

	public void setInputStates(int[] inputs) {
		for (int i = 0; i < numberOfInputs; i++)
			inputNodes.get(i).setState(inputs[i]);
	}

	public int[] solveNetwork(int[] inputs) {
		setInputStates(inputs);
		for (int i = 0; i < numberOfInputs; i++)
			for (int j = 0; j < genome.size(); j++)
				if (genome.get(j).isActive())
					if (genome.get(j).getConnection().getNode1().getName().equals(inputNodes.get(i).getName()))
						genome.get(j).getConnection().getNode2().calculateInput(
								(double) inputNodes.get(i).getState() * genome.get(j).getConnection().getWeight());

		for (int i = 0; i < hiddenNodes.size(); i++)
			for (int j = 0; j < genome.size(); j++)
				if (genome.get(j).isActive())
					if (genome.get(j).getConnection().getNode1().getName().equals(hiddenNodes.get(i).getName()))
						for (int k = 0; k < hiddenNodes.size(); k++)
							if (genome.get(j).getConnection().getNode2().getName().equals(hiddenNodes.get(k).getName()))
								hiddenNodes.get(k)
										.calculateInput((double) genome.get(j).getConnection().getNode1().getState()
												* genome.get(j).getConnection().getWeight());
		for (int i = 0; i < hiddenNodes.size(); i++)
			hiddenNodes.get(i).findState();
		for (int i = 0; i < hiddenNodes.size(); i++)
			for (int j = 0; j < genome.size(); j++)
				if (genome.get(j).isActive())
					if (genome.get(j).getConnection().getNode1().getName().equals(hiddenNodes.get(i).getName()))
						for (int k = 0; k < outputNodes.size(); k++)
							if (genome.get(j).getConnection().getNode2().getName().equals(outputNodes.get(k).getName()))
								outputNodes.get(k)
										.calculateInput((double) genome.get(j).getConnection().getNode1().getState()
												* genome.get(j).getConnection().getWeight());
		return solveOutput();
	}

	public int[] solveOutput() {
		int[] outputs = new int[numberOfOutputs];
		for (int i = 0; i < numberOfOutputs; i++)
			outputs[i] = outputNodes.get(i).findState();
		return outputs;
	}

	public void cloneGenome(Genome genome) {
		this.genome = new ArrayList<>();
		this.inputNodes = new ArrayList<>();
		this.outputNodes = new ArrayList<>();
		this.hiddenNodes = new ArrayList<>();
		this.numberOfInputs = genome.getNumberOfInputs();
		this.numberOfOutputs = genome.getNumberOfOutputs();
		this.defaultThreshold = genome.getDefaultThreshold();
		this.innovationNumber = 0;

		for (int i = 0; i < genome.getNumberOfInputs(); i++)
			inputNodes.add(new Node(genome.getInputNodes().get(i)));
		for (int i = 0; i < genome.getNumberOfOutputs(); i++)
			outputNodes.add(new Node(genome.getOutputNodes().get(i)));
		for (int i = 0; i < genome.getHiddenNodes().size(); i++)
			hiddenNodes.add(new Node(genome.getHiddenNodes().get(i)));
		for (int k = 0; k < genome.getGenome().size(); k++) {
			innovationNumber++;
			for (int i = 0; i < inputNodes.size(); i++) {
				for (int j = 0; j < outputNodes.size(); j++)
					if (genome.getGenome().get(k).getConnection().getNode1().getName()
							.equals(inputNodes.get(i).getName()))
						if (genome.getGenome().get(k).getConnection().getNode2().getName()
								.equals(outputNodes.get(j).getName()))
							this.genome.add(new Gene(
									new Connection(inputNodes.get(i), outputNodes.get(j),
											genome.getGenome().get(k).getConnection().getWeight()),
									innovationNumber, genome.getGenome().get(k).isActive()));
				for (int j = 0; j < hiddenNodes.size(); j++)
					if (genome.getGenome().get(k).getConnection().getNode1().getName()
							.equals(inputNodes.get(i).getName()))
						if (genome.getGenome().get(k).getConnection().getNode2().getName()
								.equals(hiddenNodes.get(j).getName()))
							this.genome.add(new Gene(
									new Connection(inputNodes.get(i), hiddenNodes.get(j),
											genome.getGenome().get(k).getConnection().getWeight()),
									innovationNumber, genome.getGenome().get(k).isActive()));
			}
			for (int i = 0; i < hiddenNodes.size(); i++) {
				for (int j = 0; j < outputNodes.size(); j++)
					if (genome.getGenome().get(k).getConnection().getNode1().getName()
							.equals(hiddenNodes.get(i).getName()))
						if (genome.getGenome().get(k).getConnection().getNode2().getName()
								.equals(outputNodes.get(j).getName()))
							this.genome.add(new Gene(
									new Connection(hiddenNodes.get(i), outputNodes.get(j),
											genome.getGenome().get(k).getConnection().getWeight()),
									innovationNumber, genome.getGenome().get(k).isActive()));
				for (int j = 0; j < hiddenNodes.size(); j++)
					if (genome.getGenome().get(k).getConnection().getNode1().getName()
							.equals(hiddenNodes.get(i).getName()))
						if (genome.getGenome().get(k).getConnection().getNode2().getName()
								.equals(hiddenNodes.get(j).getName()))
							this.genome.add(new Gene(
									new Connection(hiddenNodes.get(i), hiddenNodes.get(j),
											genome.getGenome().get(k).getConnection().getWeight()),
									innovationNumber, genome.getGenome().get(k).isActive()));
			}
		}

	}

	public ArrayList<Gene> getGenome() {
		return genome;
	}

	public int getInnovationNumber() {
		return innovationNumber;
	}

	public int getNumberOfInputs() {
		return numberOfInputs;
	}

	public int getNumberOfOutputs() {
		return numberOfOutputs;
	}

	public double getDefaultThreshold() {
		return defaultThreshold;
	}

	public ArrayList<Node> getInputNodes() {
		return inputNodes;
	}

	public ArrayList<Node> getOutputNodes() {
		return outputNodes;
	}

	public ArrayList<Node> getHiddenNodes() {
		return hiddenNodes;
	}

	public void setGenome(ArrayList<Gene> genome) {
		this.genome = genome;
	}

	public void setInnovationNumber(int innovationNumber) {
		this.innovationNumber = innovationNumber;
	}

	public void setNumberOfInputs(int numberOfInputs) {
		this.numberOfInputs = numberOfInputs;
	}

	public void setNumberOfOutputs(int numberOfOutputs) {
		this.numberOfOutputs = numberOfOutputs;
	}

	public void setDefaultThreshold(double defaultThreshold) {
		this.defaultThreshold = defaultThreshold;
	}

	public void setInputNodes(ArrayList<Node> inputNodes) {
		this.inputNodes = inputNodes;
	}

	public void setOutputNodes(ArrayList<Node> outputNodes) {
		this.outputNodes = outputNodes;
	}

	public void setHiddenNodes(ArrayList<Node> hiddenNodes) {
		this.hiddenNodes = hiddenNodes;
	}

	public String toString() {
		String str = "";
		for (int i = 0; i < genome.size(); i++)
			str = str + genome.get(i) + "\n";
		return str;
	}

	public void clearInputs() {
		// TODO Auto-generated method stub
		for (int i = 0; i < hiddenNodes.size(); i++)
			hiddenNodes.get(i).clearInput();
		for (int i = 0; i < outputNodes.size(); i++)
			outputNodes.get(i).clearInput();
	}
}
