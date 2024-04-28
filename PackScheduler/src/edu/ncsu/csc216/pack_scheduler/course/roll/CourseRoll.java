package edu.ncsu.csc216.pack_scheduler.course.roll;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc216.pack_scheduler.util.LinkedAbstractList;
import edu.ncsu.csc216.pack_scheduler.util.LinkedQueue;

/**
 * The CourseRoll class will serve as a list of a courses roll (enrollment).
 * 
 * @author Fahad Ansari
 * @author Chirag Sreedhara
 * @author Ryan Stauffer
 */
public class CourseRoll {

	/** The Students on roll for a specific course */
	private LinkedAbstractList<Student> roll;

	/** The Enrollment cap for a specific course */
	private int enrollmentCap;

	/** Minimum # of enrollments allowed for a class */
	private static final int MIN_ENROLLMENT = 10;

	/** Maximum # of enrollments allowed for a class */
	private static final int MAX_ENROLLMENT = 250;

	/** Waitlist functionality */
	private LinkedQueue<Student> waitlist;

	/** stores the course that the roll is associated with */
	private Course c;

	/**
	 * Constructor method for CourseRoll
	 * 
	 * @param enrollmentCap max number of enrollments for a course
	 * @param c             course to allow for waitlists
	 * @throws IllegalArgumentException if course param null
	 */
	public CourseRoll(int enrollmentCap, Course c) {
		if (c == null) {
			throw new IllegalArgumentException("Course parameter is null");
		}
		roll = new LinkedAbstractList<Student>(enrollmentCap);
		setEnrollmentCap(enrollmentCap);
		waitlist = new LinkedQueue<Student>(10);
		this.c = c;
	}

	/**
	 * Gets enrollmentCap field
	 * 
	 * @return enrollmentCap field value
	 */
	public int getEnrollmentCap() {
		return enrollmentCap;
	}

	/**
	 * Sets the enrollmentCap value
	 * 
	 * @param enrollmentCap value to set as enrollmentCap field
	 * @throws IllegalArgumentException if enrollment cap invalid
	 */
	public void setEnrollmentCap(int enrollmentCap) {
		if (enrollmentCap < MIN_ENROLLMENT || enrollmentCap > MAX_ENROLLMENT || enrollmentCap < roll.size()) {
			throw new IllegalArgumentException("Invalid enrollment cap");
		}
		roll.setCapacity(enrollmentCap);
		this.enrollmentCap = enrollmentCap;
	}

	/**
	 * Enrolls student in a course
	 * 
	 * @param s student to enroll
	 * @throws IllegalArgumentException if error with adding student to roll.
	 */
	public void enroll(Student s) {
		if (s == null || !canEnroll(s)) {
			throw new IllegalArgumentException("Can not enroll student");
		}
		try {
			roll.add(s);
		} catch (Exception e) {
			try {
				waitlist.enqueue(s);
			} catch (Exception e1) {
				throw new IllegalArgumentException("Can not enroll student");
			}
		}
	}

	/**
	 * Drops a student from a course
	 * 
	 * @param s student to drop from a course
	 * @throws IllegalArgumentException if Student can no tbe dropped
	 */
	public void drop(Student s) {
		if (s == null) {
			throw new IllegalArgumentException("Input is null.");
		}
		try {
			//drop from roll
			for (int i = 0; i < roll.size(); i++) {
				if (roll.get(i).equals(s)) {
					roll.remove(i);
					if (waitlist.size() > 0) {
						Student s1 = (Student) waitlist.dequeue();

						roll.add(s1);
						s1.getSchedule().addCourseToSchedule(c);

					}
					return;
				}
			}
			//drop from waitlist
			LinkedQueue<Student> replace = new LinkedQueue<>(10);
			
			while(!waitlist.isEmpty()) {
				Student stu = waitlist.dequeue();
				if(!s.equals(stu)) {
					replace.enqueue(stu);
				}
			}
			waitlist = replace;
		} catch (Exception e) {
			throw new IllegalArgumentException("Student can not be removed");
		}
	}

	/**
	 * Gets the open seats left in a course
	 * 
	 * @return open seats left in a course
	 */
	public int getOpenSeats() {
		return enrollmentCap - roll.size();
	}

	/**
	 * Checks if a student can be enrolled in a course
	 * 
	 * @param s student to check for enrollment
	 * @return true if student can be enrolled, false otherwise
	 */
	public boolean canEnroll(Student s) {
		if (waitlist.size() == 10 || waitlist.contains(s)) {
			return false;
		}
		for (int i = 0; i < roll.size(); i++) {
			if (roll.get(i).equals(s)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Returns waitlist size
	 * 
	 * @return size of waitlist
	 */
	public int getNumberOnWaitlist() {
		return waitlist.size();
	}
}
