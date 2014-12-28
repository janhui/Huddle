package Routing;
import java.util.ArrayDeque;
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
// find route for a query
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
	
//	find route within specific distance
	public List<Tuple> findRoute(String query, int maxDistance) {
		List<Tuple> routes = findRoute(query);
		List<Tuple> filteredRoutes = new ArrayList<Tuple>();
		for (int i = 0; i < routes.size() ; i++) {
			if(routes.get(i).getCurrentTotal() <= maxDistance) {
				filteredRoutes.add(routes.get(i));
			}
		}
		return filteredRoutes;
	}
	
	// find the distance if a the query route exist
	public Tuple findSpecificRoute(String query) {
		List<Node> finalRoute = new LinkedList<Node>();
		String[] routeJunctions = query.split("-");
		int totalDistance = 0;
		char x = ' ';
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
					x = route.getX();
					break;
				}
			}
		}
		if (x != routeJunctions[routeJunctions.length-1].charAt(0)) {
			return null;
		} else {
			Tuple tuple = new Tuple(x, totalDistance, finalRoute);
			return tuple;
		}
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

	private Deque<Tuple> getSourceSet(char source) {
		Deque<Tuple> sourceSet = new ConcurrentLinkedDeque<Tuple>();
		for (Node node : graph) {
			if (node.getSource() == source) {
				List<Node> route = new ArrayList<Node>();
				route.add(node);
				Tuple tuple = new Tuple(node.getDestination(), node
						.getDistance(), route);
				sourceSet.add(tuple);
			}
		}
		return sourceSet;
	}
	
	private Deque<Tuple> getSourceSet(char x, Tuple tuple) {
		Deque<Tuple> sourceSet = new ArrayDeque<Tuple>();
		for (Node node : graph) {
			if (node.getSource() == x && !tuple.getVisitedRoutes().contains(node)) {
				int totalDistance = tuple.getCurrentTotal() + node.getDistance();
				Tuple newTuple = new Tuple(node.getDestination(), totalDistance, tuple.getVisitedRoutes());
				newTuple.addVisitedRoutes(node);
				sourceSet.add(newTuple);
			}
		}
		return sourceSet;
	}

}
