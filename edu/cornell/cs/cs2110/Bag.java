package edu.cornell.cs.cs2110;

import java.util.NoSuchElementException;

/**
 * This interface is for multisets, an abstract datatype in which elements can
 * occur multiple times but order is not important. One can put elements in
 * using {@link #insert(Object)} and take them out using {@link #extract()}, and
 * these two operations can be done in any order. The order in which the
 * elements are extracted depends on the implementation.
 * 
 * @param <T>
 *                the type of elements contained in this {@code Bag}.
 */
public interface Bag<T> extends Iterable<T> {

        /**
         * Inserts an element into the data structure.
         * 
         * @param item
         *                the element to insert
         */
        void insert(T item);

        /**
         * Extracts an element from the data structure. The element is deleted.
         * 
         * @return the element extracted
         * @throws NoSuchElementException
         *                 if the data structure is empty
         */
        T extract() throws NoSuchElementException;

        /**
         * Tests whether the data structure is empty.
         * 
         * @return {@code true} if the data structure contains no elements
         */
        boolean isEmpty();

        /**
         * Returns the number of elements contained in the data structure.
         * 
         * @return the number of elements
         */
        int size();

        /**
         * Removes all elements from the data structure.
         */
        void clear();
}
