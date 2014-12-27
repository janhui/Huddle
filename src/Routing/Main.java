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
		Set<Node> graph = new HashSet<Node>();
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				System.in));
		SatNav satnav = new SatNav();
		System.out.println("Add graph");
		String graphString = new String();
		try {
			graphString = reader.readLine();
		} catch (IOException e) {
		}
		String[] nodeString = graphString.split(", ");
		System.out.println();
		for (int i = 0; i < nodeString.length; i++) {
			String node = nodeString[i];
			assert (node.length() == 3) : "There is some error in the way the graph was "
					+ "inputed please try again with \", \" between "
					+ "every junction without the quotes";
			graph.add(new Node(node));

		}
		satnav.addGraph(graph);
		while (graph != null) {
			System.out.println("Options :");
			System.out.println("-SR     : Shortest Route");
			System.out.println("-R      : Route");
			System.out.println("-D      : Shortest Distance");
			System.out.println("-SJ     : Specific Number of Junction");
			System.out.println("-SRD    : Shorted Route and Distance");
			System.out.println("-ND     : Number of Routes");
			String option = null;
			while (option == null) {
				try {
					option = reader.readLine();
				} catch (Exception e) {
				}

			}
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
				int min_dist = Integer.MAX_VALUE;
				Tuple tuple = null;
				for(int i = 0; i < finalTuples.size(); i++) {
					int temp = finalTuples.get(i).getCurrentTotal();
					if(temp < min_dist) {
						min_dist = temp;
						tuple = finalTuples.get(i);
					}
				}
				System.out.println(tuple.toString());
				
			} else if (option.equals("R")) {
				System.out.println(finalTuples.toString());
				
			} else if (option.equals("D")) {
				int min_dist = Integer.MAX_VALUE;
				for(int i = 0; i < finalTuples.size(); i++) {
					int temp = finalTuples.get(i).getCurrentTotal();
					if(temp < min_dist) {
						min_dist = temp;
					}
				}
				System.out.println(min_dist);
			} else if (option.equals("SJ")) {
				System.out.println(finalTuples.toString());
			} else if (option.equals("SRD")) {
				System.out.println(finalTuples.toString());
//				todo:
			} else if (option.equals("ND")) {
				
			} else {
//				System.out.println(finalTuple.toString());
//				System.out.println(finalTuple.getCurrentTotal());
			}
		}
	}

}
