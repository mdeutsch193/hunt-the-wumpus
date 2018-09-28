/*
 * Martin Deutsch
 * 11/30/15
 * HuntTheWumpus.java
 */

import java.util.Random;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/*
 * Runs the game
 */
 public class HuntTheWumpus {
 	
 	// the landscape with vertices, the hunter and the wumpus
 	private Landscape scape;
 	// the visual display of the landscape
 	private LandscapeDisplay display;
 	// the graph containing all the vertices (rooms) in the landscape
 	private Graph graph;
 	// the user controlled character
 	private Hunter hunter;
 	// the great and terrible wumpus
 	private Wumpus wumpus;
 	// the bottomless pit
 	private Pit pit;
 	// label to describe the events of the game
 	private JLabel message;
 	
 	// controls whether the simulation is playing, paused, resetting or exiting
	private enum PlayState { PLAY, WIN, LOSS, RESET }
	// the game's current state
	private PlayState state;
	
	// constructor builds the landscape and sets up UI
 	public HuntTheWumpus() {
 		this.buildMap();
		this.state = PlayState.PLAY;
		this.setupUI();
 	}
 	
 	// creates a random layout for the landscape
 	public void buildMap() {
 		this.graph = new Graph();
 		this.scape = new Landscape(5, 7);
 		Random gen = new Random();
 		
 		// add two rooms to the cave to start
 		this.graph.addEdge(new Vertex(0, 0), Vertex.Direction.EAST, new Vertex(1, 0));
 		
 		// add 25 more rooms to the cave
 		while (this.graph.vertexCount() < 25) {
 		
 			// choose random vertex from list
 			Vertex v0 = this.graph.getVertices().get(
 							gen.nextInt(this.graph.vertexCount()) );
 							
 			double x0 = v0.getX();
 			double y0 = v0.getY();
 			
 			// add connected vertices with 25% chance
 			if (gen.nextDouble() < .25 && y0-1 >= 0) {
 				Vertex v1 = new Vertex(x0, y0-1);
 				// check for doubles
 				for (Vertex v2 : this.graph.getVertices()) {
 					if (v1.getX() == v2.getX() && v1.getY() == v2.getY()) {
 						v1 = v2;
 					}
 				}
 				this.graph.addEdge(v0, Vertex.Direction.NORTH, v1);
 			}
 			if (gen.nextDouble() < .25 && y0+1 < scape.getRows()) {
 				Vertex v1 = new Vertex(x0, y0+1);
 				// check for doubles
 				for (Vertex v2 : this.graph.getVertices()) {
 					if (v1.getX() == v2.getX() && v1.getY() == v2.getY()) {
 						v1 = v2;
 					}
 				}
 				this.graph.addEdge(v0, Vertex.Direction.SOUTH, v1);
 			}
 			if (gen.nextDouble() < .25 && x0-1 >= 0) {
 				Vertex v1 = new Vertex(x0-1, y0);
 				// check for doubles
 				for (Vertex v2 : this.graph.getVertices()) {
 					if (v1.getX() == v2.getX() && v1.getY() == v2.getY()) {
 						v1 = v2;
 					}
 				}
 				this.graph.addEdge(v0, Vertex.Direction.WEST, v1);
 			}
 			if (gen.nextDouble() < .25 && x0+1 < scape.getCols()) {
 				Vertex v1 = new Vertex(x0+1, y0);
 				// check for doubles
 				for (Vertex v2 : this.graph.getVertices()) {
 					if (v1.getX() == v2.getX() && v1.getY() == v2.getY()) {
 						v1 = v2;
 					}
 				}
 				this.graph.addEdge(v0, Vertex.Direction.EAST, v1);
 			}
 		}
 		
 		// add vertices to graph
 		for (Vertex v1 : this.graph.getVertices()) {	
 			this.scape.addAgent(v1);
 		}
 		
 		// add the agents to the landscape
 		this.addCharacters();
 		
 		this.display = new LandscapeDisplay(scape, 100);
 	}
 	
 	// add the hunter, Wumpus, and a pit to the landscape
 	private void addCharacters() {
 		Random gen = new Random();
 		
 		// add the hunter to the landscape
 		this.hunter = new Hunter(this.graph.getVertices().get(0));
 		scape.addAgent(this.hunter);
 		
 		// add the wumpus to the landscape
 		int wumpStart = gen.nextInt(this.graph.getVertices().size());
 		this.wumpus = new Wumpus(this.graph.getVertices().get(wumpStart));
 		this.graph.shortestPath(this.hunter.getLocation());
 		while (this.wumpus.getLocation().getCost() <= 2) {
 			wumpStart = gen.nextInt(this.graph.getVertices().size());
 			this.wumpus = new Wumpus(this.graph.getVertices().get(wumpStart));
 		}
 		scape.addAgent(this.wumpus);
 		
 		// add the pit to the landscape
 		int pitStart = gen.nextInt(this.graph.getVertices().size());
 		this.pit = new Pit(this.graph.getVertices().get(pitStart));
 		this.graph.shortestPath(this.wumpus.getLocation());
 		
 		// make sure pit and wumpus are far enough apart, and 
 		// that the player doesn't start in the pit
 		while (this.pit.getLocation().getCost() <= 4 || pitStart == 0) {
 			pitStart = gen.nextInt(this.graph.getVertices().size());
 			this.pit = new Pit(this.graph.getVertices().get(pitStart));
 		}
 		scape.addAgent(this.pit);
 	}
 	
 	// sets up the UI for the game
 	public void setupUI() {
 		// add elements to the UI
		this.message = new JLabel("Hunt the Wumpus!");
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panel.add(this.message);
		
		this.display.add(panel, BorderLayout.SOUTH);
		this.display.pack();

		// listen for keystrokes
		Control control = new Control();
		this.display.addKeyListener(control);
		this.display.setFocusable(true);
		this.display.requestFocus();
	}
	
	// runs one iteration of the game
	public void iterate() throws InterruptedException {
		if (this.state == PlayState.PLAY)
		{
			// update the landscape, display
			this.scape.advance();
			this.scape.setDirection(null);
			this.graph.shortestPath(this.wumpus.getLocation());
			this.display.update();
			
			// interact with wumpus
			if (this.hunter.getLocation() == this.wumpus.getLocation()) {
				this.message.setText("The wumpus eats you!");
				this.wumpus.setVisible(true);
				this.display.update();
				this.state = PlayState.LOSS;
			}
			else if (this.hunter.getLocation().getCost() <= 2) {
				this.message.setText("You smell the wumpus!");
			}
			else {
				// interact with pit
				this.graph.shortestPath(this.pit.getLocation());
			
				if (this.hunter.getLocation() == this.pit.getLocation()) {
					this.message.setText("You have fallen into the bottomless pit!");
					this.pit.setVisible(true);
					this.display.update();
					this.state = PlayState.LOSS;
				}
				else if (this.hunter.getLocation().getCost() <= 2) {
					this.message.setText("You feel a breeze");
				}
				// set message back to default
				else {
					this.message.setText("Hunt the Wumpus!");
				}
			}	
		}
		Thread.sleep(1000);
	}
	
	// asks the user if they want to play again
	public boolean playAgain() throws InterruptedException {
		Thread.sleep(1000);
		
		// add new frame asking user to play again
		Control control = new Control();
		JFrame frame = new JFrame("Game over");
		JPanel pane = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel label = new JLabel("Want to play again?");
		JButton yes = new JButton("Yes");
		yes.addActionListener(control);
		JButton no = new JButton("No");
		no.addActionListener(control);
		pane.add(label);
		pane.add(yes);
		pane.add(no);
		
		frame.add(pane, BorderLayout.NORTH);
		frame.pack();
		frame.setVisible(true);
		
		// wait until user chooses a button
		while (state == PlayState.LOSS || state == PlayState.WIN) {
			Thread.sleep(1000);
		}
		
		// reset and restart game
		if (state == PlayState.RESET) {
			LandscapeDisplay oldDisplay = display;
			this.buildMap();
			this.state = PlayState.PLAY;
			this.setupUI();
			
			// close windows
			oldDisplay.dispose();
			frame.dispose();
			
			return true;
		}
		
		else { 
			// quit
			frame.dispose();
			return false;
		}	
	}
	
	/**
	 * Provides simple keyboard control to the simulation by implementing
	 * the KeyListener interface.
	 */
	private class Control extends KeyAdapter implements ActionListener
	{
		/**
		 * Controls the simulation in response to key presses.
		 */
		public void keyTyped(KeyEvent e)
		{
			// move the hunter
			if (("" + e.getKeyChar()).equalsIgnoreCase("w"))
			{
				scape.setDirection(Vertex.Direction.NORTH);
			}
			else if (("" + e.getKeyChar()).equalsIgnoreCase("a"))
			{
				scape.setDirection(Vertex.Direction.WEST);
			}
			else if (("" + e.getKeyChar()).equalsIgnoreCase("s"))
			{
				scape.setDirection(Vertex.Direction.SOUTH);
			}
			else if (("" + e.getKeyChar()).equalsIgnoreCase("d"))
			{
				scape.setDirection(Vertex.Direction.EAST);
			}
			else if (Character.isSpaceChar(e.getKeyChar()))
			{
				hunter.setArmed(!hunter.isArmed());
			}
			
			// fire the hunter's arrow
			if (hunter.isArmed()) {
				if (hunter.getLocation().getNeighbor(scape.getDirection()) ==
						wumpus.getLocation()) {
					message.setText("You have slain the wumpus!");
					wumpus.getLocation().setVisible(true);
					wumpus.setAlive(false);
					wumpus.setVisible(true);
					scape.setDirection(null);
					display.update();
					state = PlayState.WIN;
				}
				else if (scape.getDirection() != null) {
					message.setText("You missed the wumpus. It hears you and kills you.");
					scape.setDirection(null);
					wumpus.setLocation(hunter.getLocation());
					wumpus.setVisible(true);
					display.update();
					state = PlayState.LOSS;
				}
			}
					
		}
		
		// respond to buttons being pushed
		public void actionPerformed(ActionEvent event)
		{
			if (event.getActionCommand().equalsIgnoreCase("Yes"))
			{
				state = PlayState.RESET;
			}
			else if (event.getActionCommand().equalsIgnoreCase("No"))
			{
				state = PlayState.PLAY;
			}
		}	
	}
	
	// creates new game and runs until the user quits
	public static void main(String[] args) throws InterruptedException {
		HuntTheWumpus game = new HuntTheWumpus();
		int playerWins = 0;
		int wumpusWins = 0;
		
		// run until user quits
		do {
			// run game until over
			while (game.state == PlayState.PLAY)
			{	
				game.iterate();
			}
			if (game.state == PlayState.WIN) {
				playerWins++;
			}
			else {
				wumpusWins++;
			}
		}
		while (game.playAgain());
		
		// print results
		System.out.println("Player wins: " + playerWins);
		System.out.println("Wumpus wins: " + wumpusWins);
		 
		// exit
		game.display.dispose();
	}
}
 