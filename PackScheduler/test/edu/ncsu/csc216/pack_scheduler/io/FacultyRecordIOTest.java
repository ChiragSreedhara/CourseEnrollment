package edu.ncsu.csc216.pack_scheduler.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.util.LinkedList;

/**
 * Tests the FacultyRecordIO object.
 * 
 * @author David Martinez
 * @author Chirag Sreedhara
 */
class FacultyRecordIOTest {

	/** Valid student records */
	private final String validTestFile = "test-files/faculty_records.txt";

	/** Invalid student records */
	private final String invalidTestFile = "test-files/invalid_faculty_records.txt";

	/** Expected results for valid students in faculty_records.txt - line 1 */
	private String validStudent0 = "Ashely,Witt,awitt,mollis@Fuscealiquetmagna.net,MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=,2";

	/** Expected results for valid students in faculty_records.txt - line 2 */
	private String validStudent1 = "Fiona,Meadows,fmeadow,pharetra.sed@et.org,MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=,3";

	/** Expected results for valid students in faculty_records.txt - line 3 */
	private String validStudent2 = "Brent,Brewer,bbrewer,sem.semper@orcisem.co.uk,MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=,1";

	/** Expected results for valid students in faculty_records.txt - line 4 */
	private String validStudent3 = "Halla,Aguirre,haguirr,Fusce.dolor.quam@amalesuadaid.net,MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=,3";

	/** Expected results for valid students in faculty_records.txt - line 5 */
	private String validStudent4 = "Kevyn,Patel,kpatel,risus@pellentesque.ca,MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=,1";

	/** Expected results for valid students in faculty_records.txt - line 6 */
	private String validStudent5 = "Elton,Briggs,ebriggs,arcu.ac@ipsumsodalespurus.edu,MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=,3";

	/** Expected results for valid students in faculty_records.txt - line 7 */
	private String validStudent6 = "Norman,Brady,nbrady,pede.nonummy@elitfermentum.co.uk,MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=,1";

	/** Expected results for valid students in Faculty_records.txt - line 8 */
	private String validStudent7 = "Lacey,Walls,lwalls,nascetur.ridiculus.mus@fermentum.net,MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=,2";

	/** Array to hold expected results */
	private String[] validStudents = { validStudent0, validStudent1, validStudent2, validStudent3, validStudent4,
			validStudent5, validStudent6, validStudent7 };

	/** Creates hashed password for student */
	private String hashPW;

	/** Algorithm for creating a hashed password */
	private static final String HASH_ALGORITHM = "SHA-256";

	/**
	 * Creates a hashed password for each student in the array
	 * 
	 * @throws NoSuchAlgorithmException e if can not create a hashed password for
	 *                                  the student
	 */
	@BeforeEach
	public void setUp() {
		try {
			String password = "pw";
			MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
			digest.update(password.getBytes());
			hashPW = Base64.getEncoder().encodeToString(digest.digest());

			for (int i = 0; i < validStudents.length; i++) {
				validStudents[i] = validStudents[i].replace(",pw,", "," + hashPW + ",");
			}
		} catch (NoSuchAlgorithmException e) {
			fail("Unable to create hash during setup");
		}
	}

	/**
	 * Tests readValidStudentRecords() from a valid test file
	 */
	@Test
	public void testReadValidStudentRecords() {
		try {
			LinkedList<Faculty> students = FacultyRecordIO.readFacultyRecords(validTestFile);
			assertEquals(8, students.size());

			for (int i = 0; i < validStudents.length; i++) {
				assertEquals(validStudents[i], students.get(i).toString());
			}
		} catch (FileNotFoundException e) {
			fail("Unexpected error reading " + validTestFile);
		}
	}

	/**
	 * Tests readInvalidStudentRecords() from an invalid test file
	 */
	@Test
	public void testReadInvalidStudentRecords() {
		LinkedList<Faculty> students;
		try {
			students = FacultyRecordIO.readFacultyRecords(invalidTestFile);
			assertEquals(0, students.size());
		} catch (FileNotFoundException e) {
			fail("Unexpected FileNotFoundException");
		}
	}

	/**
	 * Tests writeCourseRecords() with no specific permissions
	 */
	@Test
	public void testWriteStudentRecordsNoPermissions() {
		LinkedList<Faculty> students = new LinkedList<Faculty>();
		students.add(new Faculty("Zahir", "King", "zking", "orci.Donec@ametmassaQuisque.com", hashPW, 2));

		Exception exception = assertThrows(IOException.class,
				() -> FacultyRecordIO.writeFacultyRecords("/home/sesmith5/actual_student_records.txt", students));
		assertEquals("/home/sesmith5/actual_student_records.txt (No such file or directory)", exception.getMessage());

		checkFiles("test-files/expected_faculty_records.txt", "test-files/actual_faculty_records.txt");

	}

	/**
	 * Tests writeCourseRecords() and compares it to a file with actual student
	 * records
	 */
	@Test
	public void testWriteStudentRecords() {
		LinkedList<Faculty> students = new LinkedList<Faculty>();
		students.add(new Faculty("Ashely", "Witt", "awitt", "mollis@Fuscealiquetmagna.net",
				"MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=", 2));
		students.add(new Faculty("Fiona", "Meadows", "fmeadow", "pharetra.sed@et.org",
				"MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=", 3));
		students.add(new Faculty("Brent", "Brewer", "bbrewer", "sem.semper@orcisem.co.uk",
				"MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=", 1));

		try {
			FacultyRecordIO.writeFacultyRecords("test-files/actual_faculty_records.txt", students);
		} catch (IOException e) {
			fail("Cannot write to student records file");
		}

		checkFiles("test-files/expected_faculty_records.txt", "test-files/actual_faculty_records.txt");
	}

	/**
	 * Helper method to compare two files for the same contents
	 * 
	 * @param expFile expected output
	 * @param actFile actual output
	 */
	private void checkFiles(String expFile, String actFile) {
		try (Scanner expScanner = new Scanner(new FileInputStream(expFile));
				Scanner actScanner = new Scanner(new FileInputStream(actFile));) {

			while (expScanner.hasNextLine() && actScanner.hasNextLine()) {
				String exp = expScanner.nextLine();
				String act = actScanner.nextLine();
				assertEquals(exp, act, "Expected: " + exp + " Actual: " + act);
				// The third argument helps with debugging!
			}
			if (expScanner.hasNextLine()) {
				fail("The expected results expect another line " + expScanner.nextLine());
			}
			if (actScanner.hasNextLine()) {
				fail("The actual results has an extra, unexpected line: " + actScanner.nextLine());
			}

			expScanner.close();
			actScanner.close();
		} catch (IOException e) {
			fail("Error reading files.");
		}
	}

}
