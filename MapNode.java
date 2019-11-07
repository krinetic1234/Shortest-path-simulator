package jrJava.shortestPath_1;

import java.util.ArrayList;

public class MapNode implements Comparable<MapNode> {

	private String label;
	private double x, y;
	private boolean visited;
	private MapNode previous;
	
	
	private double distanceToStart;
	private double totalEstimatedDistance; // distanceToStart + projected distance
	
	
	private ArrayList<Edge> edges;
	private ArrayList<MapNode> neighbors;
	
	
	public MapNode(String label, double x, double y){
		this.label = label;
		this.x = x;
		this.y = y;
		
		edges = new ArrayList<Edge>();
		neighbors = new ArrayList<MapNode>();
	}
	
	
	public ArrayList<Edge> getEdges() {
		return edges;
	}


	public ArrayList<MapNode> getNeighbors() {
		return neighbors;
	}


	public String getLabel() {
		return label;
	}


	public double getX() {
		return x;
	}


	public double getY() {
		return y;
	}


	public void addEdge(Edge edge){
		edges.add(edge);
	}
	
	public void addNeighbor(MapNode neighbor){
		neighbors.add(neighbor);
	}
	
	
	public boolean visited() { return visited; }
	public void setVisited() { visited = true; }
	
	public MapNode getPrevious(){ return previous; }
	public void setPrevious(MapNode previous) { this.previous = previous; }
	
	
	public double getDistanceToStart() {
		return distanceToStart;
	}
	
	public void setDistanceToStart(double distanceToStart) { 
		this.distanceToStart = distanceToStart;
	}
	
	public void setTotalEstimatedDistance(double totalEstimatedDistance){
		this.totalEstimatedDistance = totalEstimatedDistance;
	}
	
	public double getDistanceTo(MapNode mapNode){
		for(int i=0; i<edges.size(); i++){
			if(edges.get(i).getEnd().equals(mapNode.label)) return edges.get(i).getDistance();
		}
		//lmao
		return Double.POSITIVE_INFINITY;
	}
	
	
	public double calculateDistanceTo(MapNode mapNode){
		return Math.sqrt((x-mapNode.x)*(x-mapNode.x) + (y-mapNode.y)*(y-mapNode.y));
	}
	
	
	public int compareTo(MapNode mapNode){
		if(totalEstimatedDistance<mapNode.totalEstimatedDistance) return -1;
		else if(totalEstimatedDistance>mapNode.totalEstimatedDistance) return 1;
		else return 0;
	}
	
	
	
	public int hashCode(){  
		return label.hashCode();
	}
	
	public boolean equals(Object o){
		MapNode other = (MapNode) o;
		return label.equals(other.label);
	}
	
	
	public String toString(){
		return label;
	}
}





