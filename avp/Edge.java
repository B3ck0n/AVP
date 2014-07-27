/**********************************
 * Assignment 5: AVP
 * Edge.java
 * Date: 12/4/2011
 *
 * Jon Hamlin: jwh244, 2400666
 * Brooks Hoffecker: bjh83, 2015719
 * Evan Long: erl43, 2300076
 * Richard McCaffrey: rpm77, 2494167
 **********************************/
package avp;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import ugraph.UGraphComponent;

public class Edge implements UGraphComponent<Node> {

        // Adjacent Nodes
        private Set<Node> adjacentNodes;

        // Edge State
        private EdgeState edgeState;

        /**
         * Default Constructor
         * 
         */
        public Edge() {

                // No adjacent nodes
                adjacentNodes = new HashSet<Node>();

                // No edge state
                edgeState = null;

        }

        /**
         * Constructor that takes the edgeState and two nodes as constructor
         * 
         * @param eS
         * @param node1
         * @param node2
         */
        public Edge(EdgeState eS, Node node1, Node node2) {

                // Set the edge states
                setEdgeState(eS);

                // Set the adjacent
                setAdjacent(node1, node2);

        }

        @Override
        public boolean equals(Object obj) {
                if (this == obj)
                        return true;
                if (obj == null)
                        return false;
                if (getClass() != obj.getClass())
                        return false;
                Edge other = (Edge) obj;
                if (adjacentNodes == null) {
                        if (other.adjacentNodes != null)
                                return false;
                }
                else if (!adjacentNodes.equals(other.adjacentNodes))
                        return false;
                if (edgeState != other.edgeState)
                        return false;
                return true;
        }

        @Override
        public Set<Node> getAdjacent() {
                return adjacentNodes;
        }

        /**
         * Getter method for EdgeState
         * 
         * @return EdgeState
         */
        public EdgeState getEdgeState() {
                return edgeState;
        }

        public Coordinates[] getEndpointCoordinates() {
                Coordinates[] endpointCoordinates = new Coordinates[2];

                Iterator<Node> endpts = getAdjacent().iterator();

                endpointCoordinates[0] = endpts.next().getCoordinates();
                endpointCoordinates[1] = endpts.next().getCoordinates();

                return endpointCoordinates;

        }

        @Override
        public int hashCode() {
                final int prime = 31;
                int result = 1;
                result = prime * result + ((adjacentNodes == null) ? 0 : adjacentNodes.hashCode());
                result = prime * result + ((edgeState == null) ? 0 : edgeState.hashCode());
                return result;
        }

        // Equality and hashcode methods

        /**
         * Set Adjacent Method that accepts two nodes
         * 
         * @param node1
         * @param node2
         */
        private void setAdjacent(Node node1, Node node2) {

                if (node1 == null || node2 == null) {
                        throw new IllegalArgumentException("Need two non-null Nodes");
                }

                // Create the Adjacent Nodes
                Set<Node> aN = new HashSet<Node>();
                aN.add(node1);
                aN.add(node2);

                // Set the Adjacent nodes
                adjacentNodes = aN;
        }

        /**
         * Setter method for EdgeState
         * 
         * @param eS
         */
        public void setEdgeState(EdgeState eS) {
                edgeState = eS;
        }

        @Override
        public String toString() {
                String output = "Edge [Nodes=";

                for (Node n : adjacentNodes) {
                        output = output + n.getCoordinates();
                }

                output = output + ", " + edgeState + "]";

                return output;
        }

        /**
         * Checks if the Edge is a valid game edge. An edge is valid iff it has
         * two adjacent nodes
         * 
         * @return boolean
         */
        public boolean validGameEdge() {
                return getAdjacent().size() == 2;
        }

}
