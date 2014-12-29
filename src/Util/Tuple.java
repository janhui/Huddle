package Util;

import java.util.List;

import Routing.Node;
/*
 * Tuple keeps track of the route ...
 * the nodes this route has visited
 * the destination 
 * the distance this route has covered.
 */
public class Tuple implements Comparable<Tuple>{
	private char destination;
	private int currentTotal;
	private List<Node> visitedRoutes;
	
	public Tuple(char desitnation, int currentTotal, List<Node> visitedRoutes) {
		this.destination = desitnation;
		this.currentTotal = currentTotal;
		this.visitedRoutes = visitedRoutes;
	}

	public char getDestination() {
		return destination;
	}
	public int getCurrentTotal() {
		return currentTotal;
	}
	public void setDestination(char destination) {
		this.destination =  destination;
	}
	public void setCurrentTotal(int total) {
		this.currentTotal =  total;
	}

	public List<Node> getVisitedRoutes() {
		return visitedRoutes;
	}

	public void addVisitedRoutes(Node route) {
		destination = route.getDestination();
		visitedRoutes.add(route);
	}	

	@Override
	public int compareTo(Tuple o) {
		if(o.getCurrentTotal() == this.getCurrentTotal()){
			return 0;
		} else if (this.getCurrentTotal()<o.getCurrentTotal()) {
			return -1;
		} else {
			return 1;
		}
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i <visitedRoutes.size(); i++) {
			if(i == 0) {
				builder.append(visitedRoutes.get(i).getSource());
			}
			builder.append(visitedRoutes.get(i).getDestination());
		}
		return builder.toString();
	}
	
	

}
