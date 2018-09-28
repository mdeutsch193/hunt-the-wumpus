/*
 * Martin Deutsch
 * 11/30/15
 * Hunter.java
 */
 
import java.util.*;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
 
/*
 * Represents the player character
 */
public class Hunter extends Cell {
	
	// the current location of the hunter
	private Vertex location;
	
	// whether the hunter is ready to fire an arrow
	private boolean armed;
	
	// constructor initializes the location
	public Hunter(Vertex start) {
		super(start.getX(), start.getY());
		this.location = start;
		this.location.setVisible(true);
		this.armed = false;
	}
	
	// sets the location of the hunter
	public void setLocation(Vertex v) {
		this.location = v;
	}
	
	// returns the location of the hunter
	public Vertex getLocation() {
		return this.location;
	}
	
	// sets whether the hunter has an arrow ready
	public void setArmed(boolean b) {
		this.armed = b;
	}
	
	// returns true if the hunter has an arrow ready, false if not
	public boolean isArmed() {
		return this.armed;
	}
 	
 	// updates the cell's position
 	public void updateState(Landscape scape) {
 		if (scape.getDirection() == null || 
 				this.location.getNeighbor(scape.getDirection()) == null) {
 			return;
 		}
 		else {
 			this.location = this.location.getNeighbor(scape.getDirection());
 			super.setX(this.location.getX());
			super.setY(this.location.getY());
			this.location.setVisible(true);
		}
 	}
 	
 	// draws the cell
 	public void draw(Graphics g, int x0, int y0, int scale) {
 		int xpos = x0 + super.getCol()*scale;
		int ypos = y0 + super.getRow()*scale;
		if (!this.armed) {
 			try {
      			// read in image and draw
      			BufferedImage image = ImageIO.read(new File("hunter.png"));
      			Image stickMan = 
      				image.getScaledInstance(scale, scale, Image.SCALE_DEFAULT);
      			g.drawImage(stickMan, xpos, ypos, null);
    		} 
    		catch (IOException e) {
				return;
			}
   	    }
   	    else {
   	     	try {
      			// read in image and draw
      			BufferedImage image = ImageIO.read(new File("hunter2.png"));
      			Image angryStickMan = 
      				image.getScaledInstance(scale, scale, Image.SCALE_DEFAULT);
      			g.drawImage(angryStickMan, xpos, ypos, null);
    		} 
    		catch (IOException e) {
				return;
			}
   	    }
	}
	
	// unit test
	public static void main(String[] args) {
		Vertex v1 = new Vertex(0, 0);
		Hunter orion = new Hunter(v1);
		orion.setLocation(new Vertex(5, 5));
		System.out.println(orion.getLocation());
		orion.setArmed(true);
		System.out.println(orion.isArmed());
	}
}