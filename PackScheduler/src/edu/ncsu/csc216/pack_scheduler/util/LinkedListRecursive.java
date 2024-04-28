package edu.ncsu.csc216.pack_scheduler.util;

/**
 * Base creation of a linked list with recursive operations for efficiency and
 * use in PackScheduler
 * 
 * @author David Martinez
 * @author Chirag Sreedhara
 * @param <E> Data type stored in list
 */
public class LinkedListRecursive<E> {

	/** Size of list */
	private int size;

	/** Front val of list */
	private ListNode front;

	/**
	 * Constructor method
	 */
	public LinkedListRecursive() {
		size = 0;
		this.front = null;
	}

	/**
	 * Checks if list is empty
	 * 
	 * @return true if empty, false otherwise.
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * Returns size of list
	 * 
	 * @return size of list
	 */
	public int size() {
		return size;
	}

	/**
	 * Adds element to list (back)
	 * 
	 * @param elem to add
	 * @return if add was successful
	 * @throws NullPointerException     if elem if null
	 * @throws IllegalArgumentException if elem is already in the list
	 */
	public boolean add(E elem) {
		if (elem == null) {
			throw new NullPointerException();
		}
		if (contains(elem)) {
			throw new IllegalArgumentException();
		}
		if (front == null) {
			front = new ListNode(elem, front);
		} else {
			front.add(size - 1, elem);
		}
		size++;
		return true;
	}

	/**
	 * Adds element to list
	 * 
	 * @param idx  index to add at
	 * @param elem to add
	 * @throws IndexOutOfBoundsException if idx is less than 0, or equal to or
	 *                                   greater than the size
	 * @throws NullPointerException      if elem if null
	 * @throws IllegalArgumentException  if elem is already in the list
	 */
	public void add(int idx, E elem) {
		if (elem == null) {
			throw new NullPointerException();
		}
		if (idx < 0 || idx > size) {
			throw new IndexOutOfBoundsException();
		}
		if (contains(elem)) {
			throw new IllegalArgumentException();
		}
		if (front == null || idx == 0) {
			front = new ListNode(elem, front);
		} else {
			front.add(idx - 1, elem);
		}
		size++;
	}

	/**
	 * Gets an element from list
	 * 
	 * @param idx to get form
	 * @return the requested element
	 * @throws IndexOutOfBoundsException if idx is less than 0, or equal to or
	 *                                   greater than the size
	 */
	public E get(int idx) {
		if (idx < 0 || idx >= size) {
			throw new IndexOutOfBoundsException();
		}
		return front.get(idx);
	}

	/**
	 * Removes requested element from list
	 * 
	 * @param elem to remove
	 * @return If removal succesful or not
	 */
	public boolean remove(E elem) {
		if (elem == null) {
			return false;
		}
		if (isEmpty() || !contains(elem)) {
			return false;
		}
		size--;
		if (front.data.equals(elem)) {
			front = front.next;
		} else {
			front.remove(elem);
		}
		return true;
	}

	/**
	 * Removes an element at specific index from list
	 * 
	 * @param idx to remove from
	 * @return elem removed
	 * @throws IndexOutOfBoundsException if idx is less than 0, or equal to or
	 *                                   greater than the size
	 */
	public E remove(int idx) {
		if (idx < 0 || idx >= size) {
			throw new IndexOutOfBoundsException();
		}
		size--;
		if (idx == 0) {
			E rtn = front.data;
			front = front.next;
			return rtn;
		}
		return front.remove(idx - 1);
	}

	/**
	 * Sets index in list to certain value
	 * 
	 * @param idx  to set
	 * @param elem to set to
	 * @return prev element
	 * @throws IndexOutOfBoundsException if idx is less than 0, or equal to or
	 *                                   greater than the size
	 * @throws NullPointerException      if elem if null
	 * @throws IllegalArgumentException  if elem is already in the list
	 */
	public E set(int idx, E elem) {
		if (idx < 0 || idx >= size) {
			throw new IndexOutOfBoundsException();
		}
		if (elem == null) {
			throw new NullPointerException();
		}
		if (contains(elem)) {
			throw new IllegalArgumentException();
		}
		return front.set(idx, elem);
	}

	/**
	 * Checks if list contains a certain element
	 * 
	 * @param elem to check for
	 * @return boolean if in list or not
	 */
	public boolean contains(E elem) {
		if (front == null) {
			return false;
		}
		return front.contains(elem);
	}

	/**
	 * Inner listNode class for linkedListRecursive
	 * 
	 * @author David Martinez
	 * @author Chirag Sreedhara
	 */
	private class ListNode {

		/** Data stored in the node */
		private E data;

		/** Next value for the node */
		private ListNode next;

		/**
		 * ListNode constructor
		 * 
		 * @param data stored in node
		 * @param next node value
		 */
		public ListNode(E data, ListNode next) {
			this.data = data;
			this.next = next;
		}

		/**
		 * Adds node
		 * 
		 * @param idx  index to add
		 * @param elem to add
		 */
		public void add(int idx, E elem) {
			if (idx == 0) {
				this.next = new ListNode(elem, this.next);
				return;
			}
			this.next.add(idx - 1, elem);
		}

		/**
		 * Gets val
		 * 
		 * @param idx to get from
		 * @return element
		 */
		public E get(int idx) {
			if (idx == 0) {
				return this.data;
			}
			return this.next.get(idx - 1);
		}

		/**
		 * Removes an element
		 * 
		 * @param idx to remove from
		 * @return element
		 */
		public E remove(int idx) {
			if (idx == 0) {
				E rtn = this.next.data;
				this.next = this.next.next;
				return rtn;
			}
			return this.next.remove(idx - 1);
		}

		/**
		 * Removes an element given element
		 * 
		 * @param elem to remove
		 * @return if removal successful
		 */
		public boolean remove(E elem) {
			if (this.next.data.equals(elem)) {
				this.next = this.next.next;
				return true;
			}
			this.next.remove(elem);
			return false;
		}

		/**
		 * Sets a value
		 * 
		 * @param idx  to set at
		 * @param elem to set to
		 * @return element
		 */
		public E set(int idx, E elem) {
			if (idx == 0) {
				E rtn = this.data;
				this.data = elem;
				return rtn;
			}
			return this.next.set(idx - 1, elem);
		}

		/**
		 * Checks if it contains a certain elem
		 * 
		 * @param elem to check for
		 * @return if contained or not.
		 */
		public boolean contains(E elem) {
			if (this.data.equals(elem)) {
				return true;
			}
			if (this.next == null) {
				return false;
			}
			return this.next.contains(elem);
		}
	}
}
