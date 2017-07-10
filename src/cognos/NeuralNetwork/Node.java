package cognos.NeuralNetwork;

public class Node {

	private int type;
	private double threshold, input;
	private String name;
	private int state;

	public Node(Node node) {
		cloneNode(node);
	}

	public Node(int type) {
		this.type = type;
	}

	public Node(int type, double threshold) {
		this.type = type;
		this.threshold = threshold;
		state = 2;
		input = 0.0;
	}

	public void cloneNode(Node node) {
		this.type = node.getType();
		this.threshold = node.getThreshold();
		this.input = node.getInput();
		this.name = node.getName();
		this.state = node.getState();
	}

	public void mutateNode() {
		threshold = (Math.random() * 2) - 1;
	}

	public int findState() {
		if (this.type != 0) {
			if (input > threshold)
				state = 1;
			else
				state = 0;
		}
		return state;
	}

	public void calculateInput(double in) {
		input = input + in;
	}

	public void clearInput() {
		input = 0.0;
	}

	public int getType() {
		return type;
	}

	public double getThreshold() {
		return threshold;
	}

	public double getInput() {
		return input;
	}

	public String getName() {
		return name;
	}

	public int getState() {
		return state;
	}

	public void setType(int type) {
		this.type = type;
	}

	public void setThreshold(double threshold) {
		this.threshold = threshold;
	}

	public void setInput(double input) {
		this.input = input;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String toString() {
		return name + " " + type + " " + state;
	}
}
