package edu.ncsu.csc216.pack_scheduler.course;

import edu.ncsu.csc216.pack_scheduler.course.roll.CourseRoll;
import edu.ncsu.csc216.pack_scheduler.course.validator.CourseNameValidator;
import edu.ncsu.csc216.pack_scheduler.course.validator.InvalidTransitionException;

/**
 * Course class that creates a course and sets the course's name, title, section
 * number, credit hours, instructor ID, meeting days, and starting and ending
 * times.
 * 
 * @author Fahad Ansari
 * @author Niyati Patel
 * @author David Martinez
 * @author Chirag Sreedhara
 */
public class Course extends Activity implements Comparable<Course> {

	/** Course's name. */
	private String name;

	/** Course's section. */
	private String section;

	/** Course's credit hours */
	private int credits;

	/** Course's instructor */
	private String instructorId;

	/** Course's enrollment list */
	private CourseRoll roll;

	/** Minimum length of course name */
	public static final int MIN_LENGTH = 4;

	/** Maximum length of course name */
	public static final int MAX_LENGTH = 8;

	/** Length of section number */
	public static final int SECTION_LENGTH = 3;

	/** Minimum number of course credits */
	public static final int MIN_CREDITS = 1;

	/** Maximum number of course credits */
	public static final int MAX_CREDITS = 5;

	/** FSM Course Name Validator */
	private CourseNameValidator validator = new CourseNameValidator();

	/**
	 * Constructs a course with the given name, title, section, credits,
	 * instructorId, meetingDays, start time, and end time for the course.
	 * 
	 * @param name          name of the course
	 * @param title         title of the course
	 * @param section       section of course
	 * @param credits       credit hours for course
	 * @param instructorId  instructor's unity id
	 * @param enrollmentCap enrollmentCap for courseRoll
	 * @param meetingDays   meeting days for course as series of chars
	 * @param startTime     start time for course
	 * @param endTime       end time for course
	 * @throws IllegalArgumentException if course name isnt valid
	 */
	public Course(String name, String title, String section, int credits, String instructorId, int enrollmentCap,
			String meetingDays, int startTime, int endTime) {

		super(title, meetingDays, startTime, endTime);

		setName(name);
		setSection(section);
		setCredits(credits);
		setInstructorId(instructorId);
		this.roll = new CourseRoll(enrollmentCap, this);
	}

	/**
	 * Creates a Course with the given name, title, section, credits, instructorId,
	 * and meetingDays for courses that are arranged.
	 * 
	 * @param name          name of course
	 * @param title         title of course
	 * @param section       section of course
	 * @param credits       credit hours for course
	 * @param instructorId  instructor's unity id
	 * @param enrollmentCap enrollmentCap for courseRoll
	 * @param meetingDays   meeting days for course as series of chars
	 * @throws IllegalArgumentException if course name isnt valid
	 */
	public Course(String name, String title, String section, int credits, String instructorId, int enrollmentCap,
			String meetingDays) {
		this(name, title, section, credits, instructorId, enrollmentCap, meetingDays, 0, 0);
	}

	/**
	 * Return the course's name
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the Course's name. If the name is null, has a length less than 5 or more
	 * than 8, does not contain a space between letter characters and number
	 * characters, has less than 1 or more than 4 letter characters, and not exactly
	 * three trailing digit characters, an IllegalArgumentException is thrown.
	 * 
	 * @param name the name to set
	 * @throws IllegalArgumentException if name is null
	 * @throws IllegalArgumentException if the length of the course name is less
	 *                                  than the minimum length of greater than the
	 *                                  maximum length
	 * @throws IllegalArgumentException if no space has been found yet and the
	 *                                  character in the name is not a letter or a
	 *                                  space
	 * @throws IllegalArgumentException if a space has been found and the characters
	 *                                  after in the name are not digits.
	 * @throws IllegalArgumentException if the name of the course has less than 1
	 *                                  letter or greater than 4 letters.
	 * @throws IllegalArgumentException if the name of the course does not have 3
	 *                                  numbers in its name
	 */
	private void setName(String name) {
		if (name == null) {
			throw new IllegalArgumentException("Invalid course name.");
		}
		if (name.length() < MIN_LENGTH || name.length() > MAX_LENGTH) {
			throw new IllegalArgumentException("Invalid course name.");
		}

		try {
			if (!validator.isValid(name)) {
				throw new IllegalArgumentException("Invalid course name.");
			}
		} catch (InvalidTransitionException e) {
			throw new IllegalArgumentException("Invalid course name.");
		}

		this.name = name;

	}

	/**
	 * Return the course's section
	 * 
	 * @return the section number of the course
	 */
	public String getSection() {
		return section;
	}

	/**
	 * Set the course's section number
	 * 
	 * @param section the section to set
	 * @throws IllegalArgumentException if section is null or is not 3 digits
	 * @throws IllegalArgumentException if number of digits counter are not the
	 *                                  length that the section number should be
	 */
	public void setSection(String section) {
		if (section == null || section.length() != 3) {
			throw new IllegalArgumentException("Invalid section.");
		}

		int counter = 0;
		for (int i = 0; i < section.length(); i++) {
			if (Character.isDigit(section.charAt(i))) {
				counter += 1;
			}
		}

		if (counter != SECTION_LENGTH) {
			throw new IllegalArgumentException("Invalid section.");
		}

		this.section = section;
	}

	/**
	 * Return the course's credits
	 * 
	 * @return the credits amount of the course
	 */
	public int getCredits() {
		return credits;
	}

	/**
	 * Set the course's credits
	 * 
	 * @param credits the credits to set
	 * @throws IllegalArgumentException if the number of credits is less than the
	 *                                  minimum number of credits or more than the
	 *                                  maximum number of credits
	 */
	public void setCredits(int credits) {
		if (credits < MIN_CREDITS || credits > MAX_CREDITS) {
			throw new IllegalArgumentException("Invalid credits.");
		}

		this.credits = credits;

	}

	/**
	 * Return the course's instructor id
	 * 
	 * @return the instructorId of the course
	 */
	public String getInstructorId() {
		return instructorId;
	}

	/**
	 * Set the course's instructor id
	 * 
	 * @param instructorId the instructorId to set
	 * @throws IllegalArgumentException if the instructorId is null or if it is an
	 *                                  empty string
	 */
	public void setInstructorId(String instructorId) {
		if (instructorId != null && instructorId.length() == 0) {
			throw new IllegalArgumentException("Invalid instructor id.");
		}

		this.instructorId = instructorId;

	}

	/**
	 * Getter for the Course's enrollment list
	 * 
	 * @return the Course's enrollment list
	 */
	public CourseRoll getCourseRoll() {
		return roll;
	}

	/**
	 * Sets the Course's meeting days, starting time, and ending time.
	 * 
	 * @param meetingDays meeting days to set
	 * @param startTime   start time to set
	 * @param endTime     end time to set
	 * @throws IllegalArgumentException if meeting days are null or an empty string
	 * @throws IllegalArgumentException if meeting days are arranged but the
	 *                                  starting and ending times aren't 0.
	 * @throws IllegalArgumentException if the letter for the meeting day is not a
	 *                                  letter of the a weekday
	 * @throws IllegalArgumentException if one day is listed more than once in the
	 *                                  meeting days
	 */
	@Override
	public void setMeetingDaysAndTime(String meetingDays, int startTime, int endTime) {
		if (meetingDays == null || meetingDays.isEmpty()) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}

		if ("A".equals(meetingDays)) {
			if (startTime != 0 || endTime != 0) {
				throw new IllegalArgumentException("Invalid meeting days and times.");
			} else {
				super.setMeetingDaysAndTime(meetingDays, startTime, endTime);
			}
		} else {
			int[] weekdayCounter = new int[5];
			for (int i = 0; i < meetingDays.length(); i++) {
				if (meetingDays.charAt(i) == 'M') {
					weekdayCounter[0] += 1;
				} else if (meetingDays.charAt(i) == 'T') {
					weekdayCounter[1] += 1;
				} else if (meetingDays.charAt(i) == 'W') {
					weekdayCounter[2] += 1;
				} else if (meetingDays.charAt(i) == 'H') {
					weekdayCounter[3] += 1;
				} else if (meetingDays.charAt(i) == 'F') {
					weekdayCounter[4] += 1;
				} else {
					throw new IllegalArgumentException("Invalid meeting days and times.");
				}
			}

			for (int i = 0; i < weekdayCounter.length; i++) {
				if (weekdayCounter[i] > 1) {
					throw new IllegalArgumentException("Invalid meeting days and times.");
				}
			}

			super.setMeetingDaysAndTime(meetingDays, startTime, endTime);

		}

	}

	/**
	 * Generates a hashCode for course using all fields.
	 * 
	 * @return hashCode for course
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + credits;
		result = prime * result + ((instructorId == null) ? 0 : instructorId.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((section == null) ? 0 : section.hashCode());
		return result;
	}

	/**
	 * Compares a given object to this object for equality on all fields.
	 * 
	 * @param obj the Object to compare
	 * @return true if the objects are the same on all fields
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Course other = (Course) obj;
		if (credits != other.credits) {
			return false;
		}
		if (instructorId == null) {
			if (other.instructorId != null) {
				return false;
			}
		} else if (!instructorId.equals(other.instructorId)) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (section == null) {
			if (other.section != null) {
				return false;
			}
		} else if (!section.equals(other.section)) {
			return false;
		}
		return true;
	}

	/**
	 * Returns a comma separated value String of all Course fields.
	 * 
	 * @return String representation of Course
	 */
	@Override
	public String toString() {
		if ("A".equals(getMeetingDays())) {
			return name + "," + getTitle() + "," + section + "," + credits + "," + instructorId + ","
					+ roll.getEnrollmentCap() + "," + getMeetingDays();
		}
		return name + "," + getTitle() + "," + section + "," + credits + "," + instructorId + ","
				+ roll.getEnrollmentCap() + "," + getMeetingDays() + "," + getStartTime() + "," + getEndTime();
	}

	/**
	 * Creates a short display array for the course catalog and student's schedule
	 * 
	 * @return shortDisplayArray a short display array for the courses and schedule
	 */
	@Override
	public String[] getShortDisplayArray() {
		String[] shortDisplayArray = new String[5];

		shortDisplayArray[0] = name;
		shortDisplayArray[1] = section;
		shortDisplayArray[2] = getTitle();
		shortDisplayArray[3] = getMeetingString();
		shortDisplayArray[4] = String.format("%d", getCourseRoll().getOpenSeats());

		return shortDisplayArray;

	}

	/**
	 * Creates a long display array for the student's schedule
	 * 
	 * @return longDisplayArray a long display array for the schedule
	 */
	@Override
	public String[] getLongDisplayArray() {
		String[] longDisplayArray = new String[7];

		longDisplayArray[0] = name;
		longDisplayArray[1] = section;
		longDisplayArray[2] = getTitle();
		longDisplayArray[3] = String.valueOf(credits);
		longDisplayArray[4] = instructorId;
		longDisplayArray[5] = getMeetingString();
		longDisplayArray[6] = "";

		return longDisplayArray;

	}

	/**
	 * Checks if the Activity parameter is an instance of Course
	 * 
	 * @param activity the Activity to check for
	 * @return true if the Activity is an instance of Course or false if the
	 *         Activity is not an instance of Course
	 */
	@Override
	public boolean isDuplicate(Activity activity) {
		if (activity instanceof Course) {
			Course course = (Course) activity;
			boolean checker = this.getName().equals(course.getName());
			return checker;
		} else {
			return false;
		}

	}

	/**
	 * Compares this course with the specified course for order. Courses are
	 * compared based on their name and section.
	 * 
	 * @param s the Course to compare
	 * @return a negative integer, zero, or a positive integer as this Course is
	 *         less than, equal to, or greater than the specified Course
	 * @throws NullPointerException if given course is null
	 */
	@Override
	public int compareTo(Course s) {
		if (s == null) {
			throw new NullPointerException();
		}

		// Compare by name
		int lastNameComparison = this.getName().compareTo(s.getName());
		if (lastNameComparison != 0) {
			return lastNameComparison;
		}

		// If last names are the same, compare by section
		int firstNameComparison = this.getSection().compareTo(s.getSection());
		if (firstNameComparison != 0) {
			return firstNameComparison;
		}

		// If both names are the same, compare by title
		return this.getTitle().compareTo(s.getTitle());
	}

}
