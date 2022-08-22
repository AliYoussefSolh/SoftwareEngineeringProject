package Users;

import java.io.File;
import java.util.ArrayList;
import Course.Course;
import Exceptions.InvalidEmailException;
import Exceptions.InvalidRatingValueException;
import TodoList.Task;
import NoteUploading.Note;
import Reviewing.*;

public class Student extends User{
	private ArrayList<Course> enrolledCourses;
	private ArrayList<Task> tasks;
	private ArrayList<Note> notesList;
	
	public Student(){
		super();
		enrolledCourses = new ArrayList<Course>();
		tasks = new ArrayList<Task>();
		notesList = new ArrayList<Note>();
	}
	
	public Student(String name, int ID, String email) throws InvalidEmailException {
		super(name, ID, email);
		enrolledCourses = new ArrayList<Course>();
		tasks = new ArrayList<Task>();
		notesList = new ArrayList<Note>();
	}
	
	public ArrayList<Course> getEnrolledCourses() {
		return enrolledCourses;
	}

	public void setEnrolledCourses(ArrayList<Course> enrolledCourses) {
		this.enrolledCourses = enrolledCourses;
	}
	
	public ArrayList<Task> getTasks() {
		return tasks;
	}

	public void setTasks(ArrayList<Task> tasks) {
		this.tasks = tasks;
	}

	public ArrayList<Note> getNotesList() {
		return notesList;
	}

	public void setNotesList(ArrayList<Note> notesList) {
		this.notesList = notesList;
	}
	
	public void writeReview(Review r, String s) {
		r.setReviewText(s);
	}
	
	public void rateInstructor(Review rev, Rating rating) {
		for (Rating r : rev.getRatings()) {
			if(r.getSkill().equals(rating.getSkill())) {
				try {
					r.setValue(rating.getValue());
				} catch (InvalidRatingValueException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void specifyDeadline(Task task, String deadline) {
		task.setDeadline(deadline);
	}
	
	public void specifyContent(Task task, String taskDescription) {
		task.setTaskText(taskDescription);
	}
	
	public void setAsComplete(Task task, boolean b) {
		task.setComplete(b);
	}
	
	public String selectChapter() {
		//TODO
		return null;
	}
	
	public String selectSection() {
		//TODO
		return null;
	}
	
	public File selectDocument() {
		//handled in GUI
		return null;
	}
	
	public void createReview(Review review) {
		//handled in GUI
	}
	
	public void enterToDoListWindow() {
		//handled in GUI
	}
	
	public void enterNotesWindow() {
		//handled in GUI
	}
	
	public void enterReviewsWindow() {
		//handled in GUI
	}
	
}
