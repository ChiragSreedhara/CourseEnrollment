package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class of LinkedListRecursive.java
 * 
 * @author David Martinez
 * @author Chirag Sreedhara
 */
class LinkedListRecursiveTest {

	/** Test list */
	private LinkedListRecursive<String> list1;

	/**
	 * Create new ArrayList every test
	 * 
	 * @throws Exception exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		list1 = new LinkedListRecursive<String>();
	}

	/**
	 * Tests add method
	 */
	@Test
	public void testAdd() {
		assertTimeoutPreemptively(Duration.ofSeconds(5), () -> {
			assertEquals(0, list1.size());

			list1.add(0, "test1");
			assertEquals(1, list1.size());
			assertEquals("test1", list1.get(0)); // passes

			list1.add(1, "test2");
			assertEquals(2, list1.size());

		});
	}

	/**
	 * Test add with not idx parameter
	 */
	@Test
	public void testAdd2() {
		assertTimeoutPreemptively(Duration.ofSeconds(5), () -> {
			assertEquals(0, list1.size());

			list1.add("test1");
			assertEquals(1, list1.size());
			assertEquals("test1", list1.get(0)); // passes

			list1.add("test2");
			assertEquals(2, list1.size());

			Exception e1 = assertThrows(NullPointerException.class, () -> list1.add(null));
			assertNotNull(e1);

		});
	}

	/**
	 * test the method 'add' and 'get in ArrayList with valid inputs
	 */
	@Test
	void testAddandGetValid() {

		assertEquals(0, list1.size());

		list1.add(0, "test1");
		assertEquals(1, list1.size());
		assertEquals("test1", list1.get(0));

		list1.add(1, "test2");
		assertEquals(2, list1.size());
		assertEquals("test2", list1.get(1));

		list1.add(2, "test3");
		assertEquals(3, list1.size());
		assertEquals("test3", list1.get(2));

		list1.add(1, "test4");
		assertEquals(4, list1.size());
		assertEquals("test1", list1.get(0));
		assertEquals("test4", list1.get(1));
		assertEquals("test2", list1.get(2));
		assertEquals("test3", list1.get(3));

	}

	/**
	 * Tests add method (null)
	 */
	@Test
	public void testAddNullElement() {
		assertThrows(NullPointerException.class, () -> list1.add(0, null));
	}

	/**
	 * Tests add method (index out of bounds)
	 */
	@Test
	public void testAddIndexOutOfBounds() {
		assertThrows(IndexOutOfBoundsException.class, () -> list1.add(-1, "A"));
		assertThrows(IndexOutOfBoundsException.class, () -> list1.add(1, "A"));
	}

	/**
	 * Tests add method (duplicate)
	 */
	@Test
	public void testAddDuplicateElement() {
		list1.add(0, "A");
		assertThrows(IllegalArgumentException.class, () -> list1.add(0, "A"));
	}

	/**
	 * Tests remove method
	 */
	@Test
	public void testRemove() {
		list1.add(0, "A");
		list1.add(1, "B");
		assertEquals(2, list1.size());
		assertEquals("A", list1.remove(0));
		assertEquals("B", list1.get(0));
	}

	/**
	 * test remove with elem parameter
	 */
	@Test
	void testRemove2() {
		assertEquals(0, list1.size());

		list1.add(0, "test1");
		assertEquals(1, list1.size());
		assertEquals("test1", list1.get(0));

		assertTrue(list1.remove("test1"));
		assertEquals(0, list1.size());

		list1.add(0, "test1");
		assertEquals(1, list1.size());
		assertEquals("test1", list1.get(0));

		list1.add(1, "test2");
		assertEquals(2, list1.size());
		assertEquals("test2", list1.get(1));

		assertTrue(list1.remove("test2"));
		assertEquals(1, list1.size());
		assertEquals("test1", list1.get(0));

		list1.add(1, "test2"); // not adding
		assertEquals(2, list1.size());
		assertEquals("test2", list1.get(1)); // throwing index out of bound

		list1.add(2, "test3");
		assertEquals(3, list1.size());
		assertEquals("test3", list1.get(2));

		assertTrue(list1.remove("test1"));
		assertEquals(2, list1.size());
		assertEquals("test2", list1.get(0));
		assertEquals("test3", list1.get(1));

		list1.add("test4");
		assertEquals("test4", list1.get(2));
		assertEquals(3, list1.size());
		list1.add("test5");
		assertEquals("test5", list1.get(3));
		assertEquals(4, list1.size());

		assertTrue(list1.remove("test3"));
		assertEquals(3, list1.size());
		assertEquals("test2", list1.get(0));
		assertEquals("test4", list1.get(1));
		assertEquals("test5", list1.get(2));

		Exception e1 = assertThrows(IndexOutOfBoundsException.class, () -> list1.remove(3));
		assertNotNull(e1);
	}

	/**
	 * Tests remove method (index out of bounds)
	 */
	@Test
	public void testRemoveIndexOutOfBounds() {
		assertThrows(IndexOutOfBoundsException.class, () -> list1.remove(-1));
		assertThrows(IndexOutOfBoundsException.class, () -> list1.remove(1));
	}

	/**
	 * Tests set method
	 */
	@Test
	public void testSet() {
		list1.add(0, "A");
		list1.add(1, "B");
		String element = list1.set(1, "C");
		assertEquals(2, list1.size());
		assertEquals("B", element);
		assertEquals("C", list1.get(1));
	}

	/**
	 * Tests set method (null)
	 */
	@Test
	public void testSetNullElement() {
		assertThrows(IndexOutOfBoundsException.class, () -> list1.set(0, null));
	}

	/**
	 * Tests set method (index out of bounds)
	 */
	@Test
	public void testSetIndexOutOfBounds() {
		assertThrows(IndexOutOfBoundsException.class, () -> list1.set(-1, "A"));
		assertThrows(IndexOutOfBoundsException.class, () -> list1.set(1, "A"));
	}

	/**
	 * Tests set method (duplicate)
	 */
	@Test
	public void testSetDuplicateElement() {
		list1.add(0, "A");
		assertThrows(IllegalArgumentException.class, () -> list1.set(0, "A"));
	}

	/**
	 * Tests get method (index out of bounds)
	 */
	@Test
	public void testGetInvalidIndex() {
		assertThrows(IndexOutOfBoundsException.class, () -> list1.get(-1));
		assertThrows(IndexOutOfBoundsException.class, () -> list1.get(3));
	}

	/**
	 * test the method 'add' in ArrayList with invalid inputs
	 */
	@Test
	void testAddInvalid() {
		assertEquals(0, list1.size());

		Exception e1 = assertThrows(NullPointerException.class, () -> list1.add(null));
		assertNotNull(e1);

		list1.add(0, "test1");
		assertEquals(1, list1.size());
		assertEquals("test1", list1.get(0));

		Exception e2 = assertThrows(IllegalArgumentException.class, () -> list1.add(1, "test1"));
		assertNotNull(e2);

		Exception e3 = assertThrows(IndexOutOfBoundsException.class, () -> list1.add(2, "test2"));
		assertNotNull(e3);
	}

	/**
	 * test the method 'get' in Arraylist with invalid inputs
	 */
	@Test
	void testGetInvalid() {
		assertEquals(0, list1.size());

		Exception e1 = assertThrows(IndexOutOfBoundsException.class, () -> list1.get(0));
		assertNotNull(e1);

		list1.add(0, "test1");
		assertEquals(1, list1.size());
		assertEquals("test1", list1.get(0));

		Exception e2 = assertThrows(IndexOutOfBoundsException.class, () -> list1.get(1));
		assertNotNull(e2);
	}

	/**
	 * test the method 'remove' in ArrayList with invalid inputs
	 */
	@Test
	void testRemoveValid() {
		assertEquals(0, list1.size());

		list1.add(0, "test1");
		assertEquals(1, list1.size());
		assertEquals("test1", list1.get(0));

		assertEquals(list1.remove(0), "test1");
		assertEquals(0, list1.size());

		list1.add(0, "test1");
		assertEquals(1, list1.size());
		assertEquals("test1", list1.get(0));

		list1.add(1, "test2");
		assertEquals(2, list1.size());
		assertEquals("test2", list1.get(1));

		assertEquals(list1.remove(1), "test2");
		assertEquals(1, list1.size());
		assertEquals("test1", list1.get(0));

		list1.add(1, "test2"); // not adding
		assertEquals(2, list1.size());
		assertEquals("test2", list1.get(1)); // throwing index out of bound

		list1.add(2, "test3");
		assertEquals(3, list1.size());
		assertEquals("test3", list1.get(2));

		assertEquals(list1.remove(0), "test1");
		assertEquals(2, list1.size());
		assertEquals("test2", list1.get(0));
		assertEquals("test3", list1.get(1));

		list1.add("test4");
		assertEquals("test4", list1.get(2));
		assertEquals(3, list1.size());
		list1.add("test5");
		assertEquals("test5", list1.get(3));
		assertEquals(4, list1.size());

		assertEquals("test3", list1.remove(1));
		assertEquals(3, list1.size());
		assertEquals("test2", list1.get(0));
		assertEquals("test4", list1.get(1));
		assertEquals("test5", list1.get(2));

		Exception e1 = assertThrows(IndexOutOfBoundsException.class, () -> list1.remove(3));
		assertNotNull(e1);
	}

	/**
	 * test the method 'remove' in Arraylist with invalid inputs
	 */
	@Test
	void testRemoveInvalid() {
		assertEquals(0, list1.size());

		Exception e1 = assertThrows(IndexOutOfBoundsException.class, () -> list1.remove(0));
		assertNotNull(e1);

		list1.add(0, "test1");
		assertEquals(1, list1.size());
		assertEquals("test1", list1.get(0));

		Exception e2 = assertThrows(IndexOutOfBoundsException.class, () -> list1.remove(1));
		assertNotNull(e2);
	}

	/**
	 * test the method 'set' in ArrayList with valid inputs
	 */
	@Test
	void testSetValid() {
		assertEquals(0, list1.size());

		list1.add(0, "test1");
		assertEquals(1, list1.size());
		assertEquals("test1", list1.get(0));

		assertEquals("test1", list1.set(0, "test2"));
		assertEquals(1, list1.size());
		assertEquals("test2", list1.get(0));

		list1.add(1, "test1");
		assertEquals(2, list1.size());
		assertEquals("test1", list1.get(1));

		assertEquals("test1", list1.set(1, "test3"));
		assertEquals(2, list1.size());
		assertEquals("test3", list1.get(1));
	}

	/**
	 * test the method 'set' in ArrayList with invalid inputs
	 */
	@Test
	void testSetInvalid() {
		assertEquals(0, list1.size());

		Exception e1 = assertThrows(IndexOutOfBoundsException.class, () -> list1.set(0, null));
		assertNotNull(e1);

		list1.add(0, "test1");
		assertEquals(1, list1.size());
		assertEquals("test1", list1.get(0));

		Exception e2 = assertThrows(IllegalArgumentException.class, () -> list1.set(0, "test1"));
		assertNotNull(e2);

		Exception e3 = assertThrows(IndexOutOfBoundsException.class, () -> list1.set(2, "test2"));
		assertNotNull(e3);
	}

}
