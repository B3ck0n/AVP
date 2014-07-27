/**********************************
 * Assignment 5: AVP
 * GUI.java
 * Date: 12/4/2011
 *
 * Jon Hamlin: jwh244, 2400666
 * Brooks Hoffecker: bjh83, 2015719
 * Evan Long: erl43, 2300076
 * Richard McCaffrey: rpm77, 2494167
 **********************************/
package avp;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import avp.Node.Dir;

@SuppressWarnings("serial")
public class GUI extends JPanel {
	// drawing constants
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private int wallSize;
	private int padding;
	private int finalFormatX = 0, finalFormatY = 0;

	// copy of the ship in its true state
	Ship ship;

	// window stuff
	private Image alienImage = createImage(null);
	private Image predatorImage = createImage(null);
	private Image controlImage = createImage(null);
	private Image scannerImage = createImage(null);
	private boolean gridLines = false;

	//creates the gui for the game
	@SuppressWarnings("unused")
	public GUI(Ship ship) {
		
		//**********************************************************
		// For Karma - Uncomment the line below to draw gridLines
		// gridLines=true;
		
		//establishes formatting variables to account for full screen gameplay
		//sets wall size and padding for the gui
		if ((screenSize.width) <= (screenSize.height - screenSize.height / 6)) {
			if (GameConstants.WIDTH >= GameConstants.HEIGHT) {
				padding = ((screenSize.width) / GameConstants.WIDTH);
			} else {
				padding = ((screenSize.width) / (4 * GameConstants.WIDTH));
			}
			wallSize = (screenSize.width - padding) / GameConstants.WIDTH;
			while (wallSize * GameConstants.HEIGHT + padding > screenSize.height) {
				wallSize--;
			}

			finalFormatY = ((screenSize.height) - (wallSize
					* GameConstants.HEIGHT + padding)) / 2;
		} else {
			if (GameConstants.WIDTH >= GameConstants.HEIGHT) {
				padding = ((screenSize.width) / GameConstants.WIDTH);
			} else {
				padding = ((screenSize.width) / (4 * GameConstants.WIDTH));
			}
			wallSize = (screenSize.height - (screenSize.height / 6) - padding)
					/ GameConstants.HEIGHT;
			while (wallSize * GameConstants.WIDTH + padding > screenSize.width) {
				wallSize--;
			}

			finalFormatX = ((screenSize.width) - (wallSize
					* GameConstants.WIDTH + padding)) / 2;
		}
		this.ship = ship;
		setBackground(Color.BLACK);

		//imports pictures for the alien, predator, control room, and scanner
		String alienFile = "avp/images/alien.jpg";
		String predatorFile = "avp/images/predator.jpg";
		String controlFile = "avp/images/ControlRoom.jpg";
		String scannerFile = "avp/images/Scanner.jpg";
		try {
			alienImage = ImageIO.read(ClassLoader
					.getSystemResourceAsStream(alienFile));
			predatorImage = ImageIO.read(ClassLoader
					.getSystemResourceAsStream(predatorFile));
			controlImage = ImageIO.read(ClassLoader
					.getSystemResourceAsStream(controlFile));
			scannerImage = ImageIO.read(ClassLoader
					.getSystemResourceAsStream(scannerFile));
		} catch (IOException IO) {
		}
	}

	/**
	 * Visits every node and draws edges around it. Places predator, alien,
	 * scanner, and control room down
	 */
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Node travelNode;
		for (int i = 0; i < GameConstants.HEIGHT; i++) {
			for (int j = 0; j < GameConstants.WIDTH; j++) {
				travelNode = ship.nodeMap.get(new Coordinates(i, j));
				drawLines(travelNode, g);
			}
		}
		drawControlRoom(Engine.controlNode, g);

		if (Engine.scannerNode != null) {
			drawScanner(Engine.scannerNode, g);
		}
		drawAlien(Engine.ship.getAlienNode(), null, g);
		drawPredator(Engine.ship.getPredatorNode(), null, g);

		//*********************************************************
		// Uncomment for Karma
		// draws a path between two nodes for testing purposes
//		 drawPath(ship.getPath(ship.getNodeAt(0, 0),
//		 ship.getNodeAt(15,15)),g);

	}

	/**
	 * given a Node, draws the four edges around it
	 * 
	 * @param node
	 * @param g
	 */
	public void drawLines(Node node, Graphics g) {
		int x = (node.getCoordinates().col * wallSize) + padding + finalFormatX;
		int y = (node.getCoordinates().row * wallSize) + padding + finalFormatY;
		Map<Dir, Edge> edges = node.getDirections();
		Edge edge = edges.get(Dir.UP);
		g.setColor(getColor(edge));
		g.drawLine(x - (wallSize / 2), y - (wallSize / 2), x + (wallSize / 2),
				y - (wallSize / 2));

		edge = edges.get(Dir.DOWN);
		g.setColor(getColor(edge));
		g.drawLine(x - (wallSize / 2), y + (wallSize / 2), x + (wallSize / 2),
				y + (wallSize / 2));

		edge = edges.get(Dir.LEFT);
		g.setColor(getColor(edge));
		g.drawLine(x - (wallSize / 2), y - (wallSize / 2), x - (wallSize / 2),
				y + (wallSize / 2));

		edge = edges.get(Dir.RIGHT);
		g.setColor(getColor(edge));
		g.drawLine(x + (wallSize / 2), y - (wallSize / 2), x + (wallSize / 2),
				y + (wallSize / 2));
	}

	/**
	 * Draws/moves the alien picture
	 * 
	 * @param to
	 *            -the node to draw the alien
	 * @param from
	 *            -the location of the picture to erase(null if original
	 *            placement)
	 * @param g
	 */
	public void drawAlien(Node to, Node from, Graphics g) {
		if (from != null) {
			Coordinates alienCoords = from.getCoordinates();
			int alienPicX = alienCoords.col * wallSize + padding + finalFormatX
					- (wallSize / 2) + 2;
			int alienPicY = alienCoords.row * wallSize + padding + finalFormatY
					- (wallSize / 2) + 2;
			int alienPicThickness = wallSize - 4;
			g.fillRect(alienPicX, alienPicY, alienPicThickness,
					alienPicThickness);
			if (from.equals(Engine.controlNode)) {
				drawControlRoom(from, g);
			}
		}
		drawGridLines(g);
		Coordinates alienCoords = to.getCoordinates();
		int alienPicX = alienCoords.col * wallSize + padding + finalFormatX
				- (wallSize / 2) + 2;
		int alienPicY = alienCoords.row * wallSize + padding + finalFormatY
				- (wallSize / 2) + 2;
		int alienPicThickness = wallSize - 4;
		g.drawImage(alienImage, alienPicX, alienPicY, alienPicThickness,
				alienPicThickness, Color.BLACK, null);
	}

	/**
	 * Draws/moves the predator Image
	 * 
	 * @param to
	 *            -the location to draw the predator
	 * @param from
	 *            -the location of the image to erase(null if original
	 *            placement)
	 * @param g
	 */
	public void drawPredator(Node to, Node from, Graphics g) {
		if (from != null) {
			Coordinates predatorCoords = from.getCoordinates();
			int predPicX = predatorCoords.col * wallSize + padding
					+ finalFormatX - (wallSize / 2) + 2;
			int predPicY = predatorCoords.row * wallSize + padding
					+ finalFormatY - (wallSize / 2) + 2;
			int predPicThickness = wallSize - 4;
			g.fillRect(predPicX, predPicY, predPicThickness, predPicThickness);
			if (from.equals(Engine.controlNode)) {
				drawControlRoom(from, g);
			}
		}
		drawGridLines(g);
		Coordinates predatorCoords = to.getCoordinates();
		int predPicX = predatorCoords.col * wallSize + padding + finalFormatX
				- (wallSize / 2) + 2;
		int predPicY = predatorCoords.row * wallSize + padding + finalFormatY
				- (wallSize / 2) + 2;
		int predPicThickness = wallSize - 4;
		g.drawImage(predatorImage, predPicX, predPicY, predPicThickness,
				predPicThickness, Color.BLACK, null);
	}

	/**
	 * draws control room image
	 * 
	 * @param to
	 * @param g
	 */
	private void drawControlRoom(Node to, Graphics g) {
		Coordinates controlCoords = to.getCoordinates();
		int conPicX = controlCoords.col * wallSize + padding + finalFormatX
				- (wallSize / 2) + 2;
		int conPicY = controlCoords.row * wallSize + padding + finalFormatY
				- (wallSize / 2) + 2;
		int conPicThickness = wallSize - 4;
		g.drawImage(controlImage, conPicX, conPicY, conPicThickness,
				conPicThickness, Color.BLACK, null);
	}

	/**
	 * draws scanner image
	 * 
	 * @param to
	 * @param g
	 */
	private void drawScanner(Node to, Graphics g) {
		Coordinates scannerCoords = to.getCoordinates();
		int scanPicX = scannerCoords.col * wallSize + padding + finalFormatX
				- (wallSize / 2) + 2;
		int scanPicY = scannerCoords.row * wallSize + padding + finalFormatY
				- (wallSize / 2) + 2;
		int scanPicThickness = wallSize - 4;
		g.drawImage(scannerImage, scanPicX, scanPicY, scanPicThickness,
				scanPicThickness, Color.BLACK, null);
	}

	
	//returns the color that an edge should be painted base on its edge state
	private Color getColor(Edge edge) {
		if (edge == null) {
			return Color.WHITE;
		} else if (edge.getEdgeState() == EdgeState.BURNED) {
			return Color.GREEN;
		} else if (edge.getEdgeState() == EdgeState.CLOSED) {
			return Color.BLUE;
		} else {
			return Color.BLACK;
		}
	}

	//*************************************************************************
	//Anything below this point is for Karma points
	//**************************************************************************
	
	// draws grid lines to show where a player will wrap around, for testing
	public void drawGridLines(Graphics g) {
		if (gridLines) {
			g.setColor(new Color(0, 0, 200));
			for (int i = 0; i < GameConstants.HEIGHT; i++) {
				g.drawLine((finalFormatX + padding) - (wallSize / 2), i
						* wallSize + padding + finalFormatY,
						(finalFormatX + padding) + wallSize
								* GameConstants.WIDTH - (wallSize / 2), i
								* wallSize + padding + finalFormatY);
			}
			for (int i = 0; i < GameConstants.WIDTH; i++) {
				g.drawLine((finalFormatX + padding) + i * wallSize, padding
						+ finalFormatY - (wallSize / 2),
						(padding + finalFormatX) + i * wallSize, padding
								+ finalFormatY + wallSize
								* GameConstants.HEIGHT - (wallSize / 2));
			}
		}
	}
	
		// draws the path between two nodes, for testing purposes
		public void drawPath(List<Edge> path, Graphics g) {
			for (Edge temp : path) {
				Set<Node> XY = temp.getAdjacent();
				Iterator<Node> it = XY.iterator();
				Coordinates node1Coords, node2Coords;
				Node node1 = it.next();
				node1Coords = node1.getCoordinates();
				int node1X = (node1Coords.col * wallSize) + padding + finalFormatX;
				int node1Y = (node1Coords.row * wallSize) + padding + finalFormatY;
				Node node2 = it.next();
				node2Coords = node2.getCoordinates();
				int node2X = (node2Coords.col * wallSize) + padding + finalFormatX;
				int node2Y = (node2Coords.row * wallSize) + padding + finalFormatY;

				g.setColor(Color.MAGENTA);

				if (Math.abs(node1Coords.col - node2Coords.col) <= 1
						&& Math.abs(node1Coords.row - node2Coords.row) <= 1) {
					g.drawLine(node1X, node1Y, node2X, node2Y);
				}
			}
		}

}
