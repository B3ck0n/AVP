/**********************************
 * Assignment 5: AVP
 * Node.java
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
import java.util.Map;
import java.util.Set;

import ugraph.UGraphComponent;

public class Node implements UGraphComponent<Edge>, GameConstants {

        /**
         * enum types are UP, DOWN, LEFT, RIGHT
         * 
         * @author
         */
        public enum Dir {
                UP, DOWN, LEFT, RIGHT;
        }

        /**
         * Gets a pair of (Node, Edge) adjacent to the inputted node
         * 
         * @param n
         * @return Set<NodeEdgePair>
         */
        public static Set<NodeEdgePair> getAdjacentNodeEdgePair(Node n) {

                Set<NodeEdgePair> adjacentNodes = new HashSet<NodeEdgePair>();

                for (Edge e : n.getAdjacent()) {
                        adjacentNodes.add(new NodeEdgePair(getOtherNode(e, n), e));
                }

                return adjacentNodes;

        }

        /**
         * Get nodes adjacent to node n
         * 
         * @param n
         * @return Set<Node>
         */
        public static Set<Node> getAdjacentNodes(Node n) {

                Set<Node> adjacentNodes = new HashSet<Node>();

                for (Edge e : n.getAdjacent()) {
                        adjacentNodes.add(getOtherNode(e, n));
                }

                return adjacentNodes;
        }

        /**
         * Given a node, returns the other node in the Edge, given that each
         * edge is connected to two nodes.
         * 
         * @param edge
         * @param node
         * @return Node
         */
        public static Node getOtherNode(Edge edge, Node node) {

                for (Node n : edge.getAdjacent()) {

                        if (!n.equals(node)) {
                                return n;
                        }

                }
                return null;

        }

        // Position- the identifier
        private Integer row;

        private Integer col;

        // Adjacent Edges
        private Set<Edge> adjacentEdges;

        /**
         * Default Constructor
         * 
         */
        public Node() {

                // No coordinates Specified
                row = null;
                col = null;

                // Empty Set of Edges
                adjacentEdges = new HashSet<Edge>();

        }

        public Node(Coordinates c) {
                setCoordinates(c.row, c.col);
                adjacentEdges = new HashSet<Edge>();
        }

        /**
         * Constructor that takes row and column
         * 
         * @param r
         * @param c
         */
        public Node(int r, int c) {

                // Initialize Rows
                setCoordinates(r, c);

                // Empty Set of Edges
                adjacentEdges = new HashSet<Edge>();
        }

        public Node(int r, int c, Set<Edge> aE) {

                // Initialize Rows
                setCoordinates(r, c);

                // Empty Set of Edges
                adjacentEdges = new HashSet<Edge>(aE);
        }

        /**
         * Adds a single edge to a node
         * 
         * @param edge
         */
        public void addEdge(Edge edge) {

                // check for already added removed
                // if (adjacentEdges.contains(edge)) {
                // throw new
                // IllegalArgumentException("Edge has already been added");
                // } else

                if (howManyEdges() >= 4) {
                        throw new IllegalArgumentException("Cannot add more than 4 edges");
                }
                else if (edge == null) {
                        throw new IllegalArgumentException("You tried to add an adjacent null edge");
                }

                adjacentEdges.add(edge);
        }

        /**
         * Add the edges
         * 
         * @param edges
         */
        public void addEdges(Edge[] edges) {

                for (Edge edge : edges) {

                        addEdge(edge);

                }

        }

        /**
         * Returns true if the coordinates are set
         * 
         * @return boolean
         */
        public boolean areCoordinatesSet() {
                return (row != null && col != null);
        }

        @Override
        public Node clone() {
                return new Node(row, col, adjacentEdges);
        }

        @Override
        public boolean equals(Object obj) {
                if (this == obj)
                        return true;
                if (obj == null)
                        return false;
                if (getClass() != obj.getClass())
                        return false;
                Node other = (Node) obj;
                if (col == null) {
                        if (other.col != null)
                                return false;
                }
                else if (!col.equals(other.col))
                        return false;
                if (row == null) {
                        if (other.row != null)
                                return false;
                }
                else if (!row.equals(other.row))
                        return false;
                return true;
        }

        // Gets the adjacent edges
        @Override
        public Set<Edge> getAdjacent() {
                return adjacentEdges;
        }

        /**
         * Gets a pair of (Node, Edge) adjacent to {@code this}
         * 
         * @return Set<NodeEdgePair>
         */
        public Set<NodeEdgePair> getAdjacentNodeEdgePair() {
                return getAdjacentNodeEdgePair(this);
        }

        public Set<Node> getAdjacentNodes() {
                return getAdjacentNodes(this);
        }

        /**
         * Gets the column (y) position of the node
         * 
         * @return int
         */
        public int getCol() {
                return col;
        }

        /**
         * gets the coordinates of the node
         * 
         * @return
         */
        public Coordinates getCoordinates() {
                return new Coordinates(row, col);
        }

        public Map<Dir, Edge> getDirections() {
                return getDirections(HEIGHT, WIDTH);
        }

        /**
         * Returns a tuple of (direction, edge). It tells you the direction each
         * edge is
         * 
         * @param maxHeight
         * @param maxWidth
         * @return Map<Dir, Edge>
         */
        public Map<Dir, Edge> getDirections(int maxHeight, int maxWidth) {

                Map<Dir, Edge> directions = new HashMap<Dir, Edge>();

                Node otherNode;

                for (Edge e : adjacentEdges) {

                        otherNode = getOtherNode(e, this);

                        // same row
                        if (otherNode.getRow() == row) {

                                if ((col + 1) % maxWidth == otherNode.getCol()) {
                                        directions.put(Dir.RIGHT, e);
                                }
                                else {
                                        directions.put(Dir.LEFT, e);
                                }
                        }

                        // Same column
                        else {
                                if ((row + 1) % maxHeight == otherNode.getRow()) {
                                        directions.put(Dir.DOWN, e);
                                }
                                else {
                                        directions.put(Dir.UP, e);
                                }
                        }

                }
                return directions;

        }

        /**
         * Gets the row (x) position of the node
         * 
         * @return int
         */
        public int getRow() {
                return row;
        }

        @Override
        public int hashCode() {
                final int prime = 31;
                int result = 1;
                result = prime * result + ((col == null) ? 0 : col.hashCode());
                result = prime * result + ((row == null) ? 0 : row.hashCode());
                return result;
        }

        /**
         * Returns the number of edges stored in the Node
         * 
         * @return int
         */
        public int howManyEdges() {
                return adjacentEdges.size();
        }

        /**
         * Set the coordinates- Can only be done once. Will return true if
         * successful, will return false if coordinates have already been set
         * 
         * @param r
         * @param c
         * @return boolean
         */
        private boolean setCoordinates(int r, int c) {

                // If the coordinates are not already set
                if (!areCoordinatesSet()) {

                        // Ensure valid coordinates
                        if (!validCoordinates(r, c)) {
                                throw new IllegalArgumentException("Invalid coordinates");
                        }

                        // Set the coordinates
                        row = r;
                        col = c;

                        return true;

                }

                else {
                        return false;
                }

        }

        @Override
        public String toString() {
                return "Node [row=" + row + ", col=" + col + ", Edges=" + adjacentEdges + "]";
        }

        /**
         * Returns true if r and c are valid coordinates
         * 
         * @param r
         * @param c
         * @return boolean
         */
        public boolean validCoordinates(int r, int c) {
                return !(r < 0 || r >= GameConstants.HEIGHT || c < 0 || c >= GameConstants.WIDTH);

        }

        /**
         * Is a valid game node
         * 
         * @return boolean
         */
        public boolean validGameNode() {

                return (areCoordinatesSet() && howManyEdges() >= 0 && howManyEdges() <= 4);

        }

}