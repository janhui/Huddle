package Routing;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedDeque;

import Util.Tuple;

public class SatNav {
	private Set<Node> graph;

	public void addGraph(Set<Node> graph) {
		this.graph = graph;
	}

	public List<Tuple> findRoute(String query) {
		char source = query.charAt(0);
		char destination = query.charAt(1);
		Deque<Tuple> intermediates = getSourceSet(source);
		List<Tuple> finalTuples = new ArrayList<Tuple>();
		while (!intermediates.isEmpty()) {
			Tuple tuple = intermediates.pop();
			if (tuple.getX() == destination) {
				 finalTuples.add(tuple);
			} else {
				Deque<Tuple> temp = getSourceSet(tuple.getX(), tuple);
				for (Tuple tempTuple : temp) {
					intermediates.addLast(tempTuple);
				}
			}
		}
		return finalTuples;

	}
	public Tuple findSpecificRoute(String query) {
		List<Node> finalRoute = new LinkedList<Node>();
		String[] routeJunctions = query.split("-");
		int totalDistance = 0;
		for(int i = 0, j = 1; i< routeJunctions.length && j < routeJunctions.length ; i++, j++) {
			StringBuilder queryBuilder = new StringBuilder();
			queryBuilder.append(routeJunctions[i]);
			queryBuilder.append(routeJunctions[j]);
			List<Tuple> routes = findRoute(queryBuilder.toString());
			for(int k = 0; k < routes.size(); k++) {
				Tuple route = routes.get(k);
				if (route.getVisitedRoutes().size() == 1) {
					finalRoute.addAll(route.getVisitedRoutes());
					totalDistance += route.getCurrentTotal();
					break;
				}
			}
		}
		Tuple tuple = new Tuple(' ', totalDistance, finalRoute);
		return tuple;
	}
	
	public Tuple shortestRoute(List<Tuple> finalTuples) {
		int min_dist = Integer.MAX_VALUE;
		Tuple tuple = null;
		for(int i = 0; i < finalTuples.size(); i++) {
			int temp = finalTuples.get(i).getCurrentTotal();
			if(temp < min_dist) {
				min_dist = temp;
				tuple = finalTuples.get(i);
			}
		}
		return tuple;
	}

	private Deque<Tuple> getSourceSet(char x, Tuple tuple) {
		Deque<Tuple> sourceSet = new ConcurrentLinkedDeque<Tuple>();
		for (Node node : graph) {
			if (node.getSource() == x && !tuple.getVisitedRoutes().contains(node)) {
				tuple.addVisitedRoutes(node);
				sourceSet.add(new Tuple(node.getDestination(), 
							  tuple.getCurrentTotal() + node.getDistance(), tuple.getVisitedRoutes()));
			}
		}
		return sourceSet;
	}

	private Deque<Tuple> getSourceSet(char source) {
		Deque<Tuple> sourceSet = new ConcurrentLinkedDeque<Tuple>();
		for (Node node : graph) {
			if (node.getSource() == source) {
				List<Node> route = new ArrayList<Node>();
				route.add(node);
				sourceSet.add(new Tuple(node.getDestination(), node
						.getDistance(), route));
			}
		}
		return sourceSet;
	}





}
