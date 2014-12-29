package Routing;

public class Node {

	/*
	 * Assumption: Input is of size three in the format CharCharInt
	 * 
	 * Nodes of the graph contains source , destination and distance
	 */
	private char source;
	private char destination;
	private int distance;

	public Node(String info) throws NumberFormatException {
		source = info.charAt(0);
		destination = info.charAt(1);
		String distanceString = info.substring(2);
		distance = Integer.parseInt(distanceString);
	}

	public Node(char source, char destination, int distance) {
		this.source = source;
		this.destination = destination;
		this.distance = distance;
	}

	public char getSource() {
		return source;
	}

	public void setSource(char source) {
		this.source = source;
	}

	public char getDestination() {
		return destination;
	}

	public void setDestination(char destination) {
		this.destination = destination;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

}
