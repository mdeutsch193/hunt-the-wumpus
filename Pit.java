/*
 * Martin Deutsch
 * 11/30/15
 * Pit.java
 */
 
import java.util.*;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
 
/*
 * Represents the bottomless pit
 */
public class Pit extends Cell {
	
	// the location of the pit
	private Vertex location;
	
	// if the pit is visible
	private boolean visible;
	
	// initializes the location and makes the pit invisible
	public Pit(Vertex start) {
		super(start.getX(), start.getY());
		this.location = start;
		this.visible = false;
	}
	
	// returns true if the pit is visible, false if not
	public boolean isVisible() {
		return this.visible;
	}
	
	// sets whether the pit is visible
 	public void setVisible(boolean v) {
 		this.visible = v;
 	}
 	
	// returns the location of the pit
	public Vertex getLocation() {
		return this.location;
	}
 	
 	// does nothing
 	public void updateState(Landscape scape) {
 		return;
 	}
 	
 	// draws the pit
 	public void draw(Graphics g, int x0, int y0, int scale) {
 		if (!this.visible) {
 			return;
 		}
 		int xpos = x0 + super.getCol()*scale;
		int ypos = y0 + super.getRow()*scale;
 		int x = x0 + (int)(this.getX() * scale);
		int y = y0 + (int)(this.getY() * scale);
		
		g.setColor(Color.black);
		g.fillOval(x, y, scale, scale);
		
		return;
	}
	
	// unit test
	public static void main(String[] args) {
		Vertex v1 = new Vertex(0, 0);
		Wumpus wumpy = new Wumpus(v1);
		System.out.println(wumpy.isVisible());
		wumpy.setLocation(new Vertex(1, 1));
		System.out.println(wumpy.getLocation());
		wumpy.setAlive(false);
		System.out.println(wumpy.isAlive());
	}
}