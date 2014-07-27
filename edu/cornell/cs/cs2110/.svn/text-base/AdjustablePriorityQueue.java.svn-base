package edu.cornell.cs.cs2110;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * A priority queue implementation supporting in situ adjustment of priorities.
 * Priorities are double values. Because we use the elements themselves as keys
 * in the {@link #getPriority(Object)} and {@link #setPriority(Object, double)}
 * methods, the elements in the queue must be unique and the notion of equality
 * used to access them must be identity ({@code ==}), in constrast to
 * {@link java.util.PriorityQueue}, which relies on the natural ordering of the
 * elements or a supplied {@link java.util.Comparator}. An attempt to insert an
 * element that is already there results in an {@link IllegalArgumentException}.
 * 
 * @param <T>
 *                the type of elements contained in this {@code PriorityQueue}.
 */
public class AdjustablePriorityQueue<T> extends AbstractBag<T> {

        /**
         * The heap in which elements are stored.
         */
        private List<PQElement<T>> heap = new ArrayList<PQElement<T>>();

        /**
         * A map giving the location of each object in the heap.
         */
        private Map<T, Integer> map = new IdentityHashMap<T, Integer>();

        /**
         * Whether this is a min queue (true) or a max queue (false). A min
         * queue supports extract min and a max queue supports extract max.
         */
        private boolean orientation;

        private class PQElement<U> {
                private U item;
                private double priority;

                PQElement(U item, double priority) {
                        this.item = item;
                        this.priority = priority;
                }
        }

        /**
         * Constructs a {@code PriorityQueue} that extracts elements according
         * to priority. Priorities are integers that are supplied in the
         * constructor, but can be changed in situ. The priority queue can be
         * either a max queue (extract max supported) or a min queue (extract
         * min supported) depending on the value of the parameter.
         * 
         * @param orientation
         *                the orientation of the queue - true=min, false=max.
         */
        public AdjustablePriorityQueue(boolean orientation) {
                this.orientation = orientation;
        }

        /**
         * Constructs a {@code PriorityQueue} that extracts elements according
         * to their integer priority, lowest first.
         */
        public AdjustablePriorityQueue() {
                this(true);
        }

        /**
         * Inserts an item with the default priority:
         * {@link Double#POSITIVE_INFINITY} for a min queue,
         * {@link Double#NEGATIVE_INFINITY} for a max queue.
         * 
         * @param item
         *                the item to insert
         * 
         * @throws IllegalArgumentException
         *                 if the item is already in the queue
         */
        @Override
        public void insert(T item) throws IllegalArgumentException {
                insert(item, orientation ? Double.POSITIVE_INFINITY : Double.NEGATIVE_INFINITY);
        }

        /**
         * Inserts an item with the given priority.
         * 
         * @param item
         *                the item to insert
         * @param priority
         *                the integer priority of the item
         * 
         * @throws IllegalArgumentException
         *                 if the item is already in the queue
         */
        public void insert(T item, double priority) throws IllegalArgumentException {
                if (item == null)
                        throw new IllegalArgumentException();
                if (map.containsKey(item))
                        throw new IllegalArgumentException();
                PQElement<T> wrapper = new PQElement<T>(item, priority);
                map.put(item, heap.size());
                heap.add(wrapper);
                rotateUp(heap.size() - 1);
        }

        /**
         * Tests whether the data structure contains the given item.
         * 
         * @param item
         *                the item to test
         * @return true if the data structure contains the item
         */
        public boolean contains(T item) {
                return map.get(item) != null;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public T extract() throws NoSuchElementException {
                T returnValue = peek();
                map.remove(returnValue);
                PQElement<T> z = heap.remove(heap.size() - 1);
                if (!heap.isEmpty()) {
                        set(0, z);
                        rotateDown(0);
                }
                return returnValue;
        }

        /**
         * Returns the next element to be extracted without extracting it.
         * 
         * @return the next element to be extracted
         */
        public T peek() throws NoSuchElementException {
                T returnValue;
                try {
                        return heap.get(0).item;
                } catch (IndexOutOfBoundsException npe) {
                        throw new NoSuchElementException();
                }
        }

        /**
         * Gets the current priority of the given item.
         * 
         * @param item
         *                the item
         * 
         * @throws NoSuchElementException
         *                 if the item is not in the queue
         */
        public double getPriority(T item) throws NoSuchElementException {
                int index;
                try {
                        index = map.get(item);
                } catch (NullPointerException npe) {
                        throw new NoSuchElementException();
                }
                PQElement<T> y = heap.get(index);
                return y.priority;
        }

        /**
         * Sets the priority of the given item.
         * 
         * @param item
         *                the item to be adjusted
         * @param newPriority
         *                the new priority
         * 
         * @throws NoSuchElementException
         *                 if the item is not in the queue
         */
        public void setPriority(T item, double newPriority) throws NoSuchElementException {
                int index;
                try {
                        index = map.get(item);
                } catch (NullPointerException npe) {
                        throw new NoSuchElementException();
                }
                PQElement<T> y = heap.get(index);
                double oldPriority = y.priority;
                y.priority = newPriority;
                if ((orientation && newPriority < oldPriority) || (!orientation && newPriority > oldPriority)) {
                        rotateUp(index);
                }
                else
                        rotateDown(index);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int size() {
                return heap.size();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void clear() {
                heap.clear();
                map.clear();
        }

        /**
         * By using this method to enter an element in the heap, we maintain the
         * invariant that {@code map} always gives the current index of the
         * item.
         * 
         * @param index
         *                the starting index
         */
        private void set(int index, PQElement<T> x) {
                heap.set(index, x);
                map.put(x.item, index);
        }

        /**
         * Rotate up starting at the given index until heap order is
         * reestablished.
         * 
         * @param index
         *                the starting index
         */
        private void rotateUp(int index) {
                PQElement<T> x = heap.get(index);
                while (index > 0) {
                        int parentIndex = (index - 1) / 2;
                        PQElement<T> y = heap.get(parentIndex);
                        if (orientation && x.priority >= y.priority)
                                break;
                        if (!orientation && x.priority <= y.priority)
                                break;
                        set(index, y);
                        index = parentIndex;
                }
                set(index, x);
        }

        /**
         * Rotate down starting at the given index until heap order is
         * reestablished.
         * 
         * @param index
         *                the starting index
         */
        private void rotateDown(int index) {
                PQElement<T> x = heap.get(index), y, z;
                int leftIndex, rightIndex;
                while (2 * index + 2 < heap.size()) {
                        leftIndex = 2 * index + 1;
                        rightIndex = leftIndex + 1;
                        y = heap.get(leftIndex);
                        z = heap.get(rightIndex);
                        if (orientation && x.priority <= y.priority && x.priority <= z.priority)
                                break;
                        if (!orientation && x.priority >= y.priority && x.priority >= z.priority)
                                break;
                        if ((orientation && y.priority < z.priority) || (!orientation && y.priority > z.priority)) {
                                set(index, y);
                                index = leftIndex;
                        }
                        else {
                                set(index, z);
                                index = rightIndex;
                        }
                }
                leftIndex = 2 * index + 1;
                if (leftIndex < heap.size()) {
                        y = heap.get(leftIndex);
                        if ((orientation && x.priority > y.priority) || (!orientation && x.priority < y.priority)) {
                                set(index, y);
                                index = leftIndex;
                        }
                }
                set(index, x);
        }
}
