package edu.ncsu.csc216.pack_scheduler.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc217.collections.list.SortedList;

/**
 * Reads Students records from text files. Writes a studentDirectory list of students to a
 * file.
 * 
 * @author Grant Lewison
 * @author Fahad Ansari
 * @author Niyati Patel
 */
public class StudentRecordIO {

	/**
	 * Reads student records from a file and generates a list of valid Students. Any
	 * invalid Students are ignored. If the file to read cannot be found or the
	 * permissions are incorrect a File NotFoundException is thrown.
	 * @param fileName name of the file to read Students from
	 * @return studentDirectory of Students read from the given file
	 * @throws FileNotFoundException if the file cannot be found or read
	 */
	public static SortedList<Student> readStudentRecords(String fileName) throws FileNotFoundException {
		Scanner fileReader = new Scanner(new FileInputStream(fileName));
		SortedList<Student> studentDirectory = new SortedList<Student>();
		
		while(fileReader.hasNextLine()) {
			try {
				Student student = processStudent(fileReader.nextLine());
				
				studentDirectory.add(student);
				
			}
			catch(IllegalArgumentException e) {
				//Skip
			}
		}
		
		fileReader.close();
		
		return studentDirectory;
	}

	/**
	 * Writes the given list of Courses to the specific file given
	 * @param fileName name of the specific file that students should be recorded to
	 * @param studentDirectory sorted array list of all students from the file that was read
	 * @throws IOException if a failure occurs when writing to the file
	 */
	public static void writeStudentRecords(String fileName, SortedList<Student> studentDirectory) throws IOException {
		PrintStream fileWriter = new PrintStream(new File(fileName));
		
		for(int i = 0; i < studentDirectory.size(); i++) {
			fileWriter.println(studentDirectory.get(i).toString());
		}
		
		fileWriter.close();
		
	}
	
	/**
	 * Read individual line of a specific student and create a student object with the
	 * specific information on the line.
	 * @param line line of a specific student from the file
	 * @throws IllegalArgumentException if a specific element is not found in the file
	 * @return newStudent specific student that was processed from one line in the file
	 */
	private static Student processStudent(String line) {
		try (Scanner scan = new Scanner(line)) {
			scan.useDelimiter(",");
			try {
				String firstName = scan.next();
				String lastName = scan.next();
				String id = scan.next();
				String email = scan.next();
				String password = scan.next();
				int maxCredits = scan.nextInt();
				
				Student newStudent = new Student(firstName, lastName, id, email, password, maxCredits);
				return newStudent;
			}
			catch(NoSuchElementException e) {
				throw new IllegalArgumentException();
			}
		}
	}

}
