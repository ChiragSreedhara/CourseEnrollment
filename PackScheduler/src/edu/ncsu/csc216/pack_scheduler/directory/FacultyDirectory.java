package edu.ncsu.csc216.pack_scheduler.directory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import edu.ncsu.csc216.pack_scheduler.io.FacultyRecordIO;
import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.util.LinkedList;

/**
 * Maintains a directory of all Faculty enrolled at NC State. All students have
 * a unique id.
 * 
 * @author David Martinez
 * @author Chirag Sreedhara
 */
public class FacultyDirectory {

	/** List of faculty in the directory */
	private LinkedList<Faculty> facultyDirectory;
	/** Hashing algorithm */
	private static final String HASH_ALGORITHM = "SHA-256";

	/**
	 * Creates an empty faculty directory.
	 */
	public FacultyDirectory() {
		newFacultyDirectory();
	}

	/**
	 * Creates an empty faculty directory. All students in the previous list are
	 * list unless saved by the user.
	 */
	public void newFacultyDirectory() {
		facultyDirectory = new LinkedList<Faculty>();
	}

	/**
	 * Constructs the student directory by reading in faculty information from the
	 * given file. Throws an IllegalArgumentException if the file cannot be found.
	 * 
	 * @param fileName file containing list of students
	 * @throws IllegalArgumentException if file can't be read.
	 */
	public void loadFacultyFromFile(String fileName) {
		try {
			facultyDirectory = FacultyRecordIO.readFacultyRecords(fileName);
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("Unable to read file " + fileName);
		}
	}

	/**
	 * Adds a faculty to the directory. Returns true if the faculty is added and
	 * false if the faculty is unable to be added because their id matches another
	 * faculty id.
	 * 
	 * This method also hashes the student's password for internal storage.
	 * 
	 * @param firstName      faculty first name
	 * @param lastName       faculty last name
	 * @param id             faculty id
	 * @param email          faculty email
	 * @param password       faculty password
	 * @param repeatPassword faculty repeated password
	 * @param maxCredits     faculty max credits.
	 * @return true if added
	 * @throws IllegalArgumentException if error is found in creating a student
	 *                                  object or adding it to the list
	 */
	public boolean addFaculty(String firstName, String lastName, String id, String email, String password,
			String repeatPassword, int maxCredits) {
		String hashPW = "";
		String repeatHashPW = "";
		if (password == null || repeatPassword == null || "".equals(password) || "".equals(repeatPassword)) {
			throw new IllegalArgumentException("Invalid password");
		}

		hashPW = hashString(password);
		repeatHashPW = hashString(repeatPassword);

		if (!hashPW.equals(repeatHashPW)) {
			throw new IllegalArgumentException("Passwords do not match");
		}

		// If an IllegalArgumentException is thrown, it's passed up from Student
		// to the GUI
		Faculty student = null;
		student = new Faculty(firstName, lastName, id, email, hashPW, maxCredits);

		for (int i = 0; i < facultyDirectory.size(); i++) {
			Faculty s = facultyDirectory.get(i);
			if (s.getId().equals(student.getId())) {
				return false;
			}
		}
		return facultyDirectory.add(student);
	}

	/**
	 * Hashes a String according to the SHA-256 algorithm, and outputs the digest in
	 * base64 encoding. This allows the encoded digest to be safely copied, as it
	 * only uses [a-zA-Z0-9+/=].
	 * 
	 * @param toHash the String to hash
	 * @return the encoded digest of the hash algorithm in base64
	 * @throws IllegalArgumentException if NoSuchAlgorithmException is found.
	 */
	private static String hashString(String toHash) {
		try {
			MessageDigest digest1 = MessageDigest.getInstance(HASH_ALGORITHM);
			digest1.update(toHash.getBytes());
			return Base64.getEncoder().encodeToString(digest1.digest());
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException("Cannot hash password");
		}
	}

	/**
	 * Removes the faculty with the given id from the list of students with the
	 * given id. Returns true if the student is removed and false if the faculty is
	 * not in the list.
	 * 
	 * @param studentId faculty id
	 * @return true if removed
	 */
	public boolean removeFaculty(String studentId) {
		for (int i = 0; i < facultyDirectory.size(); i++) {
			Faculty s = facultyDirectory.get(i);
			if (s.getId().contains(studentId)) {
				facultyDirectory.remove(i);
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns all faculty in the directory with a column for first name, last name,
	 * and id.
	 * 
	 * @return String array containing faculty first name, last name, and id.
	 */
	public String[][] getFacultyDirectory() {
		String[][] directory = new String[facultyDirectory.size()][3];
		for (int i = 0; i < facultyDirectory.size(); i++) {
			Faculty s = facultyDirectory.get(i);
			directory[i][0] = s.getFirstName();
			directory[i][1] = s.getLastName();
			directory[i][2] = s.getId();
		}
		return directory;
	}

	/**
	 * Saves all faculty in the directory to a file.
	 * 
	 * @param fileName name of file to save faculty to.
	 * @throws IllegalArgumentException if file can't be written to
	 */
	public void saveFacultyDirectory(String fileName) {
		try {
			FacultyRecordIO.writeFacultyRecords(fileName, facultyDirectory);
		} catch (IOException e) {
			throw new IllegalArgumentException("Unable to write to file " + fileName);
		}
	}

	/**
	 * Finds Faculty by id
	 * 
	 * @param id id to look for
	 * @return Matching faculty given id
	 */
	public Faculty getFacultyById(String id) {

		for (int i = 0; i < facultyDirectory.size(); i++) {
			if (facultyDirectory.get(i).getId().equalsIgnoreCase(id)) {
				return facultyDirectory.get(i);
			}
		}

		return null;
	}

}
