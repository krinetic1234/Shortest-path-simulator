package jrJava.shortestPath_1;

import java.util.List;

public class ShortestPathApp {

	public static void main(String[] args) throws Exception {
		
		Graph graph = new Graph();
		
		List<String> path = graph.searchShortestPath("Cupertino", "J7");
		System.out.println(path);
	}

}
