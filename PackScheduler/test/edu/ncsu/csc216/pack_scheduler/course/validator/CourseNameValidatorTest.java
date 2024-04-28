package edu.ncsu.csc216.pack_scheduler.course.validator;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * tests for the state pattern implementation of the input validation
 */
public class CourseNameValidatorTest {

	/**
	 * tests the input validation
	 */
	@Test
	void testIsValid() {
		CourseNameValidator c = new CourseNameValidator();

		// error states
		// invalid start
		assertAll("Invalid Errors", () -> {
			Exception e = assertThrows(InvalidTransitionException.class, () -> {
				c.isValid(":3");
			}, "Invalid character");
			assertEquals("Course name can only contain letters and digits.", e.getMessage());

		}, () -> {
			Exception e = assertThrows(InvalidTransitionException.class, () -> {
				c.isValid("1");
			}, "Doesn't start with letter");
			assertEquals("Course name must start with a letter.", e.getMessage());

		}, () -> {
			Exception e = assertThrows(InvalidTransitionException.class, () -> {
				c.isValid("compsci");
			}, "More than 4 letters");
			assertEquals("Course name cannot start with more than 4 letters.", e.getMessage());

		}, () -> {
			Exception e = assertThrows(InvalidTransitionException.class, () -> {
				c.isValid("CSC1A");
			}, "Only 1 digit");
			assertEquals("Course name must have 3 digits.", e.getMessage());

		}, () -> {
			Exception e = assertThrows(InvalidTransitionException.class, () -> {
				c.isValid("CSC12A");
			}, "Only 2 digits");
			assertEquals("Course name must have 3 digits.", e.getMessage());

		}, () -> {
			Exception e = assertThrows(InvalidTransitionException.class, () -> {
				c.isValid("CSC1234");
			}, "More than 3 digits");
			assertEquals("Course name can only have 3 digits.", e.getMessage());

		}, () -> {
			Exception e = assertThrows(InvalidTransitionException.class, () -> {
				c.isValid("CSC123AA");
			}, "More than 1 letter suffix");
			assertEquals("Course name can only have a 1 letter suffix.", e.getMessage());

		}, () -> {
			Exception e = assertThrows(InvalidTransitionException.class, () -> {
				c.isValid("CSC123A2");
			}, "Suffix cannot have digit after it");
			assertEquals("Course name cannot contain digits after the suffix.", e.getMessage());

		});
		// valid states
		assertAll("Valid state representations", () -> {
			assertDoesNotThrow(() -> {
				c.isValid("C122");
			}, "1 letter couse");
		}, () -> {
			assertDoesNotThrow(() -> {
				c.isValid("CS122");
			}, "2 letter couse");
		}, () -> {
			assertDoesNotThrow(() -> {
				c.isValid("CSC122");
			}, "3 letter couse");
		}, () -> {
			assertDoesNotThrow(() -> {
				c.isValid("CSCI122");
			}, "4 letter couse");
		}, () -> {
			assertDoesNotThrow(() -> {
				c.isValid("CSC122A");
			}, "Suffix class");
		});
	}
}
