package edu.ncsu.csc216.pack_scheduler.course.roll;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.user.Student;

/**
 * Tests the CourseRoll class
 * 
 * @author Fahad Ansari
 * @author Chirag Sreedhara
 * @author Ryan Stauffer
 */
class CourseRollTest {

	/** Hashed password algorithm */
	private static final String HASH_ALGORITHM = "SHA-256";

	/**
	 * Tests CourseRoll constructor
	 */
	@Test
	void testCourseRollInt() {
		Course c = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", 10, "A");
		CourseRoll roll = c.getCourseRoll();
		assertEquals(10, roll.getEnrollmentCap());
		assertEquals(10, roll.getOpenSeats());
	}

	/**
	 * Tests the setEnrollmentCap() method
	 */
	@Test
	void testSetEnrollmentCap() {
		Course c = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", 10, "A");
		CourseRoll roll = c.getCourseRoll();
		assertEquals(10, roll.getEnrollmentCap());
		assertEquals(10, roll.getOpenSeats());
		roll.setEnrollmentCap(15);
		assertEquals(15, roll.getEnrollmentCap());
		assertEquals(15, roll.getOpenSeats());

		assertThrows(IllegalArgumentException.class, () -> roll.setEnrollmentCap(9));
		assertThrows(IllegalArgumentException.class, () -> roll.setEnrollmentCap(251));

	}

	/**
	 * Tests the enroll() method
	 */
	@Test
	void testEnroll() {
		String plaintextPW = "password";
		MessageDigest digest;
		String password = "pass";
		try {
			digest = MessageDigest.getInstance(HASH_ALGORITHM);
			digest.update(plaintextPW.getBytes());
			password = Base64.getEncoder().encodeToString(digest.digest());
		} catch (NoSuchAlgorithmException e) {
			fail("An unexpected NoSuchAlgorithmException was thrown.");
		}

		Course c = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", 10, "A");
		CourseRoll roll = c.getCourseRoll();
		assertEquals(10, roll.getEnrollmentCap());
		assertEquals(10, roll.getOpenSeats());
		Student s1 = new Student("first", "last", "id", "email@ncsu.edu", password);

		assertDoesNotThrow(() -> roll.enroll(s1));

		assertEquals(10, roll.getEnrollmentCap());
		assertEquals(9, roll.getOpenSeats());

		assertThrows(IllegalArgumentException.class, () -> roll.enroll(null));

		assertThrows(IllegalArgumentException.class, () -> roll.enroll(s1));
	}

	/**
	 * Tests the drop() method
	 */
	@Test
	void testDrop() {
		String plaintextPW = "password";
		MessageDigest digest;
		String password = "pass";
		try {
			digest = MessageDigest.getInstance(HASH_ALGORITHM);
			digest.update(plaintextPW.getBytes());
			password = Base64.getEncoder().encodeToString(digest.digest());
		} catch (NoSuchAlgorithmException e) {
			fail("An unexpected NoSuchAlgorithmException was thrown.");
		}

		Course c = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", 10, "A");
		CourseRoll roll = c.getCourseRoll();
		assertEquals(10, roll.getEnrollmentCap());
		assertEquals(10, roll.getOpenSeats());
		Student s1 = new Student("first", "last", "id", "email@ncsu.edu", password);

		assertDoesNotThrow(() -> roll.enroll(s1));

		assertEquals(10, roll.getEnrollmentCap());
		assertEquals(9, roll.getOpenSeats());
		assertDoesNotThrow(() -> roll.drop(s1));

		assertEquals(10, roll.getEnrollmentCap());
		assertEquals(10, roll.getOpenSeats());

		assertThrows(IllegalArgumentException.class, () -> roll.drop(null));

		assertDoesNotThrow(() -> roll.enroll(s1));
		Student s2 = new Student("first2", "last2", "id2", "email2@ncsu.edu", password);
		Student s3 = new Student("first3", "last2", "id2", "email2@ncsu.edu", password);
		Student s4 = new Student("first4", "last2", "id2", "email2@ncsu.edu", password);
		Student s5 = new Student("first5", "last2", "id2", "email2@ncsu.edu", password);
		Student s6 = new Student("first6", "last2", "id2", "email2@ncsu.edu", password);
		Student s7 = new Student("first7", "last2", "id2", "email2@ncsu.edu", password);
		Student s8 = new Student("first8", "last2", "id2", "email2@ncsu.edu", password);
		Student s9 = new Student("first9", "last2", "id2", "email2@ncsu.edu", password);
		Student s10 = new Student("first10", "last2", "id2", "email2@ncsu.edu", password);
		Student s11 = new Student("first11", "last2", "id2", "email2@ncsu.edu", password);
		assertFalse(roll.canEnroll(s1));

		assertDoesNotThrow(() -> roll.enroll(s2));
		assertDoesNotThrow(() -> roll.enroll(s3));
		assertDoesNotThrow(() -> roll.enroll(s4));
		assertDoesNotThrow(() -> roll.enroll(s5));
		assertDoesNotThrow(() -> roll.enroll(s6));
		assertDoesNotThrow(() -> roll.enroll(s7));
		assertDoesNotThrow(() -> roll.enroll(s8));
		assertDoesNotThrow(() -> roll.enroll(s9));
		assertDoesNotThrow(() -> roll.enroll(s10));
		assertDoesNotThrow(() -> roll.enroll(s11));
		assertEquals(0, roll.getOpenSeats());
		assertEquals(1, roll.getNumberOnWaitlist());
		assertDoesNotThrow(() -> roll.drop(s1));
		assertEquals(0, roll.getOpenSeats());
		assertEquals(0, roll.getNumberOnWaitlist());

	}

}
