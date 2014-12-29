package Routing;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import Util.Tuple;

public class SatNav {
	private Set<Node> graph;
	
	
	public void addGraph(Set<Node> graph) {
		this.graph = graph;
	}
	
	
	/*find route for a query
	 * params:
	 * query = StartDestination
	 * maxJunc = the maximum number of junctions constraint
	 * equal = to see if the query is equal the number of junction or upto the maxjunc
	 * 
	 * returns the list of tuples where tuples are individual routes
	*/
	public int findRouteSpecificDistance(String query, int maxJunc, boolean equal) {
		int count = 0;
		List<Tuple> routes = findRoute(query,50);
		for (int i = 0; i < routes.size(); i++ ) {
		Tuple tuple = routes.get(i);
		// gets the visited nodes of the route 
		if(equal) {
			if(tuple.getVisitedRoutes().size() == maxJunc ) {
				count++;
			}
		} else {
			if(tuple.getVisitedRoutes().size() <= maxJunc ) {
				count++;
			}
		}
	}
		return count;
	}
	
	
	
	/*find route for a query
	 * params:
	 * query = StartDestination
	 * MaxDistace  = the maximum distance the route can have
	 * returns the list of tuples where tuples are individual routes
	*/
	public List<Tuple> findRouteMaxDistance(String query, int maxDistance) {
		List<Tuple> routes = findRoute(query, maxDistance);
		List<Tuple> filteredRoutes = new ArrayList<Tuple>();
		for (int i = 0; i < routes.size() ; i++) {
			if(routes.get(i).getCurrentTotal() <= maxDistance) {
				filteredRoutes.add(routes.get(i));
			}
		}
		return filteredRoutes;
	}
	

	/*find route for a query
	 * params:
	 * query = Start-Junction-Junction-...-Destination
	 * 
	 * returns the tuples where tuple is the specific route if it exist else null 
	*/
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
					x = route.getDestination();
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
	
	/*find shortes route for a query
	 * params:
	 * finalTuples = list of all the routes
	 * 
	 * returns the shortest route tuple
	*/
	public Tuple shortestRoute(String query) {
		List<Tuple> finalTuples = findRoute(query);
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
	
	
	/*find route for a query
	 * params:
	 * query = StartDestination
	 * 
	 * returns the list of tuples where tuples are individual routes
	*/
	private List<Tuple> findRoute(String query) {
		char source = query.charAt(0);
		char destination = query.charAt(1);
		//queue of the breadth first search down the graph
		Deque<Tuple> intermediates = getSourceSet(source);
		List<Tuple> finalTuples = new ArrayList<Tuple>();
		// pops the front of the queue
		while (!intermediates.isEmpty()) {
			Tuple tuple = intermediates.pop();
			if (tuple.getDestination() == destination) {
				//add to the list of tuples to return
				 finalTuples.add(tuple);
			} else {
				Deque<Tuple> temp = getSourceSet(tuple.getDestination(), tuple);
				for (Tuple tempTuple : temp) {
					//add to the end of the queue
					intermediates.addLast(tempTuple);
				}
			}
		}
		return finalTuples;
	
	}
	
//	find all the route upto a maximum distance and return the list
	private List<Tuple> findRoute(String query, int maxDistance) {
		char source = query.charAt(0);
		char destination = query.charAt(1);
		Deque<Tuple> intermediates = getSourceSet(source);
		List<Tuple> finalTuples = new ArrayList<Tuple>();
		while (!intermediates.isEmpty()) {
			Tuple tuple = intermediates.pop();
			if (tuple.getDestination() == destination) {
				finalTuples.add(tuple);
			}
			Deque<Tuple> temp = getSourceSet(tuple.getDestination(), tuple,
					maxDistance);
			for (Tuple tempTuple : temp) {
				intermediates.addLast(tempTuple);

			}
		}
		return finalTuples;
	
	}
	
	
	/*getSourceSet: finds all the route where the junction is the start point
	 * params:
	 * source = junction
	 * 
	 * returns the list of tuples
	*/
	private Deque<Tuple> getSourceSet(char source) {
		Deque<Tuple> sourceSet = new ArrayDeque<Tuple>();
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
	/*getSourceSet: finds all the route where the junction is the start point and
	 *  add a new junction in if it hasnt been added in alread
	 *  
	 * params:
	 * source = junction
	 * tuple = the current route
	 * 
	 * returns the new list of tuples 
	*/
	private Deque<Tuple> getSourceSet(char destination, Tuple tuple)  {
		Deque<Tuple> sourceSet = new ArrayDeque<Tuple>();
		for (Node node : graph) {
			if (node.getSource() == destination && !tuple.getVisitedRoutes().contains(node)) {
				int totalDistance = tuple.getCurrentTotal() + node.getDistance();
				List<Node> tempList = new ArrayList<Node>();
				tempList.addAll(tuple.getVisitedRoutes());
				Tuple newTuple = new Tuple(node.getDestination(), totalDistance, tempList);
				newTuple.addVisitedRoutes(node);
				sourceSet.add(newTuple);
			}
		}
		return sourceSet;
	}
	
//	add the next node on the route to the tuple if the total distance < max distance
	private Deque<Tuple> getSourceSet(char destination, Tuple tuple, int maxDistance) {
		Deque<Tuple> sourceSet = new ArrayDeque<Tuple>();
		for (Node node : graph) {
			if (node.getSource() == destination ) {
				int totalDistance = tuple.getCurrentTotal() + node.getDistance();
				List<Node> tempList = new ArrayList<Node>();
				tempList.addAll(tuple.getVisitedRoutes());
				Tuple newTuple = new Tuple(node.getDestination(), totalDistance, tempList);
				newTuple.addVisitedRoutes(node);
				if(totalDistance < maxDistance) {
					sourceSet.add(newTuple);
				}
			}
		}
		return sourceSet;
	}
	



}
