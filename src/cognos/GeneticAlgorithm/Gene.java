package cognos.GeneticAlgorithm;

import cognos.NeuralNetwork.Connection;

public class Gene {

	private Connection connection;
	private int innovationNumber;
	private boolean active;

	public Gene(Gene gene) {
		cloneGene(gene);
	}

	public Gene(Connection connection, int innovationNumber, boolean active) {
		this.connection = connection;
		this.innovationNumber = innovationNumber;
		this.active = active;
	}

	public void cloneGene(Gene gene) {
		this.connection = new Connection(gene.getConnection());
		this.innovationNumber = gene.getInnovationNumber();
		this.active = gene.isActive();
	}

	public boolean isActive() {
		return active;
	}

	public Connection getConnection() {
		return connection;
	}

	public int getInnovationNumber() {
		return innovationNumber;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public void setInnovationNumber(int innovationNumber) {
		this.innovationNumber = innovationNumber;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String toString() {
		return connection + " | " + innovationNumber + " | " + active;
	}
}
