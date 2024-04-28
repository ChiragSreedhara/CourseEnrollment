package edu.ncsu.csc216.pack_scheduler.util;

import java.util.EmptyStackException;

/**
 * Interface that allows a list to function as a stack. Works with ArrayStack
 * and LinkedStack
 * 
 * @author David Martinez
 * @author Chirag Sreedhara
 * @param <E> object inside stack
 */
public interface Stack<E> {

	/**
	 * Adds the element to the top of the stack
	 * 
	 * @param element to push
	 * @throws IllegalArgumentException If there is no room (capacity has been
	 *                                  reached)
	 */
	void push(E element);

	/**
	 * Removes and returns the element at the top of the stack
	 * 
	 * @return Object thats popped
	 * @throws EmptyStackException If the stack is empty
	 */
	E pop();

	/**
	 * Return true if empty false if not
	 * 
	 * @return whether or not stack empty
	 */
	boolean isEmpty();

	/**
	 * Gives size of stack
	 * 
	 * @return stack size
	 */
	int size();

	/**
	 * Sets capacity of the stack.
	 * 
	 * @param capacity capacity to set.
	 * @throws IllegalArgumentException If the actual parameter is negative or if it
	 *                                  is less than the number of elements in the
	 *                                  stack
	 */
	void setCapacity(int capacity);
}
