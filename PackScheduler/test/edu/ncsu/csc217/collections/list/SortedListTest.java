package edu.ncsu.csc217.collections.list;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Test;

/**
 * The SortedListTest class tests if sorted list works in the PackScheduler
 * program and if it works correctly as it should function.
 * 
 * @author Fahad Ansari
 * @author Niyati Patel
 */
public class SortedListTest {

	/**
	 * Tests the sorted list called list
	 */
	@Test
	public void testSortedList() {
		SortedList<String> list = new SortedList<String>();
		assertEquals(0, list.size());
		assertFalse(list.contains("apple"));

		// Check if the list is empty and has a size of zero after construction
		assertTrue(list.isEmpty());
		assertEquals(0, list.size());

		// Remember the list's initial capacity is 10

		// Adding 11 elements to the list
		for (int i = 0; i < 11; i++) {
			list.add("element" + i);
		}
		// Checking if the list size is now 11
		assertEquals(11, list.size());
	}

	/**
	 * Tests if adding works in the sorted list called list
	 */
	@Test
	public void testAdd() {
		SortedList<String> list = new SortedList<String>();

		list.add("banana");
		assertEquals(1, list.size());
		assertEquals("banana", list.get(0));

		// Adding to the front of the list
		list.add("apple");
		assertEquals(2, list.size());
		assertEquals("apple", list.get(0));
		assertEquals("banana", list.get(1));

		// Adding to the middle of the list
		list.add("orange");
		assertEquals(3, list.size());
		assertEquals("apple", list.get(0));
		assertEquals("banana", list.get(1));
		assertEquals("orange", list.get(2));

		// Adding to the back of the list
		list.add("pear");
		assertEquals(4, list.size());
		assertEquals("apple", list.get(0));
		assertEquals("banana", list.get(1));
		assertEquals("orange", list.get(2));
		assertEquals("pear", list.get(3));

		assertEquals(4, list.size());
		assertEquals("apple", list.get(0));
		assertEquals("banana", list.get(1));
		assertEquals("orange", list.get(2));
		assertEquals("pear", list.get(3));
		assertThrows(NullPointerException.class, () -> list.add(null));

		assertEquals(4, list.size());
		Exception e1 = assertThrows(IllegalArgumentException.class, () -> list.add("banana"));
		assertEquals("Element already in list.", e1.getMessage());
	}

	/**
	 * Tests if the sorted list called list gets a specific element from the sorted
	 * list
	 */
	@Test
	public void testGet() {
		SortedList<String> list = new SortedList<String>();

		// Since get() is used throughout the tests to check the
		// contents of the list, we don't need to test main flow functionality
		// here. Instead this test method should focus on the error
		// and boundary cases.

		assertEquals(0, list.size());
		assertThrows(IndexOutOfBoundsException.class, () -> list.get(0));

		assertEquals(0, list.size());
		list.add("banana");
		list.add("apple");
		list.add("orange");
		list.add("pear");
		assertEquals(4, list.size());

		assertEquals(4, list.size());
		assertThrows(IndexOutOfBoundsException.class, () -> list.get(-1));

		assertEquals(4, list.size());
		assertThrows(IndexOutOfBoundsException.class, () -> list.get(list.size()));
	}

	/**
	 * Tests if the sorted list called list removes a specific element from the
	 * sorted list
	 */
	@Test
	public void testRemove() {
		SortedList<String> list = new SortedList<String>();

		assertEquals(0, list.size());
		assertThrows(IndexOutOfBoundsException.class, () -> list.remove(0));

		assertEquals(0, list.size());
		list.add("apple");
		list.add("banana");
		list.add("orange");
		list.add("pear");
		assertEquals(4, list.size());

		assertEquals(4, list.size());
		assertThrows(IndexOutOfBoundsException.class, () -> list.remove(-1));

		assertEquals(4, list.size());
		assertThrows(IndexOutOfBoundsException.class, () -> list.remove(list.size()));

		assertEquals(4, list.size());
		assertEquals("banana", list.remove(1));

		assertEquals(3, list.size());
		assertEquals("pear", list.remove(2));

		assertEquals(2, list.size());
		assertEquals("apple", list.remove(0));

		assertEquals(1, list.size());
		assertEquals("orange", list.remove(0));
		assertEquals(0, list.size());
	}

	/**
	 * Tests if the sorted list called list gets a specific element from the sorted
	 * list by its index, if the size of the sorted list is correct, and if making
	 * changes to the sorted list is shown in the size of the sorted list.
	 */
	@Test
	public void testIndexOf() {
		SortedList<String> list = new SortedList<String>();

		assertEquals(0, list.size());
		assertEquals(-1, list.indexOf("empty"));

		assertEquals(0, list.size());
		list.add("apple");
		list.add("banana");
		list.add("orange");
		list.add("pear");
		assertEquals(4, list.size());

		assertEquals(4, list.size());
		assertEquals(0, list.indexOf("apple"));
		assertEquals(1, list.indexOf("banana"));
		assertEquals(2, list.indexOf("orange"));
		assertEquals(3, list.indexOf("pear"));

		assertEquals(-1, list.indexOf("grape"));
		assertEquals(-1, list.indexOf("mango"));
		assertEquals(4, list.size());

		assertEquals(4, list.size());
		assertThrows(NullPointerException.class, () -> list.indexOf(null));
	}

	/**
	 * Tests if the sorted list is cleared after some elements are added and then
	 * are cleared out. Checks that the size returns to 0.
	 */
	@Test
	public void testClear() {
		SortedList<String> list = new SortedList<String>();

		assertEquals(0, list.size());
		list.add("apple");
		list.add("banana");
		list.add("orange");
		list.add("pear");
		assertEquals(4, list.size());

		list.clear();

		assertEquals(0, list.size());
	}

	/**
	 * Tests if the sorted list is empty without any elements and if it is not empty
	 * after adding an element.
	 */
	@Test
	public void testIsEmpty() {
		SortedList<String> list = new SortedList<String>();

		assertTrue(list.isEmpty());
		assertEquals(0, list.size());

		list.add("pear");
		assertEquals(1, list.size());

		assertFalse(list.isEmpty());
		assertEquals(1, list.size());
	}

	/**
	 * Tests if certain elements are in the sorted list and are correctly declared
	 * if they are or are not in the sorted list.
	 */
	@Test
	public void testContains() {
		SortedList<String> list = new SortedList<String>();

		assertFalse(list.contains("apple"));
		assertEquals(0, list.size());

		list.add("apple");
		list.add("banana");
		list.add("orange");
		assertEquals(3, list.size());

		assertTrue(list.contains("apple"));
		assertTrue(list.contains("banana"));
		assertTrue(list.contains("orange"));

		assertFalse(list.contains("grape"));
		assertFalse(list.contains("mango"));
	}

	/**
	 * Tests if different lists with specific elements order in a specific way equal
	 * each other or not.
	 */
	@Test
	public void testEquals() {
		SortedList<String> list1 = new SortedList<String>();
		SortedList<String> list2 = new SortedList<String>();
		SortedList<String> list3 = new SortedList<String>();

		list1.add("apple");
		list1.add("banana");
		list1.add("orange");

		list2.add("apple");
		list2.add("banana");
		list2.add("orange");

		list3.add("apple");
		list3.add("grape");
		list3.add("orange");

		assertTrue(list1.equals(list2));
		assertFalse(list1.equals(list3));

		assertFalse(list1.equals("apple"));
	}

	/**
	 * Tests if the hash code for same lists equal each other and if the hash code
	 * for different lists do not equal each other.
	 */
	@Test
	public void testHashCode() {
		SortedList<String> list1 = new SortedList<String>();
		SortedList<String> list2 = new SortedList<String>();
		SortedList<String> list3 = new SortedList<String>();

		list1.add("apple");
		list1.add("banana");
		list1.add("orange");

		list2.add("apple");
		list2.add("banana");
		list2.add("orange");

		list3.add("apple");
		list3.add("grape");
		list3.add("orange");

		assertEquals(list1.hashCode(), list2.hashCode());
		assertNotEquals(list1.hashCode(), list3.hashCode());
	}

}
