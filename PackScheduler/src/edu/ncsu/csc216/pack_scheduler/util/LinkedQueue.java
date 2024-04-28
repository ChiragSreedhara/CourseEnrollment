package edu.ncsu.csc216.pack_scheduler.util;

import java.util.NoSuchElementException;

/**
 * LinkedList implementation of a Queue
 * 
 * @author David Martinez
 * @author Chirag Sreedhara
 * @param <E> Object in list
 */
public class LinkedQueue<E> implements Queue<E> {

	/** Linked List for the queue */
	private LinkedAbstractList list;

	/**
	 * LinkedQueue constructor
	 * 
	 * @param capacity intial capacity of the queue
	 */
	public LinkedQueue(int capacity) {
		list = new LinkedAbstractList(capacity);
	}

	/**
	 * Queue an element, puts the element in the back of the queue
	 * 
	 * @param element element to queue
	 * @throws IllegalArgumentException if capacity met.
	 */
	@Override
	public void enqueue(E element) {
		list.add(element);
	}

	/**
	 * Remove the element at he front of the queue and return it
	 * 
	 * @return the element that was just removed from the front of the queue
	 * @throws NoSuchElementException if Queue is empty.
	 */
	@Override
	public E dequeue() {
		if (isEmpty()) {
			throw new NoSuchElementException("Queue is empty.");
		}
		return (E) list.remove(0);
	}

	/**
	 * Check if the queue is empty
	 * 
	 * @return true if queue is empty, else false
	 */
	@Override
	public boolean isEmpty() {

		return size() == 0;
	}

	/**
	 * Check the size of the queue
	 * 
	 * @return the size
	 */
	@Override
	public int size() {
		return list.size();
	}

	/**
	 * Set the max size of the queue
	 * 
	 * @param capacity the capacity to set
	 * @throws IllegalArgumentException if capacity to set is invalid.
	 */
	@Override
	public void setCapacity(int capacity) {
		list.setCapacity(capacity);
	}

	/**
	 * Finds if an element is in the list
	 * 
	 * @param elem element to check if its in the list
	 * @return true if in list, false otherwise.
	 */
	public boolean contains(E elem) {
		for (Object elem1 : list) {
			if (elem1.equals(elem)) {
				return true;
			}
		}
		return false;
	}
}
