/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.course;

/**
 * Conflict interface that checks if activities conflict with each other on the
 * schedule
 * 
 * @author Fahad Ansari
 * @author Niyati Patel
 */
public interface Conflict {

	/**
	 * Checks if two activities are conflicting with each other
	 * 
	 * @param possibleConflictingActivity the possible activity that could be
	 *                                    conflicting with another activity
	 * @throws ConflictException if the activity conflicts with another one in the
	 *                           schedule
	 */
	void checkConflict(Activity possibleConflictingActivity) throws ConflictException;

}
