/**********************************
 * Assignment 5: AVP
 * DijkstraInfo.java
 * Date: 12/4/2011
 *
 * Jon Hamlin: jwh244, 2400666
 * Brooks Hoffecker: bjh83, 2015719
 * Evan Long: erl43, 2300076
 * Richard McCaffrey: rpm77, 2494167
 **********************************/

package avp;

public class DijkstraInfo {

        // Null is infinity
        Integer distance;

        Node bestPrevNode;
        Edge bestPrevEdge;

        /**
         * Used as a helper class. Represents a node in the graph and has fields
         * for the best (shortest weight) adjacent node and edge
         * 
         * @param distance
         */
        public DijkstraInfo(Integer i) {
                distance = i;

                bestPrevNode = null;
                bestPrevEdge = null;

        }

        /**
         * Used as a helper class. Represents a node in the graph and has fields
         * for the best (shortest weight) adjacent node and edge
         * 
         * @param distance
         * @param bestPreviousNode
         * @param bestPreviousEdge
         */
        public DijkstraInfo(Integer i, Node bpn, Edge bpe) {
                distance = i;
                bestPrevNode = bpn;
                bestPrevEdge = bpe;
        }

        @Override
        public String toString() {
                return "DijkstraInfo [distance=" + distance + ", bestPrevNode=" + bestPrevNode + ", bestPrevEdge=" + bestPrevEdge + "]";
        }

}
