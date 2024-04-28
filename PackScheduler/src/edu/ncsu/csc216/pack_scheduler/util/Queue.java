package edu.ncsu.csc216.pack_scheduler.util;

import java.util.NoSuchElementException;

/**
 * Queue Interface
 * 
 * @author David Martinez
 * @author Chirag Sreedhara
 * @param <E> object inside stack
 */
public interface Queue<E> {

	/**
	 * Queue an element, puts the element in the back of the queue
	 * 
	 * @param element element to queue
	 * @throws IllegalArgumentException if capacity met.
	 */
	void enqueue(E element);

	/**
	 * Remove the element at he front of the queue and return it
	 * 
	 * @return the element that was just removed from the front of the queue
	 * @throws NoSuchElementException if Queue is empty.
	 */
	E dequeue();

	/**
	 * Check if the queue is empty
	 * 
	 * @return true if queue is empty, else false
	 */
	boolean isEmpty();

	/**
	 * Check the size of the queue
	 * 
	 * @return the size
	 */
	int size();

	/**
	 * Set the max size of the queue
	 * 
	 * @param capacity the capacity to set
	 * @throws IllegalArgumentException if invalid capacity
	 */
	void setCapacity(int capacity);
}
