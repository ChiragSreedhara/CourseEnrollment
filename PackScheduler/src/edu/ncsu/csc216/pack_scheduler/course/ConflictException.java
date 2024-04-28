/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.course;

/**
 * Exception that is created to be thrown if there are conflicts
 * 
 * @author Fahad Ansari
 * @author Niyati Patel
 */
public class ConflictException extends Exception {

	/** ID used for serialization. */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor that creates a conflict exception with a specific message
	 * 
	 * @param message the message that should be displayed upon throwing the
	 *                exception
	 */
	public ConflictException(String message) {
		super(message);
	}

	/**
	 * Constructor that creates a conflict exception with a default message that is
	 * displayed when the exception is thrown
	 */
	public ConflictException() {
		this("Schedule conflict.");
	}

}
