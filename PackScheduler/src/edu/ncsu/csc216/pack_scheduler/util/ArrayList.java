package edu.ncsu.csc216.pack_scheduler.util;

import java.util.AbstractList;

/**
 * Self implementation of the ArrayList concept. This implementation does not
 * permit duplicates
 * 
 * @author Fahad Ansari
 * @author Ryan Stauffer
 * @author Chirag Sreedhara
 * 
 * @param <E> generic Object for the ArrayList too hold
 */
public class ArrayList<E> extends AbstractList<E> {

	/**
	 * the starting size of the array
	 */
	private static final int INIT_SIZE = 10;

	/**
	 * Array of elements to hold
	 */
	private E[] list;
	/**
	 * non-empty elements in the Array List
	 */
	private int size;

	/**
	 * Constructor for ArrayList. Suppression is allowed
	 */
	@SuppressWarnings("unchecked")
	public ArrayList() {
		this.list = (E[]) new Object[INIT_SIZE];
		this.size = 0;
	}

	/**
	 * sets the element at the given index to the given element.
	 * 
	 * @param index   - index to add `element` at
	 * @param element - element to add too the ArrayList
	 * 
	 * @throws NullPointerException      if `element` is null
	 * @throws IndexOutOfBoundsException if the index is less than 0 or greater than
	 *                                   the size;
	 * @throws IllegalArgumentException  if `element` already exists in the list
	 *                                   according to `equals()` functionality
	 * 
	 * @return the element at the given index
	 */
	@Override
	public E set(int index, E element) {
		if (element == null) {
			throw new NullPointerException();
		}
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException();
		}
		for (int i = 0; i < size(); i++) {

			if (list[i].equals(element)) {
				throw new IllegalArgumentException();
			}
		}

		E returnElem = list[index];
		list[index] = element;
		return returnElem;
	};

	/**
	 * Adds an element at the given index, right shifting everything and growing the
	 * internal array by doubling it if necessary
	 * 
	 * @param index   - index to add `element` at
	 * @param element - element to add too the ArrayList
	 * 
	 * @throws NullPointerException      if `element` is null
	 * @throws IndexOutOfBoundsException if the index is less than 0 or greater than
	 *                                   the size;
	 * @throws IllegalArgumentException  if `element` already exists in the list
	 *                                   according to `equals()` functionality
	 * 
	 */
	@Override
	public void add(int index, E element) {
		if (element == null) {
			throw new NullPointerException();
		}
		if (index < 0 || index > size()) {
			throw new IndexOutOfBoundsException();
		}
		for (int i = 0; i < size(); i++) {

			if (list[i].equals(element)) {
				throw new IllegalArgumentException();
			}
		}

		if (size == list.length) {
			growArray();
		}

		for (int i = list.length - 1; i >= index; i--) {
			if (i == list.length - 1) {
				continue;
			}
			list[i + 1] = list[i];
		}

		list[index] = element;
		size++;
	};

	/**
	 * Removes the element at `index`, returning it. This left shifts all items
	 * beyond `index`
	 * 
	 * @param index - index of element to retrieve and remove
	 * 
	 * @throws IndexOutOfBoundsException if the index is less than 0 or greater than
	 *                                   the size;
	 * 
	 * @return the element at `index`
	 * @throws IndexOutOfBoundsException if IOB
	 */
	@Override
	public E remove(int index) {
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException();
		}
		E returnElem = list[index];

		for (int i = index; i < list.length - 1; i++) {
			list[i] = list[i + 1];
		}
		list[list.length - 1] = null;
		size--;
		return returnElem;
	}

	/**
	 * doubles the internal Array size
	 */
	private void growArray() {
		@SuppressWarnings("unchecked")
		E[] newList = (E[]) new Object[list.length * 2];
		for (int i = 0; i < list.length; i++) {
			newList[i] = list[i];
		}
		list = null;
		list = newList;
	};

	/**
	 * getter for size - we assert that each method handles size change correctly
	 * for this
	 * 
	 * @return the size of the ArrayList - not the size of the internal Array
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * gets the element at `index` - non-destructive
	 * 
	 * @param index - index of element to retrieve
	 * @return the element at `index`
	 * @throws IndexOutOfBoundsException if IOB
	 */
	@Override
	public E get(int index) {
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException();
		}
		return list[index];
	}

}
