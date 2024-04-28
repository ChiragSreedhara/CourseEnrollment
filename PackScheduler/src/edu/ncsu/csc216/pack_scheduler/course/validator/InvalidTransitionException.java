/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.course.validator;

/**
 * Error state to represent a Finite State Machine's invalid representation.
 */
public class InvalidTransitionException extends Exception {

	/**
	 * Field that stores valid lettering for name
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * default error message inplementation of InvalidTransitionExcepiton
	 */
	public InvalidTransitionException() {
		this("Invalid FSM Transition.");
	}

	/**
	 * error with custom message constructor
	 * 
	 * @param errMsg message to pass up the parent error chain
	 */
	public InvalidTransitionException(String errMsg) {
		super(errMsg);
	}

}
