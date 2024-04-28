package edu.ncsu.csc216.pack_scheduler.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc217.collections.list.SortedList;

/**
 * Tests CouresRecordIO.
 * 
 * @author SarahHeckman
 */
public class CourseRecordIOTest {

	/** Valid course records */
	private final String validTestFile = "test-files/course_records.txt";
	/** Invalid course records */
	private final String invalidTestFile = "test-files/invalid_course_records.txt";

	/** Expected results for valid courses in course_records.txt - line 1 */
	private final String validCourse1 = "CSC116,Intro to Programming - Java,001,3,null,10,MW,910,1100";
	/** Expected results for valid courses in course_records.txt - line 2 */
	private final String validCourse2 = "CSC116,Intro to Programming - Java,002,3,null,10,MW,1120,1310";
	/** Expected results for valid courses in course_records.txt - line 3 */
	private final String validCourse3 = "CSC116,Intro to Programming - Java,003,3,null,10,TH,1120,1310";
	/**
	 * Expected results for valid courses in course_records.txt - line 5 (line 4 is
	 * a duplicate of line 2 b/c of how we defined Course.equals().)
	 */
	private final String validCourse4 = "CSC216,Software Development Fundamentals,001,3,null,10,TH,1330,1445";
	/** Expected results for valid courses in course_records.txt - line 6 */
	private final String validCourse5 = "CSC216,Software Development Fundamentals,002,3,null,10,MW,1330,1445";
	/** Expected results for valid courses in course_records.txt - line 7 */
	private final String validCourse6 = "CSC216,Software Development Fundamentals,601,3,null,10,A";
	/** Expected results for valid courses in course_records.txt - line 8 */
	private final String validCourse7 = "CSC217,Software Development Fundamentals Lab,202,1,null,10,M,1040,1230";
	/** Expected results for valid courses in course_records.txt - line 9 */
	private final String validCourse8 = "CSC217,Software Development Fundamentals Lab,211,1,null,10,T,830,1020";
	/** Expected results for valid courses in course_records.txt - line 10 */
	private final String validCourse9 = "CSC217,Software Development Fundamentals Lab,223,1,null,10,W,1500,1650";
	/** Expected results for valid courses in course_records.txt - line 11 */
	private final String validCourse10 = "CSC217,Software Development Fundamentals Lab,601,1,null,10,A";
	/** Expected results for valid courses in course_records.txt - line 12 */
	private final String validCourse11 = "CSC226,Discrete Mathematics for Computer Scientists,001,3,null,10,MWF,935,1025";
	/** Expected results for valid courses in course_records.txt - line 13 */
	private final String validCourse12 = "CSC230,C and Software Tools,001,3,null,10,MW,1145,1300";
	/** Expected results for valid courses in course_records.txt - line 14 */
	private final String validCourse13 = "CSC316,Data Structures and Algorithms,001,3,null,10,MW,830,945";

	/** Array to hold expected results */
	private final String[] validCourses = { validCourse1, validCourse2, validCourse3, validCourse4, validCourse5,
			validCourse6, validCourse7, validCourse8, validCourse9, validCourse10, validCourse11, validCourse12,
			validCourse13 };

	/**
	 * Resets course_records.txt for use in other tests.
	 * 
	 * @throws Exception if error in set up.
	 */
	@Before
	public void setUp() throws Exception {
		// Reset course_records.txt so that it's fine for other needed tests
		Path sourcePath = FileSystems.getDefault().getPath("test-files", "starter_course_records.txt");
		Path destinationPath = FileSystems.getDefault().getPath("test-files", "course_records.txt");
		try {
			Files.deleteIfExists(destinationPath);
			Files.copy(sourcePath, destinationPath);
		} catch (IOException e) {
			fail("Unable to reset files");
		}
	}

	/**
	 * Tests readValidCourseRecords().
	 */
	@Test
	public void testReadValidCourseRecords() {
		try {
			SortedList<Course> courses = CourseRecordIO.readCourseRecords(validTestFile);
			assertEquals(13, courses.size());

			for (int i = 0; i < validCourses.length; i++) {
				assertEquals(validCourses[i], courses.get(i).toString());
			}
		} catch (FileNotFoundException e) {
			fail("Unexpected error reading " + validTestFile);
		}
	}

	/**
	 * Tests readInvalidCourseRecords().
	 */
	@Test
	public void testReadInvalidCourseRecords() {
		SortedList<Course> courses;
		try {
			courses = CourseRecordIO.readCourseRecords(invalidTestFile);
			assertEquals(0, courses.size());
		} catch (FileNotFoundException e) {
			fail("Unexpected FileNotFoundException");
		}
	}

	/**
	 * Tests writeCourseRecords()
	 */
	@Test
	public void testWriteCourseRecords() {
		SortedList<Course> courses = new SortedList<Course>();
		courses.add(new Course("CSC116", "Intro to Programming - Java", "003", 3, "spbalik", 10, "MW", 1250, 1440));
		courses.add(
				new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 10, "MW", 1330, 1445));
		courses.add(new Course("CSC216", "Software Development Fundamentals", "601", 3, "jctetter", 10, "A"));

		try {
			CourseRecordIO.writeCourseRecords("test-files/actCourseRecords.txt", courses);
		} catch (IOException e) {
			fail("Cannot write to course records file");
		}

		checkFiles("test-files/expected_course_records.txt", "test-files/actCourseRecords.txt");
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
