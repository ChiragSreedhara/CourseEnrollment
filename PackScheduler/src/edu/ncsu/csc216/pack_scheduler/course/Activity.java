package edu.ncsu.csc216.pack_scheduler.course;

/**
 * Activity super class of Course that creates an Activity and sets the course's
 * title, meeting days, starting times, and ending times.
 * 
 * @author Fahad Ansari
 * @author Niyati Patel
 */
public abstract class Activity implements Conflict {

	/** Course's title. */
	private String title;

	/** Course's meeting days */
	private String meetingDays;

	/** Course's starting time */
	private int startTime;

	/** Course's ending time */
	private int endTime;

	/** Upper hour limit */
	public static final int UPPER_HOUR = 23;

	/** Upper minute limit */
	public static final int UPPER_MIN = 59;

	/**
	 * Creates a short display array that is used to add rows of the course catalog
	 * and the student's schedule
	 * 
	 * @return the short display array of course catalog and student schedule
	 */
	public abstract String[] getShortDisplayArray();

	/**
	 * Creates a long display array that is used to add rows to a schedule and show
	 * the final schedule
	 * 
	 * @return the long display array of the student schedule
	 */
	public abstract String[] getLongDisplayArray();

	/**
	 * Checks if there are duplicate activities and returns true or false
	 * accordingly
	 * 
	 * @param activity the Activity
	 * @return true if there are no duplicates or false if there are duplicates
	 */
	public abstract boolean isDuplicate(Activity activity);

	/**
	 * Constructs an Activity with the given title, meeting days, starting time, and
	 * ending time.
	 * 
	 * @param title       title of the Activity
	 * @param meetingDays meeting days for the Activity
	 * @param startTime   starting time of the Activity
	 * @param endTime     ending time of the Activity
	 */
	public Activity(String title, String meetingDays, int startTime, int endTime) {
		super();
		setTitle(title);
		setMeetingDaysAndTime(meetingDays, startTime, endTime);
	}

	/**
	 * Return the course's title
	 * 
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Set the course's title
	 * 
	 * @param title the title to set
	 * @throws IllegalArgumentException if title is null or is an empty string
	 */
	public void setTitle(String title) {
		if (title == null || title.length() == 0) {
			throw new IllegalArgumentException("Invalid title.");
		}

		this.title = title;

	}

	/**
	 * Return the course's meeting days
	 * 
	 * @return the meetingDays of the course
	 */
	public String getMeetingDays() {
		return meetingDays;
	}

	/**
	 * Return the course's start time
	 * 
	 * @return the startTime of the course
	 */
	public int getStartTime() {
		return startTime;
	}

	/**
	 * Return the course's end time
	 * 
	 * @return the endTime of the course
	 */
	public int getEndTime() {
		return endTime;
	}

	/**
	 * Sets the Activity's meeting days, starting time, and ending time. If the
	 * Activity's meeting days are arranged, then the starting and ending time will
	 * both be set to 0.
	 * 
	 * @param meetingDays meeting days to set
	 * @param startTime   start time to set
	 * @param endTime     end time to set
	 * @throws IllegalArgumentException if meeting days are null or an empty string
	 * @throws IllegalArgumentException if starting time is greater than ending time
	 * @throws IllegalArgumentException if starting time has an hour greater than 23
	 *                                  or less than 0 and if the starting time has
	 *                                  minutes greater than 59 or less than 0.
	 * @throws IllegalArgumentException if ending time has an hour greater than 23
	 *                                  or less than 0 and if the ending time has
	 *                                  minutes greater than 59 or less than 0.
	 */
	public void setMeetingDaysAndTime(String meetingDays, int startTime, int endTime) {
		if (meetingDays == null 
				|| meetingDays.isEmpty()) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}

		if (startTime > endTime) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}

		int startHour = startTime / 100;
		int startMinute = startTime % 100;
		int endHour = endTime / 100;
		int endMinute = endTime % 100;

		if (startHour < 0 || startHour > UPPER_HOUR) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}

		if (startMinute < 0 || startMinute > UPPER_MIN) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}

		if (endHour < 0 || endHour > UPPER_HOUR) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}

		if (endMinute < 0 || endMinute > UPPER_MIN) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}

		this.meetingDays = meetingDays;
		this.startTime = startTime;
		this.endTime = endTime;

	}

	/**
	 * Converts the military time of the course to standard 12 hour time and returns
	 * the String 12 hour format of the time.
	 * 
	 * @param time in military format
	 * @return a String of the time in a 12 hour format
	 */
	private String getTimeString(int time) {
		int hours = time / 100;
		int minutes = time % 100;
		String aMPM = "AM";

		if (hours >= 12) {
			aMPM = "PM";
			if (hours > 12) {
				hours = hours - 12;
			}
		}

		if (hours == 0) {
			hours = 12;
			aMPM = "AM";
		}

		String minutesTime = String.valueOf(minutes);
		if (minutes < 10) {
			minutesTime = "0" + minutesTime;
		}

		return hours + ":" + minutesTime + aMPM;

	}

	/**
	 * Checks whether a selected activity wanting to be added conflicts with another
	 * activity already in the schedule
	 * 
	 * @param possibleConflictingActivity the activity being checked if it conflicts
	 *                                    with another activity in the schedule
	 * @throws ConflictException if both activities conflict with each other
	 */
	@Override
	public void checkConflict(Activity possibleConflictingActivity) throws ConflictException {
		for(int i = 0; i < meetingDays.length(); i++) {
			for(int j = 0; j < possibleConflictingActivity.getMeetingDays().length(); j++) {
				if(meetingDays.charAt(i) == possibleConflictingActivity.getMeetingDays().charAt(j)) {
					if(startTime < possibleConflictingActivity.getEndTime() &&
							endTime > possibleConflictingActivity.getStartTime()) {
						throw new ConflictException();
					}
					else if(startTime == possibleConflictingActivity.getEndTime() && endTime > possibleConflictingActivity.getStartTime()) {
						throw new ConflictException();
					}
					else if(endTime == possibleConflictingActivity.getStartTime() && startTime < possibleConflictingActivity.getEndTime()) {
						throw new ConflictException();
					}
				}
			}
		}
	}

	/**
	 * Return the string format for the meeting days of the course along with the
	 * times if the meeting days are not arranged. If the meeting days are arranged,
	 * only the meeting days string is returned.
	 * 
	 * @return a string of the meeting days.
	 */
	public String getMeetingString() {
		if ("A".equals(meetingDays)) {
			return "Arranged";
		} else {
			String startTimeString = getTimeString(startTime);
			String endTimeString = getTimeString(endTime);

			String meeting = meetingDays + " " + startTimeString + "-" + endTimeString;
			return meeting;
		}
	}

	/**
	 * Generates a hashCode for Activity using all fields.
	 * 
	 * @return hashCode for Activity
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + endTime;
		result = prime * result + ((meetingDays == null) ? 0 : meetingDays.hashCode());
		result = prime * result + startTime;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Activity other = (Activity) obj;
		if (endTime != other.endTime)
			return false;
		if (meetingDays == null) {
			if (other.meetingDays != null)
				return false;
		} else if (!meetingDays.equals(other.meetingDays))
			return false;
		if (startTime != other.startTime)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

}