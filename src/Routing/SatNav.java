package Routing;
import java.util.ArrayList;
import java.util.Deque;
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
