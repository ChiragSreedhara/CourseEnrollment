package edu.ncsu.csc216.pack_scheduler.util;

import java.util.AbstractSequentialList;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Allows functionality for all standard list methods by implementing with
 * iterators.
 * 
 * @author David Martinez
 * @author Chirag Sreedhara
 * @param <E> Object contained in list
 */
public class LinkedList<E> extends AbstractSequentialList<E> {
	/** Stores front of list */
	private ListNode front;
	/** Stores back of list */
	private ListNode back;
	/** Stores size of list */
	private int size;

	/** LinkedList constructor */
	public LinkedList() {
		front = new ListNode(null);
		back = new ListNode(null);
		size = 0;

		front.next = back;
		back.prev = front;
	}

	/**
	 * Class to store information for each node in LinkedList
	 * 
	 * @author David Martinez
	 * @author Chirag Sreedhara
	 */
	private class ListNode {

		/** Stores data in the node */
		public E data;
		/** Stores pointer to next node */
		public ListNode next;
		/** Stores pointer to prev node */
		public ListNode prev;

		/**
		 * Constructor for ListNode with just data
		 * 
		 * @param data stored in node
		 */
		ListNode(E data) {
			this(data, null, null);
		}

		/**
		 * Constructor for ListNode with all info
		 * 
		 * @param e    to store in node
		 * @param prev pointer to prev node
		 * @param next pointer to next node
		 */
		ListNode(E e, ListNode prev, ListNode next) {
			this.data = e;
			this.prev = prev;
			this.next = next;
		}

	}

	/**
	 * Creates a concrete implementation of AbstractSequentialList for use in
	 * LinkedList
	 * 
	 * @author David Martinez
	 * @author Chirag Sreedhara
	 */
	private class LinkedListIterator implements ListIterator<E> {

		/** Stores previous index of iterator */
		public int previousIndex;
		/** Stores next index of iterator */
		public int nextIndex;
		/** Stores next node */
		public ListNode next;
		/** Stores prev node */
		public ListNode previous;
		/** Stores last retrieved node */
		private ListNode lastRetrieved;

		/**
		 * Constructor method
		 * 
		 * @param index to iterate to
		 * @throws IndexOutOfBoundsException if IOB
		 */
		public LinkedListIterator(int index) {
			if (index < 0 || index > size) {
				throw new IndexOutOfBoundsException();
			}

			ListNode current = front;
			int currentIdx = 0;
			for (int i = 0; i < index; i++) {
				current = current.next;
				currentIdx = i + 1;
			}
			previousIndex = currentIdx - 1;
			nextIndex = currentIdx;
			previous = current.prev;
			next = current;

			lastRetrieved = null;
		}

		/**
		 * Checks if iterator has a next val
		 * 
		 * @return true if it does, false otherwise
		 */
		@Override
		public boolean hasNext() {
			lastRetrieved = null;
			return next.data != null;
		}

		/**
		 * Returns the next value
		 * 
		 * @return next node
		 * @throws NoSuchElementException if no next element exists
		 */
		@Override
		public E next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			E rtn = next.data;
			lastRetrieved = next;
			previousIndex++;
			nextIndex++;
			next = next.next;
//			if (previous != null) {
//				previous = previous.next;
//			}
			previous = next.prev;
			return rtn;
		}

		/**
		 * Checks if a previous node in list exist
		 * 
		 * @returns true if prev value exists, false otherwise
		 */
		@Override
		public boolean hasPrevious() {
			lastRetrieved = null;
			return previous != null;
		}

		/**
		 * Returns previous node in the list
		 * 
		 * @return prev node
		 * @throws NoSuchElementException if no previous exists
		 */
		@Override
		public E previous() {
			if (!hasPrevious()) {
				throw new NoSuchElementException();
			}
			E rtn = previous.data;
			lastRetrieved = previous;
			previousIndex--;
			nextIndex--;
			next = next.prev;
//			if (previous != null) {
//				previous = previous.prev;
//			}
			previous = next.prev;
			return rtn;
		}

		/**
		 * Returns the next index in list
		 * 
		 * @return next index in list
		 */
		@Override
		public int nextIndex() {
			if (hasNext()) {
				return nextIndex;
			}
			return size;
		}

		/**
		 * Returns the prev index in list
		 * 
		 * @return prev index in list
		 */
		@Override
		public int previousIndex() {
			if (hasPrevious()) {
				return previousIndex;
			}
			return -1;
		}

		/**
		 * Removes the most recently retrieved obj.
		 * 
		 * @throws IllegalStateException if lastRetrieved is null
		 */
		@Override
		public void remove() {
			if (lastRetrieved == null) {
				throw new IllegalStateException();
			}

			if (lastRetrieved == back) {
				back = back.prev;
			}
			if (lastRetrieved == front) {
				front = front.next;
			} else {
				lastRetrieved.prev.next = lastRetrieved.next;
				lastRetrieved.next.prev = lastRetrieved.prev;
			}

//			if(lastRetrieved.prev != null) {
//				lastRetrieved.prev.next = lastRetrieved.next;
//				
//			}

			lastRetrieved = null;
			size--;
		}

		/**
		 * Sets the recently returned value to a new value
		 * 
		 * @param e the element with which to replace the last element returned by next
		 *          or previous
		 * 
		 * @throws IllegalStateException if lastRetrieved is null
		 * @throws NullPointerException  if null new value.
		 */
		@Override
		public void set(E e) {
			if (lastRetrieved == null) {
				throw new IllegalStateException();
			}
			if (e == null) {
				throw new NullPointerException();
			}

			lastRetrieved.data = e;

		}

		/**
		 * Add the element between previous and next
		 * 
		 * @param e added element
		 * @throws NullPointerException if given element null
		 */
		@Override
		public void add(E e) {
			if (e == null) {
				throw new NullPointerException();
			}
			ListNode add = new ListNode(e, this.previous, this.next);
			if (previous != null) {
				previous.next = add;
			}
			next.prev = add;
			lastRetrieved = null;
			if (add.next == front) {
				front = add;
			}
			if (add.prev == back) {
				back = add;
			}
			size++;

		}

	}

	/**
	 * Returns the size of the list.
	 * 
	 * @return list's size
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * Replaces a value with a given value
	 * 
	 * @param index   to replace at
	 * @param element to replace with
	 * @return set elem
	 * @throws IllegalArgumentException if element already in list.
	 */
	@Override
	public E set(int index, E element) {
		if (contains(element)) {
			throw new IllegalArgumentException();
		}
		return super.set(index, element);
	}

	/**
	 * Adds a val t oa list
	 * 
	 * @param index   to add at
	 * @param element to add
	 * @throws IllegalArgumentException if element already in list.
	 */
	@Override
	public void add(int index, E element) {
		if (contains(element)) {
			throw new IllegalArgumentException();
		}
		super.add(index, element);
	}

	/**
	 * Iterator for the list
	 * 
	 * @param index to iterate
	 * @return New listIterator
	 */
	@Override
	public ListIterator<E> listIterator(int index) {
		return new LinkedListIterator(index);
	}

}
