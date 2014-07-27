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

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

public class Predator implements Agent {
        // The predator is trying to close off the control room
        boolean controlRoom = false;
        static boolean hasScanner;
        Ship predatorShip;
        LinkedList<Edge> travelled = new LinkedList<Edge>();
        LinkedList<Edge> travelledBack = new LinkedList<Edge>();
        boolean hasSensed = false;

        // fields for the closing off room Method
        LinkedList<Edge> path = new LinkedList<Edge>();
        Set<Edge> movingBack = new HashSet<Edge>();

        /**
         * When the predator has reached the control room he will close it off
         * from the rest of the ship. If there is a burned edge, the predator
         * must leave the control Node, close of the adjacent hallways, and then
         * return to the control room to press the button.
         * 
         * @param info
         * @return
         */
        private Move closeOffRoom(Info info) {
                // close any open doors
                Node location = info.location;
                for (Edge e : location.getAdjacent()) {
                        if (e.getEdgeState() == EdgeState.OPEN) {
                                return new Move(e, false, true);
                        }
                }
                // visit rooms on other side of burned edges
                for (Edge e : location.getAdjacent()) {
                        if (e.getEdgeState() == EdgeState.BURNED) {
                                if (!path.contains(e) && !movingBack.contains(e)) {
                                        path.addFirst(e);
                                        return new Move(e, true, false);
                                }

                        }
                }

                Edge moving = path.removeFirst();
                movingBack.add(moving);
                return new Move(moving, true, false);

        }

        /**
         * clones the ship and stores it
         */
        @Override
        public void init(Ship ship) {
                predatorShip = ship.clone();

        }

        /**
         * determines the next move depending on the info provided
         */
        @Override
        public Move nextMove(Info info) {
                // will be the edge to move
                Edge moveEdge = null;

                // update predators memory of ship, keeps scanner image
                if (info.ship == null) {
                        updateShip(info);
                }
                else {
                        predatorShip = info.ship;
                }

                // if the predator has been at the control center, close himself
                // off
                if (info.location.equals(Engine.controlNode)) {
                        controlRoom = true;
                }
                if (controlRoom == true) {
                        return closeOffRoom(info);
                }

                // PREDATOR SENSE!
                if (info.adversaryPresent) {

                        if (info.adversaryDirection.getEdgeState() != EdgeState.BURNED) {

                                // first choice, close off predatorSense
                                return new Move(info.adversaryDirection, false, true);
                        }
                        else {

                                for (Edge e : info.location.getAdjacent()) {
                                        // run if you can
                                        if (e.getEdgeState() == EdgeState.OPEN) {
                                                return new Move(e, true, true);
                                        }
                                        else if (e.getEdgeState() == EdgeState.CLOSED) {
                                                return new Move(e, true, false);
                                        }
                                }

                                // can't run, open a door
                                for (Edge e : info.location.getAdjacent()) {
                                        if (e.getEdgeState() == EdgeState.CLOSED) {
                                                return new Move(e, false, true);
                                        }
                                }

                                // no cooridors, no doors....give up
                                return new Move(info.adjacentEdges.iterator().next(), false, false);
                        }

                }
                else {

                        List<Edge> predPath = predatorShip.getPath(predatorShip.getNodeAt(info.location.getRow(), info.location.getCol()),
                                        predatorShip.getNodeAt(Engine.controlNode.getRow(), Engine.controlNode.getCol()));

                        if (predPath == null) {// no where to go
                                try {
                                        // go backwards
                                        Edge toMove = travelled.removeLast();
                                        toMove = Engine.ship.edgeMap.get(toMove.getAdjacent());

                                        // if it is closed, open it and add it
                                        // to move next turn
                                        if (toMove != null && toMove.getEdgeState() == EdgeState.CLOSED) {
                                                travelled.add(toMove);
                                                return new Move(toMove, false, true);
                                                // when open, go back
                                        }
                                        else if (toMove != null && toMove.getEdgeState() == EdgeState.OPEN) {
                                                travelledBack.add(toMove);
                                                return new Move(toMove, true, true);
                                        }
                                } catch (NoSuchElementException ex) {

                                        try {
                                                Edge toMove = travelledBack.removeLast();
                                                toMove = Engine.ship.edgeMap.get(toMove.getAdjacent());

                                                // if it is closed, open it and
                                                // add it to move next turn
                                                if (toMove != null && toMove.getEdgeState() == EdgeState.CLOSED) {
                                                        travelledBack.add(toMove);
                                                        return new Move(toMove, false, true);
                                                        // when open, go back
                                                }
                                                else if (toMove != null && toMove.getEdgeState() == EdgeState.OPEN) {
                                                        return new Move(toMove, true, true);
                                                }
                                        } catch (NoSuchElementException no) {
                                                // when the predator has reached
                                                // the beginning
                                                // check for closed doors
                                                for (Edge e : info.location.getAdjacent()) {
                                                        if (e.getEdgeState() == EdgeState.CLOSED) {
                                                                return new Move(e, false, true);
                                                        }
                                                }

                                                // if no closed doors, check for
                                                // a cooridor
                                                for (Edge e : info.location.getAdjacent()) {
                                                        if (e.getEdgeState() == EdgeState.OPEN) {
                                                                return new Move(e, true, false);
                                                        }
                                                }
                                        }
                                }
                        }
                        else {
                                // go on to the next edge in the path
                                Iterator<Edge> it = predPath.iterator();
                                Edge predEdge = it.next();

                                moveEdge = Engine.ship.edgeMap.get(predEdge.getAdjacent());
                                if (moveEdge.getEdgeState() == EdgeState.CLOSED) {
                                        return new Move(moveEdge, false, true);
                                }
                                else if (moveEdge.getEdgeState() == EdgeState.BURNED) {
                                        travelled.add(moveEdge);
                                        return new Move(moveEdge, true, false);
                                }
                                travelled.add(moveEdge);

                                return new Move(moveEdge, true, true);
                        }
                }
                // only happens when the predator is backed into a wall...he
                // stop making turns and pray
                return new Move(null, false, false);
        }

        /**
         * Updates the ship in the predators memory
         * 
         * @param info
         */
        private void updateShip(Info info) {

                Node nodeOne;
                Node nodeTwo;
                Set<Node> nodes;

                // visit every edge in the actual ship
                for (Edge e : info.adjacentEdges) {
                        nodeOne = info.location;
                        nodeTwo = Node.getOtherNode(e, nodeOne);
                        nodes = new HashSet<Node>();
                        nodes.add(nodeOne);
                        nodes.add(nodeTwo);
                        Edge predEdge = predatorShip.edgeMap.get(nodes);

                        // compare it to the predators memory, makes necessary
                        // adjustments
                        if (predEdge != null) {
                                if (e.getEdgeState() != predEdge.getEdgeState()) {
                                        predEdge.setEdgeState(e.getEdgeState());
                                }
                        }
                        else {
                                Node predsNodeOne = predatorShip.getNodeAt(nodeOne.getRow(), nodeOne.getCol());
                                Node predsNodeTwo = predatorShip.getNodeAt(nodeTwo.getRow(), nodeTwo.getCol());
                                Set<Node> nodesForEdge = new HashSet<Node>();
                                nodesForEdge.add(predsNodeOne);
                                nodesForEdge.add(predsNodeTwo);
                                predatorShip.edgeMap.put(nodesForEdge, new Edge(e.getEdgeState(), predsNodeOne, predsNodeTwo));
                        }
                }

        }
}
