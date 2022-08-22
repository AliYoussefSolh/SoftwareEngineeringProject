package LMS_data;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import Course.Course;
import Exceptions.InvalidEmailException;
import Users.*;

public class LMS_API{
	
	public static Student getStudent(String id) throws FileNotFoundException, NumberFormatException, InvalidEmailException {
		Scanner scan = new Scanner(new File("./src/LMS_data/Students.txt"));
		while (scan.hasNextLine()) {
			String s = scan.nextLine();
			String[] line = s.split("\\|");
			if (line[0].equals(id)) 
				return new Student(line[1],Integer.parseInt(line[0]),line[2]);
		}
		return null;
	}
	
	public static Instructor getInstructor(String id) throws FileNotFoundException, NumberFormatException, InvalidEmailException {
		Scanner scan = new Scanner(new File("./src/LMS_data/Instructors.txt"));
		while (scan.hasNextLine()) {
			String s = scan.nextLine();
			String[] line = s.split("\\|");
			if (line[0].equals(id)) 
				return new Instructor(line[1],Integer.parseInt(line[0]),line[2]);
		}
		return null;
	}
	
	public static Course getCourse(String id) throws FileNotFoundException, NumberFormatException, InvalidEmailException {
		Scanner scan = new Scanner(new File("./src/LMS_data/Courses.txt"));		
		while (scan.hasNextLine()) {
			String s = scan.nextLine();
			String[] line = s.split("\\|");
			if (line[0].equals(id)) {
				String[] studentsRegistered=line[3].split(",");
				ArrayList<Student> list = new ArrayList<Student>();
				for (int i=0;i<studentsRegistered.length;i++) 
					list.add(getStudent(studentsRegistered[i]));
				return new Course(getInstructor(line[2]), Integer.parseInt(line[0]), line[1],list);
			}			
		}
		return null;	
	}
	
	public static Administrator getAdmin(String id) throws FileNotFoundException, NumberFormatException, InvalidEmailException {
		Scanner scan = new Scanner(new File("./src/LMS_data/Admins.txt"));
		while (scan.hasNextLine()) {
			String s = scan.nextLine();
			String[] line = s.split("\\|");
			if (line[0].equals(id)) 
				return new Administrator(line[1],Integer.parseInt(line[0]),line[2]);
		}
		return null;
	}
	
	public static Course getCourseFromName(String name) throws FileNotFoundException, NumberFormatException, InvalidEmailException {
		Scanner scan = new Scanner(new File("./src/LMS_data/Courses.txt"));		
		while (scan.hasNextLine()) {
			String s = scan.nextLine();
			String[] line = s.split("\\|");
			if (line[1].equals(name)) {
				String[] studentsRegistered=line[3].split(",");
				ArrayList<Student> list = new ArrayList<Student>();
				for (int i=0;i<studentsRegistered.length;i++) 
					list.add(getStudent(studentsRegistered[i]));
				return new Course(getInstructor(line[2]), Integer.parseInt(line[0]), line[1],list);
			}			
		}
		return null;	
	}
	
	public static ArrayList<Course> coursesTaughtByInstructor(Instructor instructor) throws FileNotFoundException, NumberFormatException, InvalidEmailException{
		Scanner scan = new Scanner(new File("./src/LMS_data/Courses.txt"));
		ArrayList<Course> courses = new ArrayList<Course>();
		while (scan.hasNextLine()) {
			String s = scan.nextLine();
			String[] line = s.split("\\|");
			String courseID = line[0];
			int currCourseInstructor= Integer.parseInt(line[2]);
			if (instructor.getID() == currCourseInstructor) {
				courses.add(getCourse(courseID));
			}
		}
		scan.close();
		return courses;
	}

	public static ArrayList<Course> coursesRegisteredByStudent(Student student) throws FileNotFoundException, NumberFormatException, InvalidEmailException{
		Scanner scan = new Scanner(new File("./src/LMS_data/Courses.txt"));
		ArrayList<Course> courses = new ArrayList<Course>();
		int studentID = student.getID();
		while (scan.hasNextLine()) {
			String s = scan.nextLine();
			String[] line = s.split("\\|");
			String[] studentsRegisteredIDs = line[3].split(",");
			for (String str : studentsRegisteredIDs) {
				if (Integer.parseInt(str) == studentID)
					courses.add(getCourse(line[0]));
			}
		}
		scan.close();
		return courses;
	}
	
}
