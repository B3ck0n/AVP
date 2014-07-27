/**********************************
 * Assignment 5: AVP
 * EdgeState.java
 * Date: 12/4/2011
 *
 * Jon Hamlin: jwh244, 2400666
 * Brooks Hoffecker: bjh83, 2015719
 * Evan Long: erl43, 2300076
 * Richard McCaffrey: rpm77, 2494167
 **********************************/
package avp;

/**
 * The state of an edge. The Predator can change an edge from open to closed or
 * from closed to open. The Alien can change an edge from open/closed to burned
 * and can also add new burned edges.
 */
public enum EdgeState {
        OPEN, CLOSED, BURNED
}
