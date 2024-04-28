package edu.ncsu.csc216.pack_scheduler.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import org.junit.jupiter.api.Test;

/**
 * Faculty JUnit Test file
 * 
 * @author David Martinez
 * @author Chirag Sreedhara
 */
class FacultyTest {

	/** Test Faculty's first name. */
	private String firstName = "first";
	/** Test Faculty's last name */
	private String lastName = "last";
	/** Test Faculty's id */
	private String id = "flast";
	/** TestFacultyt's email */
	private String email = "first_last@ncsu.edu";
	/** Test Faculty's hashed password */
	private String password = "pw";
	/** Test Faculty's max credits */
	private int maxCourses = 3;
	/** Hashed password algorithm */
	private static final String HASH_ALGORITHM = "SHA-256";

	{
		try {
			String plaintextPW = "password";
			MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
			digest.update(plaintextPW.getBytes());
			this.password = Base64.getEncoder().encodeToString(digest.digest());
		} catch (NoSuchAlgorithmException e) {
			fail("An unexpected NoSuchAlgorithmException was thrown.");
		}
	}

	/**
	 * Test Faculty Constructor
	 */
	@Test
	void testConstructor() {
		try {
			Faculty f1 = new Faculty(firstName, lastName, id, email, password, maxCourses);
			assertEquals(3, f1.getMaxCourses());
		} catch (Exception e) {
			fail();
		}

		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> new Faculty(firstName, lastName, id, email, password, 0));
		assertEquals("Invalid max courses", e1.getMessage());

		Exception e4 = assertThrows(IllegalArgumentException.class,
				() -> new Faculty(firstName, lastName, id, email, password, 4));
		assertEquals("Invalid max courses", e4.getMessage());
	}

	/**
	 * Test 'equals' and 'hashCode' methods
	 */
	@Test
	void testEqualsAndHashcode() {
		Faculty f1 = new Faculty(firstName, lastName, id, email, password, maxCourses);
		Faculty f2 = new Faculty(firstName, lastName, id, email, password, maxCourses);
		Faculty f3 = new Faculty(firstName, lastName, id, email, password, 1);
		Faculty f4 = new Faculty(firstName, lastName, id, email, password, 2);
		Faculty f5 = new Faculty("name", lastName, id, email, password, maxCourses);

		assertTrue(f1.equals(f1));
		assertTrue(f1.equals(f2));
		assertFalse(f1.equals(f3));
		assertFalse(f1.equals(f4));
		assertFalse(f4.equals(f3));
		assertFalse(f1.equals(f5));
		assertFalse(f4.equals(f5));
		assertFalse(f1.equals(new Object()));

		assertEquals(f1.hashCode(), f2.hashCode());
		assertNotEquals(f1.hashCode(), f3.hashCode());
		assertNotEquals(f1.hashCode(), f4.hashCode());
		assertNotEquals(f2.hashCode(), f5.hashCode());
	}

	/**
	 * Test 'toString' method
	 */
	@Test
	void testToString() {
		Faculty f1 = new Faculty(firstName, lastName, id, email, password, maxCourses);
		String format1 = String.format("%s,%s,%s,%s,%s,%d", firstName, lastName, id, email, password, maxCourses);

		assertEquals(format1, f1.toString());

		Faculty f3 = new Faculty(firstName, lastName, id, email, password, 1);
		String format3 = String.format("%s,%s,%s,%s,%s,%d", firstName, lastName, id, email, password, 1);

		assertEquals(format3, f3.toString());
	}
}
