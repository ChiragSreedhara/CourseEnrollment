package edu.ncsu.csc216.pack_scheduler.user;

import edu.ncsu.csc216.pack_scheduler.user.schedule.FacultySchedule;

/**
 * Faculty User Class
 * 
 * @author David Martinez
 * @author Chirag Sreedhara
 */
public class Faculty extends User {

	/** Faculty courses responsible for */
	private int maxCourses;
	/** Faculty min courses needed */
	private static final int MIN_COURSES = 1;
	/** Faculty max coureses allowed */
	private static final int MAX_COURSES = 3;
	/** Faculty schedule */
	private FacultySchedule fs;

	/**
	 * Faculty Constructor, calls superclass constructor
	 * 
	 * @param firstName  Faculty first name
	 * @param lastName   Faculty last name
	 * @param id         Faculty id
	 * @param email      Faculty email
	 * @param password   Faculty password
	 * @param maxCources Faculty max courses
	 */
	public Faculty(String firstName, String lastName, String id, String email, String password, int maxCources) {
		super(firstName, lastName, id, email, password);
		setMaxCourses(maxCources);
		fs = new FacultySchedule(id);
	}

	/**
	 * Returns the faculty's schedule.
	 * 
	 * @return the schedule of the faculty member.
	 */
	public FacultySchedule getSchedule() {
		return fs;
	}

	/**
	 * Tells if schedule is overloaded with courses or not
	 * 
	 * @return true if overloaded, false otherwise.
	 */
	public boolean isOverloaded() {
		return fs.getNumScheduledCourses() > maxCourses;
	}

	/**
	 * Set Max Courses, must be between 1 and 3 inclusive
	 * 
	 * @param maxCourses courses to set
	 * @throws IllegalArgumentException if max course invalid
	 */
	public void setMaxCourses(int maxCourses) {
		if (maxCourses < MIN_COURSES || maxCourses > MAX_COURSES) {
			throw new IllegalArgumentException("Invalid max courses");
		}
		this.maxCourses = maxCourses;
	}

	/**
	 * Get the Faculty max courses
	 * 
	 * @return max courses
	 */
	public int getMaxCourses() {
		return maxCourses;
	}

	/**
	 * Check hashcode equality
	 * 
	 * @return hash value
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + maxCourses;
		return result;
	}

	/**
	 * Check if faculties are equal
	 * 
	 * @param obj object to check if it is equal to this instance
	 * @return true if equal, else false
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Faculty other = (Faculty) obj;
		return maxCourses == other.maxCourses;
	}

	/**
	 * Returns the faculty object as a formatted string.
	 * 
	 * @return formatted string
	 */
	@Override
	public String toString() {
		return getFirstName() + "," + getLastName() + "," + getId() + "," + getEmail() + "," + getPassword() + ","
				+ getMaxCourses();
	}
}
