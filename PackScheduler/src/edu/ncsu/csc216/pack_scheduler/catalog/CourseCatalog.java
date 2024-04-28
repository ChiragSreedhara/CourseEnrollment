/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.catalog;

import java.io.FileNotFoundException;
import java.io.IOException;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.io.CourseRecordIO;
import edu.ncsu.csc217.collections.list.SortedList;

/**
 * CourseCatalog class that interacts with the list of courses
 * 
 * @author Fahad Ansari
 * @author Niyati Patel
 */
public class CourseCatalog {

	/// ** Constant upper hour */
	// private static final int UPPER_HOUR = 24;

	/// ** Constant upper minute */
	// private static final int UPPER_MINUTE = 60;

	/** Sorted list of all available courses */
	private SortedList<Course> catalog;

	/**
	 * Constructor for creating a new course catalog
	 */
	public CourseCatalog() {
		newCourseCatalog();
	}

	/**
	 * Creates a new course catalog with no courses in the catalog initially
	 */
	public void newCourseCatalog() {
		catalog = new SortedList<Course>();
	}

	/**
	 * Loads all courses from a specific file and adds them into the catalog list
	 * 
	 * @param fileName name of the file
	 * @throws IllegalArgumentException if file can't be read.
	 */
	public void loadCoursesFromFile(String fileName) {
		try {
			catalog = CourseRecordIO.readCourseRecords(fileName);
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("Unable to read file " + fileName);
		}
	}

	/**
	 * Adds a specific course to the catalog with its name, title, section, credits,
	 * instructor's ID, meeting days, starting time, and ending time
	 * 
	 * @param name          name of the course
	 * @param title         title of the course
	 * @param section       section of the course
	 * @param credits       number of credits for the course
	 * @param instructorId  ID of the instructor for the course
	 * @param meetingDays   meeting days of the course
	 * @param startTime     starting time of the course
	 * @param endTime       ending time of the course
	 * @param enrollmentCap Max number of enrollments allowed
	 * @throws IllegalArgumentException if
	 * @return true if the course was successfully added to the list or false if the
	 *         course couldn't be added
	 */
	public boolean addCourseToCatalog(String name, String title, String section, int credits, String instructorId,
			int enrollmentCap, String meetingDays, int startTime, int endTime) {

		Course adder = new Course(name, title, section, credits, instructorId, enrollmentCap, meetingDays, startTime,
				endTime);

		for (int i = 0; i < catalog.size(); i++) {
			Course c = catalog.get(i);
			if (adder.getName().equals(c.getName()) && adder.getSection().equals(c.getSection())) {
				return false;
			}
		}
		return catalog.add(adder);
	}

	/**
	 * Removes a specific course from the catalog based on the course's name and
	 * section
	 * 
	 * @param name    name of the course
	 * @param section section of the course
	 * @return true if the course is successfully removed or false if the course
	 *         could not be removed
	 */
	public boolean removeCourseFromCatalog(String name, String section) {
		for (int i = 0; i < catalog.size(); i++) {
			if (catalog.get(i).getName().equals(name) && catalog.get(i).getSection().equals(section)) {
				catalog.remove(i);
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns a specific course from the catalog based on the course's name and
	 * section
	 * 
	 * @param name    name of the course
	 * @param section section of the course
	 * @return the specific course that is found
	 */
	public Course getCourseFromCatalog(String name, String section) {

		for (int i = 0; i < catalog.size(); i++) {
			if (catalog.get(i).getName().equals(name) && catalog.get(i).getSection().equals(section)) {
				return catalog.get(i);
			}
		}

		return null;

	}

	/**
	 * Returns a 2D array of all courses from the catalog
	 * 
	 * @return catalogArray the array of all courses in the catalog
	 */
	public String[][] getCourseCatalog() {

		String[][] catalogArray = new String[catalog.size()][5];
		for (int i = 0; i < catalog.size(); i++) {
			String[] course = catalog.get(i).getShortDisplayArray();
			catalogArray[i] = course;
		}
		return catalogArray;
	}

	/**
	 * Saves the course catalog to a specific file
	 * 
	 * @param fileName name of the file
	 * @throws IllegalArgumentException if an IO Exception occurs when trying to
	 *                                  write the course catalog to the file
	 */
	public void saveCourseCatalog(String fileName) {
		try {
			CourseRecordIO.writeCourseRecords(fileName, catalog);
		} catch (IOException e) {
			throw new IllegalArgumentException("Unable to write to file " + fileName);
		}
	}
}
