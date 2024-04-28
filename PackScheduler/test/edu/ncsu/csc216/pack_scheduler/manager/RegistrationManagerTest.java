package edu.ncsu.csc216.pack_scheduler.manager;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.pack_scheduler.catalog.CourseCatalog;
import edu.ncsu.csc216.pack_scheduler.directory.StudentDirectory;
import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc216.pack_scheduler.user.schedule.Schedule;

/**
 * Testing for the Registration Manager
 * 
 * @author Fahad Ansari
 * @author Chirag Sreedhara
 * @author Ryan Stauffer
 */
public class RegistrationManagerTest {

	/**
	 * Course catalog for manager
	 */
	private CourseCatalog courseCatalog;
	/**
	 * Student directory for manager
	 */
	private StudentDirectory studentDirectory;

	/** Instance for RegistrationManager */
	private RegistrationManager manager;
	/** Registrar user name */
	private String registrarUsername;
	/** Registrar password */
	private String registrarPassword;
	/** Properties file */
	private static final String PROP_FILE = "registrar.properties";

	/**
	 * Sets up the CourseManager and clears the data.
	 * 
	 * @throws Exception if error
	 */
	@BeforeEach
	public void setUp() throws Exception {
		manager = RegistrationManager.getInstance();
		manager.logout();
		manager.clearData();

		studentDirectory = manager.getStudentDirectory();
		courseCatalog = manager.getCourseCatalog();

		Properties prop = new Properties();

		try (InputStream input = new FileInputStream(PROP_FILE)) {
			prop.load(input);

			registrarUsername = prop.getProperty("id");
			registrarPassword = prop.getProperty("pw");
		} catch (IOException e) {
			throw new IllegalArgumentException("Cannot process properties file.");
		}

	}

	/**
	 * Test cases for getCourseCatalog
	 */
	@Test
	public void testGetCourseCatalog() {
		assertNotNull(courseCatalog);
	}

	/**
	 * Tests getStudentDirectory
	 */
	@Test
	public void testGetStudentDirectory() {
		assertNotNull(studentDirectory);
	}

	/**
	 * Tests login method
	 */
	@Test
	public void testLogin() {
		// Add a student to the student directory
		studentDirectory.addStudent("Fahad", "Ansari", "mfansari", "mfansari@ncsu.edu", "password", "password", 15);

		// Test successful login with correct credentials
		assertTrue(manager.login("mfansari", "password"), "");
		assertEquals("mfansari", manager.getCurrentUser().getId());
		manager.logout();

		// Test unsuccessful login with incorrect password
		studentDirectory.addStudent("Fahad", "Ansari", "mfansari", "mfansari@ncsu.edu", "password", "password", 15);

		assertFalse(manager.login("mfansari", "wrongpassword"), "");

		assertNull(manager.getCurrentUser(), "");

		// Test unsuccessful login with non-existing ID
		assertThrows(IllegalArgumentException.class, () -> manager.login("nonexistingid", "password"));
		assertNull(manager.getCurrentUser(), "");

		// Test unsuccessful login with null ID
		assertFalse(manager.login(null, "password"), "");
		assertNull(manager.getCurrentUser(), "");

		// Test unsuccessful login with null password
		assertFalse(manager.login("mfansari", null), "");
		assertNull(manager.getCurrentUser(), "");
	}

	/**
	 * tests logout() method
	 */
	@Test
	public void testLogout() {
		// Add a student to the student directory
		studentDirectory.addStudent("Fahad", "Ansari", "mfansari", "mfansari@ncsu.edu", "password", "password", 15);

		// Log in
		manager.login("mfansari", "password");

		// Test logout
		manager.logout();
		assertNull(manager.getCurrentUser(), "");
	}

	/**
	 * Tests getCurrentUser() method
	 */
	@Test
	public void testGetCurrentUser() {
		// Add a student to the student directory
		studentDirectory.addStudent("Fahad", "Ansari", "mfansari", "mfansari@ncsu.edu", "password", "password", 15);

		// Test getCurrentUser() when no user is logged in
		assertNull(manager.getCurrentUser(), "");

		// Log in
		manager.login("mfansari", "password");

		// Test getCurrentUser() when a student is logged in
		assertNotNull(manager.getCurrentUser());
		assertEquals("mfansari", manager.getCurrentUser().getId());

		// Logout
		manager.logout();

		// Test getCurrentUser() after logout
		assertNull(manager.getCurrentUser(), "");
	}

	/**
	 * Tests RegistrationManager.enrollStudentInCourse()
	 */
	@Test
	public void testEnrollStudentInCourse() {
		StudentDirectory directory = manager.getStudentDirectory();
		directory.loadStudentsFromFile("test-files/student_records.txt");

		CourseCatalog catalog = manager.getCourseCatalog();
		catalog.loadCoursesFromFile("test-files/course_records.txt");

		manager.logout(); // In case not handled elsewhere

		// test if not logged in
		try {
			manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "001"));
			fail("RegistrationManager.enrollStudentInCourse() - If the current user is null, an IllegalArgumentException should be thrown, but was not.");
		} catch (IllegalArgumentException e) {
			assertNull(manager.getCurrentUser(),
					"RegistrationManager.enrollStudentInCourse() - currentUser is null, so cannot enroll in course.");
		}

		// test if registrar is logged in
		manager.login(registrarUsername, registrarPassword);
		try {
			manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "001"));
			fail("RegistrationManager.enrollStudentInCourse() - If the current user is registrar, an IllegalArgumentException should be thrown, but was not.");
		} catch (IllegalArgumentException e) {
			assertEquals(registrarUsername, manager.getCurrentUser().getId(),
					"RegistrationManager.enrollStudentInCourse() - currentUser is registrar, so cannot enroll in course.");
		}
		manager.logout();

		manager.login("efrost", "pw");
		assertTrue(manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "001")),
				"Action: enroll\nUser: efrost\nCourse: CSC216-001\nResults: True - Student max credits are 3 and course has 3.");
		assertFalse(manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC217", "211")),
				"Action: enroll\nUser: efrost\nCourse: CSC216-001 then CSC 217-211\nResults: False - Student max credits are 3 and additional credit of CSC217-211 cannot be added, will exceed max credits.");

		// Check Student Schedule
		Student efrost = directory.getStudentById("efrost");
		Schedule scheduleFrost = efrost.getSchedule();
		assertEquals(3, scheduleFrost.getScheduleCredits());
		String[][] scheduleFrostArray = scheduleFrost.getScheduledCourses();
		assertEquals(1, scheduleFrostArray.length);
		assertEquals("CSC216", scheduleFrostArray[0][0], "User: efrost\nCourse: CSC216-001\n");
		assertEquals("001", scheduleFrostArray[0][1], "User: efrost\nCourse: CSC216-001\n");
		assertEquals("Software Development Fundamentals", scheduleFrostArray[0][2],
				"User: efrost\nCourse: CSC216-001\n");
		assertEquals("TH 1:30PM-2:45PM", scheduleFrostArray[0][3], "User: efrost\nCourse: CSC216-001\n");
		assertEquals("9", scheduleFrostArray[0][4], "User: efrost\nCourse: CSC216-001\n");

		manager.logout();

		manager.login("ahicks", "pw");
		assertTrue(manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "001")),
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001\nResults: True");
		assertTrue(manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC226", "001")),
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001\nResults: True");
		assertFalse(manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC226", "001")),
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC226-001\nResults: False - duplicate");
		assertFalse(manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC116", "001")),
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-001\nResults: False - time conflict");
		assertTrue(manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC116", "003")),
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\nResults: True");
		assertFalse(manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "601")),
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, CSC116-002\nResults: False - already in section of 116");
		assertFalse(manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC230", "001")),
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, CSC230-001\nResults: False - exceeded max credits");

		// Check Student Schedule
		Student ahicks = directory.getStudentById("ahicks");
		Schedule scheduleHicks = ahicks.getSchedule();
		assertEquals(9, scheduleHicks.getScheduleCredits());
		String[][] scheduleHicksArray = scheduleHicks.getScheduledCourses();
		assertEquals(3, scheduleHicksArray.length);
		assertEquals("CSC216", scheduleHicksArray[0][0], "User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n");
		assertEquals("001", scheduleHicksArray[0][1], "User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n");
		assertEquals("Software Development Fundamentals", scheduleHicksArray[0][2],
				"User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n");
		assertEquals("TH 1:30PM-2:45PM", scheduleHicksArray[0][3],
				"User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n");
		assertEquals("8", scheduleHicksArray[0][4], "User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n");
		assertEquals("CSC226", scheduleHicksArray[1][0], "User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n");
		assertEquals("001", scheduleHicksArray[1][1], "User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n");
		assertEquals("Discrete Mathematics for Computer Scientists", scheduleHicksArray[1][2],
				"User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n");
		assertEquals("MWF 9:35AM-10:25AM", scheduleHicksArray[1][3],
				"User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n");
		assertEquals("9", scheduleHicksArray[1][4], "User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n");
		assertEquals("CSC116", scheduleHicksArray[2][0], "User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n");
		assertEquals("003", scheduleHicksArray[2][1], "User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n");
		assertEquals("Intro to Programming - Java", scheduleHicksArray[2][2],
				"User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n");
		assertEquals("TH 11:20AM-1:10PM", scheduleHicksArray[2][3],
				"User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n");
		assertEquals("9", scheduleHicksArray[2][4], "User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n");

		assertThrows(IllegalArgumentException.class,
				() -> manager.resetFacultySchedule(manager.getFacultyDirectory().getFacultyById("ahicks")));

		assertThrows(IllegalArgumentException.class,
				() -> manager.removeFacultyFromCourse(manager.getCourseCatalog().getCourseFromCatalog("CSC216", "001"),
						manager.getFacultyDirectory().getFacultyById("ahicks")));

		assertThrows(IllegalArgumentException.class,
				() -> manager.addFacultyToCourse(manager.getCourseCatalog().getCourseFromCatalog("CSC216", "001"),
						manager.getFacultyDirectory().getFacultyById("ahicks")));

		manager.logout();
	}

	/**
	 * Tests RegistrationManager.dropStudentFromCourse()
	 */
	@Test
	public void testDropStudentFromCourse() {
		StudentDirectory directory = manager.getStudentDirectory();
		directory.loadStudentsFromFile("test-files/student_records.txt");

		CourseCatalog catalog = manager.getCourseCatalog();
		catalog.loadCoursesFromFile("test-files/course_records.txt");

		manager.logout(); // In case not handled elsewhere

		// test if not logged in
		try {
			manager.dropStudentFromCourse(catalog.getCourseFromCatalog("CSC216", "001"));
			fail("RegistrationManager.dropStudentFromCourse() - If the current user is null, an IllegalArgumentException should be thrown, but was not.");
		} catch (IllegalArgumentException e) {
			assertNull(manager.getCurrentUser(),
					"RegistrationManager.dropStudentFromCourse() - currentUser is null, so cannot enroll in course.");
		}

		// test if registrar is logged in
		manager.login(registrarUsername, registrarPassword);
		try {
			manager.dropStudentFromCourse(catalog.getCourseFromCatalog("CSC216", "001"));
			fail("RegistrationManager.dropStudentFromCourse() - If the current user is registrar, an IllegalArgumentException should be thrown, but was not.");
		} catch (IllegalArgumentException e) {
			assertEquals(registrarUsername, manager.getCurrentUser().getId(),
					"RegistrationManager.dropStudentFromCourse() - currentUser is registrar, so cannot enroll in course.");
		}
		manager.logout();

		manager.login("efrost", "pw");
		assertTrue(manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "001")),
				"Action: enroll\nUser: efrost\nCourse: CSC216-001\nResults: True - Student max credits are 3 and course has 3.");
		assertFalse(manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC217", "211")),
				"Action: enroll\nUser: efrost\nCourse: CSC216-001 then CSC 217-211\nResults: False - Student max credits are 3 and additional credit of CSC217-211 cannot be added, will exceed max credits.");

		assertFalse(manager.dropStudentFromCourse(catalog.getCourseFromCatalog("CSC217", "211")),
				"Action: drop\nUser: efrost\nCourse: CSC217-211\nResults: False - student not enrolled in the course");
		assertTrue(manager.dropStudentFromCourse(catalog.getCourseFromCatalog("CSC216", "001")),
				"Action: drop\nUser: efrost\nCourse: CSC216-001\nResults: True");

		// Check Student Schedule
		Student efrost = directory.getStudentById("efrost");
		Schedule scheduleFrost = efrost.getSchedule();
		assertEquals(0, scheduleFrost.getScheduleCredits());
		String[][] scheduleFrostArray = scheduleFrost.getScheduledCourses();
		assertEquals(0, scheduleFrostArray.length);

		manager.logout();

		manager.login("ahicks", "pw");
		assertTrue(manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "001")),
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001\nResults: True");
		assertTrue(manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC226", "001")),
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001\nResults: True");
		assertFalse(manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC226", "001")),
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC226-001\nResults: False - duplicate");
		assertFalse(manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC116", "001")),
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-001\nResults: False - time conflict");
		assertTrue(manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC116", "003")),
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\nResults: True");
		assertFalse(manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "601")),
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, CSC116-002\nResults: False - already in section of 116");
		assertFalse(manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC230", "001")),
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, CSC230-001\nResults: False - exceeded max credits");

		Student ahicks = directory.getStudentById("ahicks");
		Schedule scheduleHicks = ahicks.getSchedule();
		assertEquals(9, scheduleHicks.getScheduleCredits());
		String[][] scheduleHicksArray = scheduleHicks.getScheduledCourses();
		assertEquals(3, scheduleHicksArray.length);
		assertEquals("CSC216", scheduleHicksArray[0][0], "User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n");
		assertEquals("001", scheduleHicksArray[0][1], "User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n");
		assertEquals("Software Development Fundamentals", scheduleHicksArray[0][2],
				"User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n");
		assertEquals("TH 1:30PM-2:45PM", scheduleHicksArray[0][3],
				"User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n");
		assertEquals("9", scheduleHicksArray[0][4], "User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n");
		assertEquals("CSC226", scheduleHicksArray[1][0], "User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n");
		assertEquals("001", scheduleHicksArray[1][1], "User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n");
		assertEquals("Discrete Mathematics for Computer Scientists", scheduleHicksArray[1][2],
				"User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n");
		assertEquals("MWF 9:35AM-10:25AM", scheduleHicksArray[1][3],
				"User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n");
		assertEquals("9", scheduleHicksArray[1][4], "User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n");
		assertEquals("CSC116", scheduleHicksArray[2][0], "User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n");
		assertEquals("003", scheduleHicksArray[2][1], "User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n");
		assertEquals("Intro to Programming - Java", scheduleHicksArray[2][2],
				"User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n");
		assertEquals("TH 11:20AM-1:10PM", scheduleHicksArray[2][3],
				"User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n");
		assertEquals("9", scheduleHicksArray[2][4], "User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n");

		assertTrue(manager.dropStudentFromCourse(catalog.getCourseFromCatalog("CSC226", "001")),
				"Action: drop\nUser: ahicks\nCourse: CSC226-001\nResults: True");

		// Check schedule
		ahicks = directory.getStudentById("ahicks");
		scheduleHicks = ahicks.getSchedule();
		assertEquals(6, scheduleHicks.getScheduleCredits());
		scheduleHicksArray = scheduleHicks.getScheduledCourses();
		assertEquals(2, scheduleHicksArray.length);
		assertEquals("CSC216", scheduleHicksArray[0][0],
				"User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, removed CSC226-001\n");
		assertEquals("001", scheduleHicksArray[0][1],
				"User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, removed CSC226-001\n");
		assertEquals("Software Development Fundamentals", scheduleHicksArray[0][2],
				"User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, removed CSC226-001\n");
		assertEquals("TH 1:30PM-2:45PM", scheduleHicksArray[0][3],
				"User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, removed CSC226-001\n");
		assertEquals("9", scheduleHicksArray[0][4],
				"User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, removed CSC226-001\n");
		assertEquals("CSC116", scheduleHicksArray[1][0],
				"User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, removed CSC226-001\n");
		assertEquals("003", scheduleHicksArray[1][1],
				"User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, removed CSC226-001\n");
		assertEquals("Intro to Programming - Java", scheduleHicksArray[1][2],
				"User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, removed CSC226-001\n");
		assertEquals("TH 11:20AM-1:10PM", scheduleHicksArray[1][3],
				"User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, removed CSC226-001\n");
		assertEquals("9", scheduleHicksArray[1][4],
				"User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, removed CSC226-001\n");

		assertFalse(manager.dropStudentFromCourse(catalog.getCourseFromCatalog("CSC226", "001")),
				"Action: drop\nUser: ahicks\nCourse: CSC226-001\nResults: False - already dropped");

		assertTrue(manager.dropStudentFromCourse(catalog.getCourseFromCatalog("CSC216", "001")),
				"Action: drop\nUser: ahicks\nCourse: CSC216-001\nResults: True");

		// Check schedule
		ahicks = directory.getStudentById("ahicks");
		scheduleHicks = ahicks.getSchedule();
		assertEquals(3, scheduleHicks.getScheduleCredits());
		scheduleHicksArray = scheduleHicks.getScheduledCourses();
		assertEquals(1, scheduleHicksArray.length);
		assertEquals("CSC116", scheduleHicksArray[0][0],
				"User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, removed CSC226-001, CSC216-001\n");
		assertEquals("003", scheduleHicksArray[0][1],
				"User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, removed CSC226-001, CSC216-001\n");
		assertEquals("Intro to Programming - Java", scheduleHicksArray[0][2],
				"User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, removed CSC226-001, CSC216-001\n");
		assertEquals("TH 11:20AM-1:10PM", scheduleHicksArray[0][3],
				"User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, removed CSC226-001, CSC216-001\n");
		assertEquals("9", scheduleHicksArray[0][4],
				"User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, removed CSC226-001, CSC216-001\n");

		assertTrue(manager.dropStudentFromCourse(catalog.getCourseFromCatalog("CSC116", "003")),
				"Action: drop\nUser: ahicks\nCourse: CSC116-003\nResults: True");

		// Check schedule
		ahicks = directory.getStudentById("ahicks");
		scheduleHicks = ahicks.getSchedule();
		assertEquals(0, scheduleHicks.getScheduleCredits());
		scheduleHicksArray = scheduleHicks.getScheduledCourses();
		assertEquals(0, scheduleHicksArray.length);

		manager.logout();
	}

	/**
	 * Tests RegistrationManager.resetSchedule()
	 */
	@Test
	public void testResetSchedule() {
		StudentDirectory directory = manager.getStudentDirectory();
		directory.loadStudentsFromFile("test-files/student_records.txt");

		CourseCatalog catalog = manager.getCourseCatalog();
		catalog.loadCoursesFromFile("test-files/course_records.txt");

		manager.logout(); // In case not handled elsewhere

		// Test if not logged in
		try {
			manager.resetSchedule();
			fail("RegistrationManager.resetSchedule() - If the current user is null, an IllegalArgumentException should be thrown, but was not.");
		} catch (IllegalArgumentException e) {
			assertNull(manager.getCurrentUser(),
					"RegistrationManager.resetSchedule() - currentUser is null, so cannot enroll in course.");
		}

		// test if registrar is logged in
		manager.login(registrarUsername, registrarPassword);
		try {
			manager.resetSchedule();
			fail("RegistrationManager.resetSchedule() - If the current user is registrar, an IllegalArgumentException should be thrown, but was not.");
		} catch (IllegalArgumentException e) {
			assertEquals(registrarUsername, manager.getCurrentUser().getId(),
					"RegistrationManager.resetSchedule() - currentUser is registrar, so cannot enroll in course.");
		}
		manager.logout();

		manager.login("efrost", "pw");
		assertTrue(manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "001")),
				"Action: enroll\nUser: efrost\nCourse: CSC216-001\nResults: True - Student max credits are 3 and course has 3.");
		assertFalse(manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC217", "211")),
				"Action: enroll\nUser: efrost\nCourse: CSC216-001 then CSC 217-211\nResults: False - Student max credits are 3 and additional credit of CSC217-211 cannot be added, will exceed max credits.");

		manager.resetSchedule();
		// Check Student Schedule
		Student efrost = directory.getStudentById("efrost");
		Schedule scheduleFrost = efrost.getSchedule();
		assertEquals(0, scheduleFrost.getScheduleCredits());
		String[][] scheduleFrostArray = scheduleFrost.getScheduledCourses();
		assertEquals(0, scheduleFrostArray.length);
		assertEquals(10, catalog.getCourseFromCatalog("CSC226", "001").getCourseRoll().getOpenSeats());

		manager.logout();

		manager.login("ahicks", "pw");
		assertTrue(manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "001")),
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001\nResults: True");
		assertTrue(manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC226", "001")),
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001\nResults: True");
		assertFalse(manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC226", "001")),
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC226-001\nResults: False - duplicate");
		assertFalse(manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC116", "001")),
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-001\nResults: False - time conflict");
		assertTrue(manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC116", "003")),
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\nResults: True");
		assertFalse(manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "601")),
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, CSC116-002\nResults: False - already in section of 116");
		assertFalse(manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC230", "001")),
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, CSC230-001\nResults: False - exceeded max credits");

		manager.resetSchedule();
		// Check Student schedule
		Student ahicks = directory.getStudentById("ahicks");
		Schedule scheduleHicks = ahicks.getSchedule();
		assertEquals(0, scheduleHicks.getScheduleCredits());
		String[][] scheduleHicksArray = scheduleHicks.getScheduledCourses();
		assertEquals(0, scheduleHicksArray.length);
		assertEquals(10, catalog.getCourseFromCatalog("CSC226", "001").getCourseRoll().getOpenSeats());
		assertEquals(10, catalog.getCourseFromCatalog("CSC216", "001").getCourseRoll().getOpenSeats());
		assertEquals(10, catalog.getCourseFromCatalog("CSC116", "003").getCourseRoll().getOpenSeats());

		manager.logout();
	}

}