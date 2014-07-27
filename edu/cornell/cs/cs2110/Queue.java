package edu.cornell.cs.cs2110;

import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * A FIFO implementation of the {@link Bag} interface.
 * 
 * @param <T>
 *                the type of elements contained in this {@code Queue}.
 */
public class Queue<T> extends AbstractBag<T> {

        private LinkedList<T> queue = new LinkedList<T>();

        /**
         * {@inheritDoc}
         */
        @Override
        public void insert(T item) {
                queue.add(item);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public T extract() throws NoSuchElementException {
                return queue.remove();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void clear() {
                queue.clear();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int size() {
                return queue.size();
        }
}