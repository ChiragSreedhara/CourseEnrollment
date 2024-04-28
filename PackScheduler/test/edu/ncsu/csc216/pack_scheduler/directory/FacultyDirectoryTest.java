package edu.ncsu.csc216.pack_scheduler.directory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.pack_scheduler.user.Faculty;

/**
 * Tests StudentDirectory.
 * 
 * @author David Martinez
 * @author Chirag Sreedhara
 */
public class FacultyDirectoryTest {

	/** Valid course records */
	private final String validTestFile = "test-files/faculty_records.txt";
	/** Test first name */
	private static final String FIRST_NAME = "Stu";
	/** Test last name */
	private static final String LAST_NAME = "Dent";
	/** Test id */
	private static final String ID = "sdent";
	/** Test email */
	private static final String EMAIL = "sdent@ncsu.edu";
	/** Test password */
	private static final String PASSWORD = "pw";
	/** Test max credits */
	private static final int MAX_CREDITS = 2;

	/**
	 * Resets course_records.txt for use in other tests.
	 * 
	 * @throws Exception if something fails during setup.
	 */
	@Before
	public void setUp() throws Exception {
		// Reset student_records.txt so that it's fine for other needed tests
		Path sourcePath = FileSystems.getDefault().getPath("test-files", "expected_full_faculty_records.txt");
		Path destinationPath = FileSystems.getDefault().getPath("test-files", "faculty_records.txt");
		try {
			Files.deleteIfExists(destinationPath);
			Files.copy(sourcePath, destinationPath);
		} catch (IOException e) {
			fail("Unable to reset files");
		}
	}

	/**
	 * Tests StudentDirectory().
	 */
	@Test
	public void testStudentDirectory() {
		// Test that the StudentDirectory is initialized to an empty list
		FacultyDirectory sd = new FacultyDirectory();
		assertFalse(sd.removeFaculty("sesmith5"));
		assertEquals(0, sd.getFacultyDirectory().length);
	}

	/**
	 * Tests StudentDirectory.testNewStudentDirectory().
	 */
	@Test
	public void testNewStudentDirectory() {
		// Test that if there are students in the directory, they
		// are removed after calling newStudentDirectory().
		FacultyDirectory sd = new FacultyDirectory();

		sd.loadFacultyFromFile(validTestFile);
		assertEquals(8, sd.getFacultyDirectory().length);

		sd.newFacultyDirectory();
		assertEquals(0, sd.getFacultyDirectory().length);
	}

	/**
	 * Tests StudentDirectory.loadStudentsFromFile().
	 */
	@Test
	public void testLoadStudentsFromFile() {
		FacultyDirectory sd = new FacultyDirectory();
		assertEquals(0, sd.getFacultyDirectory().length);
		// Test valid file
		sd.loadFacultyFromFile(validTestFile);
		assertEquals(8, sd.getFacultyDirectory().length);

		sd.newFacultyDirectory();
		assertEquals(0, sd.getFacultyDirectory().length);

		// Test invalid file
		Exception e1 = assertThrows(IllegalArgumentException.class, () -> sd.loadFacultyFromFile(""));
		assertEquals("Unable to read file ", e1.getMessage());
		assertEquals(0, sd.getFacultyDirectory().length);

	}

	/**
	 * Tests StudentDirectory.addStudent().
	 */
	@Test
	public void testAddStudent() {
		FacultyDirectory sd = new FacultyDirectory();

		// Test valid Student
		sd.addFaculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, PASSWORD, MAX_CREDITS);
		String[][] studentDirectory = sd.getFacultyDirectory();
		assertEquals(1, studentDirectory.length);
		assertEquals(FIRST_NAME, studentDirectory[0][0]);
		assertEquals(LAST_NAME, studentDirectory[0][1]);
		assertEquals(ID, studentDirectory[0][2]);

		// Test invalid Student
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> sd.addFaculty(FIRST_NAME, LAST_NAME, ID, EMAIL, null, PASSWORD, MAX_CREDITS));
		assertEquals("Invalid password", e1.getMessage());
		Exception e2 = assertThrows(IllegalArgumentException.class,
				() -> sd.addFaculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, null, MAX_CREDITS));
		assertEquals("Invalid password", e2.getMessage());
		Exception e3 = assertThrows(IllegalArgumentException.class,
				() -> sd.addFaculty(FIRST_NAME, LAST_NAME, ID, EMAIL, "", PASSWORD, MAX_CREDITS));
		assertEquals("Invalid password", e3.getMessage());
		Exception e4 = assertThrows(IllegalArgumentException.class,
				() -> sd.addFaculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, "", MAX_CREDITS));
		assertEquals("Invalid password", e4.getMessage());

		Exception e5 = assertThrows(IllegalArgumentException.class,
				() -> sd.addFaculty(FIRST_NAME, LAST_NAME, ID, EMAIL, "password", PASSWORD, MAX_CREDITS));
		assertEquals("Passwords do not match", e5.getMessage());

	}

	/**
	 * Tests StudentDirectory.removeStudent().
	 */
	@Test
	public void testRemoveStudent() {
		FacultyDirectory sd = new FacultyDirectory();

		// Add students and remove
		sd.loadFacultyFromFile(validTestFile);
		assertEquals(8, sd.getFacultyDirectory().length);
		assertTrue(sd.removeFaculty("fmeadow"));
		String[][] studentDirectory = sd.getFacultyDirectory();
		assertEquals(7, studentDirectory.length);
		assertEquals("Ashely", studentDirectory[0][0]);
		assertEquals("Witt", studentDirectory[0][1]);
		assertEquals("awitt", studentDirectory[0][2]);
	}

	/**
	 * Tests StudentDirectory.saveStudentDirectory().
	 */
	@Test
	public void testSaveStudentDirectory() {
		FacultyDirectory sd = new FacultyDirectory();

		// Add a student
		sd.addFaculty("Zahir", "King", "zking", "orci.Donec@ametmassaQuisque.com", "pw", "pw", 2);
		assertEquals(1, sd.getFacultyDirectory().length);
		sd.saveFacultyDirectory("test-files/actFaculty2.txt");
		checkFiles("test-files/expFaculty2.txt", "test-files/actFaculty2.txt");

		// Test invalid Student
		Exception e1 = assertThrows(IllegalArgumentException.class, () -> sd.saveFacultyDirectory(""));
		assertEquals("Unable to write to file ", e1.getMessage());
	}

	/**
	 * Tests StudentDirectory.getStudentById().
	 */
	@Test
	public void testGetStudentById() {
		FacultyDirectory sd = new FacultyDirectory();

		sd.addFaculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, PASSWORD, 2);
		Faculty student = sd.getFacultyById(ID);
		assertNotNull(student);
		assertEquals(FIRST_NAME, student.getFirstName());
		assertEquals(LAST_NAME, student.getLastName());
		assertEquals(ID, student.getId());
		assertEquals(EMAIL, student.getEmail());
		assertNull(sd.getFacultyById("jdoe123"));
	}

	/**
	 * Helper method to compare two files for the same contents
	 * 
	 * @param expFile expected output
	 * @param actFile actual output
	 */
	private void checkFiles(String expFile, String actFile) {
		try {
			Scanner expScanner = new Scanner(new FileInputStream(expFile));
			Scanner actScanner = new Scanner(new FileInputStream(actFile));

			while (expScanner.hasNextLine()) {
				assertEquals(expScanner.nextLine(), actScanner.nextLine());
			}

			expScanner.close();
			actScanner.close();
		} catch (IOException e) {
			fail("Error reading files.");
		}
	}

}
