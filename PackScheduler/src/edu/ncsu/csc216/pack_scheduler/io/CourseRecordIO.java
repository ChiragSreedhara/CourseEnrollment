package edu.ncsu.csc216.pack_scheduler.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.manager.RegistrationManager;
import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc217.collections.list.SortedList;

/**
 * Reads Course records from text files. Writes a set of CourseRecords to a
 * file.
 * 
 * @author Fahad Ansari
 * @author Niyati Patel
 * @author Sarah Heckman
 */
public class CourseRecordIO {

	/**
	 * Reads course records from a file and generates a list of valid Courses. Any
	 * invalid Courses are ignored. If the file to read cannot be found or the
	 * permissions are incorrect a File NotFoundException is thrown.
	 * 
	 * @param fileName file to read Course records from
	 * @return a list of valid Courses
	 * @throws FileNotFoundException if the file cannot be found or read
	 */
	public static SortedList<Course> readCourseRecords(String fileName) throws FileNotFoundException {
		Scanner fileReader = new Scanner(new FileInputStream(fileName)); // Create a file scanner to read the file
		SortedList<Course> courses = new SortedList<Course>(); // Create an empty array of Course objects
		while (fileReader.hasNextLine()) { // While we have more lines in the file
			try { // Attempt to do the following
					// Read the line, process it in readCourse, and get the object
					// If trying to construct a Course in readCourse() results in an exception, flow
					// of control will transfer to the catch block, below
				Course course = readCourse(fileReader.nextLine());

				// Create a flag to see if the newly created Course is a duplicate of something
				// already in the list
				boolean duplicate = false;
				// Look at all the courses in our list
				for (int i = 0; i < courses.size(); i++) {
					// Get the course at index i
					Course current = courses.get(i);
					// Check if the name and section are the same
					if (course.getName().equals(current.getName())
							&& course.getSection().equals(current.getSection())) {
						// It's a duplicate!
						duplicate = true;
						break; // We can break out of the loop, no need to continue searching
					}
				}
				// If the course is NOT a duplicate
				if (!duplicate) {
					courses.add(course); // Add to the List!
				} // Otherwise ignore
			} catch (IllegalArgumentException e) {
				// The line is invalid b/c we couldn't create a course, skip it!
			}
		}
		// Close the Scanner b/c we're responsible with our file handles
		fileReader.close();
		// Return the List with all the courses we read!
		return courses;
	}

	/**
	 * Read individual line of a specific course and create a course object with the
	 * specific information on the line.
	 * 
	 * @param line of a specific course from the file
	 * @return a newly constructed Course object with the information about the
	 *         course that was read from the line
	 * @throws IllegalArgumentException if meeting days are arranged and there are
	 *                                  more tokens after the meeting days
	 * @throws IllegalArgumentException if there are more tokens are the starting
	 *                                  time and ending time if the course has
	 *                                  specific times
	 * @throws IllegalArgumentException if the code catches an
	 *                                  InputMismatchException
	 */
	private static Course readCourse(String line) {
		try (Scanner scan = new Scanner(line)) {
			scan.useDelimiter(",");

			try {
				String name = scan.next();
				String title = scan.next();
				String section = scan.next();
				int creditHours = scan.nextInt();
				String instructorId = scan.next();
				int enrollmentCap = scan.nextInt();
				String meetingDays = scan.next();
				Course newCourse;

				if ("A".equals(meetingDays)) {
					if (scan.hasNext()) {
						throw new IllegalArgumentException();
					} else {
						newCourse = new Course(name, title, section, creditHours, null, enrollmentCap, meetingDays);

					}
				} else {
					int startTime = scan.nextInt();
					int endTime = scan.nextInt();

					if (scan.hasNext()) {
						throw new IllegalArgumentException();
					}

					newCourse = new Course(name, title, section, creditHours, null, enrollmentCap, meetingDays,
							startTime, endTime);

				}

				Faculty f = RegistrationManager.getInstance().getFacultyDirectory().getFacultyById(instructorId);
				if (f != null) {
					f.getSchedule().addCourseToSchedule(newCourse);
				}

				return newCourse;
			} catch (NoSuchElementException e) {
				throw new IllegalArgumentException();
			}
		}
	}

	/**
	 * Writes the given list of Courses to
	 * 
	 * @param fileName file to write schedule of Courses to
	 * @param courses  list of Courses to write
	 * @throws IOException if cannot write to file
	 */
	public static void writeCourseRecords(String fileName, SortedList<Course> courses) throws IOException {
		PrintStream fileWriter = new PrintStream(new File(fileName));

		for (int i = 0; i < courses.size(); i++) {
			fileWriter.println(courses.get(i).toString());
		}

		fileWriter.close();

	}

}