/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.course;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Tests the conflict exception class
 * 
 * @author Niyati Patel
 */
class ConflictExceptionTest {

	/**
	 * Tests for conflict exception with a custom message. Test method for
	 * {@link edu.ncsu.csc216.wolf_scheduler.course.ConflictException#ConflictException(java.lang.String)}.
	 */
	@Test
	public void testConflictExceptionString() {
		ConflictException ce = new ConflictException("Custom exception message");
		assertEquals("Custom exception message", ce.getMessage());
	}

	/**
	 * Tests for conflict exception with a default message. Test method for
	 * {@link edu.ncsu.csc216.wolf_scheduler.course.ConflictException#ConflictException()}.
	 */
	@Test
	public void testConflictException() {
		ConflictException ce = new ConflictException();
		assertEquals("Schedule conflict.", ce.getMessage());
	}

}
