package edu.ncsu.csc216.pack_scheduler.course.validator;

/**
 * Finite State Machine for checking whether a Course's Name is valid.
 * 
 * @author Chirag Sreedhara
 * @author Fahad Ansari
 * @author Ryan Stauffer
 */
public class CourseNameValidator {

	/**
	 * Stores if valid end state has been met
	 */
	private boolean validEndState;
	/**
	 * Count of letters
	 */
	private int letterCount;
	/**
	 * Count of digits
	 */
	private int digitCount;
	/**
	 * State for current number
	 */
	private State stateNumber;
	/**
	 * State for current letter
	 */
	private State stateLetter;
	/**
	 * Value for initial state
	 */
	private State stateInitial;
	/**
	 * State for suffix
	 */
	private State stateSuffix;
	/**
	 * The current existing state
	 */
	private State currentState;

	/**
	 * Constructor method
	 */
	public CourseNameValidator() {
		stateNumber = new NumberState();
		stateLetter = new LetterState();
		stateInitial = new InitialState();
		stateSuffix = new SuffixState();
		currentState = stateInitial;
		validEndState = false;
		letterCount = 0;
		digitCount = 0;
	};

	/**
	 * Abstract state class for extension by subclasses
	 * 
	 * @author Chirag Sreedhara
	 * @author Fahad Ansari
	 * @author Ryan Stauffer
	 */
	public abstract class State {

		/**
		 * Constructor method
		 */
		public State() {
			// Default constructor
		}

		/**
		 * Abstract method for when letter is encountered
		 * 
		 * @throws InvalidTransitionException if transition is invalid
		 */
		public abstract void onLetter() throws InvalidTransitionException;

		/**
		 * Abstract method for when digit is encountered
		 * 
		 * @throws InvalidTransitionException if transition is invalid
		 */
		public abstract void onDigit() throws InvalidTransitionException;

		/**
		 * Abstract method for when non-digit/letter is encountered
		 * 
		 * @throws InvalidTransitionException if transition is invalid
		 */
		public void onOther() throws InvalidTransitionException {
			throw new InvalidTransitionException("Course name can only contain letters and digits.");
		}
	}

	/**
	 * State in for handling any letters found
	 * 
	 * @author Chirag Sreedhara
	 * @author Fahad Ansari
	 * @author Ryan Stauffer
	 */
	public class LetterState extends State {

		/**
		 * Maximum number prefix letters
		 */
		private static final int MAX_PREFIX_LETTERS = 4;

		/**
		 * Construcor method
		 */
		private LetterState() {

		}

		/**
		 * Method for if on letter and new letter is recieved
		 * 
		 * @throws InvalidTransitionException if invalid name
		 */
		@Override
		public void onLetter() throws InvalidTransitionException {
			letterCount++;
			if (letterCount > MAX_PREFIX_LETTERS && !validEndState) {
				throw new InvalidTransitionException("Course name cannot start with more than 4 letters.");
			}
		}

		/**
		 * Method if on letter and digit is recieved
		 */
		@Override
		public void onDigit() {
			digitCount++;
			currentState = stateNumber;
		}

	}

	/**
	 * State in for handling if there are any additional letter at end of string
	 * found
	 * 
	 * @author Chirag Sreedhara
	 * @author Fahad Ansari
	 * @author Ryan Stauffer
	 */
	public class SuffixState extends State {

		/**
		 * Constructor method
		 */
		private SuffixState() {

		}

		/**
		 * Method if on suffix and letter recieved
		 * 
		 * @throws InvalidTransitionException if invalid name
		 */
		@Override
		public void onLetter() throws InvalidTransitionException {
			throw new InvalidTransitionException("Course name can only have a 1 letter suffix.");

		}

		/**
		 * Method if on suffix and digit recieved
		 * 
		 * @throws InvalidTransitionException if invalid name
		 */
		@Override
		public void onDigit() throws InvalidTransitionException {
			throw new InvalidTransitionException("Course name cannot contain digits after the suffix.");

		}

	}

	/**
	 * Starter state for
	 * 
	 * @author Chirag Sreedhara
	 * @author Fahad Ansari
	 * @author Ryan Stauffer
	 */
	public class InitialState extends State {

		/**
		 * Constructor method
		 */
		private InitialState() {

		}

		/**
		 * Method if starting with letter
		 */
		@Override
		public void onLetter() {
			letterCount++;
			currentState = stateLetter;
		}

		/**
		 * Method if starting with digit
		 * 
		 * @throws InvalidTransitionException if invalid name
		 */
		@Override
		public void onDigit() throws InvalidTransitionException {
			throw new InvalidTransitionException("Course name must start with a letter.");
		}

	}

	/**
	 * State in for handling any digitss found
	 * 
	 * @author Chirag Sreedhara
	 * @author Fahad Ansari
	 * @author Ryan Stauffer
	 */
	public class NumberState extends State {
		/**
		 * Max length of nums
		 */
		private static final int COURSE_NUBER_LENGTH = 3;

		/**
		 * Constructor
		 */
		private NumberState() {

		}

		/**
		 * Method if you have digit and recieve letter
		 * 
		 * @throws InvalidTransitionException if invalid name
		 */
		@Override
		public void onLetter() throws InvalidTransitionException {
			if (digitCount != 3) {
				throw new InvalidTransitionException("Course name must have 3 digits.");
			}
			currentState = stateSuffix;

		}

		/**
		 * Method if you have digit and recieve digit
		 * 
		 * @throws InvalidTransitionException if invalid name
		 */
		@Override
		public void onDigit() throws InvalidTransitionException {
			digitCount++;
			if (digitCount > COURSE_NUBER_LENGTH) {
				throw new InvalidTransitionException("Course name can only have 3 digits.");
			}
			if (digitCount == COURSE_NUBER_LENGTH) {
				validEndState = true;
			}
		}

	}

	/**
	 * Returns true if the course name is valid, based on a string matching Finite
	 * State Machine.
	 * 
	 * The course name must match the following format: (1-4 letters)(3
	 * digits)(optionally, a 1 letter suffix)
	 * 
	 * @param courseName the name of the course
	 * @return true if the course name is valid, or false if the course name is
	 *         invalid
	 * @throws InvalidTransitionException when the FSM attempts an invalid
	 *                                    transition
	 */
	public boolean isValid(String courseName) throws InvalidTransitionException {
		validEndState = false;
		letterCount = 0;
		digitCount = 0;
		this.currentState = stateInitial;
		// Set the state field to be the initial state

		for (char c : courseName.toCharArray()) {
			if (Character.isLetter(c)) {
				currentState.onLetter();
			} else if (Character.isDigit(c)) {
				currentState.onDigit();
			} else {
				currentState.onOther();
			}
		}

		// Iterate through the ID, examining one character at a time
		return validEndState;
	}
}
