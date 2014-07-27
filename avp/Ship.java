/**********************************
 * Assignment 5: AVP
 * Agent.java
 * Date: 12/4/2011
 *
 * Jon Hamlin: jwh244, 2400666
 * Brooks Hoffecker: bjh83, 2015719
 * Evan Long: erl43, 2300076
 * Richard McCaffrey: rpm77, 2494167
 **********************************/
package avp;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import ugraph.UGraph;
import avp.Node.Dir;
import edu.cornell.cs.cs2110.RandomBag;

public class Ship implements UGraph<Node, Edge>, GameConstants {

        // Checks to see if two nodes are adjacent
        public static boolean adjacentCoordinates(Coordinates c1, Coordinates c2, int height, int width) {

                // If they are in the same row
                if (c1.row == c2.row) {

                        // If they are one to the left or one to the right of
                        // each other
                        if ((c1.col + 1) % width == c2.col || (c2.col + 1) % width == c1.col) {
                                return true;
                        }

                }
                // If they are in the same column
                if (c1.col == c2.col) {

                        // If they are one above or below each other
                        if ((c1.row + 1) % height == c2.row || (c2.row + 1) % height == c1.row) {
                                return true;
                        }
                }

                return false;

        }

        // Method that will create a completely connected graph that is the
        // right
        // size for the game
        public static Ship completelyConnectedGraph() {
                return completelyConnectedGraph(HEIGHT, WIDTH);
        }

        // Method that will create a new ship that is completely connected
        public static Ship completelyConnectedGraph(int height, int width) {

                // Create the new ship
                Ship maxShip = new Ship();

                // Create a 2D array to assist with creating the
                Node[][] nodes = new Node[height][width];

                // Create a list to store the edges
                List<Edge> edges = new LinkedList<Edge>();

                // Go through the entire 2D array
                for (int row = 0; row < height; row++) {
                        for (int col = 0; col < width; col++) {

                                // Initialize a Node at every cell
                                nodes[row][col] = new Node(row, col);

                                // Add that Node to the ship
                                maxShip.addNode(nodes[row][col]);
                        }
                }

                // Go through the entire 2D array
                for (int row = 0; row < height; row++) {
                        for (int col = 0; col < width; col++) {
                                // Add an edge for the node to right
                                edges.add(new Edge(EdgeState.OPEN, nodes[row][col], nodes[row][(col + 1) % width]));
                                // Add an edge for the node to the down
                                edges.add(new Edge(EdgeState.OPEN, nodes[row][col], nodes[(row + 1) % height][col]));
                        }
                }

                // Add every edge to the ship
                for (Edge e : edges) {
                        maxShip.addEdge(e);
                }

                // Return the completed ship
                return maxShip;
        }

        /**
         * Method that gets every possible edge from given coordinates
         * 
         * @param c
         * @param ship
         * @param completelyConnectedShip
         * @return
         */
        public static Set<Edge> getAll(Coordinates c, Ship ship, Ship completelyConnectedShip) {
                Set<Edge> adj = new HashSet<Edge>(ship.nodeMap.get(c).getAdjacent());

                Set<Set<Node>> adjNodeSets = new HashSet<Set<Node>>();

                for (Edge e : adj) {
                        adjNodeSets.add(e.getAdjacent());
                }

                Set<Edge> all = completelyConnectedShip.nodeMap.get(c).getAdjacent();

                for (Edge e : all) {

                        if (!adjNodeSets.contains(e.getAdjacent())) {
                                Iterator<Node> tempNodes = e.getAdjacent().iterator();
                                adj.add(new Edge(null, tempNodes.next(), tempNodes.next()));
                        }

                }

                return adj;
        }

        // Unweighed Dijkstra's Algorithm. Takes the ship, the starting point,
        // the
        // ending point, and the allowable edge types
        private static List<Edge> getPathUnweighted(Ship ship, Node start, Node end, Set<EdgeState> acceptableEdges) {

                // Make sure the two nodes are in in the ship
                if (!(ship.nodeMap.containsValue(start) && ship.nodeMap.containsValue(end))) {
                        throw new IllegalArgumentException("Both nodes not contained in the graph");
                }

                // Get the first node
                Node firstNode = ship.nodeMap.get(start.getCoordinates());

                // Create the map to store info for each node
                Map<Node, DijkstraInfo> nodeInfo = new HashMap<Node, DijkstraInfo>();

                // Put the first node in the node info
                nodeInfo.put(firstNode, new DijkstraInfo(0));
                // Create Queue and add the first node to the queue
                Queue<Node> toDo = new LinkedList<Node>();
                toDo.add(firstNode);

                // While there are still nodes left in the queue
                while (toDo.size() > 0) {

                        // Remove a node
                        Node currentNode = toDo.remove();

                        // Get each of the adjacent nodes
                        Set<NodeEdgePair> adjacentNodes = currentNode.getAdjacentNodeEdgePair();

                        // For every adjacent node
                        for (NodeEdgePair nep : adjacentNodes) {
                                // If the edge is an acceptable edge to count
                                if (acceptableEdges.contains(nep.edge.getEdgeState())) {
                                        // If the node is not already in the map
                                        if (!nodeInfo.containsKey(nep.node)) {
                                                // Add the node to the map and
                                                // store its distance,
                                                // previous
                                                // node, and previous edge
                                                nodeInfo.put(nep.node, new DijkstraInfo(nodeInfo.get(currentNode).distance + 1, currentNode, nep.edge));

                                                // Put the node in the Queue
                                                toDo.add(nep.node);

                                        }
                                        // If it already in the map
                                        else {

                                                // Current best distance
                                                DijkstraInfo currentBest = nodeInfo.get(nep.node);
                                                int newDistance = (nodeInfo.get(currentNode).distance + 1);

                                                // If the current node allows a
                                                // faster path to the node
                                                if (currentBest.distance > newDistance) {
                                                        // Then update the best
                                                        // path
                                                        currentBest.distance = newDistance;
                                                        currentBest.bestPrevNode = currentNode;
                                                        currentBest.bestPrevEdge = nep.edge;
                                                }

                                        }
                                }

                        }

                }

                // If the Dijkstra search did not find the end point. Return
                // null to
                // indicate no path has been found
                if (!nodeInfo.containsKey(ship.nodeMap.get(end.getCoordinates()))) {
                        return null;

                }

                else {

                        // Best Path list
                        List<Edge> bestPath = new LinkedList<Edge>();

                        // Current node (starts with the last node
                        Node currentNode = ship.nodeMap.get(end.getCoordinates());

                        // While it has not made it to the first node
                        while (!currentNode.equals(firstNode)) {

                                // Add the edge to the best path at the
                                // beginning
                                bestPath.add(0, nodeInfo.get(currentNode).bestPrevEdge);
                                // Change current node
                                currentNode = nodeInfo.get(currentNode).bestPrevNode;
                        }

                        return bestPath;
                }

        }

        // Method that makes a regulation ship
        public static Ship makeGameShip() {

                // Calculate the correct number of extra edges
                int edges = 3 * (int) (Math.sqrt(HEIGHT * WIDTH));

                // Create a completely connected graph and then get the spanning
                // tree
                // from it
                Ship ship = ((Ship) Ship.completelyConnectedGraph().spanningTree());

                // Add the random edges to the ship
                ship.addRandomEdges(edges, HEIGHT, WIDTH);

                // Return the ship
                return ship;
        }

        // Set of nodes and edges
        Map<Coordinates, Node> nodeMap;

        Map<Set<Node>, Edge> edgeMap;

        // Alien and predator node
        private Node alienNode;

        private Node predatorNode;

        Ship() {

                // Initialize the node and edge map
                nodeMap = new HashMap<Coordinates, Node>();
                edgeMap = new HashMap<Set<Node>, Edge>();

        }

        // Constructor used for the clone method
        Ship(Map<Coordinates, Node> nodeMap, Map<Set<Node>, Edge> edgeMap, Node alienNode, Node predatorNode) {

                if (nodeMap != null) {
                        this.nodeMap = new HashMap<Coordinates, Node>(nodeMap);
                }
                else {
                        this.nodeMap = null;
                }

                if (edgeMap != null) {
                        this.edgeMap = new HashMap<Set<Node>, Edge>(edgeMap);
                }
                else {
                        this.edgeMap = null;
                }

                if (alienNode != null) {
                        this.alienNode = alienNode.clone();
                }
                else {
                        this.alienNode = null;
                }

                if (predatorNode != null) {
                        this.predatorNode = predatorNode.clone();
                }
                else {
                        this.predatorNode = null;
                }

        }

        // Method to add an edge to the ship
        @Override
        public void addEdge(Edge edge) {

                // check to see if it is a valid edge
                if (!edge.validGameEdge()) {
                        throw new IllegalArgumentException("Invalid edge inserted.  Edges must have both of their endpoints defined.");
                }

                // Check to see that both nodes have been added to the ship
                for (Node n : edge.getAdjacent()) {
                        if (!nodeMap.containsValue(n)) {
                                throw new IllegalStateException("Both nodes must be added before an edge can be added");
                        }
                }

                // Check to see if there are no contradictions
                // For each node in the edge
                for (Node n : edge.getAdjacent()) {
                        // For each edge in each one of those nodes
                        for (Edge e : nodeMap.get(n.getCoordinates()).getAdjacent()) {
                                // If there is an edge that connects the same
                                // two nodes as the one being added
                                if (e.getAdjacent().equals(edge.getAdjacent())) {
                                        // They better match edge states or else
                                        // there is a contradiction
                                        if (!e.getEdgeState().equals(edge.getEdgeState())) {
                                                throw new IllegalStateException("There is a contradiction in the edge states");
                                        }
                                }
                        }

                }

                // Add the edge to both nodes
                for (Node n : edge.getAdjacent()) {
                        nodeMap.get(n.getCoordinates()).addEdge(edge);
                }

                // Add the edge to the edgeMap
                edgeMap.put(edge.getAdjacent(), edge);

        }

        // Method to add a node to the ship
        @Override
        public void addNode(Node node) {

                // Check to see if it is a valid node
                if (!node.validGameNode()) {
                        // Throw an error if an invalid node was attempted to be
                        // added
                        throw new IllegalArgumentException("Invalid node inserted.  Node must have its coordinates set and a valid number of edges.");
                }

                // Check to see if it is already inserted
                if (containsNodeAtCoordinates(node)) {
                        // Throw an error if the node has already been inserted
                        throw new IllegalArgumentException("There is already a node at the given coordinates");
                }

                // Add the node
                nodeMap.put(node.getCoordinates(), node);

                // Add all of the edges contained in the node
                for (Edge e : node.getAdjacent()) {
                        addEdge(e);
                }

        }

        // Method that adds random edges to a ship. Must receive the ship's
        // correct
        // height and width
        public void addRandomEdges(int numOfEdges, int height, int width) {

                // A random bag for storing the possible edges
                RandomBag<Edge> possibleEdges = new RandomBag<Edge>();

                // For every node in the ship
                for (Node n : nodeMap.values()) {

                        // Get nodes that point in each direction
                        Map<Dir, Edge> directions = n.getDirections();

                        // If there is not an edge pointing right
                        if (!edgeMap.containsKey(new Edge(EdgeState.OPEN, n, getNodeAt(n.getRow(), (n.getCol() + 1) % width)).getAdjacent())) {

                                // Add an edge pointing right to the random bag
                                possibleEdges.insert(new Edge(EdgeState.OPEN, n, getNodeAt(n.getRow(), (n.getCol() + 1) % width)));

                        }

                        // If there is not an edge pointing down
                        if (!edgeMap.containsKey(new Edge(EdgeState.OPEN, n, getNodeAt((n.getRow() + 1) % height, n.getCol())).getAdjacent())) {

                                // Add an edge pointing down to the random bag
                                possibleEdges.insert(new Edge(EdgeState.OPEN, n, getNodeAt((n.getRow() + 1) % height, n.getCol())));

                        }
                }

                // Remove a given number of edges from the bag and add them to
                // the ship
                for (int i = 0; i < numOfEdges; i++) {

                        try {
                                // Add the edge to the ship
                                addEdge(possibleEdges.extract());
                        } catch (Exception e) {
                                throw new IllegalStateException("Cannot add that many edges");
                        }
                }

        }

        // Change the state of an edge
        public void changeEdge(Edge edgeToRemove, EdgeState newState) {
                // If the edge does not exist then throw an error
                if (!edgeMap.containsValue(edgeToRemove)) {
                        throw new IllegalStateException("Tried to change a nonexistent edge");
                }

                // Remove the old edge
                removeEdge(edgeToRemove);

                // Get an iterator for the edges
                Iterator<Node> nodes = edgeToRemove.getAdjacent().iterator();

                // Replace the edge
                addEdge(new Edge(newState, nodes.next(), nodes.next()));

        }

        // Clone the ship
        @Override
        public Ship clone() {

                // Create new hashmap for the new nodes
                Map<Coordinates, Node> nodes = new HashMap<Coordinates, Node>();

                // Put transfer each node to the new hashmap
                for (Node n : nodeMap.values()) {
                        Coordinates coords = n.getCoordinates();
                        nodes.put(coords, new Node(coords.row, coords.col));
                }

                // New Edge Map
                Map<Set<Node>, Edge> edges = new HashMap<Set<Node>, Edge>();

                // Transfer all the edges to the new ship
                for (Edge e : edgeMap.values()) {
                        Set<Node> oldNodes = e.getAdjacent();
                        Iterator<Node> I = oldNodes.iterator();
                        Node nodeOne = I.next();
                        Node nodeTwo = I.next();
                        Node newNodeOne = nodes.get(new Coordinates(nodeOne.getCoordinates().row, nodeOne.getCoordinates().col));
                        Node newNodeTwo = nodes.get(new Coordinates(nodeTwo.getCoordinates().row, nodeTwo.getCoordinates().col));
                        Set<Node> newNodes = new HashSet<Node>();
                        newNodes.add(newNodeOne);
                        newNodes.add(newNodeTwo);
                        Edge newEdge = new Edge(e.getEdgeState(), newNodeOne, newNodeTwo);
                        edges.put(newNodes, newEdge);
                        newNodeOne.addEdge(newEdge);
                        newNodeTwo.addEdge(newEdge);
                }
                return new Ship(nodes, edges, alienNode, predatorNode);

        }

        // Does the list already contain the node at the given coordinates
        public boolean containsNodeAtCoordinates(Node node) {
                return nodeMap.containsKey(node.getCoordinates());
        }

        public Node getAlienNode() {
                return alienNode;
        }

        // Get path will get the quickest path through open doors, burnt
        // walls, and doors
        public List<Edge> getAnyPath(Node start, Node end) {
                // Acceptable edges for the getPath to go through
                Set<EdgeState> openAndBurnAndClosed = new HashSet<EdgeState>();
                openAndBurnAndClosed.add(EdgeState.OPEN);
                openAndBurnAndClosed.add(EdgeState.BURNED);
                openAndBurnAndClosed.add(EdgeState.CLOSED);

                return getPathUnweighted(this, start, end, openAndBurnAndClosed);
        }

        // Returns all of the edges on the map
        @Override
        public Set<Edge> getEdges() {
                return new HashSet<Edge>(edgeMap.values());
        }

        // Get a node at given coordinates
        public Node getNodeAt(Coordinates c) {

                return nodeMap.get(c);

        }

        // Get a node at a given row and column
        public Node getNodeAt(int row, int col) {

                return nodeMap.get(new Coordinates(row, col));

        }

        // Return all of the nodes on the map
        @Override
        public Set<Node> getNodes() {
                return new HashSet<Node>(nodeMap.values());
        }

        // Get path will get the quickest path through open doors and burnt
        // walls
        @Override
        public List<Edge> getPath(Node start, Node end) {
                // Acceptable edges for the getPath to go through
                Set<EdgeState> openAndBurn = new HashSet<EdgeState>();
                openAndBurn.add(EdgeState.OPEN);
                openAndBurn.add(EdgeState.BURNED);

                return getPathUnweighted(this, start, end, openAndBurn);
        }

        public Node getPredatorNode() {
                return predatorNode;
        }

        // Print out the nodes in the ship. For debugging purposes
        public void printNodes() {
                for (Node n : nodeMap.values()) {
                        System.out.println(n);
                }
        }

        // Remove an edge from the map
        @Override
        public void removeEdge(Edge edge) {

                // Remove the edge from all effected nodes
                for (Node node : edge.getAdjacent()) {
                        nodeMap.get(node.getCoordinates()).getAdjacent().remove(edge);
                }

                // Remove the edge from the edgemap
                edgeMap.remove(edge.getAdjacent());

        }

        // This method will go to the node at the coordinates in the NodeMap
        @Override
        public void removeNode(Node node) {

                // Check to see if the Node is contained in the NodeMap
                if (!nodeMap.containsValue(node)) {
                        // If it is not contained then throw an error for trying
                        // to remove a nonexistent node
                        throw new IllegalStateException("Tried to remove a nonexistent node.  Remember that Nodes are defined by their coordinates.");
                }

                // For each edge stored in the node, remove that edge from
                // everywhere it is stored
                for (Edge edge : nodeMap.get(node.getCoordinates()).getAdjacent()) {
                        removeEdge(edge);
                }

                // Remove the node
                nodeMap.remove(node.getCoordinates());

        }

        public void setAlienNode(Node alienNode) {
                this.alienNode = alienNode;
        }

        public void setPredatorNode(Node predatorNode) {
                this.predatorNode = predatorNode;
        }

        // Creates a spanning tree of the ship
        @Override
        public UGraph<Node, Edge> spanningTree() {

                // Holder data structures

                // Nodes that have yet to be added
                Set<Node> toDoNodes = new HashSet<Node>(nodeMap.values());

                // Nodes that are done
                Map<Coordinates, Node> doneNodes = new HashMap<Coordinates, Node>();

                // All the possible edges
                Set<Edge> allPossibleEdges = new HashSet<Edge>(edgeMap.values());

                // Final min spanning edges
                List<Edge> minEdges = new LinkedList<Edge>();

                // RandomBag
                RandomBag<Edge> edgeBag = new RandomBag<Edge>();

                // The starter node
                Node starterNode = toDoNodes.iterator().next();

                // Remove the starter node from the to do list
                toDoNodes.remove(starterNode);
                // Put starter node in the done nodes
                doneNodes.put(starterNode.getCoordinates(), new Node(starterNode.getRow(), starterNode.getCol()));

                // Put the first node's edges in the bag
                for (Edge e : starterNode.getAdjacent()) {
                        if (allPossibleEdges.remove(e)) {
                                Iterator<Node> nodes = e.getAdjacent().iterator();
                                edgeBag.insert(new Edge(e.getEdgeState(), nodes.next(), nodes.next()));
                        }
                }

                // while every node is not connected
                while (toDoNodes.size() > 0) {

                        if (edgeBag.size() == 0) {
                                throw new IllegalStateException("Not enough edges to create a spanning tree");
                        }

                        // Remove an edge from the bag
                        Edge edge = edgeBag.extract();

                        // Check both end points of the edge
                        for (Node n : edge.getAdjacent()) {
                                // If an end point is not in the done Node set
                                if (!doneNodes.containsValue(n)) {

                                        // Add the edge to the done pile
                                        minEdges.add(edge);

                                        // Put node in the done nodes pile
                                        toDoNodes.remove(n);
                                        doneNodes.put(n.getCoordinates(), new Node(n.getRow(), n.getCol()));

                                        // Add the node's edges to the bag
                                        for (Edge e : n.getAdjacent()) {
                                                if (allPossibleEdges.remove(e)) {
                                                        edgeBag.insert(e);
                                                }
                                        }

                                }

                        }

                }

                // Create the new ship
                Ship spanShip = new Ship();

                // Add all of the nodes to the ship
                for (Node n : doneNodes.values()) {
                        spanShip.addNode(n);
                }

                // For each edge to add to the ship
                for (Edge e : minEdges) {

                        // Iterator for the nodes
                        Iterator<Node> nodes = e.getAdjacent().iterator();

                        // Add the edge to the ship
                        spanShip.addEdge(new Edge(e.getEdgeState(), doneNodes.get(nodes.next().getCoordinates()), doneNodes.get(nodes.next().getCoordinates())));

                }

                // Return the spanning tree ship
                return spanShip;
        }

}
