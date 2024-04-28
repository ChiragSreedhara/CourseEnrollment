/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.catalog;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

import org.junit.Before;
import org.junit.jupiter.api.Test;

/**
 * Tests the CourseCatalog class
 * 
 * @author Fahad Ansari
 * @author Niyati Patel
 */
public class CourseCatalogTest {

	/** Valid course records */
	private final String validTestFile = "test-files/course_records.txt";
	/** Course name */
	private static final String NAME = "CSC216";
	/** Course title */
	private static final String TITLE = "Software Development Fundamentals";
	/** Course section */
	private static final String SECTION = "001";
	/** Course credits */
	private static final int CREDITS = 3;
	/** Course instructor id */
	private static final String INSTRUCTOR_ID = "sesmith5";

	/** Course enrollment cap */
	private static final int ENROLLMENT_CAP = 10;
	/** Course meeting days */
	private static final String MEETING_DAYS = "TH";
	/** Course start time */
	private static final int START_TIME = 1330;
	/** Course end time */
	private static final int END_TIME = 1445;

	/**
	 * Resets course_records.txt for use in other tests.
	 * 
	 * @throws Exception if something fails during setup.
	 */
	@Before
	public void setUp() throws Exception {
		Path sourcePath = FileSystems.getDefault().getPath("test-files", "expected_course_records.txt");
		Path destinationPath = FileSystems.getDefault().getPath("test-files", "course_records.txt");
		try {
			Files.deleteIfExists(destinationPath);
			Files.copy(sourcePath, destinationPath);
		} catch (IOException e) {
			fail("Unable to reset files");
		}
	}

	/**
	 * Tests CourseCatalog() constructor
	 */
	@Test
	public void testCourseCatalog() {
		CourseCatalog cc = new CourseCatalog();
		assertFalse(cc.removeCourseFromCatalog(NAME, SECTION));
		assertEquals(0, cc.getCourseCatalog().length);
	}

	/**
	 * Tests newCourseCatalog()
	 */
	@Test
	public void testNewCourseCatalog() {
		CourseCatalog cc = new CourseCatalog();

		cc.loadCoursesFromFile(validTestFile);
		assertEquals(13, cc.getCourseCatalog().length);

		cc.newCourseCatalog();
		assertEquals(0, cc.getCourseCatalog().length);
	}

	/**
	 * Tests loadCoursesFromFile()
	 */
	@Test
	public void testLoadStudentsFromFile() {
		CourseCatalog cc = new CourseCatalog();

		// Test valid file
		cc.loadCoursesFromFile(validTestFile);
		assertEquals(13, cc.getCourseCatalog().length);

		// Test invalid file
		Exception e1 = assertThrows(IllegalArgumentException.class, () -> cc.loadCoursesFromFile(""));
		assertEquals("Unable to read file ", e1.getMessage());
	}

	/**
	 * Tests addCourseToCatalog()
	 */
	@Test
	public void testAddCourseToCatalog() {
		CourseCatalog cc = new CourseCatalog();

		cc.addCourseToCatalog(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME,
				END_TIME);
		String[][] courseCatalog = cc.getCourseCatalog();
		assertEquals(1, courseCatalog.length);
		assertEquals(NAME, courseCatalog[0][0]);
		assertEquals(SECTION, courseCatalog[0][1]);
		assertEquals(TITLE, courseCatalog[0][2]);

		Exception e1 = assertThrows(IllegalArgumentException.class, () -> cc.addCourseToCatalog(null, TITLE, SECTION,
				CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, END_TIME));
		assertEquals("Invalid course name.", e1.getMessage());
		Exception e2 = assertThrows(IllegalArgumentException.class, () -> cc.addCourseToCatalog(NAME, null, SECTION,
				CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, END_TIME));
		assertEquals("Invalid title.", e2.getMessage());
		Exception e3 = assertThrows(IllegalArgumentException.class, () -> cc.addCourseToCatalog(NAME, TITLE, null,
				CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, END_TIME));
		assertEquals("Invalid section.", e3.getMessage());
		Exception e4 = assertThrows(IllegalArgumentException.class, () -> cc.addCourseToCatalog(NAME, TITLE, SECTION, 0,
				INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, END_TIME));
		assertEquals("Invalid credits.", e4.getMessage());
//		Exception e5 = assertThrows(IllegalArgumentException.class, () -> cc.addCourseToCatalog(NAME, TITLE, SECTION,
//				CREDITS, null, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, END_TIME));
//		assertEquals("Invalid instructor id.", e5.getMessage());
		Exception e6 = assertThrows(IllegalArgumentException.class, () -> cc.addCourseToCatalog(NAME, TITLE, SECTION,
				CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, null, START_TIME, END_TIME));
		assertEquals("Invalid meeting days and times.", e6.getMessage());

		CourseCatalog catalog = new CourseCatalog();
		catalog.addCourseToCatalog("CSC216", "Introduction to Computer Science", "001", 3, "12345", ENROLLMENT_CAP,
				"MW", 900, 1030);
		assertFalse(catalog.addCourseToCatalog("CSC216", "Introduction to Computer Science", "001", 3, "12345",
				ENROLLMENT_CAP, "MW", 900, 1030));
		assertNotEquals(2, catalog.getCourseCatalog().length);
	}

	/**
	 * Tests removeCourseFromCatalog()
	 */
	@Test
	public void testRemoveCourseFromCatalog() {
		CourseCatalog cc = new CourseCatalog();

		// Correct Case
		cc.loadCoursesFromFile(validTestFile);
		assertEquals(13, cc.getCourseCatalog().length);
		assertTrue(cc.removeCourseFromCatalog("CSC116", "001"));
		String[][] courseCatalog = cc.getCourseCatalog();
		assertEquals(12, courseCatalog.length);
		assertEquals("CSC217", courseCatalog[5][0]);
		assertEquals("202", courseCatalog[5][1]);
		assertEquals("Software Development Fundamentals Lab", courseCatalog[5][2]);

		// false case
		assertFalse(cc.removeCourseFromCatalog(":3", ":3"));
		assertEquals(12, cc.getCourseCatalog().length);
	}

	/**
	 * Test getCourseFromCatalog()
	 */
	@Test
	public void testGetCourseFromCatalog() {
		CourseCatalog cc = new CourseCatalog();
		cc.loadCoursesFromFile(validTestFile);
		assertEquals(13, cc.getCourseCatalog().length);

		// Attempt to get a course that doesn't exist
		assertNull(cc.getCourseFromCatalog("CSC492", "001"));

		// Attempt to get a course that does exist
		// Course c = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID,
		// ENROLLMENT_CAP, MEETING_DAYS, START_TIME,
		// END_TIME);
		// assertEquals(c, cc.getCourseFromCatalog("CSC216", "001"));
	}

	/**
	 * Tests getCourseCatalog()
	 */
	@Test
	public void testGetCourseCatalog() {
		CourseCatalog cc = new CourseCatalog();
		cc.loadCoursesFromFile(validTestFile);
		assertEquals(13, cc.getCourseCatalog().length);

		// Get the catalog and make sure contents are correct
		// Name, section, title
		String[][] catalog = cc.getCourseCatalog();
		// Row 0
		assertEquals("CSC116", catalog[0][0]);
		assertEquals("001", catalog[0][1]);
		assertEquals("Intro to Programming - Java", catalog[0][2]);
		// Row 1
		assertEquals("CSC116", catalog[1][0]);
		assertEquals("002", catalog[1][1]);
		assertEquals("Intro to Programming - Java", catalog[1][2]);
		// Row 2
		assertEquals("CSC116", catalog[2][0]);
		assertEquals("003", catalog[2][1]);
		assertEquals("Intro to Programming - Java", catalog[2][2]);
		// Row 3
		assertEquals("CSC216", catalog[3][0]);
		assertEquals("001", catalog[3][1]);
		assertEquals("Software Development Fundamentals", catalog[3][2]);
		// Row 4
		assertEquals("CSC216", catalog[4][0]);
		assertEquals("002", catalog[4][1]);
		assertEquals("Software Development Fundamentals", catalog[4][2]);
		// Row 5
		assertEquals("CSC216", catalog[5][0]);
		assertEquals("601", catalog[5][1]);
		assertEquals("Software Development Fundamentals", catalog[5][2]);
		// Row 6
		assertEquals("CSC217", catalog[6][0]);
		assertEquals("202", catalog[6][1]);
		assertEquals("Software Development Fundamentals Lab", catalog[6][2]);
		// Row 7
		assertEquals("CSC217", catalog[7][0]);
		assertEquals("211", catalog[7][1]);
		assertEquals("Software Development Fundamentals Lab", catalog[7][2]);
		// Row 8
		assertEquals("CSC217", catalog[8][0]);
		assertEquals("223", catalog[8][1]);
		assertEquals("Software Development Fundamentals Lab", catalog[8][2]);
		// Row 9
		assertEquals("CSC217", catalog[9][0]);
		assertEquals("601", catalog[9][1]);
		assertEquals("Software Development Fundamentals Lab", catalog[9][2]);
		// Row 10
		assertEquals("CSC226", catalog[10][0]);
		assertEquals("001", catalog[10][1]);
		assertEquals("Discrete Mathematics for Computer Scientists", catalog[10][2]);
		// Row 11
		assertEquals("CSC230", catalog[11][0]);
		assertEquals("001", catalog[11][1]);
		assertEquals("C and Software Tools", catalog[11][2]);
		// Row 12
		assertEquals("CSC316", catalog[12][0]);
		assertEquals("001", catalog[12][1]);
		assertEquals("Data Structures and Algorithms", catalog[12][2]);
	}

	/**
	 * Tests saveCourseCatalog()
	 */
	@Test
	public void testSaveCourseCatalog() {
		CourseCatalog cc = new CourseCatalog();
		cc.addCourseToCatalog("CSC116", "Intro to Programming - Java", "003", 3, "spbalik", 10, "MW", 1250, 1440);
		cc.addCourseToCatalog(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, "MW", START_TIME, END_TIME);
		cc.addCourseToCatalog("CSC216", "Software Development Fundamentals", "601", 3, "jctetter", 10, "A", 0, 0);
		assertEquals(3, cc.getCourseCatalog().length);
		cc.saveCourseCatalog("test-files/actual_course_records.txt");
		checkFiles("test-files/expected_course_records.txt", "test-files/actual_course_records.txt");

		Exception e1 = assertThrows(IllegalArgumentException.class, () -> cc.saveCourseCatalog(""));
		assertEquals("Unable to write to file ", e1.getMessage());
	}

	/**
	 * Helper method to compare two files for the same contents
	 * 
	 * @param expFile expected output
	 * @param actFile actual output
	 */
	private void checkFiles(String expFile, String actFile) {
		try (Scanner expScanner = new Scanner(new File(expFile));
				Scanner actScanner = new Scanner(new File(actFile));) {

			while (actScanner.hasNextLine()) {
				assertEquals(expScanner.nextLine(), actScanner.nextLine());
			}
			if (expScanner.hasNextLine()) {
				fail();
			}

			expScanner.close();
			actScanner.close();
		} catch (IOException e) {
			fail("Error reading files.");
		}
	}

}
