package edu.cornell.cs.cs2110;

import java.util.NoSuchElementException;

/**
 * A LIFO implementation of the {@link Bag} interface.
 * 
 * @param <T>
 *                the type of elements contained in this {@code Stack}.
 */
public class Stack<T> extends AbstractBag<T> {

        private java.util.Stack<T> stack = new java.util.Stack<T>();

        /**
         * {@inheritDoc}
         */
        @Override
        public T extract() throws NoSuchElementException {
                return stack.pop();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void insert(T item) {
                stack.push(item);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void clear() {
                stack.clear();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int size() {
                return stack.size();
        }
}
