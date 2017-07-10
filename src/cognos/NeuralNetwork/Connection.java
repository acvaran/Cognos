package cognos.NeuralNetwork;

public class Connection {

	private Node node1, node2;
	private double weight;

	public Connection(Connection connection) {
		cloneConnection(connection);
	}

	public Connection(Node node1, Node node2, double weight) {
		this.node1 = node1;
		this.node2 = node2;
		this.weight = weight;
	}

	public void cloneConnection(Connection connection) {
		this.node1 = new Node(connection.getNode1());
		this.node2 = new Node(connection.getNode2());
		this.weight = connection.getWeight();
	}

	public Node getNode1() {
		return node1;
	}

	public Node getNode2() {
		return node2;
	}

	public double getWeight() {
		return weight;
	}

	public void setNode1(Node node1) {
		this.node1 = node1;
	}

	public void setNode2(Node node2) {
		this.node2 = node2;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public String toString() {
		return node1.getName() + " | " + node2.getName() + " | " + weight;
	}
}
