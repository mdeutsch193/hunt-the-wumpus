/* 
 * Cell.java
 * Martin Deutsch
 * 10/21/15
 */
 
 import java.util.*;
 import java.awt.*;
 
 /* 
  * Represents a cell at one location on the grid
  */
 public abstract class Cell {
 	
 	// the x coordinate of the cell
 	private double x;
 	
 	// the y coordinate of the cell
 	private double y;
 	
 	// creates a cell located at the given coordinates (x0, y0)
 	public Cell(double x0, double y0) {
 		this.x = x0;
 		this.y = y0;
 	}
 	
 	// returns the x location as a double
 	public double getX() {
 		return this.x;
 	}
 	
 	// returns the x location as the nearest integer
 	public int getCol() {
 		return (int) (x + .5);
 	}
 	
 	// returns the y location as a double
 	public double getY() {
 		return this.y;
 	}
 	
 	// returns the y location as the nearest integer
 	public int getRow() {
 		return (int) (y + .5);
 	}
 	
 	// sets the x location
 	public void setX(double x0) {
 		this.x = x0;
 	}
 	
 	// sets the y location
 	public void setY(double y0) {
 		this.y = y0;
 	}
 	
 	// returns a string containing a single period
 	public String toString() {
 		return ".";
 	}
 	
 	// updates the cell's position when implemented
 	public abstract void updateState(Landscape scape);
 	
 	// draws the cell when implemented
 	public abstract void draw(Graphics g, int x, int y, int scale);
 }
 	