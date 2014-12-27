package Util;

import java.util.List;

import Routing.Node;

public class Tuple implements Comparable<Tuple>{
	private char x;
	private int currentTotal;
	List<Node> visitedRoutes;
	
	public Tuple(char x, int currentTotal, List<Node> visitedRoutes) {
		this.x = x;
		this.currentTotal = currentTotal;
		this.visitedRoutes = visitedRoutes;
	}
	
	public char getX() {
		return x;
	}
	public int getCurrentTotal() {
		return currentTotal;
	}
	public void setX(char x) {
		this.x =  x;
	}
	public void setCurrentTotal(int y) {
		this.currentTotal =  y;
	}

	public List<Node> getVisitedRoutes() {
		return visitedRoutes;
	}

	public void setVisitedRoutes(List<Node> visitedRoutes) {
		this.visitedRoutes = visitedRoutes;
	}
	public void addVisitedRoutes(Node route) {
		this.visitedRoutes.add(route);
	}	
	public void addVisitedRoutes(List<Node> routes) {
		this.visitedRoutes.addAll(routes);
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
