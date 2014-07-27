package edu.cornell.cs.cs2110;

import java.util.NoSuchElementException;

/**
 * An implementation of the {@link Bag} interface in which elements are
 * extracted according to their natural order, as defined by their
 * implementation of the {@link Comparable} interface, or its opposite.
 * 
 * @param <T>
 *                the type of elements contained in this {@code PriorityQueue}.
 */
public class PriorityQueue<T extends Comparable<T>> extends AbstractBag<T> {

        private boolean orientation;
        private java.util.PriorityQueue<Oriented<T>> pq = new java.util.PriorityQueue<Oriented<T>>();

        /**
         * Constructs a {@code PriorityQueue} that extracts elements according
         * to their natural order (lowest to highest) or its opposite (highest
         * to lowest), depending on the value of the parameter
         * {@code orientation}. The natural ordering is used if the parameter is
         * {@code true} and its opposite is used if the parameter is
         * {@code false}.
         * 
         * @param orientation
         *                the orientation of the ordering
         */
        public PriorityQueue(boolean orientation) {
                this.orientation = orientation;
        }

        /**
         * Constructs a {@code PriorityQueue} that extracts elements according
         * to their natural order, lowest to highest.
         */
        public PriorityQueue() {
                this(true);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void insert(T item) {
                pq.add(new Oriented<T>(item));
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public T extract() throws NoSuchElementException {
                return pq.remove().item;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int size() {
                return pq.size();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void clear() {
                pq.clear();
        }

        /**
         * Removes one instance of the specified element from the queue, if
         * there. Throws a NoSuchElementException if not there.
         * 
         * @param obj
         *                the element to remove
         * @throws NoSuchElementException
         *                 if the element is not present in the queue.
         */
        void remove(T obj) {
                pq.remove(obj);
        }

        private class Oriented<S extends Comparable<S>> implements Comparable<Oriented<S>> {
                private S item;

                private Oriented(S item) {
                        this.item = item;
                }

                @Override
                public int compareTo(Oriented<S> o) {
                        return orientation ? item.compareTo(o.item) : o.item.compareTo(item);
                }
        }
}
