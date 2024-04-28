package edu.ncsu.csc216.pack_scheduler.users;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc216.pack_scheduler.user.schedule.Schedule;

/**
 * Tests the Student object.
 * 
 * @author Sarah Heckman
 * @author Niyati Patel
 * @author Fahad Ansari
 */
public class StudentTest {

	/** Test Student's first name. */
	private String firstName = "first";
	/** Test Student's last name */
	private String lastName = "last";
	/** Test Student's id */
	private String id = "flast";
	/** Test Student's email */
	private String email = "first_last@ncsu.edu";
	/** Test Student's hashed password */
	private String password;
	/** Test Student's max credits */
	private int maxCredits = 15;
	/** Hashed password algorithm */
	private static final String HASH_ALGORITHM = "SHA-256";

	// This is a block of code that is executed when the StudentTest object is
	// created by JUnit. Since we only need to generate the hashed version
	// of the plaintext password once, we want to create it as the StudentTest
	// object is
	// constructed. By automating the hash of the plaintext password, we are
	// not tied to a specific hash implementation. We can change the algorithm
	// easily.
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
	 * Tests student constructor with no specified max credits
	 */
	@Test
	public void testStudentConstructor() {
		Student s1 = assertDoesNotThrow(() -> new Student(firstName, lastName, id, email, password, 18),
				"Should not throw exception");
		assertAll("Student", () -> assertEquals(firstName, s1.getFirstName(), "Incorrect first name"),
				() -> assertEquals(lastName, s1.getLastName(), "Incorrect last name"),
				() -> assertEquals(id, s1.getId(), "Incorrect id"),
				() -> assertEquals(email, s1.getEmail(), "Incorrect email"),
				() -> assertEquals(password, s1.getPassword(), "Incorrect password"),
				() -> assertEquals(18, s1.getMaxCredits(), "Incorrect max credits"));
	}

	/**
	 * Tests student constructor with specific max credits
	 */
	@Test
	public void testStudentConstructor2() {
		Student s1 = assertDoesNotThrow(() -> new Student(firstName, lastName, id, email, password, maxCredits),
				"Should not throw exception");
		assertAll("Student", () -> assertEquals(firstName, s1.getFirstName(), "Incorrect first name"),
				() -> assertEquals(lastName, s1.getLastName(), "Incorrect last name"),
				() -> assertEquals(id, s1.getId(), "Incorrect id"),
				() -> assertEquals(email, s1.getEmail(), "Incorrect email"),
				() -> assertEquals(password, s1.getPassword(), "Incorrect password"),
				() -> assertEquals(maxCredits, s1.getMaxCredits(), "Incorrect max credits"));
	}

	/**
	 * Tests set first name with valid strings
	 */
	@Test
	public void testSetFirstNameValid() {
		Student s1 = new Student("first", "last", "id", "email@ncsu.edu", password);
		assertEquals("first", s1.getFirstName());
	}

	/**
	 * Tests set first name with invalid strings to check if exceptions are thrown
	 */
	@Test
	public void testSetFirstNameInvalid() {
		Student s1 = new Student("first", "last", "id", "email@ncsu.edu", password);
		Exception e1 = assertThrows(IllegalArgumentException.class, () -> s1.setFirstName(null));
		assertEquals("Invalid first name", e1.getMessage());
		Exception e2 = assertThrows(IllegalArgumentException.class, () -> s1.setFirstName(""));
		assertEquals("Invalid first name", e2.getMessage());
	}

	/**
	 * Tests set last name with valid strings
	 */
	@Test
	public void testSetLastNameValid() {
		Student s1 = new Student("first", "last", "id", "email@ncsu.edu", password);
		assertEquals("last", s1.getLastName());
	}

	/**
	 * Tests set last name with invalid strings to check if exceptions are thrown
	 */
	@Test
	public void testSetLastNameInvalid() {
		Student s1 = new Student("first", "last", "id", "email@ncsu.edu", password);
		Exception e1 = assertThrows(IllegalArgumentException.class, () -> s1.setLastName(null));
		assertEquals("Invalid last name", e1.getMessage());
		Exception e2 = assertThrows(IllegalArgumentException.class, () -> s1.setLastName(""));
		assertEquals("Invalid last name", e2.getMessage());
	}

	/**
	 * Tests set id with valid strings
	 */
	@Test
	public void testSetIdValid() {
		Student student = assertDoesNotThrow(() -> new Student("first", "last", "id", "email@ncsu.edu", password),
				"Should not throw exception");
		assertEquals("id", student.getId(), "Failed test with valid id");
	}

	/**
	 * Tests set id with invalid strings to check if exceptions are thrown
	 */
	@Test
	public void testSetIdInvalid() {
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> new Student("first", "last", null, "email@ncsu.edu", password));
		assertEquals("Invalid id", e1.getMessage());
		Exception e2 = assertThrows(IllegalArgumentException.class,
				() -> new Student("first", "last", "", "email@ncsu.edu", password));
		assertEquals("Invalid id", e2.getMessage());

	}

	/**
	 * Tests set email with valid strings
	 */
	@Test
	public void testSetEmailValid() {
		Student s1 = new Student("first", "last", "id", "email@ncsu.edu", password);
		assertEquals("email@ncsu.edu", s1.getEmail());
	}

	/**
	 * Tests set email with invalid strings to check if exceptions are thrown
	 */
	@Test
	public void testSetEmailInvalid() {
		Student s1 = new Student("first", "last", "id", "email@ncsu.edu", password);
		Exception e1 = assertThrows(IllegalArgumentException.class, () -> s1.setEmail(null));
		assertEquals("Invalid email", e1.getMessage());
		Exception e2 = assertThrows(IllegalArgumentException.class, () -> s1.setEmail(""));
		assertEquals("Invalid email", e2.getMessage());
		Exception e3 = assertThrows(IllegalArgumentException.class, () -> s1.setEmail("emailncsu.edu"));
		assertEquals("Invalid email", e3.getMessage());
		Exception e4 = assertThrows(IllegalArgumentException.class, () -> s1.setEmail("email@ncsuedu"));
		assertEquals("Invalid email", e4.getMessage());
		Exception e5 = assertThrows(IllegalArgumentException.class, () -> s1.setEmail("email.ncsu@edu"));
		assertEquals("Invalid email", e5.getMessage());
	}

	/**
	 * Tests set password with valid strings
	 */
	@Test
	public void testSetPasswordValid() {
		Student s1 = new Student("first", "last", "id", "email@ncsu.edu", password);
		assertEquals(password, s1.getPassword());
	}

	/**
	 * Tests set password with invalid strings to check if exceptions are thrown
	 */
	@Test
	public void testSetPasswordInvalid() {
		Student s1 = new Student("first", "last", "id", "email@ncsu.edu", password);
		Exception e1 = assertThrows(IllegalArgumentException.class, () -> s1.setPassword(null));
		assertEquals("Invalid password", e1.getMessage());
		Exception e2 = assertThrows(IllegalArgumentException.class, () -> s1.setPassword(""));
		assertEquals("Invalid password", e2.getMessage());
	}

	/**
	 * Tests set max credits with valid values
	 */
	@Test
	public void testSetMaxCreditsValid() {
		Student s1 = new Student("first", "last", "id", "email@ncsu.edu", password, 18);
		assertEquals(18, s1.getMaxCredits());
	}

	/**
	 * Tests set max credits with invalid values to check if exceptions are thrown
	 */
	@Test
	public void testSetMaxCreditsInvalid() {
		Student s1 = new Student("first", "last", "id", "email@ncsu.edu", password, 18);
		Exception e1 = assertThrows(IllegalArgumentException.class, () -> s1.setMaxCredits(1));
		assertEquals("Invalid max credits", e1.getMessage());
		Exception e2 = assertThrows(IllegalArgumentException.class, () -> s1.setMaxCredits(20));
		assertEquals("Invalid max credits", e2.getMessage());
	}

	/**
	 * Tests getting the schedule of a student and modifying it
	 */
	@Test
	public void testGetSchedule() {
		Student s1 = new Student("first", "last", "id", "email@ncsu.edu", password, 18);
		Schedule s = s1.getSchedule();
		assertEquals("My Schedule", s.getTitle());
	}

	/**
	 * Tests compareTo method by last name, first name, and unity id
	 */
	@Test
	public void testCompareTo() {
		// Create students with different attributes for testing
		Student student1 = new Student("Fahad", "Ansari", "mfansari", "mfansari@ncsu.edu", password);
		Student student2 = new Student("Fahad", "Ansark", "mfansari", "mfansari@ncsu.edu", password);
		Student student3 = new Student("Fahak", "Ansari", "mfansari", "mfansari@ncsu.edu", password);
		Student student4 = new Student("Fahad", "Ansari", "mfansari2", "mfansari2@ncsu.edu", password);

		// Test ordering by last name
		assertEquals(-2, student1.compareTo(student2));
		assertEquals(2, student2.compareTo(student1));

		// Test ordering by first name when last names are the same
		assertEquals(-7, student1.compareTo(student3));
		assertEquals(7, student3.compareTo(student1));

		// Test ordering by unity ID when last names and first names are the same
		assertEquals(-1, student1.compareTo(student4));
		assertEquals(1, student4.compareTo(student1));
	}

	/**
	 * Tests toString() method.
	 */
	@Test
	public void testToString() {
		Student s1 = new Student(firstName, lastName, id, email, password);
		assertEquals("first,last,flast,first_last@ncsu.edu," + password + ",18", s1.toString());
	}

	/**
	 * Tests that hashCode works correctly.
	 */
	@Test
	public void testHashCode() {
		Student s1 = new Student("first", "last", "id", "email@ncsu.edu", password, maxCredits);
		Student s2 = new Student("first", "last", "id", "email@ncsu.edu", password, maxCredits);
		Student s3 = new Student("different", "last", "id", "email@ncsu.edu", password, maxCredits);
		Student s4 = new Student("first", "different", "id", "email@ncsu.edu", password, maxCredits);
		Student s5 = new Student("first", "last", "different", "email@ncsu.edu", password, maxCredits);
		Student s6 = new Student("first", "last", "id", "different@ncsu.edu", password, maxCredits);
		Student s7 = new Student("first", "last", "id", "email@ncsu.edu", "different", maxCredits);
		Student s8 = new Student(firstName, lastName, id, email, password, maxCredits);

		// Test for the same hash code for the same values
		assertEquals(s1.hashCode(), s2.hashCode());

		// Test for each of the fields
		assertNotEquals(s1.hashCode(), s3.hashCode());
		assertNotEquals(s1.hashCode(), s4.hashCode());
		assertNotEquals(s1.hashCode(), s5.hashCode());
		assertNotEquals(s1.hashCode(), s6.hashCode());
		assertNotEquals(s1.hashCode(), s7.hashCode());
		assertNotEquals(s1.hashCode(), s8.hashCode());
	}

	/**
	 * Tests that the equals method works for all Student fields.
	 */
	@Test
	public void testEqualsObject() {
		Student s1 = new Student("first", "last", "id", "email@ncsu.edu", password, maxCredits);
		Student s2 = new Student("first", "last", "id", "email@ncsu.edu", password, maxCredits);
		Student s3 = new Student("different", "last", "id", "email@ncsu.edu", password, maxCredits);
		Student s4 = new Student("first", "different", "id", "email@ncsu.edu", password, maxCredits);
		Student s5 = new Student("first", "last", "different", "email@ncsu.edu", password, maxCredits);
		Student s6 = new Student("first", "last", "id", "different@ncsu.edu", password, maxCredits);
		Student s7 = new Student("first", "last", "id", "email@ncsu.edu", "different", maxCredits);
		Student s8 = new Student("first", "last", "id", "email@ncsu.edu", password, 10);

		// Test for equality in both directions
		assertEquals(s1, s2);
		assertEquals(s2, s1);

		// Test for each of the fields
		assertNotEquals(s1, s3);
		assertNotEquals(s1, s4);
		assertNotEquals(s1, s5);
		assertNotEquals(s1, s6);
		assertNotEquals(s1, s7);
		assertNotEquals(s1, s8);

		// Test for everything equal up until the end

		assertTrue(s1.equals(s1));
		assertNotNull(s1 == null);
		Object otherS1 = new Object();
		assertFalse(s1.equals(otherS1));
	}

	/**
	 * Tests getting the schedule of a student and modifying it
	 */
	@Test
	public void testCanAdd() {
		Student sched = new Student("first", "last", "id", "email@ncsu.edu", password, 18);
		Schedule schedule = sched.getSchedule();
		Course c = new Course("CSC216", "Software Engineering Principles", "003", 3, "csreed", 10, "MWF");
		Course c1 = new Course("CSC226", "Discrete Mathematics for CS", "003", 3, "csreed", 10, "TH");

		assertTrue(sched.canAdd(c1));
		assertTrue(sched.canAdd(c));

		assertTrue(schedule.addCourseToSchedule(c));
		assertFalse(sched.canAdd(c));
		assertTrue(schedule.addCourseToSchedule(c1));
		assertFalse(sched.canAdd(c1));
		assertFalse(sched.canAdd(null));
	}

}