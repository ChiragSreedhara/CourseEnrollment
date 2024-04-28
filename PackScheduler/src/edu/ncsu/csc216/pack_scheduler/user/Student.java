package edu.ncsu.csc216.pack_scheduler.user;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.user.schedule.Schedule;

/**
 * Student class that creates a student and sets the student's first name, last
 * name, id, email, password, and maximum credits.
 * 
 * @author Grant Lewison
 * @author Fahad Ansari
 * @author Niyati Patel
 */
public class Student extends User implements Comparable<Student> {

	/** Student's maximum credits */
	private int maxCredits;

	/** Maximum credits possible for each student */
	public static final int MAX_CREDITS = 18;

	/**
	 * The student's Schedule
	 */
	private Schedule schedule;

	/**
	 * Constructs a student with their given first name, last name, id number,
	 * email, password, and maximum credits.
	 * 
	 * @param firstName  first name of the student
	 * @param lastName   last name of the student
	 * @param id         id number of the student
	 * @param email      email of the student
	 * @param password   password of the student
	 * @param maxCredits number of maximum credits of the student
	 */
	public Student(String firstName, String lastName, String id, String email, String password, int maxCredits) {
		super(firstName, lastName, id, email, password);
		setMaxCredits(maxCredits);
		schedule = new Schedule();

	}

	/**
	 * Constructs a student with their given first name, last name, id number,
	 * email, password, and maximum credits possible of 18.
	 * 
	 * @param firstName first name of the student
	 * @param lastName  last name of the student
	 * @param id        id number of the student
	 * @param email     email of the student
	 * @param password  password of the student
	 */
	public Student(String firstName, String lastName, String id, String email, String password) {
		this(firstName, lastName, id, email, password, 18);
	}

	/**
	 * Returns the student's maximum number of credits
	 * 
	 * @return maxCredits maximum credits of the student
	 */
	public int getMaxCredits() {
		return maxCredits;
	}

	/**
	 * Sets the maximum credits of the student to the specified number
	 * 
	 * @param maxCredits the maxCredits to set
	 * @throws IllegalArgumentException invalid max credits if the specified number
	 *                                  is less than 3 or greater than 18
	 */
	public void setMaxCredits(int maxCredits) {
		if (maxCredits < 3 || maxCredits > 18) {
			throw new IllegalArgumentException("Invalid max credits");
		}
		this.maxCredits = maxCredits;
	}

	/**
	 * getter for the Student's schedule
	 * 
	 * @return the student's schedule
	 */
	public Schedule getSchedule() {
		return schedule;
	}

	/**
	 * Compares this student with the specified student for order. Students are
	 * compared based on their last name, then their first name, and finally their
	 * unity ID.
	 * 
	 * @param s the Student to compare
	 * @return a negative integer, zero, or a positive integer as this student is
	 *         less than, equal to, or greater than the specified student
	 */
	@Override
	public int compareTo(Student s) {
		int compareLastName = getLastName().compareTo(s.getLastName());
		if (compareLastName != 0) {
			return compareLastName;
		}

		int compareFirstName = getFirstName().compareTo(s.getFirstName());
		if (compareFirstName != 0) {
			return compareFirstName;
		}
		return getId().compareTo(s.getId());
	}

	/**
	 * Returns the student object as a formatted string.
	 */
	@Override
	public String toString() {
		return getFirstName() + "," + getLastName() + "," + getId() + "," + getEmail() + "," + getPassword() + ","
				+ getMaxCredits();
	}

	/**
	 * Hash code method for student.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + maxCredits;
		return result;
	}

	/**
	 * Method to check if two student objects are equal
	 * 
	 * @param obj to check if equal.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof Student)) {
			return false;
		}
		Student other = (Student) obj;
		return maxCredits == other.maxCredits;
	}

	/**
	 * Checks if a course can be added to the schedule (isn't null, doesn't overlap
	 * with existing course, and isnt a duplicate of an existing course).
	 * 
	 * @param c Course to check
	 * @return true if it can be added, false otherwise.
	 */
	public boolean canAdd(Course c) {

		return !(!schedule.canAdd(c) || c.getCredits() + schedule.getScheduleCredits() > getMaxCredits());
	}

}