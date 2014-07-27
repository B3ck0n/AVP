package edu.cornell.cs.cs2110;

import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Random;

/**
 * An implementation of the {@link Bag} interface in which elements are
 * extracted in a random order.
 * 
 * @param <T>
 *                the type of elements contained in this {@code RandomBag}.
 */
public class RandomBag<T> extends AbstractBag<T> {

        private PriorityQueue<Ordered<T>> pq = new PriorityQueue<Ordered<T>>();
        private static Random rand = new Random();

        /**
         * {@inheritDoc}
         */
        @Override
        public void insert(T item) {
                pq.add(new Ordered<T>(item));
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

        private class Ordered<S> implements Comparable<Ordered<S>> {
                private S item;
                private int priority;

                private Ordered(S item) {
                        this.item = item;
                        priority = rand.nextInt();
                }

                @Override
                public int compareTo(Ordered<S> o) {
                        return o.priority - priority;
                }
        }
}
