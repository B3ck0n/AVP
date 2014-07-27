package edu.cornell.cs.cs2110;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * An adapter implementation of the {@code Bag} interface. Provides default
 * implementations of {@link #iterator()} and {@link #isEmpty()}. Subclasses
 * need only implement {@link #insert(Object)}, {@link #extract()},
 * {@link #size()}, and {@link #clear()}.
 * 
 * @param <T>
 *                the type of elements contained in this {@link Bag}.
 */
public abstract class AbstractBag<T> implements Bag<T> {

        /**
         * {@inheritDoc}
         */
        @Override
        public abstract void insert(T item);

        /**
         * {@inheritDoc}
         */
        @Override
        public abstract T extract() throws NoSuchElementException;

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean isEmpty() {
                return size() == 0;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public abstract int size();

        /**
         * {@inheritDoc}
         */
        @Override
        public abstract void clear();

        /**
         * The {@code Iterator}, unless it is overridden in the concrete
         * subclass, produces elements in the same order that repeated execution
         * of {@link #extract()} would. Note that this is not guaranteed by
         * <code><a href="http://java.sun.com/j2se/1.5.0/docs/api/java/util/Stack.html?is-external=true">java.util.Stack</a></code>
         * , for example.
         */
        @Override
        public Iterator<T> iterator() {
                return new Iterator<T>() {
                        @Override
                        public boolean hasNext() {
                                return !isEmpty();
                        }

                        @Override
                        public T next() {
                                return extract();
                        }

                        @Override
                        public void remove() {
                                throw new UnsupportedOperationException();
                        }
                };
        }
}
