package edu.ncsu.csc216.pack_scheduler.user.schedule;

import edu.ncsu.csc216.pack_scheduler.course.ConflictException;
import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.util.ArrayList;

/**
 * Class that stores information for schedule
 * 
 * @author Fahad Ansari
 * @author Ryan Stauffer
 * @author Chirag Sreedhara
 */
public class Schedule {

	/** Title for schedule */
	private String title;

	/** ArrayList of everything in schedule */
	private ArrayList<Course> schedule;

	/**
	 * Constructor method for schedule
	 */
	public Schedule() {
		schedule = new ArrayList<Course>();
		setTitle("My Schedule");

	}

	/**
	 * Adds a given course to schedule array list
	 * 
	 * @param c course to add
	 * @return if course has been successfully added
	 * @throws NullPointerException     propagated up from ArrayList if c is null
	 * @throws IllegalArgumentException if conflict or duplicate
	 */
	public boolean addCourseToSchedule(Course c) {
		for (int i = 0; i < schedule.size(); i++) {
			if (schedule.get(i).getName().equals(c.getName())) {
				throw new IllegalArgumentException(String.format("You are already enrolled in %s", c.getName()));
			}
			try {
				schedule.get(i).checkConflict(c);
			} catch (ConflictException e) {
				throw new IllegalArgumentException("The course cannot be added due to a conflict.");
			}
		}
		return schedule.add(c);
	}

	/**
	 * Removes a course from the schedule array list.
	 * 
	 * @param c course to remove
	 * @return if remove was successful.
	 */
	public boolean removeCourseFromSchedule(Course c) {
		for (int i = 0; i < schedule.size(); i++) {
			if (schedule.get(i).equals(c)) {
				schedule.remove(i);
				return true;
			}
		}
		return false;
	}

	/**
	 * Resets schedule to default (empty).
	 */
	public void resetSchedule() {
		schedule = new ArrayList<Course>();
		title = "My Schedule";
	}

	/**
	 * Converts abbreviated version of scheduled courses into a 2D array for use in
	 * GUI
	 * 
	 * @return Scheduled courses as a 2D array
	 */
	public String[][] getScheduledCourses() {
		String[][] schedCourses = new String[schedule.size()][5];
		for (int i = 0; i < schedule.size(); i++) {
			Course c = schedule.get(i);
			schedCourses[i] = c.getShortDisplayArray();

		}
		return schedCourses;
	}

	/**
	 * Sets the schedule title
	 * 
	 * @param title of schedule
	 * @throws IllegalArgumentException if the title is null
	 */
	public void setTitle(String title) {
		if (title == null) {
			throw new IllegalArgumentException("Title cannot be null.");
		}
		this.title = title;
	}

	/**
	 * Returns the schedule title
	 * 
	 * @return title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Returns total num of credits in the schedule
	 * 
	 * @return total num of credits in schedule
	 */
	public int getScheduleCredits() {
		int credits = 0;

		for (int i = 0; i < schedule.size(); i++) {
			credits += schedule.get(i).getCredits();
		}

		return credits;
	}

	/**
	 * Checks if a course can be added to the schedule (isn't null, doesn't overlap
	 * with existing course, and isnt a duplicate of an existing course).
	 * 
	 * @param c Course to check
	 * @return true if it can be added, false otherwise.
	 */
	public boolean canAdd(Course c) {
		if (c == null) {
			return false;
		}
		for (int i = 0; i < schedule.size(); i++) {
			if (c.getName().equals(schedule.get(i).getName())) {
				return false;
			}
			try {
				c.checkConflict(schedule.get(i));
			} catch (ConflictException e) {
				return false;
			}
		}

		return true;
	}

}
