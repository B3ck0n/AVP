/**********************************
 * Assignment 5: AVP
 * Alien.java
 * Date: 12/4/2011
 *
 * Jon Hamlin: jwh244, 2400666
 * Brooks Hoffecker: bjh83, 2015719
 * Evan Long: erl43, 2300076
 * Richard McCaffrey: rpm77, 2494167
 **********************************/

package avp;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.cornell.cs.cs2110.RandomBag;

public class Alien implements Agent {

        private Set<Edge> visitedEdges;
        private boolean wasSensed = false;
        private Edge lastLoc;
        private Edge moveEdge;
        boolean hasScanner = false;
        List<Edge> alienPath;

        @Override
        public void init(Ship ship) {

                visitedEdges = new HashSet<Edge>();

        }

        @Override
        public Move nextMove(Info info) {
                if (info.atScanner) {
                        hasScanner = true;
                }

                // has the scanner
                if (hasScanner && info.ship.getPath(info.location, info.ship.getPredatorNode()) != null) {
                        alienPath = info.ship.getPath(info.location, info.ship.getPredatorNode());
                        moveEdge = alienPath.get(0);

                        if (info.adversaryThruWall != null) {
                                // Burn through the wall
                                return new Move(info.adversaryThruWall, false, true);
                        }
                        else if (moveEdge.getEdgeState() == EdgeState.BURNED || moveEdge.getEdgeState() == EdgeState.OPEN) {
                                return new Move(moveEdge, true, false);
                        }
                }

                // If predator is on the other side of the wall
                if (info.adversaryThruWall != null) {
                        // Burn through the wall
                        return new Move(info.adversaryThruWall, false, true);
                }

                // If we sense the predator
                else if (info.adversaryPresent) {
                        // Follow the predator
                        wasSensed = true;
                        lastLoc = info.adversaryDirection;
                        return new Move(lastLoc, true, false);
                }

                // If the predator is not in sight
                else {

                        RandomBag<Edge> randomEdge = new RandomBag<Edge>();

                        // For each adjacent edge
                        for (Edge e : info.adjacentEdges) {

                                // If it is an open door or burned wall then put
                                // it in the
                                // random bag
                                if (e.getEdgeState() == EdgeState.OPEN || e.getEdgeState() == EdgeState.BURNED) {
                                        randomEdge.insert(e);
                                }

                        }

                        // While there are still edges left in the bag
                        while (randomEdge.size() > 0) {

                                // Get an edge from the bag
                                Edge possibleEdge = randomEdge.extract();

                                // If we have not already visited the edge
                                if (!visitedEdges.contains(possibleEdge)) {

                                        // Add edge to the list of visited edges
                                        visitedEdges.add(possibleEdge);

                                        // Indicate that we want to move on that
                                        // edge

                                        return new Move(possibleEdge, true, false);

                                }

                        }

                        // ------------------------
                        // If alien gets to this point, then there are no open
                        // doors or
                        // burned walls it has not already been to
                        // ------------------------

                        // for all possible edges
                        for (Edge e : info.allEdges) {
                                // If it is not an open door or a burned edge
                                if (!(e.getEdgeState() == EdgeState.OPEN || e.getEdgeState() == EdgeState.BURNED)) {
                                        // Add it to the bag
                                        randomEdge.insert(e);
                                }

                        }

                        // while there are still edges left in the bag
                        while (randomEdge.size() > 0) {

                                // Get an edge from the bag
                                Edge possibleEdge = randomEdge.extract();

                                // Indicate that we want to burn through the
                                // wall or closed door
                                return new Move(possibleEdge, false, true);

                        }

                        // ------------------------
                        // If alien gets to this point, then there are no
                        // open/burned edges
                        // it has not visited and nothing to burn
                        // ------------------------

                        // For each adjacent edge
                        for (Edge e : info.adjacentEdges) {

                                // If it is an open door or burned wall then put
                                // it in the
                                // random bag
                                if (e.getEdgeState() == EdgeState.OPEN || e.getEdgeState() == EdgeState.BURNED) {
                                        randomEdge.insert(e);
                                }

                        }

                        // while there are still edges left in the bag
                        while (randomEdge.size() > 0) {

                                // Get an edge from the bag
                                Edge possibleEdge = randomEdge.extract();

                                // Indicate that we want to move on that edge
                                return new Move(possibleEdge, true, false);

                        }
                }
                // ------------------------
                // If alien gets to this point, then something has gone
                // terribly
                // wrong
                // ------------------------
                return null;
        }
}
