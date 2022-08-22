package GUI;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;
import javax.swing.*;
import Course.Course;
import Data.Database;
import Exceptions.InvalidEmailException;
import Exceptions.InvalidFileFormatException;
import Exceptions.InvalidRatingValueException;
import NoteUploading.Note;
import Users.Instructor;

@SuppressWarnings("serial")
public class InstructorHomePage extends JFrame implements ActionListener{
	protected JLabel welcomeLabel, teachingCourse, noCourseSelected, successfullPublish, notification;
	private JButton ViewReviewAction, ValidateNoteAction;
	private Instructor instructor;
	
	public InstructorHomePage(Instructor user) throws FileNotFoundException, NumberFormatException, InvalidEmailException {
		instructor = user;
		instructor.setCourseTaught(LMS_data.LMS_API.coursesTaughtByInstructor(instructor));
		
		this.setTitle("Instructor home page");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.setSize(450, 170);
		this.setLayout(null);
		
		ArrayList<Note> notesToValidate = new ArrayList<Note>();
		for (Course c : instructor.getCourseTaught()) {
			ArrayList<Note> currCourseNotes = new ArrayList<Note>();
			try {
				currCourseNotes = Database.selectCourseNotes(c);
			} catch (FileNotFoundException | InvalidFileFormatException e) {
				e.printStackTrace();
			}
			if (currCourseNotes.size() != 0) {
				for (Note n : currCourseNotes) 
					if (n.isPublic()) 
						notesToValidate.add(n);
			}
		}
		
		welcomeLabel = new JLabel();
		welcomeLabel.setBounds(150, 10, 150, 20);
		welcomeLabel.setText("Welcome "+instructor.getName());
		this.add(welcomeLabel);
		
		notification = new JLabel();
		notification.setBounds(30, 35, 380, 20);
		if (notesToValidate.size()==0) {
			notification.setText("");
		}else {
			notification.setText("You have "+notesToValidate.size()+" notifications. Check our the notes you have to validate");
		}
		
		notification.setForeground(Color.red);
		this.add(notification);
		
		ViewReviewAction = new JButton();
		ViewReviewAction.setText("View Reviews");
		ViewReviewAction.setBounds(30, 80, 150, 35);
		ViewReviewAction.addActionListener(this);
		this.add(ViewReviewAction);
		
		ValidateNoteAction = new JButton();
		ValidateNoteAction.setText("Validate Notes");
		ValidateNoteAction.setBounds(235, 80, 150, 35);
		ValidateNoteAction.addActionListener(this);
		this.add(ValidateNoteAction);
		
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	public Instructor getInstructor() {
		return instructor;
	}

	public void setInstructor(Instructor instructor) {
		this.instructor = instructor;
	}

	public void actionPerformed(ActionEvent click) {
		if (click.getSource() == ViewReviewAction) {
			try {
				try {
					new ViewReviewInstructorPage(instructor, this);
				} catch (InvalidEmailException e) {
					e.printStackTrace();
				}
			} catch (NumberFormatException | FileNotFoundException | ParseException | InvalidRatingValueException e) {
				e.printStackTrace();
			}
		}
		if (click.getSource() == ValidateNoteAction) {
			try {
				new ValidateNotesPage(instructor, this);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (InvalidEmailException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}