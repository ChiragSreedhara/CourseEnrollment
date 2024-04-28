package edu.ncsu.csc216.pack_scheduler.util;

import java.util.EmptyStackException;

/**
 * Implements a stack in the form of an ArrayList.
 * 
 * @author David Martinez
 * @author Chirag Sreedhara
 * @param <E> Object in Stack
 */
public class ArrayStack<E> implements Stack<E> {

	/** Array list to use for the stack */
	ArrayList<E> list;

	/** Capacity of the list */
	private int capacity;

	/**
	 * Constructor method
	 * 
	 * @param capacity for list
	 */
	public ArrayStack(int capacity) {
		list = new ArrayList<E>();
		setCapacity(capacity);
	}

	/**
	 * Adds an element to the stack
	 * 
	 * @param element to add
	 * @throws IllegalArgumentException If there is no room (capacity has been
	 *                                  reached)
	 */
	@Override
	public void push(E element) {
		if (list.size() >= capacity) {
			throw new IllegalArgumentException("Capacity reached");
		}
		list.add(element);

	}

	/**
	 * Removes element from stack
	 * 
	 * @return Element removed.
	 * @throws EmptyStackException If the stack is empty
	 */
	@Override
	public E pop() {
		if (list.size() == 0) {
			throw new EmptyStackException();
		}
		return list.remove(list.size() - 1);
	}

	/**
	 * Tells us if stack is empty
	 * 
	 * @return true if stack empty, false otherwise.
	 */
	@Override
	public boolean isEmpty() {
		return list.size() == 0;
	}

	/**
	 * Returns size of the stack
	 * 
	 * @return size
	 */
	@Override
	public int size() {
		return list.size();
	}

	/**
	 * Sets the capacity of the stack
	 * 
	 * @throws IllegalArgumentException If the actual parameter is negative or if it
	 *                                  is less than the number of elements in the
	 *                                  stack
	 */
	@Override
	public void setCapacity(int capacity) {
		if (capacity < 0 || capacity < list.size()) {
			throw new IllegalArgumentException("Invalid capacity.");
		}
		this.capacity = capacity;

	}

}
