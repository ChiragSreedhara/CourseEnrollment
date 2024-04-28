package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

/**
 * ArrayQueue JUnit test
 * 
 * @author David Martinez
 * @author Chirag Sreedhara
 */
class ArrayQueueTest {
	/** test list */
	private ArrayQueue list1;

	/**
	 * Test ArrayQueue constructor
	 */
	@Test
	void testConstructor() {
		list1 = new ArrayQueue(1);
		assertNotNull(list1);
		assertEquals(0, list1.size());

		Exception e1 = assertThrows(IllegalArgumentException.class, () -> list1.setCapacity(-1));
		assertEquals("Invalid capacity.", e1.getMessage());

		String s1 = "test1";
		try {
			list1.enqueue(s1);
		} catch (Exception e) {
			fail();
		}
	}

	/**
	 * test enqueueing and dequeueing
	 */
	@Test
	void testEnqueueAndDequeue() {
		list1 = new ArrayQueue(1);
		assertNotNull(list1);
		assertEquals(0, list1.size());
		assertTrue(list1.isEmpty());

		String s1 = "test1";
		try {
			list1.enqueue(s1);
			assertEquals(1, list1.size());
			assertFalse(list1.isEmpty());
		} catch (Exception e) {
			fail();
		}

		assertEquals("test1", list1.dequeue());
		assertEquals(0, list1.size());
		assertTrue(list1.isEmpty());

		list1.setCapacity(3);
		assertEquals(0, list1.size());
		assertTrue(list1.isEmpty());

		String s2 = "test2";
		String s3 = "test3";

		try {
			list1.enqueue(s1);
			assertEquals(1, list1.size());
			assertFalse(list1.isEmpty());

			list1.enqueue(s2);
			assertEquals(2, list1.size());
			list1.enqueue(s3);
			assertEquals(3, list1.size());
			assertFalse(list1.isEmpty());

		} catch (Exception e) {
			fail();
		}

		Exception e2 = assertThrows(IllegalArgumentException.class, () -> list1.setCapacity(2));
		assertEquals("Invalid capacity.", e2.getMessage());

		assertEquals("test1", list1.dequeue());
		assertEquals(2, list1.size());
		assertFalse(list1.isEmpty());

		assertEquals("test2", list1.dequeue());
		assertEquals(1, list1.size());
		assertFalse(list1.isEmpty());

		assertEquals("test3", list1.dequeue());
		assertEquals(0, list1.size());
		assertTrue(list1.isEmpty());
	}
}
