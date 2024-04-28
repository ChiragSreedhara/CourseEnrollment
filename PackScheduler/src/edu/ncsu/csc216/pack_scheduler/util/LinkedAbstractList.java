package edu.ncsu.csc216.pack_scheduler.util;

import java.util.AbstractList;

/**
 * LinkedList implementation - all items must be unique
 * 
 * @author Fahad Ansari
 * @author Ryan Stauffer
 * @author Chirag Sreedhara
 * 
 * @param <E> element to keep in the List
 */
public class LinkedAbstractList<E> extends AbstractList<E> {
	/**
	 * size of the Linked Abstract List
	 */
	private int size;
	/**
	 * capacity of the Linked Abstract List
	 */
	private int capacity;
	/**
	 * Entrypoint node to the Linked List
	 */
	private ListNode front;

	/**
	 * Back of the linked list
	 */
	private ListNode back;

	/**
	 * Constructor for Linked Abstract List.
	 * 
	 * @param capacity capacity for the linked list - error is thrown if size
	 *                 exceeded this value
	 * @throws IllegalArgumentException if capacity is less than 0 or less than size
	 */
	public LinkedAbstractList(int capacity) {
		this.size = 0;
		this.front = null;
		this.back = null;
		if (capacity < 0 || capacity < size()) {
			throw new IllegalArgumentException();
		}
		setCapacity(capacity);

	}

	/**
	 * get the item at index
	 * 
	 * 
	 * @param idx index of the item to retrieve the value of
	 * @return value of the item at index
	 * @throws IndexOutOfBoundsException if the index is less than 0 or great than
	 *                                   or equal to the list's size
	 */
	@Override
	public E get(int idx) {
		if (idx < 0 || idx >= size()) {
			throw new IndexOutOfBoundsException();
		}
		ListNode current = front;
		for (int i = 0; i < idx; i++) {
			current = current.next;
		}
		return current.data;
	}

	/**
	 * set the item at index idx to element
	 * 
	 * @param idx     index of the element to set
	 * @param element element to set as the new value
	 * 
	 * @return the previous value at index idx
	 * 
	 * @throws IndexOutOfBoundsException if the index is less than 0 or great than
	 *                                   or equal to the list's size
	 * @throws NullPointerException      if the element to set is null
	 * @throws IllegalArgumentException  if the element to set is not unique (as
	 *                                   determined by equals) to the other elements
	 *                                   in the list
	 */
	@Override
	public E set(int idx, E element) {
		if (element == null) {
			throw new NullPointerException();
		}
		if (!isUnique(element)) {
			throw new IllegalArgumentException();
		}
		if (idx < 0 || idx >= size()) {
			throw new IndexOutOfBoundsException();
		}

		ListNode current = front;
		for (int i = 0; i < idx; i++) {
			current = current.next;
		}
		E prevElement = current.data;
		current.data = element;

		return prevElement;
	}

	/**
	 * add an item element at index idx, right shifting all values at or greater to
	 * that index by one
	 * 
	 * @param idx     index to add the element too
	 * @param element element to add
	 * 
	 * @throws IndexOutOfBoundsException if the index is less than 0 or great than
	 *                                   or equal to the list's size
	 * @throws NullPointerException      if the element to add is null
	 * @throws IllegalArgumentException  if the element to add is not unique (as
	 *                                   determined by equals) to the other elements
	 *                                   in the list
	 * @throws IllegalArgumentException  if the list is already at capacity
	 */
	@Override
	public void add(int idx, E element) {
		if (element == null) {
			throw new NullPointerException();
		}
		if (size() == capacity || !isUnique(element)) {
			throw new IllegalArgumentException();
		}

		if (idx < 0 || idx > size()) {
			throw new IndexOutOfBoundsException();
		}
		if (size == 0) {
			front = new ListNode(element);
			back = front;
		} else if (idx == 0) {
			front = new ListNode(element, front);
		} else if (idx == size()) {
			back.next = new ListNode(element);
			back = back.next;
		} else {
			ListNode current = front;
			for (int i = 0; i < idx - 1; i++) {
				current = current.next;
			}
			current.next = new ListNode(element, current.next);
		}
		size++;
	}

	/**
	 * remove the item at index. If the list has one element, front is set to null.
	 * 
	 * @param idx index of the item to remove
	 * @throws IndexOutOfBoundsException if the index is less than 0 or great than
	 *                                   or equal to the list's size
	 */
	@Override
	public E remove(int idx) {
		if (idx < 0 || idx >= size()) {
			throw new IndexOutOfBoundsException();
		}
		E returnVal;
		if (size() == 1) {
			returnVal = front.data;
			front = null;
		} else if (idx == 0) {
			returnVal = front.data;
			front = front.next;
		} else {
			ListNode current = front;
			for (int i = 0; i < idx - 1; i++) {
				current = current.next;
			}
			if (idx == size() - 1) {
				back = current;
			}

			returnVal = current.next.data;
			current.next = current.next.next;
		}
		size--;
		return returnVal;
	}

	/**
	 * getter for the List's size
	 * 
	 * @return the size of the list
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * helper method to check if an element is unique
	 * 
	 * @param element element to check
	 * @return true if the element is unique, otherwise false
	 */
	private boolean isUnique(E element) {
		ListNode current = front;
		while (current != null) {
			if (current.data.equals(element)) {
				return false;
			}
			current = current.next;
		}
		return true;
	}

	/**
	 * Sets capacity of list
	 * 
	 * @param capacity to set
	 * @throws IllegalArgumentException if capacity invalid
	 */
	public void setCapacity(int capacity) {
		if (capacity < 0 || capacity < size) {
			throw new IllegalArgumentException("Invalid capacity.");
		}
		this.capacity = capacity;
	}

	/**
	 * Class to store info for each individual node used in the linked list, private
	 * inner class
	 * 
	 * @author Chirag Sreedhara
	 * @author Ryan Stauffer
	 * @author Fahad Ansari
	 */
	private class ListNode {
		/**
		 * data for node to hold
		 */
		E data;
		/**
		 * reference to the next node - if null then there is no ref
		 */
		ListNode next;

		/**
		 * Constructor with data and next reference
		 * 
		 * @param data data for node to hold
		 * @param next reference to next node
		 */
		public ListNode(E data, ListNode next) {
			this.data = data;
			this.next = next;
		}

		/**
		 * Constructor with only data - next is initially null
		 * 
		 * @param data data for node to hold
		 */
		public ListNode(E data) {
			this(data, null);
		}
	}
}