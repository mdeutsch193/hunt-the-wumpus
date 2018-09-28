/* 
 * Landscape.java
 * Martin Deutsch
 * 10/21/15
 */
 
 import java.util.*;
 
 /*
  * Represents a 2D grid of Cell objects
  */
 public class Landscape {
 	
 	 // the height in pixels of the landscape
 	private double height;
 
 	// the width in pixels of the landscape
 	private double width;
 
 	// a list of all the agents in the landscape
 	private ArrayList<Cell> agents;
 	
 	// the direction the hunter is moving
 	private Vertex.Direction direction;
 	
 	// creates a landscape with the given number of rows and 
 	// columns, and initializes the linked list to empty
 	public Landscape(int rows, int cols) {
 		this.height = (double) rows;
 		this.width = (double) cols;
 		this.agents = new ArrayList<Cell>();
 		this.direction = null;
 	}
 	
 	// creates a landscape with the given double numbers of rows and
 	// columns, and initializes the linked list to empty
 	public Landscape(double rows, double cols) {
 		this.height = rows;
 		this.width = cols;
 		this.agents = new ArrayList<Cell>();
 		this.direction = null;
 	}
 	
 	// clears the landscape of agents
 	public void reset() {
 		this.agents.clear();
 	}
 	
 	// returns the height rounded to an integer
 	public int getRows() {
 		return (int)(this.height+.5);
 	}
 	
 	// returns the height as a double
 	public double getHeight() {
 		return this.height;
 	}
 	
  	// returns the number of columns
 	public int getCols() {
 		return (int)(this.width+.5);
 	}
 	
 	// returns the width as a double
 	public double getWidth() {
 		return this.width;
 	}
 	
 	// adds cell c to the landscape
 	public void addAgent(Cell c) {
 		this.agents.add(c);
 	}
 	
 	// removes cell c from the landscape
 	public void removeAgent(Cell c) {
 		this.agents.remove(c);
 	}
 	
 	// returns the list of agents
 	public ArrayList<Cell> getAgents() {
 		return this.agents;
 	}	
 	
 	// returns a string representation of the landscape
 	public String toString() {
 		ArrayList<String> s = new ArrayList<String>();
 		
		for(int i=0;i<height;i++) {
			for(int j=0;j<width;j++) {
				s.add(" ");
			}
		s.add("\n");
		}

		for( Cell item: agents ) {
			int r = item.getRow();
			int c = item.getCol();

			if(r >= 0 && r < height && c >= 0 && c < width ) {
				int index = r * (this.getCols() + 1) + c;
				s.set( index, "." );
			}
		}

		String sout = "";
		for( String a: s ) {
			sout += a;
		}

		return sout;
 	}
 	
 	// returns the current direction the hunter is moving
 	public Vertex.Direction getDirection() {
 		return this.direction;
 	}
 	
 	// sets the direction the hunter is moving
 	public void setDirection(Vertex.Direction dir) {
 		this.direction = dir;
 	}

 	// calls updateState on each cell in the landscape
 	public void advance() {
 		// updates each agent in the list
 		for (Cell c : this.agents) {
 			c.updateState(this);
 		}
 	}
 	
 	// unit test
	public static void main(String[] args) {
		Landscape scape = new Landscape(50, 75);
		scape.addAgent(new Vertex(0, 0));
		scape.addAgent(new Vertex(10, 10));
		System.out.println(scape);
	}
 }