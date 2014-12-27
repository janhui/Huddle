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
		
//		Main while loop
		while (graph != null) {
			System.out.println("Options :");
			System.out.println("-SR     : Shortest Route");
			System.out.println("-R      : Route");
			System.out.println("-D      : Shortest Distance");
			System.out.println("-SJM    : Maximum Number of Junction");
			System.out.println("-SJE    : Maximum Number of Junction");
			System.out.println("-SRD    : Shortest Route and Distance");
			System.out.println("-ND     : Number of Routes");
			//get the option
			String option = null;
			while (option == null) {
				try {
					option = reader.readLine();
				} catch (Exception e) {
				}

			}
			// query the user wants to check
			System.out.println("Query");
			String query = null;
			while (query == null) {
				try {
					query = reader.readLine();
				} catch (Exception e) {
				}
			}
			List<Tuple> finalTuples = satnav.findRoute(query);
			System.out.println(finalTuples.size());
			for(int i = 0; i< finalTuples.size(); i++) {
				System.out.println(finalTuples.get(i).toString());
			}
			if(finalTuples.isEmpty()) {
				System.out.println("No Such Route");
				
			} else if (option.equals("SR")) {
				Tuple shourtestRoute = satnav.shortestRoute(finalTuples);
				System.out.println(shourtestRoute.toString());
				
			} else if (option.equals("R")) {
				Tuple specificRoute = satnav.findSpecificRoute(query);
				System.out.println("Route    : "+specificRoute.toString());
				System.out.println("Distance : "+specificRoute.getCurrentTotal());
			} else if (option.equals("D")) {
				Tuple shortestRoute = satnav.shortestRoute(finalTuples);
				System.out.println(shortestRoute.getCurrentTotal());
//				TODO:
			} else if (option.equals("SJM")) {
				System.out.println(finalTuples.toString());
				
				//TODO:
			} else if (option.equals("SJE")) {
				System.out.println(finalTuples.toString());
				
				
			} else if (option.equals("SRD")) {
				Tuple shourtestRoute = satnav.shortestRoute(finalTuples);
				System.out.println(shourtestRoute.toString());
				System.out.println(shourtestRoute.getCurrentTotal());
//TODO:
			} else if (option.equals("ND")) {
				System.out.println(finalTuples.size());
			} else {
//				System.out.println(finalTuple.toString());
//				System.out.println(finalTuple.getCurrentTotal());
			}
		}
	}

}
