package edu.ncsu.csc216.pack_scheduler.util;

import java.util.NoSuchElementException;

/**
 * Array based Queue
 * 
 * @author David Martinez
 * @author Chirag Sreedhara
 * @param <E> Object in list
 */
public class ArrayQueue<E> implements Queue<E> {

	/** ArrayList for the queue */
	private ArrayList<E> list;
	/** Capacity of the queue */
	private int capacity;

	/**
	 * ArrayQueue constructer, set the intial capacity of the list
	 * 
	 * @param capacity intitial capacity
	 */
	public ArrayQueue(int capacity) {
		list = new ArrayList<>();
		setCapacity(capacity);
	}

	/**
	 * Queue an element, puts the element in the back of the queue
	 * 
	 * @param element element to queue
	 * @throws IllegalArgumentException if queue full
	 */
	@Override
	public void enqueue(E element) {
		if (list.size() == capacity) {
			throw new IllegalArgumentException("Queue is full.");
		}
		list.add(list.size(), element);
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
		return list.remove(0);
	}

	/**
	 * Check if the queue is empty
	 * 
	 * @return true if queue is empty, else false
	 */
	@Override
	public boolean isEmpty() {
		return list.size() == 0;
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
	 * @throws IllegalArgumentException if queue full.
	 */
	@Override
	public void setCapacity(int capacity) {
		if (capacity < 0 || capacity < list.size()) {
			throw new IllegalArgumentException("Invalid capacity.");
		}
		this.capacity = capacity;
	}

}
