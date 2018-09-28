/*
 * Martin Deutsch
 * 11/30/15
 * Wumpus.java
 */
 
import java.util.*;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
 
/*
 * Represents the Wumpus beast
 */
public class Wumpus extends Cell {
	
	// the home of the wumpus
	private Vertex home;
	
	// if the wumpus is visible
	private boolean visible;
	
	// if the wumpus is alive
	private boolean alive;
	
	// initializes the location and sets the wumpus to alive but invisible
	public Wumpus(Vertex start) {
		super(start.getX(), start.getY());
		this.home = start;
		this.visible = false;
		this.alive = true;
	}
	
	// returns true if the wumpus is visible, false if not
	public boolean isVisible() {
		return this.visible;
	}
	
	// sets whether the wumpus is visible
 	public void setVisible(boolean v) {
 		this.visible = v;
 	}
 	
	// returns the location of the wumpus
	public Vertex getLocation() {
		return this.home;
	}
	
	// sets the location of the wumpus
	public void setLocation(Vertex v) {
		this.home = v;
		super.setX(v.getX());
		super.setY(v.getY());
	}
	
	// returns true if the wumpus is alive, false if not
	public boolean isAlive() {
		return this.alive;
	}
	
	// sets the living status of the wumpus
	public void setAlive(boolean living) {
		this.alive = living;
	}
	
 	// updates the cell's position
 	public void updateState(Landscape scape) {
 		return;
 	}
 	
 	// draws the cell
 	public void draw(Graphics g, int x0, int y0, int scale) {
 		if (!this.visible) {
 			return;
 		}
 		int xpos = x0 + super.getCol()*scale;
		int ypos = y0 + super.getRow()*scale;
 		if (this.alive) {
 			try {
      			// read in image and draw
      			BufferedImage image = ImageIO.read(new File("wumpus.png"));
      			Image victoriousWumpus = 
      				image.getScaledInstance(scale, scale, Image.SCALE_DEFAULT);
      			g.drawImage(victoriousWumpus, xpos, ypos, null);
    		} 
    		catch (IOException e) {
				return;
   	    	}
   	    }
   	    else {
   	    	try {
      			// read in image and draw
      			BufferedImage image = ImageIO.read(new File("wumpus2.png"));
      			Image deadWumpus = 
      				image.getScaledInstance(scale, scale, Image.SCALE_DEFAULT);
      			g.drawImage(deadWumpus, xpos, ypos, null);
    		} 
    		catch (IOException e) {
				return;
   	    	}
   	    }
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