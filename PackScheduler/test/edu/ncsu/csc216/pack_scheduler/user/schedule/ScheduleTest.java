package edu.ncsu.csc216.pack_scheduler.user.schedule;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.pack_scheduler.course.Course;

/**
 * Tests Schedule.java
 * 
 * @author Fahad Ansari
 * @author Ryan Stauffer
 * @author Chirag Sreedhara
 */
public class ScheduleTest {

	/**
	 * Tests adding course to schedule.
	 */
	@Test
	public void testAddCourseToSchedule() {
		Schedule sched = new Schedule();
		Course c = new Course("CSC216", "Software Engineering Principles", "003", 3, "csreed", 10, "MWF");
		Course c1 = new Course("CSC226", "Discrete Mathematics for CS", "003", 3, "csreed", 10, "TH");

		assertTrue(sched.addCourseToSchedule(c));
		assertThrows(IllegalArgumentException.class, () -> sched.addCourseToSchedule(c));
		assertTrue(sched.addCourseToSchedule(c1));
	}

	/**
	 * Tests removing course from schedule.
	 */
	@Test
	public void testRemoveCourseFromSchedule() {
		Schedule sched = new Schedule();
		Course c = new Course("CSC216", "Software Engineering Principles", "003", 3, "csreed", 10, "MWF");
		Course c1 = new Course("CSC226", "Discrete Mathematics for CS", "003", 3, "csreed", 10, "TH");

		assertTrue(sched.addCourseToSchedule(c));

		assertFalse(sched.removeCourseFromSchedule(c1));
		assertTrue(sched.removeCourseFromSchedule(c));
	}

	/**
	 * Tests resetting schedule.
	 */
	@Test
	public void testResetSchedule() {
		Schedule sched = new Schedule();
		Course c = new Course("CSC216", "Software Engineering Principles", "003", 3, "csreed", 10, "MWF");
		assertTrue(sched.addCourseToSchedule(c));
		sched.resetSchedule();
		String[][] schedCourses = new String[0][4];
		assertEquals(sched.getScheduledCourses().length, schedCourses.length);

		Schedule schedule = new Schedule();
		schedule.addCourseToSchedule(
				new Course("CSC216", "Software Engineering Principles", "003", 3, "csreed", 10, "MWF"));
		schedule.addCourseToSchedule(new Course("CSC230", "C and Software Tools", "601", 3, "csreed", 10, "MWF"));

		String[][] result = schedule.getScheduledCourses();
		assertEquals(2, result.length);
		assertEquals("CSC216", result[0][0]);
		assertEquals("003", result[0][1]);
		assertEquals("CSC230", result[1][0]);
		assertEquals("601", result[1][1]);
	}

	/**
	 * Tests setting title.
	 */
	@Test
	public void testSetTitle() {
		Schedule sched = new Schedule();
		assertEquals("My Schedule", sched.getTitle());

		sched.setTitle("Test");
		assertEquals("Test", sched.getTitle());

		assertThrows(IllegalArgumentException.class, () -> sched.setTitle(null));
	}

	/**
	 * Tests getting total credits.
	 */
	@Test
	public void testGetScheduleCredits() {
		Schedule sched = new Schedule();
		Course c = new Course("CSC216", "Software Engineering Principles", "003", 3, "csreed", 10, "MWF");
		Course c1 = new Course("CSC226", "Discrete Mathematics for CS", "003", 3, "csreed", 10, "TH");
		assertEquals(0, sched.getScheduleCredits());
		assertTrue(sched.addCourseToSchedule(c));
		assertEquals(3, sched.getScheduleCredits());
		assertTrue(sched.addCourseToSchedule(c1));
		assertEquals(6, sched.getScheduleCredits());
	}

	/**
	 * Tests canAdd() method
	 */
	@Test
	public void testCanAdd() {
		Schedule sched = new Schedule();
		Course c = new Course("CSC216", "Software Engineering Principles", "003", 3, "csreed", 10, "MWF");
		Course c1 = new Course("CSC226", "Discrete Mathematics for CS", "003", 3, "csreed", 10, "TH");

		assertTrue(sched.canAdd(c1));
		assertTrue(sched.canAdd(c));

		assertTrue(sched.addCourseToSchedule(c));
		assertFalse(sched.canAdd(c));
		assertTrue(sched.addCourseToSchedule(c1));
		assertFalse(sched.canAdd(c1));
		assertFalse(sched.canAdd(null));
	}

}
