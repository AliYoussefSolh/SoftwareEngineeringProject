package Course;

import java.util.ArrayList;

import Users.*;

public class Course {
	private Instructor professor;
	private ArrayList<Student> students;
	private int courseID;
	private String courseTitle;
	
	public Course() {
		students = new ArrayList<Student>();
	}
	
	public Course(Instructor i, int courseID, String courseTitle) {
		setProfessor(i);
		setCourseID(courseID);
		setCourseTitle(courseTitle);
	}
	public Course(Instructor i, int courseID, String courseTitle,ArrayList<Student> list) {
		setProfessor(i);
		setCourseID(courseID);
		setCourseTitle(courseTitle);
		setStudents(list);
	}
	
	public Instructor getProfessor() {
		return professor;
	}

	public void setProfessor(Instructor professor) {
		this.professor = professor;
	}

	public String getCourseTitle() {
		return courseTitle;
	}

	public void setCourseTitle(String courseTitle) {
		this.courseTitle = courseTitle;
	}
	
	public ArrayList<Student> getStudents() {
		return students;
	}
	
	public void addStudent(Student s) {
		students.add(s);
	}
	
	public void setStudents(ArrayList<Student> students) {
		this.students = students;
	}

	public int getCourseID() {
		return courseID;
	}

	public void setCourseID(int courseID) {
		this.courseID = courseID;
	}

}
