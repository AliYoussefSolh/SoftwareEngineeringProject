package Tests;

import static org.junit.Assert.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import org.junit.jupiter.api.Test;
import Course.Course;
import Exceptions.InvalidEmailException;
import LMS_data.LMS_API;
import Users.Administrator;
import Users.Instructor;
import Users.Student;

public class LMSAPIMethodsTests {

	@Test
	void TestGetStudentByID() throws NumberFormatException, FileNotFoundException, InvalidEmailException {
		String id = "202004410";
		Student studentOfAppMethod = LMS_API.getStudent(id);
		Scanner scan = new Scanner(new File("./src/LMS_data/Students.txt"));
		Student expectedStudent = null;
		while (scan.hasNextLine()) {
			String s = scan.nextLine();
			String[] line = s.split("\\|");
			if (line[0].equals(id)) {
				expectedStudent = new Student(line[1], Integer.parseInt(line[0]), line[2]);
				break;
			}
		}
		assertEquals(expectedStudent.getID(), studentOfAppMethod.getID());
		assertEquals(expectedStudent.getName(), studentOfAppMethod.getName());
		assertEquals(expectedStudent.getEmail(), studentOfAppMethod.getEmail());
		assertEquals(expectedStudent.getEnrolledCourses(), studentOfAppMethod.getEnrolledCourses());
	}

	// assert null if the id of student did not exist
	@Test
	void TestGetStudentByIDNullCase() throws NumberFormatException, FileNotFoundException, InvalidEmailException {
		String id = "200905510"; // id not exist
		// student returned by the methods
		Student studentOfAppMethod = LMS_API.getStudent(id);
		assertNull(studentOfAppMethod);
	}

	@Test
	void TestGetInstructorByID() throws NumberFormatException, FileNotFoundException, InvalidEmailException {
		String id = "202004405";
		// student returned by the methods
		Instructor instructorOfAppMethod = LMS_API.getInstructor(id);
		Instructor expectedInstructor = null;
		Scanner scan = new Scanner(new File("./src/LMS_data/Instructors.txt"));
		while (scan.hasNextLine()) {
			String s = scan.nextLine();
			String[] line = s.split("\\|");
			if (line[0].equals(id)) {
				expectedInstructor = new Instructor(line[1], Integer.parseInt(line[0]), line[2]);
				break;
			}
		}
		assertEquals(expectedInstructor.getID(), instructorOfAppMethod.getID());
		assertEquals(expectedInstructor.getName(), instructorOfAppMethod.getName());
		assertEquals(expectedInstructor.getEmail(), instructorOfAppMethod.getEmail());
		assertEquals(expectedInstructor.getCourseTaught(), instructorOfAppMethod.getCourseTaught());
	}

	// assert null if the id of instructor did not exist
	@Test
	void TestGetInstrcutorByIDNullCase() throws NumberFormatException, FileNotFoundException, InvalidEmailException {
		String id = "200905510"; // id not exist
		// student returned by the methods
		Instructor instructorOfAppMethod = LMS_API.getInstructor(id);
		assertNull(instructorOfAppMethod);
	}
	
	@Test
	void TestGetCourseByID() throws NumberFormatException, FileNotFoundException, InvalidEmailException {
		String id = "111494";
		// student returned by the methods
		Course courseOfAppMethod = LMS_API.getCourse(id);
		Course expectedCourse = null;
		Scanner scan = new Scanner(new File("./src/LMS_data/Courses.txt"));		
		while (scan.hasNextLine()) {
			String s = scan.nextLine();
			String[] line = s.split("\\|");
			if (line[0].equals(id)) {
				String[] studentsRegistered=line[3].split(",");
				ArrayList<Student> list = new ArrayList<Student>();
				for (int i=0;i<studentsRegistered.length;i++) 
					list.add(LMS_API.getStudent(studentsRegistered[i])); //tested above
				expectedCourse = new Course(LMS_API.getInstructor(line[2]), Integer.parseInt(line[0]), line[1], list);
				break;//getInstructor tested above
			}			
		}
		assertEquals(expectedCourse.getProfessor().getID(), courseOfAppMethod.getProfessor().getID());
		assertEquals(expectedCourse.getCourseID(), courseOfAppMethod.getCourseID());
		assertEquals(expectedCourse.getCourseTitle(), courseOfAppMethod.getCourseTitle());
		for (int i = 0; i < expectedCourse.getStudents().size(); i++) { 
			assertEquals(expectedCourse.getStudents().get(i).getID(), courseOfAppMethod.getStudents().get(i).getID());
		}
	}
	
	@Test
	void TestGetCourseByIDNullCase() throws NumberFormatException, FileNotFoundException, InvalidEmailException {
		String id = "200905510"; // id not exist
		// student returned by the methods
		Course courseOfAppMethod = LMS_API.getCourse(id);
		assertNull(courseOfAppMethod);
	}
	
	@Test
	void TestGetAdminByID() throws NumberFormatException, FileNotFoundException, InvalidEmailException {
		String id = "202000";
		// student returned by the methods
		Administrator adminOfAppMethod = LMS_API.getAdmin(id);
		Scanner scan = new Scanner(new File("./src/LMS_data/Admins.txt"));
		Administrator expectedAdmin = null;
		while (scan.hasNextLine()) {
			String s = scan.nextLine();
			String[] line = s.split("\\|");
			if (line[0].equals(id)) {
				expectedAdmin =new Administrator(line[1],Integer.parseInt(line[0]),line[2]);
				break;
			}		
		}	
		assertEquals(expectedAdmin.getID(), adminOfAppMethod.getID());
		assertEquals(expectedAdmin.getName(), adminOfAppMethod.getName());
		assertEquals(expectedAdmin.getEmail(), adminOfAppMethod.getEmail());
	}
	
	@Test
	void TestGetAdminByIDNullCase() throws NumberFormatException, FileNotFoundException, InvalidEmailException {
		String id = "200905510"; // id not exist
		// admin returned by the methods
		Administrator adminOfAppMethod = LMS_API.getAdmin(id);
		assertNull(adminOfAppMethod);
	}
	
	@Test
	void TestGetCourseByName() throws NumberFormatException, FileNotFoundException, InvalidEmailException {
		String name = "Software Engineering";
		Course courseOfAppMethod = LMS_API.getCourseFromName(name);
		Course expectedCourse = null;
		Scanner scan = new Scanner(new File("./src/LMS_data/Courses.txt"));
		while (scan.hasNextLine()) {
			String s = scan.nextLine();
			String[] line = s.split("\\|");
			if (line[1].equals(name)) {
				String[] studentsRegistered=line[3].split(",");
				ArrayList<Student> list = new ArrayList<Student>();
				for (int i=0;i<studentsRegistered.length;i++) 
					list.add(LMS_API.getStudent(studentsRegistered[i])); //tested before
				expectedCourse =new Course(LMS_API.getInstructor(line[2]), Integer.parseInt(line[0]), line[1],list);
				break;
			}			
		}
		//id is unique
		assertEquals(expectedCourse.getProfessor().getID(), courseOfAppMethod.getProfessor().getID());
		assertEquals(expectedCourse.getCourseID(), courseOfAppMethod.getCourseID());
		assertEquals(expectedCourse.getCourseTitle(), courseOfAppMethod.getCourseTitle());
		
	}
	
	@Test
	void TestGetCourseByNameNullCase() throws NumberFormatException, FileNotFoundException, InvalidEmailException {
		String name = "No course has this name"; // id not exist
		// student returned by the methods
		Course courseOfAppMethod = LMS_API.getCourseFromName(name);
		assertNull(courseOfAppMethod);
	}
	
	@Test
	void TestGetcoursesTaughtByInstructor() throws NumberFormatException, FileNotFoundException, InvalidEmailException {
		Instructor instructor = LMS_API.getInstructor("202004405");  //tested before
		ArrayList<Course> coursesReturnedByTheMethod = LMS_API.coursesTaughtByInstructor(instructor);
		Scanner scan = new Scanner(new File("./src/LMS_data/Courses.txt"));
		ArrayList<Course> coursesExpected = new ArrayList<Course>();
		while (scan.hasNextLine()) {
			String s = scan.nextLine();
			String[] line = s.split("\\|");
			String courseID = line[0];
			int currCourseInstructor= Integer.parseInt(line[2]);
			if (instructor.getID() == currCourseInstructor) {
				coursesExpected.add(LMS_API.getCourse(courseID));
			}
		}
		for (int i=0;i<coursesReturnedByTheMethod.size();i++) {
			assertEquals(coursesReturnedByTheMethod.get(i).getCourseID(),coursesExpected.get(i).getCourseID());
			//if is unique
		}		
	}
	
	@Test
	void Test_GetcoursesregisteredByStudent() throws NumberFormatException, FileNotFoundException, InvalidEmailException {
		Student student = LMS_API.getStudent("202004410"); //tested before
		Scanner scan = new Scanner(new File("./src/LMS_data/Courses.txt"));
		ArrayList<Course> coursesReturnedByTheMethod = LMS_API.coursesRegisteredByStudent(student);
		int studentID = student.getID();
		ArrayList<Course> coursesExpected = new ArrayList<Course>();
		while (scan.hasNextLine()) {
			String s = scan.nextLine();
			String[] line = s.split("\\|");
			String[] studentsRegisteredIDs = line[3].split(",");
			for (String str : studentsRegisteredIDs) {
				if (Integer.parseInt(str) == studentID)
					coursesExpected.add(LMS_API.getCourse(line[0])); //tested before
			}
		}
		for (int i=0;i<coursesReturnedByTheMethod.size();i++) {
			assertEquals(coursesReturnedByTheMethod.get(i).getCourseID(),coursesExpected.get(i).getCourseID());
		}	
	}
	
}
