/*
 * Martin Deutsch
 * 11/26/15
 * Graph.java
 */
 
import java.util.*;
 
/*
 * Represents a graph of Vertex objects
 */
public class Graph {
	
	// list of vertices in the graph
	private ArrayList<Vertex> vertices;
	
	// constructor initializes the list of vertices
	public Graph() {
		vertices = new ArrayList<Vertex>();
	}
	
	// returns the number of vertices in the graph
	public int vertexCount() {
		return vertices.size();
	}
	
	// adds the given vertices to the graph, if necessary,
	// and adds edges between them
	public void addEdge(Vertex v1, Vertex.Direction dir, Vertex v2) {
		if (!this.vertices.contains(v1)) {
			vertices.add(v1);
		}
		if (!this.vertices.contains(v2)) {
			vertices.add(v2);
		}
		v1.connect(v2, dir);
		v2.connect(v1, v2.opposite(dir));
	}
	
	// sets the cost of each vertex in the graph
	// to the shortest path between it and v0
	public void shortestPath(Vertex v0) {
		for (Vertex v : this.vertices) {
			v.setCost(Integer.MAX_VALUE);
			v.setMarked(false);
		}
		PriorityQueue<Vertex> q = new PriorityQueue<Vertex>();
		v0.setCost(0);
		q.add(v0);
		while(q.size() != 0) {
			Vertex v = q.poll();
			v.setMarked(true);
			for (Vertex w : v.getNeighbors()) {
				if (!w.isMarked() && v.getCost()+1 < w.getCost()) {
					w.setCost(v.getCost()+1);
					q.remove(w);
					q.add(w);
				}
			}
		}
	}
	
	// returns an arraylist of the vertices
	public ArrayList<Vertex> getVertices() {
		return this.vertices;
	}
	
	// unit test
	public static void main(String[] args) {
		Graph g = new Graph();
		Vertex v1 = new Vertex(0, 0);
		Vertex v2 = new Vertex(0, 1);
		Vertex v3 = new Vertex(0, 2);
		Vertex v4 = new Vertex(1, 0);
		g.addEdge(v1, Vertex.Direction.NORTH, v2);
		System.out.println(v2.getNeighbor(Vertex.Direction.SOUTH));
		
		g.addEdge(v2, Vertex.Direction.NORTH, v3);
		g.addEdge(v1, Vertex.Direction.EAST, v4);
		System.out.println(g.vertexCount());
		g.shortestPath(v1);
		System.out.println(v2);
		System.out.println(v3);
		System.out.println(v4);
		g.shortestPath(v2);
		System.out.println(v1);
		System.out.println(v3);
	}
}