package edu.ncsu.csc216.pack_scheduler.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.util.LinkedList;

/**
 * Reads Faculty records from text files. Writes a FacultyDirectory list of
 * students to a file.
 * 
 * @author David Martinez
 * @author Chirag Sreedhara
 */
public class FacultyRecordIO {

	/**
	 * Reads student records from a file and generates a list of valid Students. Any
	 * invalid Students are ignored. If the file to read cannot be found or the
	 * permissions are incorrect a File NotFoundException is thrown.
	 * 
	 * @param fileName name of the file to read Students from
	 * @return studentDirectory of Students read from the given file
	 * @throws FileNotFoundException if the file cannot be found or read
	 */
	public static LinkedList<Faculty> readFacultyRecords(String fileName) throws FileNotFoundException {
		Scanner fileReader = new Scanner(new FileInputStream(fileName));
		LinkedList<Faculty> studentDirectory = new LinkedList<Faculty>();

		while (fileReader.hasNextLine()) {
			try {
				Faculty student = processFaculty(fileReader.nextLine());

				studentDirectory.add(student);

			} catch (IllegalArgumentException e) {
				// Skip
			}
		}

		fileReader.close();

		return studentDirectory;
	}

	/**
	 * Writes the given list of Courses to the specific file given
	 * 
	 * @param fileName         name of the specific file that students should be
	 *                         recorded to
	 * @param studentDirectory sorted array list of all students from the file that
	 *                         was read
	 * @throws IOException if a failure occurs when writing to the file
	 */
	public static void writeFacultyRecords(String fileName, LinkedList<Faculty> studentDirectory) throws IOException {
		PrintStream fileWriter = new PrintStream(new File(fileName));

		for (int i = 0; i < studentDirectory.size(); i++) {
			fileWriter.println(studentDirectory.get(i).toString());
		}

		fileWriter.close();

	}

	/**
	 * Read individual line of a specific student and create a student object with
	 * the specific information on the line.
	 * 
	 * @param line line of a specific student from the file
	 * @throws IllegalArgumentException if a specific element is not found in the
	 *                                  file
	 * @return newStudent specific student that was processed from one line in the
	 *         file
	 */
	private static Faculty processFaculty(String line) {
		try (Scanner scan = new Scanner(line)) {
			scan.useDelimiter(",");
			try {
				String firstName = scan.next();
				String lastName = scan.next();
				String id = scan.next();
				String email = scan.next();
				String password = scan.next();
				int maxCredits = scan.nextInt();

				Faculty newStudent = new Faculty(firstName, lastName, id, email, password, maxCredits);
				return newStudent;
			} catch (NoSuchElementException e) {
				throw new IllegalArgumentException();
			}
		}
	}

}
