package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests ArrayList class
 * 
 * @author Fahad Ansari
 * @author Ryan Stauffer
 * @author Chirag Sreedhara
 */
public class ArrayListTest {

	/**
	 * Create ArrayList object
	 */
	private ArrayList<String> arrayList;

	/**
	 * SetUp the program
	 */
	@Before
	public void setUp() {
		arrayList = new ArrayList<>();
	}

	/**
	 * Tests add method
	 */
	@Test
	public void testAdd() {
		arrayList.add(0, "A");
		arrayList.add(1, "B");
		arrayList.add(1, "C");
		assertEquals(3, arrayList.size());
		assertEquals("A", arrayList.get(0));
		assertEquals("C", arrayList.get(1));
		assertEquals("B", arrayList.get(2));
	}

	/**
	 * Tests add method (null)
	 */
	@Test
	public void testAddNullElement() {
		assertThrows(NullPointerException.class, () -> arrayList.add(0, null));
	}

	/**
	 * Tests add method (index out of bounds)
	 */
	@Test
	public void testAddIndexOutOfBounds() {
		assertThrows(IndexOutOfBoundsException.class, () -> arrayList.add(-1, "A"));
		assertThrows(IndexOutOfBoundsException.class, () -> arrayList.add(1, "A"));
	}

	/**
	 * Tests add method (duplicate)
	 */
	@Test
	public void testAddDuplicateElement() {
		arrayList.add(0, "A");
		assertThrows(IllegalArgumentException.class, () -> arrayList.add(0, "A"));
	}

	/**
	 * Tests remove method
	 */
	@Test
	public void testRemove() {
		arrayList.add(0, "A");
		arrayList.add(1, "B");
		assertEquals(2, arrayList.size());
		assertEquals("A", arrayList.remove(0));
		assertEquals("B", arrayList.get(0));
	}

	/**
	 * Tests remove method (index out of bounds)
	 */
	@Test
	public void testRemoveIndexOutOfBounds() {
		assertThrows(IndexOutOfBoundsException.class, () -> arrayList.remove(-1));
		assertThrows(IndexOutOfBoundsException.class, () -> arrayList.remove(1));
	}

	/**
	 * Tests set method
	 */
	@Test
	public void testSet() {
		arrayList.add(0, "A");
		arrayList.add(1, "B");
		String element = arrayList.set(1, "C");
		assertEquals(2, arrayList.size());
		assertEquals("B", element);
		assertEquals("C", arrayList.get(1));
	}

	/**
	 * Tests set method (null)
	 */
	@Test
	public void testSetNullElement() {
		assertThrows(NullPointerException.class, () -> arrayList.set(0, null));
	}

	/**
	 * Tests set method (index out of bounds)
	 */
	@Test
	public void testSetIndexOutOfBounds() {
		assertThrows(IndexOutOfBoundsException.class, () -> arrayList.set(-1, "A"));
		assertThrows(IndexOutOfBoundsException.class, () -> arrayList.set(1, "A"));
	}

	/**
	 * Tests set method (duplicate)
	 */
	@Test
	public void testSetDuplicateElement() {
		arrayList.add(0, "A");
		assertThrows(IllegalArgumentException.class, () -> arrayList.set(0, "A"));
	}

	/**
	 * Tests get method (index out of bounds)
	 */
	@Test
	public void testGetInvalidIndex() {
		assertThrows(IndexOutOfBoundsException.class, () -> arrayList.get(-1));
		assertThrows(IndexOutOfBoundsException.class, () -> arrayList.get(3));
	}
}