package jrJava.shortestPath_1;


import java.awt.Graphics;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Graph {

	private MapNodeManager mapNodeManager;
	private GraphicsBoard board;
	private Graphics g;
	
	
	public Graph(){
		board = new GraphicsBoard(800, 800);
		g = board.getCanvas();

		mapNodeManager = new MapNodeManager();
		try {
			mapNodeManager.parseMapNodes("jrJava/shortestPath_1/mapNodes.txt");
			mapNodeManager.parseEdges("jrJava/shortestPath_1/edges.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		mapNodeManager.drawEdges(g);
		mapNodeManager.drawMapNodes(g);
		board.repaint();
	}


	//coulda used a stack maybe
	public List<String> searchShortestPath(String start, String end){
		MapNode startNode = mapNodeManager.get(start);
		MapNode endNode = mapNodeManager.get(end);

		mapNodeManager.setInfinity();
		LinkedList list = new LinkedList();
		
		startNode.setDistanceToStart(0); 
		list.insert(startNode); 
		
		MapNode current = null;
		
		while(!list.isEmpty()){
			current = list.removeFirst();
			
			if(current.visited()) continue;
			current.setVisited();
			
			//don't really need this statement but ui is better...?
			mapNodeManager.markMapNode(g, current);
			if(current!=startNode) mapNodeManager.markEdge(g, current.getPrevious(), current);
			board.repaint(); 
			
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) { }
			
			// if current is endNode, break out. (we found the path).
			if(current==endNode){
				mapNodeManager.showPath(g, startNode, current);
				board.repaint();
				return reconstructPath(startNode, current);
			}
			
			// we evaluate all of current's neighbor cities. 
			ArrayList<MapNode> neighbors = current.getNeighbors();
			for(int i=0; i<neighbors.size(); i++){
				
				MapNode neighbor = neighbors.get(i);
				if(neighbor.visited()) continue;
				
				double neighborDist = current.getDistanceTo(neighbor);
				double neighborDistanceToStart = current.getDistanceToStart() + neighborDist;
				
				if(neighborDistanceToStart<neighbor.getDistanceToStart()) {
					neighbor.setDistanceToStart(neighborDistanceToStart);
					double projectedDistance = neighbor.calculateDistanceTo(endNode);
					double totalEstimatedDistance = neighborDistanceToStart + projectedDistance;
					//neighbor.setTotalEstimatedDistance(totalEstimatedDistance); // A-star
					//neighbor.setTotalEstimatedDistance(neighborDistanceToStart); // Dijkstra
					neighbor.setTotalEstimatedDistance(projectedDistance); // Greedy-Best first
					
					neighbor.setPrevious(current);
					list.insert(neighbor);  
				}
			}
		}
		
		return null;
	}

	
	private List<String> reconstructPath(MapNode start, MapNode end){
		List<String> list = new ArrayList<String>();
		
		MapNode c = end;
		while(c!=start){
			list.add(0, c.getLabel());
			c = c.getPrevious();
		}
		list.add(0, start.getLabel());
		
		return list;
	}
	
	
	//own implementation - modified for map node
	private class LinkedList {

		private Link first;

		public boolean isEmpty(){
			return first==null;
		}

		public void insert(MapNode mapNode){
			Link toInsert = new Link(mapNode);

			Link current = first;
			Link previous = null;
			while(current!=null && current.mapNode.compareTo(mapNode)<0) {
				previous = current;
				current = current.next;
			}

			if(previous==null){
				if(first!=null) toInsert.next = current;
				first = toInsert;
			}
			else {
				previous.next = toInsert;
				toInsert.next = current;
			}
		}


		public MapNode removeFirst(){
			Link temp = first;
			if(first!=null) first = first.next;
			return temp.mapNode;
		}


		private class Link {
			private Link next;
			private MapNode mapNode;
			public Link(MapNode mapNode){ this.mapNode = mapNode; }
			public String toString(){ return mapNode.toString(); }
		}
	}


}










