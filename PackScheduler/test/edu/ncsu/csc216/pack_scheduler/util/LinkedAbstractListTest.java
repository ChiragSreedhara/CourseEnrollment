package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests LinkedAbstractList class
 * 
 * @author Fahad Ansari
 * @author Ryan Stauffer
 * @author Chirag Sreedhara
 */
public class LinkedAbstractListTest {

	/**
	 * Create LinkedLAbstractList object
	 */
	private LinkedAbstractList<String> list;

	/**
	 * SetUp the program
	 */
	@Before
	public void setUp() {
		list = new LinkedAbstractList<>(5); // Initialize list with capacity 5
	}

	/**
	 * Tests add method
	 */
	@Test
	public void testAdd() {
		list.add(0, "A");
		list.add(1, "B");
		list.add(1, "C");
		assertEquals(3, list.size());
		assertEquals("A", list.get(0));
		assertEquals("C", list.get(1));
		assertEquals("B", list.get(2));
	}

	/**
	 * Tests add method (null)
	 */
	@Test
	public void testAddNullElement() {
		assertThrows(NullPointerException.class, () -> list.add(0, null));
	}

	/**
	 * Tests add method (index out of bounds)
	 */
	@Test
	public void testAddIndexOutOfBounds() {
		assertThrows(IndexOutOfBoundsException.class, () -> list.add(-1, "A"));
		assertThrows(IndexOutOfBoundsException.class, () -> list.add(1, "A"));
	}

	/**
	 * Tests add method (duplicate)
	 */
	@Test
	public void testAddDuplicateElement() {
		list.add(0, "A");
		assertThrows(IllegalArgumentException.class, () -> list.add(0, "A"));
	}

	/**
	 * Tests remove method
	 */
	@Test
	public void testRemove() {
		list.add("A");
		list.add("B");
		list.add("C");
		list.add("D");
		assertEquals("B", list.remove(1));
		assertEquals(3, list.size());
		assertEquals("D", list.remove(2));
		assertEquals(2, list.size());
		assertEquals("A", list.remove(0));
	}

	/**
	 * Tests remove method (index out of bounds)
	 */
	@Test
	public void testRemoveIndexOutOfBounds() {
		assertThrows(IndexOutOfBoundsException.class, () -> list.remove(-1));
		assertThrows(IndexOutOfBoundsException.class, () -> list.remove(1));
	}

	/**
	 * Tests set method
	 */
	@Test
	public void testSet() {
		list.add(0, "A");
		list.add(1, "B");
		String element = list.set(1, "C");
		assertEquals(2, list.size());
		assertEquals("B", element);
		assertEquals("C", list.get(1));
	}

	/**
	 * Tests set method (null)
	 */
	@Test
	public void testSetNullElement() {
		assertThrows(NullPointerException.class, () -> list.set(0, null));
	}

	/**
	 * Tests set method (index out of bounds)
	 */
	@Test
	public void testSetIndexOutOfBounds() {
		assertThrows(IndexOutOfBoundsException.class, () -> list.set(-1, "A"));
		assertThrows(IndexOutOfBoundsException.class, () -> list.set(1, "A"));
	}

	/**
	 * Tests set method (duplicate)
	 */
	@Test
	public void testSetDuplicateElement() {
		list.add(0, "A");
		assertThrows(IllegalArgumentException.class, () -> list.set(0, "A"));
	}

	/**
	 * Tests get method (index out of bounds)
	 */
	@Test
	public void testGetInvalidIndex() {
		assertThrows(IndexOutOfBoundsException.class, () -> list.get(-1));
		assertThrows(IndexOutOfBoundsException.class, () -> list.get(3));
	}
}