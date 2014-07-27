/**********************************
 * Assignment 5: AVP
 * Info.java
 * Date: 12/4/2011
 *
 * Jon Hamlin: jwh244, 2400666
 * Brooks Hoffecker: bjh83, 2015719
 * Evan Long: erl43, 2300076
 * Richard McCaffrey: rpm77, 2494167
 **********************************/
package avp;

import java.util.Set;

/**
 * Information passed to the agents when requesting their desired next move.
 * 
 * Version 2. Changes from Version 1: Added fields {@code location} and
 * {@code allEdges}.
 */
public class Info {

        /**
         * Location of the agent. Added in version 2.
         */
        Node location;

        /**
         * The set of all potential edges adjacent to the node currently
         * occupied by the agent. This includes potential edges not currently in
         * the graph (walls). Added in version 2.
         * 
         * The reason for this addition is so that the Alien can burn through an
         * adjacent wall, even if the Predator is not on the other side of that
         * wall. Walls are represented as non-edges in the graph.
         */
        Set<Edge> allEdges;

        /**
         * The set of all edges adjacent to the node currently occupied by the
         * agent.
         */
        Set<Edge> adjacentEdges;

        /**
         * Is the agent currently in the control room? Always false for the
         * Alien.
         */
        boolean atControlRoom;

        /**
         * Is the agent currently occupying the same node as the scanner?
         */
        boolean atScanner;

        /**
         * A map of the ship in its true state. Provided to the agent who has
         * found the scanner, {@code null} otherwise.
         */
        Ship ship;

        /**
         * Is the adversary within the sensing distance of the agent?
         */
        boolean adversaryPresent;

        /**
         * Direction to travel to get to the adversary along open corridors if
         * adversaryPresent is true; otherwise null
         */
        Edge adversaryDirection;

        /**
         * Edge the Alien can burn through to get to the Predator if the
         * Predator is directly on the other side of the wall. The Alien's
         * {@code nextMove} method should return this edge to signal the desire
         * to burn through the wall. The edge will be added to the graph in the
         * burned state.
         * 
         * Always null for the Predator.
         */
        Edge adversaryThruWall;

        Info(Node currentLocation, Set<Edge> allPossibleEdges, Set<Edge> adjacentEdges, boolean atTheControlRoom, boolean atTheScanner, Ship scannerShip,
                        boolean spideySense, Edge directionOfOpponent, Edge opponentThroughWall) {
                location = currentLocation;
                allEdges = allPossibleEdges;
                this.adjacentEdges = adjacentEdges;
                atControlRoom = atTheControlRoom;
                atScanner = atTheScanner;
                this.ship = scannerShip;
                adversaryPresent = spideySense;
                adversaryDirection = directionOfOpponent;
                adversaryThruWall = opponentThroughWall;
        }

}
