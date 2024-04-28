package edu.ncsu.csc216.pack_scheduler.util;

import java.util.EmptyStackException;

/**
 * Implements a stack in the form of an LinkedList.
 * 
 * @author David Martinez
 * @author Chirag Sreedhara
 * @param <E> Object in Stack
 */
public class LinkedStack<E> implements Stack<E> {

	/** Field for linked list */
	LinkedAbstractList<E> list;

	/**
	 * Constructor method
	 * 
	 * @param capacity for list
	 */
	public LinkedStack(int capacity) {
		list = new LinkedAbstractList<E>(capacity);
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
	 * Method to change capacity of a linked stack
	 * 
	 * @param capacity to set
	 */
	@Override
	public void setCapacity(int capacity) {
		list.setCapacity(capacity);

	}

}
