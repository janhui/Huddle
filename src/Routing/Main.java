package Routing;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import Util.Tuple;

public class Main {
	

	public static void main(String[] args) {
		//graph is a set of nodes
		Set<Node> graph = new HashSet<Node>();
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				System.in));
		
		// make a satnav
		SatNav satnav = new SatNav();
		System.out.println("Add graph");
		String graphString = new String();
		try {
			graphString = reader.readLine();
		} catch (IOException e) {
		}
		// split the query string into nodes
		String[] nodeString = graphString.split(", ");
		System.out.println();
		// add the nodes to graph 
		for (int i = 0; i < nodeString.length; i++) {
			String node = nodeString[i];
			assert (node.length() == 3) : "There is some error in the way the graph was "
					+ "inputed please try again with \", \" between "
					+ "every junction without the quotes";
			graph.add(new Node(node));

		}
		//add graph to SatNav
		satnav.addGraph(graph);
		//quite string for while loop
		String quit = null;
//		Main while loop
		do {
			quit = null;
			System.out.println("Options :");
			System.out.println("-R      : Route");
			System.out.println("-D      : Shortest Distance");
			System.out.println("-SR     : Shortest Route");
			System.out.println("-SRD    : Shortest Route and Distance");
			System.out.println("-SJM    : Maximum Number of Junction");
			System.out.println("-SJE    : Exact Number of Junction");
			System.out.println("-NR     : Number of Routes");
			//get the option
			String option = null;
			while (option == null) {
				try {		
					System.out.println("Select your option");
					option = reader.readLine();
					// to stop wrong option inputs
					if(!(option.equals("SR") || option.equals("R") || option.equals("D") 
							|| option.equals("SJM") || option.equals("SJE") || option.equals("SRD") 
							|| option.equals("NR"))) {
						option = null;
					}
				} catch (Exception e) {}

			}
			// query the user wants to check
			System.out.println("Query?");
			String query = null;
			while (query == null) {
				try {
					query = reader.readLine();
				} catch (Exception e) {
				}
			}
			if (option.equals("R")) {
				Tuple specificRoute = satnav.findSpecificRoute(query);
				if (specificRoute == null) {
					System.out.println("No Such Route");
				} else {
					System.out.println("Route    : "+specificRoute.toString());
					System.out.println("Distance : "+specificRoute.getCurrentTotal());
				}
				
			} else if (option.equals("D")) {
				Tuple shortestRoute = satnav.shortestRoute(query);
				if(shortestRoute == null) {
					System.out.println("No Such Route");
				} else {
					System.out.println(shortestRoute.getCurrentTotal());
				}
				
			} else if (option.equals("SR")) {
				Tuple shortestRoute = satnav.shortestRoute(query);
				if(shortestRoute == null) {
					System.out.println("No Such Route");
				} else {
					System.out.println(shortestRoute.toString());
				}
				
			} else if (option.equals("SRD")) {
				Tuple shortestRoute = satnav.shortestRoute(query);
				if(shortestRoute == null) {
					System.out.println("No Such Route");
				} else {
					System.out.println(shortestRoute.toString());
					System.out.println(shortestRoute.getCurrentTotal());
				}
				
			} else if (option.equals("SJM")) {
				String[] sjmQueries = query.split(" ");
				if(sjmQueries.length != 2) {
					System.out.println("Wrong number of arguments"); 
				} else  {
					query = sjmQueries[0];
					int maxJunc = Integer.parseInt(sjmQueries[1]);
					int count = satnav.findRouteSpecificDistance(query, maxJunc, false);
					System.out.println(count);
				}
				
			} else if (option.equals("SJE")) {
				String[] sjeQueries = query.split(" ");
				if(sjeQueries.length != 2) {
					System.out.println("Wrong number of arguments"); 
				} else  {
					query = sjeQueries[0];
					int maxJunc = Integer.parseInt(sjeQueries[1]);
					int count = satnav.findRouteSpecificDistance(query, maxJunc, true);
					System.out.println(count);
				}	
			} else if (option.equals("NR")) {
				String[] queryStrings = query.split(" ");
				if(queryStrings.length != 2) {
					System.out.println("Wrong number of arguments"); 
				} else  {
					List<Tuple> routes = satnav.findRouteMaxDistance(queryStrings[0], Integer.parseInt(queryStrings[1]));
					for (Tuple tuple : routes) {
						System.out.println(tuple.toString());
					}
					System.out.println(routes.size());
				}
			}
			
			System.out.println("\n\n");
			System.out.println("press q to quit or any other button to do another query");
			while (quit == null) {
				try {
					quit = reader.readLine();
				} catch (Exception e) {
				}

			}
		} while (!quit.equals("q"));
	}

}
