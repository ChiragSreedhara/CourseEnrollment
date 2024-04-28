package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.EmptyStackException;

import org.junit.jupiter.api.Test;

/**
 * Tests LinkedStack class
 * 
 * @author David Martinez
 * @author Chirag Sreedhara
 */
class LinkedStackTest {

	/**
	 * Tests push() method
	 */
	@Test
	void testPush() {
		LinkedStack<String> as = new LinkedStack<String>(2);
		assertEquals(as.size(), 0);
		as.push("String 1");
		assertEquals(as.size(), 1);
		as.push("String 2");
		assertEquals(as.size(), 2);
		assertThrows(IllegalArgumentException.class, () -> as.push("String 3"));
	}

	/**
	 * Tests pop() method
	 */
	@Test
	void testPop() {
		LinkedStack<String> as = new LinkedStack<String>(5);
		assertThrows(EmptyStackException.class, () -> as.pop());
		assertEquals(as.size(), 0);
		as.push("String 1");
		assertEquals(as.size(), 1);
		as.push("String 2");
		assertEquals(as.size(), 2);
		assertEquals(as.pop(), "String 2");
		assertEquals(as.pop(), "String 1");
	}

	/**
	 * Tests isEmpty() method
	 */
	@Test
	void testIsEmpty() {
		LinkedStack<String> as = new LinkedStack<String>(5);
		assertTrue(as.isEmpty());
		as.push("String 1");
		assertFalse(as.isEmpty());
	}

	/**
	 * Tests setCapacity() method.
	 */
	@Test
	void testSetCapacity() {
		LinkedStack<String> as = new LinkedStack<String>(5);
		assertThrows(IllegalArgumentException.class, () -> as.setCapacity(-1));
		as.push("String 1");
		as.push("String 2");
		assertThrows(IllegalArgumentException.class, () -> as.setCapacity(1));

	}

}
