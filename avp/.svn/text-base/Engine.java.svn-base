/**********************************
 * Assignment 5: AVP
 * Engine.java
 * Date: 12/4/2011
 *
 * Jon Hamlin: jwh244, 2400666
 * Brooks Hoffecker: bjh83, 2015719
 * Evan Long: erl43, 2300076
 * Richard McCaffrey: rpm77, 2494167
 **********************************/
package avp;

import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.swing.JOptionPane;

import avp.Node.Dir;

public class Engine implements GameConstants {

        // Whether or not the program should print out diagnotic information
        static final boolean PRINT_DIAGNOSTIC = false;

        static UIGUI uigui;
        static GUI gui;
        static Ship ship;
        static Ship connectedShip;
        Alien alien;
        Predator predator;
        static Node controlNode;
        static Node scannerNode;
        static int move = SPEED_RATIO;
        static int predCount;
        static int alCount;
        static boolean alienHasScanner = false;
        static boolean predatorHasScanner = false;
        static int speed = 500;
        static Engine engine;

        public static void main(String[] args) {
                engine = new Engine();
        }

        // Racers, START YOUR ENGINES
        Engine() {

                // Create a completely connected version of the ship
                connectedShip = Ship.completelyConnectedGraph();

                // Make a the ship for the game
                ship = Ship.makeGameShip();

                // initialize the agents

                // Make the predator
                predator = new Predator();
                // Initialize the predator
                predator.init(ship);
                // Make the alien
                alien = new Alien();
                // Initialize the alien
                alien.init(null);

                // setup initial state of the control, scanner, alien, and
                // predator nodes
                setupInterestingNodes();

                // setup the GUI
                gui = new GUI(ship);
                uigui = new UIGUI(gui);
        }

        /**
         * Sets up the Control, Alien, Predator, and Scanner Nodes. <br>
         * The Predator is set to be "far away" from the Alien and Control room
         * 
         * <br>
         * The Scanner cannot spawn on any other the other three
         */
        @SuppressWarnings("unused")
        private void setupInterestingNodes() {

                // Random number generator
                Random rand = new Random();

                // Coordinates for the 4 important nodes
                int scannerColumn, scannerRow, predatorColumn, predatorRow, controlRow, controlColumn, alienRow, alienColumn;

                // initialize predator to random place
                predatorColumn = rand.nextInt(WIDTH);
                predatorRow = rand.nextInt(HEIGHT);

                // Put the predator on the ship
                ship.setPredatorNode(ship.getNodeAt(predatorRow, predatorColumn));

                // initialize control room and the alien to random place in the
                // 20% band
                // farthest away from predator
                if (HEIGHT > WIDTH) {
                        controlColumn = rand.nextInt(WIDTH);
                        controlRow = ((int) (predatorRow + .4 * HEIGHT + rand.nextInt((int) (.2 * HEIGHT)))) % HEIGHT;

                        alienColumn = rand.nextInt(WIDTH);
                        alienRow = ((int) (predatorRow + .4 * HEIGHT + rand.nextInt((int) (.2 * HEIGHT)))) % HEIGHT;

                }
                else {
                        controlRow = rand.nextInt(HEIGHT);
                        controlColumn = ((int) (predatorColumn + .4 * WIDTH + rand.nextInt((int) (.2 * WIDTH)))) % WIDTH;

                        alienRow = rand.nextInt(HEIGHT);
                        alienColumn = ((int) (predatorColumn + .4 * WIDTH + rand.nextInt((int) (.2 * WIDTH)))) % WIDTH;

                }

                // Place the control room on the ship
                controlNode = ship.getNodeAt(controlRow, controlColumn);

                // Place the alien on the ship
                ship.setAlienNode(ship.getNodeAt(alienRow, alienColumn));

                // Find a location for the scanner that is not on top of any of
                // the
                // previous nodes
                do {
                        scannerColumn = rand.nextInt(WIDTH);
                        scannerRow = rand.nextInt(HEIGHT);
                        scannerNode = ship.getNodeAt(scannerRow, scannerColumn);
                        // don't spawn on top of the other nodes of interest
                } while (scannerNode.equals(ship.getPredatorNode()) || scannerNode.equals(controlNode) || scannerNode.equals(ship.getAlienNode()));
        }

        /**
         * Plays the game in a synchronized method! Controls when the alien and
         * predator can move, what info they get, and if their moves are valid
         * 
         */
        synchronized void play() {

                // Go sit in the corner and think about what you've done
                int predatorTimeOut = -1;
                int alienTimeOut = -1;

                Move predatorMove = null;
                Move alienMove = null;

                while (true) {
                        // checks if game should end
                        winCondition();

                        // Pause the game for turns
                        pause();

                        // If the predator is in time out
                        if (predatorTimeOut > -1) {
                                // Decriment his time out
                                predatorTimeOut--;
                        }

                        // If the predator is not in time out
                        if (predatorTimeOut == -1) {
                                // prints to the status bar
                                uigui.changeTurnStatus("  It is the Predator's move");
                                // Ask the predator for his move
                                predatorMove = askPredatorForMove();

                                // If the predator wants to close a door
                                if (predatorMove.changeState == true && predatorMove.move == false
                                                && ship.edgeMap.get(predatorMove.edge.getAdjacent()).getEdgeState() == EdgeState.CLOSED) {

                                        // Put the predator in time out
                                        predatorTimeOut = -1 + OPEN_TIME;
                                }
                                // Otherwise execute the move
                                else {
                                        makeMove(predatorMove, false);
                                }
                        }

                        // If the predator just finished his time out
                        if (predatorTimeOut == 0) {
                                makeMove(predatorMove, false);
                        }

                        // Call the alien the correct number of times
                        for (int i = 0; i < SPEED_RATIO; i++) {
                                // Check winning conditions
                                winCondition();

                                // Pause the game for turns
                                pause();

                                // If the alien is in time out
                                if (alienTimeOut > -1) {
                                        // Decriment his time out
                                        alienTimeOut--;
                                }

                                // If the alien is not in time out
                                if (alienTimeOut == -1) {
                                        alienMove = askAlienForMove();

                                        // prints to the status bar
                                        uigui.changeTurnStatus("  It is the Alien's move");
                                        // If the alien wants to burn through a
                                        // wall
                                        if (alienMove.changeState == true && alienMove.move == false) {

                                                // Put the alien in time out
                                                alienTimeOut = -1 + BURN_TIME;
                                        }
                                        // Otherwise make the move
                                        else {
                                                makeMove(alienMove, true);
                                        }

                                }

                                // If the predator just finished his time out
                                if (alienTimeOut == 0) {
                                        makeMove(alienMove, true);
                                }

                        }
                }
        }

        /**
         * Method that deals with asking the alien for a move
         */
        private Move askAlienForMove() {

                // Get all possible nodes for the alien
                final Set<Edge> allPossibleEdges = Ship.getAll(ship.getAlienNode().getCoordinates(), ship, connectedShip);

                // Get the adjacent nodes for the predator
                final Set<Edge> adjacentEdges = ship.getAlienNode().getAdjacent();

                // Determine whether or not the alien is at the scanner
                boolean alienAtScanner = ship.getAlienNode().equals(scannerNode);

                // If the Alien picks up the scanner, indicate that he should
                // have it
                // for the rest of the game
                if (alienAtScanner) {
                        alienHasScanner = true;
                        scannerNode = null;

                        // prints to status bar
                        uigui.changeScannerStatus("  The Alien has the Scanner");
                }

                // By default the Alien does not get updates from the
                // scanner
                Ship scannerShip = null;

                // If the alien has the scanner, then give him a copy of
                // the ship
                if (alienHasScanner) {
                        // Make a copy of the ship
                        scannerShip = Engine.ship.clone();
                }

                // By default, the predator is not within sight
                boolean isPredatorWithinSight = false;

                // By default, the alien does not know which direction
                // the predator
                // is in
                Edge directionOfPredator = null;

                // Get the shortest path between the alien and predator
                List<Edge> shortestPath = Engine.ship.getPath(ship.getAlienNode(), ship.getPredatorNode());

                // If there is a path between the alien and the predator
                // and it is
                // within the alien's sensing distance
                if (shortestPath != null && shortestPath.size() <= ALIEN_SENSE) {

                        // Indicate that the predator is in sight of the
                        // Alien
                        isPredatorWithinSight = true;

                        // Indicate the shortest path to the predator
                        directionOfPredator = shortestPath.iterator().next();

                        // prints to the status bar
                        uigui.changeASenseStatus("  The Alien senses the Predator");
                }
                else {
                        uigui.changeASenseStatus("");
                }

                // By default, the predator is not on the other side of the wall
                Edge wall = null;

                // For every possible edge surrounding the alien
                for (Edge e : allPossibleEdges) {

                        // If the predator is on the other side of the edge
                        if (Node.getOtherNode(e, ship.getAlienNode()).equals(ship.getPredatorNode())) {

                                // prints to the status bar
                                uigui.changeASenseStatus("  The Alien senses the Predator");

                                // If the edge is a wall or a closed door
                                if ((e.getEdgeState() == null || e.getEdgeState() == EdgeState.CLOSED)) {
                                        // Store the edge
                                        wall = e;
                                }
                        }
                }

                // Info for the alien
                Info info = new Info(ship.getAlienNode(), allPossibleEdges, adjacentEdges, false, alienAtScanner, scannerShip, isPredatorWithinSight,
                                directionOfPredator, wall);

                // Get the alien's move
                Move alienMove = alien.nextMove(info);

                // Validate the alien's move
                if (validateMove(alienMove, allPossibleEdges, true, PRINT_DIAGNOSTIC)) {

                        // If valid, make the move
                        return alienMove;
                }
                else {
                        // Otherwise indicate a pass
                        return new Move(adjacentEdges.iterator().next(), false, false);
                }

        }

        /**
         * Method that deals with asking the predator for a move
         */
        private Move askPredatorForMove() {

                // Get the All possible edges for the predator
                final Set<Edge> allPossibleEdges = Ship.getAll(ship.getPredatorNode().getCoordinates(), ship, connectedShip);

                // Get the adjacent edges for the predator
                final Set<Edge> adjacentEdges = ship.getPredatorNode().getAdjacent();

                // Is the predator at the control room
                boolean predAtControlRoom = ship.getPredatorNode().equals(controlNode);

                // prints to the status bar
                if (predAtControlRoom) {
                        uigui.changeCRStatus("  The Predator is at the Control Room");
                }

                // Is the predator at the scanner
                boolean predAtScanner = ship.getPredatorNode().equals(scannerNode);

                // If the predator it on the scanner, make sure he gets it for
                // the rest
                // of the game
                if (predAtScanner) {
                        // prints to the status bar
                        uigui.changeScannerStatus("  The Predator has the scanner");

                        predatorHasScanner = true;
                        scannerNode = null;
                }

                // By default the predator does not get info from the
                // scanner
                Ship scannerShip = null;

                // If the predator has the scanner, give him a copy of
                // the ship
                if (predatorHasScanner) {
                        // make a copy of the ship
                        scannerShip = Engine.ship.clone();
                }

                // By default, the Predator cannot sense the alien
                boolean isAlienWithinSight = false;

                // By default, the Predator does not know if the
                Edge directionOfAlien = null;

                // Get the shortest path between the alien and predator
                List<Edge> shortestPath = Engine.ship.getPath(ship.getPredatorNode(), ship.getAlienNode());

                // If there is a path between the alien and the predator and the
                // distance within the predator's sensing distance
                if (shortestPath != null && shortestPath.size() <= PRED_SENSE) {

                        // Indicate that the alien is within sight
                        isAlienWithinSight = true;

                        // Store the direction of the Alien
                        directionOfAlien = shortestPath.iterator().next();

                        // prints to status bar
                        uigui.changePSenseStatus("  The Predator senses the Alien");
                }
                else {
                        uigui.changePSenseStatus("");
                }

                // Make an info class for the Alien
                Info info = new Info(ship.getPredatorNode(), allPossibleEdges, adjacentEdges, predAtControlRoom, predAtScanner, scannerShip,
                                isAlienWithinSight, directionOfAlien, null);

                // Get the predator's move
                Move predatorMove = predator.nextMove(info);

                // Validate the predator's move
                if (validateMove(predatorMove, allPossibleEdges, false, PRINT_DIAGNOSTIC)) {
                        // If valid, make the move
                        return predatorMove;
                }
                else {
                        // If invalid move then indicate a pass
                        return new Move(adjacentEdges.iterator().next(), false, false);
                }

        }

        /**
         * Checks the conditions necessary for the game to end
         */
        private static void winCondition() {

                // If the alien and the predator are on the same node
                if (ship.getAlienNode().equals(ship.getPredatorNode())) {

                        // prints to the status bar
                        uigui.changeWinStatus("  The Alien Wins");

                        // Display message indicating the Alien's victory
                        JOptionPane.showMessageDialog(uigui.getGUI(), "THE ALIEN WINS!!!!! AHHHHHH", "", JOptionPane.INFORMATION_MESSAGE);

                        // Close the program
                        System.exit(0);
                }
                // If the predator is at the control room and he has walled
                // himself off
                // from the alieen
                if (ship.getPredatorNode().equals(controlNode) && ship.getPath(ship.getPredatorNode(), ship.getAlienNode()) == null) {

                        // prints to the status bar
                        uigui.changeWinStatus("  The Predator Wins");

                        // Display message indicating the Predator's victory
                        JOptionPane.showMessageDialog(uigui.getGUI(), "THE PREDATOR IS A WINNER", "", JOptionPane.INFORMATION_MESSAGE);

                        // Close the program
                        System.exit(0);
                }
        }

        public static void makeMove(Move move, boolean alienTurn) {
                // If it is the alien's turn
                if (alienTurn) {
                        // If the alien wants to move
                        if (move.move) {
                                uigui.getGUI().drawAlien(Node.getOtherNode(move.edge, ship.getAlienNode()), ship.getAlienNode(), uigui.getGUI().getGraphics());

                                ship.setAlienNode(Node.getOtherNode(move.edge, ship.getAlienNode()));
                        }
                        // If the alien wants to burn through an edge
                        if (move.changeState) {

                                // If the alien wants to burn through a wall
                                if (move.edge.getEdgeState() == null) {

                                        // Get the two endpoints of the new edge
                                        Iterator<Node> nodes = move.edge.getAdjacent().iterator();

                                        // Create the new edge
                                        Edge edgeToAdd = new Edge(EdgeState.BURNED, ship.nodeMap.get(nodes.next().getCoordinates()), ship.nodeMap.get(nodes
                                                        .next().getCoordinates()));

                                        // Add the edge
                                        ship.addEdge(edgeToAdd);

                                        // Draw the new edge
                                        uigui.getGUI().drawLines(ship.getAlienNode(), uigui.getGUI().getGraphics());
                                        uigui.getGUI().drawLines(Node.getOtherNode(edgeToAdd, ship.getAlienNode()), uigui.getGUI().getGraphics());
                                }
                                // If the alien is burning through a door
                                else {
                                        // Replace the edge on the ship
                                        ship.changeEdge(ship.edgeMap.get(move.edge.getAdjacent()), EdgeState.BURNED);

                                        // Update the GUI
                                        uigui.getGUI().drawLines(ship.getAlienNode(), uigui.getGUI().getGraphics());
                                        uigui.getGUI().drawLines(Node.getOtherNode(ship.edgeMap.get(move.edge.getAdjacent()), ship.getAlienNode()),
                                                        uigui.getGUI().getGraphics());
                                }

                        }

                }

                // If it is the predator's turn
                else {
                        // If the predator wants to move
                        if (move.move) {

                                // Update the predator's location
                                uigui.getGUI().drawPredator(Node.getOtherNode(move.edge, ship.getPredatorNode()), ship.getPredatorNode(),
                                                uigui.getGUI().getGraphics());

                                // assign the predator to his new node
                                ship.setPredatorNode(Node.getOtherNode(move.edge, ship.getPredatorNode()));
                        }
                        // If the predator wants to change the state of a door
                        if (move.changeState) {
                                // If the edge has not already been burned by
                                // the alien
                                // Swap it from open to closed
                                if (ship.edgeMap.get(move.edge.getAdjacent()).getEdgeState() == EdgeState.OPEN) {
                                        ship.changeEdge(ship.edgeMap.get(move.edge.getAdjacent()), EdgeState.CLOSED);
                                }
                                // Swap it from closed to open
                                else if (ship.edgeMap.get(move.edge.getAdjacent()).getEdgeState() == EdgeState.CLOSED) {
                                        ship.changeEdge(ship.edgeMap.get(move.edge.getAdjacent()), EdgeState.OPEN);
                                }

                                // Update the gui
                                uigui.getGUI().drawLines(ship.getPredatorNode(), uigui.getGUI().getGraphics());
                                uigui.getGUI().drawLines(Node.getOtherNode(move.edge, ship.getPredatorNode()), uigui.getGUI().getGraphics());

                        }

                }
        }

        // Method that finds an edge in a given direction
        public static Edge findMoveEdge(Node.Dir dir, Node node) {
                return node.getDirections().get(dir);
        }

        // Method that will check to see if the move made by the player is valid
        public boolean validateMove(Move m, Set<Edge> allEdges, boolean isAlien, boolean printDebuggingInfo) {

                // Check to see the move is not null
                if (m == null) {
                        // Debugging info
                        if (printDebuggingInfo) {
                                System.out.println("The " + (isAlien ? "Alien" : "Predator") + "gave a null move");
                        }
                        // Invalid move
                        return false;
                }

                // Check to see the move is one of the valid edges
                if (!allEdges.contains(m.edge) && m.move != false && m.changeState != false) {
                        // Debugging info
                        if (printDebuggingInfo) {
                                System.out.println("The " + (isAlien ? "Alien" : "Predator") + "returned an edge that was not an option");
                        }
                        // Invalid move
                        return false;
                }

                // If it is the alien's move
                if (isAlien) {

                        // Decline to move
                        if (m.move == false && m.changeState == false) {

                                // printing to the status bar
                                uigui.changeMoveStatus("  The Alien declined to move");

                                // Debugging info
                                if (printDebuggingInfo) {
                                        System.out.println("The Alien declined to move.");
                                }
                                // Valid move
                                return true;
                        }

                        // Move through a corridor
                        if (m.move == true && m.changeState == false) {

                                // Check to see if the edge exists in the ship
                                // and it is
                                // actually an open door
                                if (m.edge != null
                                                && ship.edgeMap.containsKey(m.edge.getAdjacent())
                                                && (ship.edgeMap.get(m.edge.getAdjacent()).getEdgeState() == EdgeState.OPEN || ship.edgeMap.get(
                                                                m.edge.getAdjacent()).getEdgeState() == EdgeState.BURNED)) {

                                        // printing to the status bar
                                        uigui.changeMoveStatus("  The Alien walked through an open door");

                                        // Debugging info
                                        if (printDebuggingInfo) {
                                                System.out.println("The Alien walked through a " + ship.edgeMap.get(m.edge.getAdjacent()).getEdgeState()
                                                                + " corridor.");
                                        }
                                        // Valid move
                                        return true;
                                }

                                // The alien tried to make an invalid move
                                else {
                                        // Debugging info
                                        if (printDebuggingInfo) {
                                                System.out.println("ERROR: The Alien tried to walk down an invalid edge");
                                        }
                                        // Invalid move
                                        return false;
                                }

                        }

                        // Burn a wall, or door
                        if (m.move == false && m.changeState == true) {

                                // If the alien tries to burn through a door
                                if (m.edge != null
                                                && ship.edgeMap.containsKey(m.edge.getAdjacent())
                                                && (ship.edgeMap.get(m.edge.getAdjacent()).getEdgeState() == EdgeState.CLOSED || ship.edgeMap.get(
                                                                m.edge.getAdjacent()).getEdgeState() == EdgeState.OPEN)) {
                                        // Debugging info
                                        if (printDebuggingInfo) {
                                                System.out.println("The Alien wants to burn through a " + ship.edgeMap.get(m.edge.getAdjacent()).getEdgeState()
                                                                + " door");
                                        }
                                        // print to status panel
                                        uigui.changeMoveStatus("  The Alien burned through a closed door");

                                        // Valid Move
                                        return true;
                                }
                                // If the alien tries to burn through a wall
                                else if (m.edge != null
                                                && !ship.edgeMap.containsKey(m.edge.getAdjacent())
                                                && Ship.adjacentCoordinates(m.edge.getEndpointCoordinates()[0], m.edge.getEndpointCoordinates()[1], HEIGHT,
                                                                WIDTH)) {
                                        // Debugging info
                                        if (printDebuggingInfo) {
                                                System.out.println("The Alien wants to burn through a wall");
                                        }

                                        // print to status bar
                                        uigui.changeMoveStatus("  The Alien burned through a wall");

                                        // Valid Move
                                        return true;
                                }

                                // Debugging info
                                if (printDebuggingInfo) {
                                        System.out.println("ERROR: The Alien tried to burn through an invald edge");
                                }
                                // Invalid move
                                return false;
                        }

                        // ILLEGAL MOVE- An alien is not allowed to move and
                        if (m.move == true && m.changeState == true) {
                                if (printDebuggingInfo) {
                                        System.out.println("ERROR: The Alien tried to move and burn in the same turn.");
                                }
                                // Illegal Move
                                return false;
                        }
                }
                // If it is the Predator's Move
                else {
                        // Decline to move
                        if (m.move == false && m.changeState == false) {

                                // printing to the status bar
                                uigui.changeMoveStatus("  The Predator declined to move");

                                // Debugging info
                                if (printDebuggingInfo) {
                                        System.out.println("The Predator declined to move.");
                                }
                                // Valid move
                                return true;
                        }
                        // Move through a door without closing it behind you
                        if (m.move == true && m.changeState == false) {
                                // Check to see if the edge exists in the ship
                                // and it is
                                // actually an open door
                                if (m.edge != null
                                                && ship.edgeMap.containsKey(m.edge.getAdjacent())
                                                && (ship.edgeMap.get(m.edge.getAdjacent()).getEdgeState() == EdgeState.OPEN || ship.edgeMap.get(
                                                                m.edge.getAdjacent()).getEdgeState() == EdgeState.BURNED)) {

                                        // printing to the status bar
                                        uigui.changeMoveStatus("  The Predator walked through an open door");
                                        // Debugging info
                                        if (printDebuggingInfo) {
                                                System.out.println("The Predator walked through a " + ship.edgeMap.get(m.edge.getAdjacent()).getEdgeState()
                                                                + " corridor.");
                                        }
                                        // Valid move
                                        return true;
                                }

                                // The predator tried to make an invalid move
                                else {
                                        // Debugging info
                                        if (printDebuggingInfo) {
                                                System.out.println("ERROR: The Predator tried to walk down an invalid edge");
                                        }
                                        // Invalid move
                                        return false;
                                }
                        }
                        // Open/Close a door
                        if (m.move == false && m.changeState == true) {

                                // Check to see if the edge is a door (open or
                                // closed)
                                if (m.edge != null
                                                && ship.edgeMap.containsKey(m.edge.getAdjacent())
                                                && (ship.edgeMap.get(m.edge.getAdjacent()).getEdgeState() == EdgeState.OPEN || ship.edgeMap.get(
                                                                m.edge.getAdjacent()).getEdgeState() == EdgeState.CLOSED)) {

                                        // printing to the status bar
                                        uigui.changeMoveStatus("  The Predator "
                                                        + (ship.edgeMap.get(m.edge.getAdjacent()).getEdgeState() == EdgeState.OPEN ? "closed" : "opened")
                                                        + " a door");

                                        // Debugging info
                                        if (printDebuggingInfo) {
                                                System.out.println("The Predator "
                                                                + (ship.edgeMap.get(m.edge.getAdjacent()).getEdgeState() == EdgeState.OPEN ? "closed"
                                                                                : "opened") + " a door");
                                        }
                                        return true;
                                }

                                else {
                                        // Debugging info
                                        if (printDebuggingInfo) {
                                                System.out.println("ERROR: The Predator tried to close a wall or a burned hole.");
                                        }
                                        // Invalid move
                                        return false;
                                }

                        }
                        // Go through door and close it behind you
                        if (m.move == true && m.changeState == true) {
                                // Check to see if it is an open door
                                if (m.edge != null && ship.edgeMap.containsKey(m.edge.getAdjacent())
                                                && ship.edgeMap.get(m.edge.getAdjacent()).getEdgeState() == EdgeState.OPEN) {

                                        // printing to the status bar
                                        uigui.changeMoveStatus("  The Predator walked through and closed a door");

                                        // Debugging info
                                        if (printDebuggingInfo) {
                                                System.out.println("The Predator walked through a door and closed the door behind it");
                                        }
                                        // Valid move
                                        return true;
                                }
                                // The predator tried to make an invalid move
                                else {
                                        // Debugging info
                                        if (printDebuggingInfo) {
                                                System.out.println("ERROR: The Predator tried to open a go through and close an invalid edge");
                                        }
                                        // Invalid move
                                        return false;
                                }
                        }

                }

                return false;
        }

        public static void pause() {
                // Pause the game to make it look like they are taking turns
                try {
                        Thread.sleep(speed);
                } catch (InterruptedException e1) {
                        e1.printStackTrace();
                }
        }

        //
        //
        //
        //
        //
        //
        //
        // ----------------------------------------------------------
        // ATTENTION-------------------------------------------------
        // EVERYTHING PAST THIS POINT--------------------------------
        // IS FOR KARMA ONLY-----------------------------------------
        // NOT PART OF FINAL RELEASE---------------------------------
        // ----------------------------------------------------------
        //
        //
        //
        //
        //

        // Burns the edge
        public static Edge makeBurnEdge(Node.Dir dir) {
                Node nodeOne = ship.getAlienNode();

                // find second node using connected ship
                Node connectedNodeOne = connectedShip.getNodeAt(nodeOne.getRow(), nodeOne.getCol());
                Edge connectedMoveEdge = connectedShip.getNodeAt(nodeOne.getRow(), nodeOne.getCol()).getDirections().get(dir);
                Node connectedNodeTwo = Node.getOtherNode(connectedMoveEdge, connectedNodeOne);
                // find corresponding node in actual ship
                Node nodeTwo = ship.getNodeAt(connectedNodeTwo.getRow(), connectedNodeTwo.getCol());

                Set<Node> nodes = new HashSet<Node>();
                nodes.add(nodeOne);
                nodes.add(nodeTwo);
                Edge shipEdge = ship.edgeMap.get(nodes);
                Edge moveEdge;
                if (shipEdge == null) {
                        moveEdge = new Edge(null, nodeOne, nodeTwo);
                }
                else {
                        moveEdge = shipEdge;
                }

                return moveEdge;
        }

        /**
         * Move class for the AI. Checks which agent passed the move and if it
         * is a valid move. If it is, executes the move
         * 
         * @param move
         * @param alien
         */
        public static void move(Move move, boolean alien) {
                // If it is the alien's move
                if (alien) {
                        // the alien is the agent passing the move
                        if (move.move) {
                                // the alien moves to another node
                                uigui.getGUI().drawAlien(Node.getOtherNode(move.edge, ship.getAlienNode()), ship.getAlienNode(), uigui.getGUI().getGraphics());

                                ship.setAlienNode(Node.getOtherNode(move.edge, ship.getAlienNode()));

                                // check if alien is on the scanner node
                                if (ship.getAlienNode().equals(scannerNode)) {
                                        alienHasScanner = true;
                                }
                        }

                        if (move.changeState) {
                                // the alien will change the state of an
                                // adjacent edge
                                if (move.edge.getEdgeState() == null) {
                                        // the adjacent edge is a wall
                                        Iterator<Node> nodes = move.edge.getAdjacent().iterator();
                                        Edge edgeToAdd = new Edge(EdgeState.BURNED, ship.nodeMap.get(nodes.next().getCoordinates()), ship.nodeMap.get(nodes
                                                        .next().getCoordinates()));

                                        ship.addEdge(edgeToAdd);
                                        uigui.getGUI().drawLines(ship.getAlienNode(), uigui.getGUI().getGraphics());
                                        uigui.getGUI().drawLines(Node.getOtherNode(edgeToAdd, ship.getAlienNode()), uigui.getGUI().getGraphics());

                                }
                                else {
                                        // the adjacent edge is closed
                                        Iterator<Node> nodes = move.edge.getAdjacent().iterator();
                                        Edge edgeToAdd = new Edge(EdgeState.BURNED, ship.nodeMap.get(nodes.next().getCoordinates()), ship.nodeMap.get(nodes
                                                        .next().getCoordinates()));
                                        ship.changeEdge(move.edge, EdgeState.BURNED);
                                        uigui.getGUI().drawLines(ship.getAlienNode(), uigui.getGUI().getGraphics());
                                        uigui.getGUI().drawLines(Node.getOtherNode(edgeToAdd, ship.getAlienNode()), uigui.getGUI().getGraphics());

                                }

                        }
                }
                else {
                        // the predator is passing the move
                        if (!(move.move && move.changeState)) {

                        }

                        if (move.move) {
                                // the predator moves to an adjacent node
                                uigui.getGUI().drawPredator(Node.getOtherNode(move.edge, ship.getPredatorNode()), ship.getPredatorNode(),
                                                uigui.getGUI().getGraphics());

                                ship.setPredatorNode(Node.getOtherNode(move.edge, ship.getPredatorNode()));
                                if (ship.getPredatorNode().equals(scannerNode)) {
                                        predatorHasScanner = true;
                                }
                        }
                        if (move.changeState) {
                                // the alien changes the state of an adjacent
                                // edge
                                if (move.edge.getEdgeState() == EdgeState.OPEN) {
                                        // closes an open door
                                        ship.removeEdge(move.edge);
                                        ship.addEdge(new Edge(EdgeState.CLOSED, ship.getPredatorNode(), Node.getOtherNode(move.edge, ship.getPredatorNode())));
                                        uigui.getGUI().drawLines(ship.getPredatorNode(), uigui.getGUI().getGraphics());
                                        uigui.getGUI().drawLines(Node.getOtherNode(move.edge, ship.getPredatorNode()), uigui.getGUI().getGraphics());
                                        if (!move.move) {

                                        }

                                }
                                else if (move.edge.getEdgeState() == EdgeState.CLOSED) {
                                        // opens a closed door
                                        ship.removeEdge(move.edge);
                                        ship.addEdge(new Edge(EdgeState.OPEN, ship.getPredatorNode(), Node.getOtherNode(move.edge, ship.getPredatorNode())));
                                        uigui.getGUI().drawLines(ship.getPredatorNode(), uigui.getGUI().getGraphics());
                                        uigui.getGUI().drawLines(Node.getOtherNode(move.edge, ship.getPredatorNode()), uigui.getGUI().getGraphics());

                                }
                        }

                }
        }

        /**
         * Move method for the human controls
         * 
         * @param dir
         * @param e
         */
        public static void move(int dir, KeyEvent e) {
                winCondition();
                switch (dir) {

                case 0: // left
                        if (Engine.move < SPEED_RATIO) { // alien
                                if (e.isShiftDown()) {// make edge to burrrrnnn
                                        Edge moveEdge = makeBurnEdge(Dir.LEFT);
                                        Engine.move(new Move(moveEdge, false, true), true);
                                        Engine.move = Engine.move + BURN_TIME;
                                }
                                else {
                                        Edge moveEdge = findMoveEdge(Dir.LEFT, ship.getAlienNode());
                                        if (moveEdge != null) {
                                                Move moveToMove = new Move(moveEdge, true, false);

                                                Engine.move++;

                                                Engine.move(moveToMove, true);

                                        }
                                }
                        }
                        else {
                                Edge moveEdge = ship.getPredatorNode().getDirections().get(Dir.LEFT);
                                if (e.isShiftDown()) {// open/close door

                                        // check if he's trying to open/close a
                                        // wall...silly
                                        // predator
                                        if (moveEdge == null) {
                                                System.out.println("predator tried to open/close a wall");
                                        }
                                        else {
                                                Move moveToMove = new Move(moveEdge, false, true);
                                                Engine.move(moveToMove, false);
                                                if (moveEdge.getEdgeState() == EdgeState.OPEN) {
                                                        // alien takes his
                                                        // allotted number of
                                                        // turns
                                                        Engine.move = Engine.move - SPEED_RATIO;
                                                }
                                                else {
                                                        // alien takes his
                                                        // allotted number of
                                                        // turns
                                                        // (OPENTIME) times
                                                        Engine.move = Engine.move - (SPEED_RATIO * OPEN_TIME);
                                                }
                                        }
                                }
                                else {
                                        Move moveToMove;
                                        // trying to walk through a wall?
                                        if (moveEdge == null) {
                                                System.out.println("predator tried to walk through a wall...silly predator");
                                        }
                                        else {
                                                if (e.isControlDown()) {// move
                                                                        // and
                                                                        // close
                                                                        // door
                                                                        // behind
                                                                        // him
                                                        moveToMove = new Move(moveEdge, true, true);
                                                }
                                                else {
                                                        moveToMove = new Move(moveEdge, true, false);
                                                }

                                                Engine.move(moveToMove, false);
                                                Engine.move = Engine.move - SPEED_RATIO;
                                        }
                                }
                        }
                        break;
                case 1: // up
                        if (Engine.move < SPEED_RATIO) { // alien
                                if (e.isShiftDown()) {// make edge to burrrrnnn
                                        Edge moveEdge = makeBurnEdge(Dir.UP);
                                        Engine.move(new Move(moveEdge, false, true), true);
                                        Engine.move = Engine.move + BURN_TIME;
                                }
                                else {
                                        Edge moveEdge = findMoveEdge(Dir.UP, ship.getAlienNode());
                                        if (moveEdge != null) {
                                                Move moveToMove = new Move(moveEdge, true, false);
                                                Engine.move++;
                                                Engine.move(moveToMove, true);

                                        }
                                }
                        }
                        else {
                                Edge moveEdge = ship.getPredatorNode().getDirections().get(Dir.UP);
                                if (e.isShiftDown()) {// open/close door

                                        // check if he's trying to open/close a
                                        // wall...silly
                                        // predator
                                        if (moveEdge == null) {
                                                System.out.println("predator tried to open/close a wall");
                                        }
                                        else {
                                                Move moveToMove = new Move(moveEdge, false, true);
                                                Engine.move(moveToMove, false);
                                                if (moveEdge.getEdgeState() == EdgeState.OPEN) {
                                                        // alien takes his
                                                        // allotted number of
                                                        // turns
                                                        Engine.move = Engine.move - SPEED_RATIO;
                                                }
                                                else {
                                                        // alien takes his
                                                        // allotted number of
                                                        // turns
                                                        // (OPENTIME) times
                                                        Engine.move = Engine.move - (SPEED_RATIO * OPEN_TIME);
                                                }
                                        }
                                }
                                else {
                                        Move moveToMove;
                                        // trying to walk through a wall?
                                        if (moveEdge == null) {
                                                System.out.println("predator tried to walk through a wall...silly predator");
                                        }
                                        else {
                                                if (e.isControlDown()) {// move
                                                                        // and
                                                                        // close
                                                                        // door
                                                                        // behind
                                                                        // him
                                                        moveToMove = new Move(moveEdge, true, true);
                                                }
                                                else {
                                                        moveToMove = new Move(moveEdge, true, false);
                                                }

                                                Engine.move(moveToMove, false);
                                                Engine.move = Engine.move - SPEED_RATIO;
                                        }
                                }
                        }
                        break;
                case 2: // right
                        if (Engine.move < SPEED_RATIO) { // alien
                                if (e.isShiftDown()) {// make edge to burrrrnnn
                                        Edge moveEdge = makeBurnEdge(Dir.RIGHT);
                                        Engine.move(new Move(moveEdge, false, true), true);
                                        Engine.move = Engine.move + BURN_TIME;
                                }
                                else {
                                        Edge moveEdge = findMoveEdge(Dir.RIGHT, ship.getAlienNode());
                                        if (moveEdge != null) {
                                                Move moveToMove = new Move(moveEdge, true, false);
                                                Engine.move++;
                                                Engine.move(moveToMove, true);

                                        }
                                }
                        }
                        else {
                                Edge moveEdge = ship.getPredatorNode().getDirections().get(Dir.RIGHT);
                                if (e.isShiftDown()) {// open/close door

                                        // check if he's trying to open/close a
                                        // wall...silly
                                        // predator
                                        if (moveEdge == null) {
                                                System.out.println("predator tried to open/close a wall");
                                        }
                                        else {
                                                Move moveToMove = new Move(moveEdge, false, true);
                                                Engine.move(moveToMove, false);
                                                if (moveEdge.getEdgeState() == EdgeState.OPEN) {
                                                        // alien takes his
                                                        // allotted number of
                                                        // turns
                                                        Engine.move = Engine.move - SPEED_RATIO;
                                                }
                                                else {
                                                        // alien takes his
                                                        // allotted number of
                                                        // turns
                                                        // (OPENTIME) times
                                                        Engine.move = Engine.move - (SPEED_RATIO * OPEN_TIME);
                                                }
                                        }
                                }
                                else {
                                        Move moveToMove;
                                        // trying to walk through a wall?
                                        if (moveEdge == null) {
                                                System.out.println("predator tried to walk through a wall...silly predator");
                                        }
                                        else {
                                                if (e.isControlDown()) {// move
                                                                        // and
                                                                        // close
                                                                        // door
                                                                        // behind
                                                                        // him
                                                        moveToMove = new Move(moveEdge, true, true);
                                                }
                                                else {
                                                        moveToMove = new Move(moveEdge, true, false);
                                                }

                                                Engine.move(moveToMove, false);
                                                Engine.move = Engine.move - SPEED_RATIO;
                                        }
                                }
                        }
                        break;
                case 3: // down
                        if (Engine.move < SPEED_RATIO) { // alien
                                if (e.isShiftDown()) {// make edge to burrrrnnn
                                        Edge moveEdge = makeBurnEdge(Dir.DOWN);
                                        Engine.move(new Move(moveEdge, false, true), true);
                                        Engine.move = Engine.move + BURN_TIME;
                                }
                                else {
                                        Edge moveEdge = findMoveEdge(Dir.DOWN, ship.getAlienNode());
                                        if (moveEdge != null) {
                                                Move moveToMove = new Move(moveEdge, true, false);
                                                Engine.move++;
                                                Engine.move(moveToMove, true);

                                        }
                                }
                        }
                        else {
                                Edge moveEdge = ship.getPredatorNode().getDirections().get(Dir.DOWN);
                                if (e.isShiftDown()) {// open/close door

                                        // check if he's trying to open/close a
                                        // wall...silly
                                        // predator
                                        if (moveEdge == null) {
                                                System.out.println("predator tried to open/close a wall");
                                        }
                                        else {
                                                Move moveToMove = new Move(moveEdge, false, true);
                                                Engine.move(moveToMove, false);
                                                if (moveEdge.getEdgeState() == EdgeState.OPEN) {
                                                        // alien takes his
                                                        // allotted number of
                                                        // turns
                                                        Engine.move = Engine.move - SPEED_RATIO;
                                                }
                                                else {
                                                        // alien takes his
                                                        // allotted number of
                                                        // turns
                                                        // (OPENTIME) times
                                                        Engine.move = Engine.move - (SPEED_RATIO * OPEN_TIME);
                                                }
                                        }
                                }
                                else {
                                        Move moveToMove;
                                        // trying to walk through a wall?
                                        if (moveEdge == null) {
                                                System.out.println("predator tried to walk through a wall...silly predator");
                                        }
                                        else {
                                                if (e.isControlDown()) {// move
                                                                        // and
                                                                        // close
                                                                        // door
                                                                        // behind
                                                                        // him
                                                        moveToMove = new Move(moveEdge, true, true);
                                                }
                                                else {
                                                        moveToMove = new Move(moveEdge, true, false);
                                                }

                                                Engine.move(moveToMove, false);
                                                Engine.move = Engine.move - SPEED_RATIO;
                                        }
                                }
                        }
                        break;
                }
        }

}