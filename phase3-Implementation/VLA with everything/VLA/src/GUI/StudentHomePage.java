package GUI;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.text.ParseException;

import javax.swing.*;
import Data.Database;
import Exceptions.InvalidEmailException;
import Exceptions.InvalidFileFormatException;
import Exceptions.InvalidRatingValueException;
import LMS_data.LMS_API;
import TodoList.Task;
import Users.Student;

@SuppressWarnings("serial")
public class StudentHomePage extends JFrame  implements ActionListener{
	protected JLabel welcomeLabel, registeredCoursesLabel, creditsLimitReached;
	public JLabel notification;
	private JButton NoteAction, TaskAction, ReviewAction;
	private Student student;
	
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}

	public StudentHomePage(Student s) 
			throws FileNotFoundException, InvalidFileFormatException, NumberFormatException, ParseException, InvalidRatingValueException, InvalidEmailException {
		student = s;
		student.setEnrolledCourses(LMS_API.coursesRegisteredByStudent(s));
		student.setNotesList(Database.selectStudentNotes(s));
		student.setTasks(Database.selectStudentTasks(s));

		int nbOfIncompleteTasks = 0;
		for (Task t : student.getTasks())
			if (!t.isComplete())
				++nbOfIncompleteTasks;
		notification = new JLabel();
		notification.setBounds(30, 35, 380, 20);
		if (nbOfIncompleteTasks==0) {
			notification.setText("");
		}else {
			notification.setText("Check out your tasks in to do list .You have "+nbOfIncompleteTasks+" Incomplete tasks");
		}
		notification.setForeground(Color.red);
		this.add(notification);
		
		this.setTitle("Student home page");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.setSize(470, 280);
		this.setLayout(null);
		
		welcomeLabel = new JLabel();
		welcomeLabel.setBounds(150, 10, 150, 20);
		welcomeLabel.setText("Welcome "+student.getName());
		this.add(welcomeLabel);
		
		NoteAction = new JButton();
		NoteAction.setBounds(65, 60, 150, 60);
		NoteAction.addActionListener(this);
		this.add(NoteAction);
		
		TaskAction = new JButton();
		TaskAction.setBounds(245, 60, 150, 60);
		TaskAction.addActionListener(this);
		this.add(TaskAction);
		
		ReviewAction = new JButton();
		ReviewAction.setBounds(155, 150, 150, 60);
		ReviewAction.addActionListener(this);
		this.add(ReviewAction);

		NoteAction.setText("Note page");
		TaskAction.setText("To-Do list page");
		ReviewAction.setText("Review page");
		
		
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	public void actionPerformed(ActionEvent click) {
		if (click.getSource() == NoteAction) {
			try {
				enterNotesSection();
			} catch (NumberFormatException | InvalidEmailException e) {
				e.printStackTrace();
			}
		}
		if (click.getSource() == TaskAction) { 
			try {
				try {
					new ToDOLIST(student, this);
				} catch (InvalidEmailException e) {
					e.printStackTrace();
				}
			} catch (NumberFormatException | FileNotFoundException | ParseException | InvalidRatingValueException e) {
				e.printStackTrace();
			}
		}
		if (click.getSource() == ReviewAction) {
			new CreateOrViewReview(student, this);
		}
	}
	
	private void enterNotesSection() throws NumberFormatException, InvalidEmailException {
		new NotesPage(student, this);
	}
}