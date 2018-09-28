/*
 * Martin Deutsch
 * 11/26/15
 * Vertex.java
 */
 
import java.util.*;
import java.awt.*;
 
/*
 * Represents a single room in the cave
 */
public class Vertex extends Cell implements Comparable<Vertex> {
	
	// the possible directions for connections between vertices
	public enum Direction { NORTH, EAST, SOUTH, WEST };
	
	// the edges which connect this vertex with other vertices
	private HashMap<Direction, Vertex> edges;
	// the cost of this vertex
	private int cost;
	// whether this vertex is marked
	private boolean marked;
	// whether this room is visible
	private boolean visible;
	
	// constructor initializes empty set of edges and infinite cost
	public Vertex(double x0, double y0) {
		super(x0, y0);
		this.edges = new HashMap<Direction, Vertex>();
		this.cost = Integer.MAX_VALUE;
		this.marked = false;
		this.visible = false;
	}
	
	// returns the opposite compass direction to the given direction
	public static Direction opposite(Direction d) {
		if (d.equals(Direction.NORTH)) {
			return Direction.SOUTH;
		}
		else if (d.equals(Direction.EAST)) {
			return Direction.WEST;
		}
		else if (d.equals(Direction.SOUTH)) {
			return Direction.NORTH;
		}
		else {
			return Direction.EAST;
		}
	}
	
	// connect the given vertex with this vertex in the given direction
	public void connect(Vertex other, Direction dir) {
		this.edges.put(dir, other);
	}
	
	// returns the vertex in the given direction
	public Vertex getNeighbor(Direction dir) {
		return this.edges.get(dir);
	}
	
	// returns all the vertices this vertex is connected to
	public Collection<Vertex> getNeighbors() {
		return this.edges.values();
	}
	
	// returns the current cost
	public int getCost() {
		return this.cost;
	}
	
	// sets the cost
	public void setCost(int newCost) {
		this.cost = newCost;
	}
	
	// returns true if the vertex is marked, false if not
	public boolean isMarked() {
		return this.marked;
	}
	
	// sets whether the vertex is marked
	public void setMarked(boolean mark) {
		this.marked = mark;
	}
	
	// returns the vertex's fields in a string
	public String toString() {
		String str = "";
		str += "Location: ";
		str += super.getX() + ", " + super.getY(); 
		str += "\nNumber of neighbors: ";
		str += this.edges.size();
		str += "\nCost: ";
		str += this.cost;
		str += "\nMarked: ";
		str += this.marked;
		return str;
	}
	
	// returns negative if this vertex has a smaller cost, 0 if equal costs,
	// and positive if has a greater cost
	public int compareTo(Vertex v) {
		return this.cost - v.getCost();
	}
 	
 	// does nothing
 	public void updateState(Landscape scape) {
 		return;
 	}
 	
 	// sets whether the vertex is visible
 	public void setVisible(boolean v) {
 		this.visible = v;
 	}
 	
 	// draws the cell
 	public void draw(Graphics g, int x0, int y0, int scale) {
 		if (!this.visible) {
 			return;
		}
		
		int xpos = x0 + super.getCol()*scale;
		int ypos = y0 + super.getRow()*scale;
		int border = 2;
		int half = scale / 2;
		int eighth = scale / 8;
		int sixteenth = scale / 16;
		
		// draw rectangle for the walls of the cave
		if (this.cost <= 2) {
			// wumpus is nearby
			g.setColor(Color.red);
		}
		else {
			// wumpus is not nearby
			g.setColor(Color.black);
		}
			
		g.drawRect(xpos + border, ypos + border, scale - 2*border, scale - 2 * border);
		
		// draw doorways as boxes
		g.setColor(Color.black);
		if (this.edges.containsKey(Direction.NORTH)) {
			g.fillRect(xpos + half - sixteenth, ypos, eighth, eighth + sixteenth);
		}
		if (this.edges.containsKey(Direction.SOUTH)) {
			g.fillRect(xpos + half - sixteenth, ypos + scale - (eighth + sixteenth), 
				  eighth, eighth + sixteenth);
		}
		if (this.edges.containsKey(Direction.WEST)) {
			g.fillRect(xpos, ypos + half - sixteenth, eighth + sixteenth, eighth);
		}
		if (this.edges.containsKey(Direction.EAST)) {
			g.fillRect(xpos + scale - (eighth + sixteenth), ypos + half - sixteenth, 
				  eighth + sixteenth, eighth);
		}
	}
 	
	// unit test
	public static void main(String[] args) {
		System.out.println(opposite(Direction.SOUTH));
		System.out.println(opposite(Direction.WEST));
		System.out.println(opposite(Direction.NORTH));
		System.out.println(opposite(Direction.EAST));
		
		Vertex v1 = new Vertex(0, 0);
		Vertex v2 = new Vertex(10, 10);
		
		v1.setCost(10);
		v2.setCost(20);
		System.out.println(v1.compareTo(v2));
		
		v1.connect(v2, Direction.SOUTH);
		v1.connect(new Vertex(20, 20), Direction.NORTH);
		System.out.println(v1);
		System.out.println(v1.getNeighbor(Direction.NORTH));
		
		System.out.println(v2.getCost());
		v2.setMarked(true);
		System.out.println(v2.isMarked());
	}
}