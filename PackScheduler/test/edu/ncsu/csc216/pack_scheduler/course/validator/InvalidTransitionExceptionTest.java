package edu.ncsu.csc216.pack_scheduler.course.validator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * test class for
 * {@link edu.ncsu.csc216.pack_scheduler.course.validator.InvalidTransitionException}
 */
class InvalidTransitionExceptionTest {

	/**
	 * tests default error message
	 */
	@Test
	void testInvalidTransitionException() {
		Exception e = assertThrows(InvalidTransitionException.class, () -> {
			throw new InvalidTransitionException();
		});
		assertEquals("Invalid FSM Transition.", e.getMessage());
	}

	/**
	 * tests changing the error message
	 */
	@Test
	void testInvalidTransitionExceptionString() {
		Exception e = assertThrows(InvalidTransitionException.class, () -> {
			throw new InvalidTransitionException("we <3 our TAs");
		});
		assertEquals("we <3 our TAs", e.getMessage());
	}

}
