package jrJava.shortestPath_1;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class MapNodeManager {

	private int size = 30;
	private HashSet<MapNode> set = new HashSet<MapNode>(size);
	
	
	public void parseMapNodes(String fileName) throws FileNotFoundException {
		Scanner s = new Scanner(new File(fileName));
		while(s.hasNext()){
			set.add(new MapNode(s.next(), s.nextDouble(), s.nextDouble()));
		}
	}
	
	
	public MapNode get(String label){
		return set.get(new MapNode(label, 0, 0));
	}
	
	
	public void parseEdges(String fileName) throws FileNotFoundException {
		Scanner s = new Scanner(new File(fileName));
		
		String start, road, compass, end;
		double dist;
		
		while(s.hasNext()){
			start = s.next();
			road = s.next();
			dist = s.nextDouble();
			compass = s.next();
			end = s.next();
			
			get(start).addEdge(new Edge(start, end, road, dist, compass));
			get(start).addNeighbor(get(end));
			
			
			//you need the string literal otherwise it can't concatenate
			String compassReverse = "" + compass.charAt(1) + compass.charAt(0);
			get(end).addEdge(new Edge(end, start, road, dist, compassReverse));
			get(end).addNeighbor(get(start));	
		}
	}
	
	
	public void drawMapNodes(Graphics g){
		g.setColor(Color.BLACK);
		g.setFont(new Font("Courier", Font.BOLD, 10));
		int r = 5;
		
		Iterator<MapNode> iter = set.iterator();
		
		//saves memory to declare instance variables outside while loop bc iterator will initialize a new space each time
		MapNode node;
		int x, y;
		String label;
		while(iter.hasNext()){
			node = iter.next();
			label = node.getLabel();
			x = (int) node.getX();
			y = (int) node.getY();
			g.fillOval(x-r, y-r, 2*r, 2*r);
			g.drawString(label, x-10, y-10);
		}
		
	}
	
	
	public void drawEdges(Graphics g){
		g.setFont(new Font("Courier", Font.BOLD, 9));
		
		int i, x1, y1, x2, y2;
		double dist;
		MapNode node;
		ArrayList<Edge> list;
		Iterator<MapNode> iter = set.iterator();
		while(iter.hasNext()){
			node = iter.next();
			node.getEdges();
			list = node.getEdges();
			
			for(i=0; i<list.size(); i++){
				x1 = (int) get(list.get(i).getStart()).getX();
				y1 = (int) get(list.get(i).getStart()).getY();
				x2 = (int) get(list.get(i).getEnd()).getX();
				y2 = (int) get(list.get(i).getEnd()).getY();
				dist = list.get(i).getDistance();
				
				g.setColor(Color.ORANGE);
				g.drawLine(x1, y1, x2, y2);
				g.setColor(Color.GRAY); 
				g.drawString("" + dist, (x1+x2)/2, (y1+y2)/2);
			}
		}
		
	}
	
	public void setInfinity(){
		Iterator<MapNode> iter = set.iterator();
		while(iter.hasNext()){
			iter.next().setDistanceToStart(Double.POSITIVE_INFINITY); 
		}
	}
	
	
	// just to make things more clear and nicer ui
	public void markMapNode(Graphics g, MapNode mapNode){
		g.setColor(Color.RED);
		g.drawOval((int)mapNode.getX()-10, (int)mapNode.getY()-10, 20, 20);
	}
	
	public void markEdge(Graphics g, MapNode node1, MapNode node2){
		g.setColor(Color.BLUE);
		((Graphics2D)g).setStroke(new BasicStroke(3.5f));
		g.drawLine((int)node1.getX(), (int)node1.getY(), (int)node2.getX(), (int)node2.getY());
	}
	
	
	public void showPath(Graphics g, MapNode start, MapNode end){
		g.setColor(Color.RED);
		((Graphics2D)g).setStroke(new BasicStroke(5.0f));
		
		MapNode c = end;
		MapNode p;
		while(c!=start){
			p = c.getPrevious();
			g.drawLine((int)p.getX(), (int)p.getY(), (int)c.getX(), (int)c.getY());
			c = p;
		}
		
	}
} 
 










