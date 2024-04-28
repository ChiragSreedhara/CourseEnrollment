package edu.ncsu.csc216.pack_scheduler.manager;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Properties;

import edu.ncsu.csc216.pack_scheduler.catalog.CourseCatalog;
import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.course.roll.CourseRoll;
import edu.ncsu.csc216.pack_scheduler.directory.FacultyDirectory;
import edu.ncsu.csc216.pack_scheduler.directory.StudentDirectory;
import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc216.pack_scheduler.user.User;
import edu.ncsu.csc216.pack_scheduler.user.schedule.Schedule;

/**
 * singleton manager class for Student and Course directories
 * 
 * @author Ryan Stauffer
 * @author Chirag Sreedhara
 * @author Fahad Ansari
 * @author David Martinez
 */
public class RegistrationManager {

	/**
	 * singleton alive instance of the manager
	 */
	private static RegistrationManager instance;
	/**
	 * the managed course catalog
	 */
	private CourseCatalog courseCatalog;
	/**
	 * student directory managed by the catalog
	 */
	private StudentDirectory studentDirectory;
	/**
	 * Faculty directory managed by catalog
	 */
	private FacultyDirectory facultyDirectory;
	/**
	 * the registrar to manage
	 */
	private User registrar;
	/**
	 * the current logged in user
	 */
	private User currentUser;
	/** Hashing algorithm */
	private static final String HASH_ALGORITHM = "SHA-256";
	/**
	 * file the registrar info is stored in
	 */
	private static final String PROP_FILE = "registrar.properties";

	/**
	 * Constructor for registration manager
	 */
	private RegistrationManager() {
		createRegistrar();
		this.courseCatalog = new CourseCatalog();
		this.studentDirectory = new StudentDirectory();
		this.facultyDirectory = new FacultyDirectory();
	}

	/**
	 * Creates a new registrar
	 * 
	 * @throws IllegalArgumentException if registrar can't be made due to
	 *                                  IOException.
	 */
	private void createRegistrar() {
		Properties prop = new Properties();

		try (InputStream input = new FileInputStream(PROP_FILE)) {
			prop.load(input);

			String hashPW = hashPW(prop.getProperty("pw"));

			registrar = new Registrar(prop.getProperty("first"), prop.getProperty("last"), prop.getProperty("id"),
					prop.getProperty("email"), hashPW);
		} catch (IOException e) {
			throw new IllegalArgumentException("Cannot create registrar.");
		}
	}

	/**
	 * Encodes (hashes) given password
	 * 
	 * @param pw password to be hashed
	 * @return hashed version of pw
	 * @throws IllegalArgumentException if pw can't be hashed.
	 */
	private String hashPW(String pw) {
		try {
			MessageDigest digest1 = MessageDigest.getInstance(HASH_ALGORITHM);
			digest1.update(pw.getBytes());
			return Base64.getEncoder().encodeToString(digest1.digest());
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException("Cannot hash password");
		}
	}

	/**
	 * singleton entry point for the Registration Manager
	 * 
	 * @return the single instance of the Registration manager
	 */
	public static RegistrationManager getInstance() {
		if (instance == null) {
			instance = new RegistrationManager();
		}
		return instance;
	}

	/**
	 * Adds faculty to course (given)
	 * 
	 * @param c course to add
	 * @param f faculty to add to
	 * @return if successful
	 * @throws IllegalArgumentException if not valid roles.
	 */
	public boolean addFacultyToCourse(Course c, Faculty f) {
		if (currentUser != null && currentUser == registrar) {
			f.getSchedule().addCourseToSchedule(c);
			return true;
		}
		throw new IllegalArgumentException();
	}

	/**
	 * Removes course from faculty
	 * 
	 * @param c course to remove
	 * @param f faculty to remove from
	 * @return if successful
	 * @throws IllegalArgumentException if not valid roles.
	 */
	public boolean removeFacultyFromCourse(Course c, Faculty f) {
		if (currentUser != null && currentUser == registrar) {
			f.getSchedule().removeCourseFromSchedule(c);
			return true;
		}
		throw new IllegalArgumentException();
	}

	/**
	 * Resets a given faculty's schedule
	 * 
	 * @param f faculty to remove from
	 * @throws IllegalArgumentException if not valid roles.
	 */
	public void resetFacultySchedule(Faculty f) {
		if (currentUser != null && currentUser == registrar) {
			f.getSchedule().resetSchedule();
		} else {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * getter for the course catalog
	 * 
	 * @return the course catalog
	 */
	public CourseCatalog getCourseCatalog() {
		return courseCatalog;
	}

	/**
	 * getter for the student directory
	 * 
	 * @return the student directory
	 */
	public StudentDirectory getStudentDirectory() {
		return studentDirectory;
	}

	/**
	 * getter for faculty directory
	 * 
	 * @return the faculty directory field
	 */
	public FacultyDirectory getFacultyDirectory() {
		return facultyDirectory;
	}

	/**
	 * log a user in
	 * 
	 * @param id       of user trying to log in
	 * @param password of the user trying to log in
	 * @return true if the user successfully logs in, otherwise false
	 * 
	 * @throws IllegalArgumentException if the user specified in the input does not
	 *                                  exist
	 */
	public boolean login(String id, String password) {
		if (id == null || password == null || currentUser != null) {
			return false;
		}
		String localHashPW = hashPW(password);
		System.err.println();
		if (registrar != null && registrar.getId().equals(id)) {
			if (registrar.getPassword().equals(localHashPW)) {
				currentUser = registrar;
				return true;
			} else {
				return false;
			}
		}

		Student s = studentDirectory.getStudentById(id);
		Faculty f = facultyDirectory.getFacultyById(id);
		if (s != null && s.getPassword().equals(localHashPW)) {
			currentUser = s;
			return true;
		} else if (f == null && s == null) {
			throw new IllegalArgumentException("User doesn't exist.");
		} else if (f != null && f.getPassword().equals(localHashPW)) {
			currentUser = f;
			return true;
		}

		return false;
	}

	/**
	 * logs the current user out
	 */
	public void logout() {
		currentUser = null;
	}

	/**
	 * getter for the current user
	 * 
	 * @return the current user
	 */
	public User getCurrentUser() {
		return currentUser;
	}

	/**
	 * clears the current data, resetting the course and student catalogs
	 */
	public void clearData() {
		courseCatalog.newCourseCatalog();
		studentDirectory.newStudentDirectory();
		facultyDirectory.newFacultyDirectory();
	}

	/**
	 * Registrar class that acts is an extension of the abstract class User
	 * 
	 * @author Ryan Stauffer
	 * @author Chirag Sreedhara
	 * @author Fahad Ansari
	 */
	private static class Registrar extends User {
		/**
		 * Create a registrar user.
		 * 
		 * @param firstName firstName of the user
		 * @param lastName  lastName of the user
		 * @param id        id of the user
		 * @param email     email of the user
		 * @param hashPW    password of the user
		 */
		public Registrar(String firstName, String lastName, String id, String email, String hashPW) {
			super(firstName, lastName, id, email, hashPW);
		}
	}

	/**
	 * Returns true if the logged in student can enroll in the given course.
	 * 
	 * @param c Course to enroll in
	 * @return true if enrolled
	 * @throws IllegalArgumentException if student is invalid
	 */
	public boolean enrollStudentInCourse(Course c) {
		if (!(currentUser instanceof Student)) {
			throw new IllegalArgumentException("Illegal Action");
		}
		try {
			Student s = (Student) currentUser;
			Schedule schedule = s.getSchedule();
			CourseRoll roll = c.getCourseRoll();

			if (s.canAdd(c) && roll.canEnroll(s)) {
				schedule.addCourseToSchedule(c);
				roll.enroll(s);
				return true;
			}

		} catch (IllegalArgumentException e) {
			return false;
		}
		return false;
	}

	/**
	 * Returns true if the logged in student can drop the given course.
	 * 
	 * @param c Course to drop
	 * @return true if dropped
	 * @throws IllegalArgumentException if student is invalid
	 */
	public boolean dropStudentFromCourse(Course c) {
		if (!(currentUser instanceof Student)) {
			throw new IllegalArgumentException("Illegal Action");
		}
		try {
			Student s = (Student) currentUser;
			c.getCourseRoll().drop(s);
			return s.getSchedule().removeCourseFromSchedule(c);
		} catch (IllegalArgumentException e) {
			return false;
		}
	}

	/**
	 * Resets the logged in student's schedule by dropping them from every course
	 * and then resetting the schedule.
	 * 
	 * @throws IllegalArgumentException if exception encountered.
	 */
	public void resetSchedule() {
		if (!(currentUser instanceof Student)) {
			throw new IllegalArgumentException("Illegal Action");
		}
		try {
			Student s = (Student) currentUser;
			Schedule schedule = s.getSchedule();
			String[][] scheduleArray = schedule.getScheduledCourses();
			for (int i = 0; i < scheduleArray.length; i++) {
				Course c = courseCatalog.getCourseFromCatalog(scheduleArray[i][0], scheduleArray[i][1]);
				c.getCourseRoll().drop(s);
			}
			schedule.resetSchedule();
		} catch (IllegalArgumentException e) {
			// do nothing
		}
	}
}
