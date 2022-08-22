package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import javax.swing.*;
import Course.Course;
import Data.Database;
import Exceptions.InvalidEmailException;
import Exceptions.InvalidFileFormatException;
import NoteUploading.Note;
import Users.Student;

@SuppressWarnings("serial")
public class NotesPage extends JFrame implements ActionListener{
	private Student student;
	private JLabel personalNotes, courseNotes;
	private JComboBox<String> personalDocNames;
	private JComboBox<String> courseDocNames;
	private JButton personalButton, courseButton, addNote;
	private ArrayList<Note> publicNotes, privateNotes;
	
	public NotesPage(Student s, StudentHomePage studentHomePage) throws NumberFormatException, InvalidEmailException {
		student = s;
		
		this.setTitle("Notes section");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.setSize(600, 320);
		this.setLayout(null);
		studentHomePage.setVisible(false);
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				studentHomePage.setVisible(true);
		    }  
		});
		
		privateNotes = student.getNotesList();
		String[] docNames = new String[privateNotes.size()]; 
		for (int i = 0; i < privateNotes.size(); i++) 
			docNames[i] = privateNotes.get(i).getDoc().getName();
		
		personalNotes = new JLabel();
		personalNotes.setBounds(160, 20, 100, 20);
		personalNotes.setText("personal notes");
		this.add(personalNotes);
		
		personalDocNames = new JComboBox<String>(docNames);
		personalDocNames.setBounds(160, 50, 100, 30); 
		this.add(personalDocNames);
		
		personalButton = new JButton();
		personalButton.setText("Open Note");
		personalButton.setBounds(160, 100, 100, 30);
		personalButton.addActionListener(this);
		this.add(personalButton);
		
		
		publicNotes = new ArrayList<Note>();
		for (Course c : student.getEnrolledCourses()) {
			ArrayList<Note> courseNotes = new ArrayList<Note>();
			try {
				courseNotes = Database.selectCourseNotes(c);
			} catch (FileNotFoundException | InvalidFileFormatException e) {
				e.printStackTrace();
			}
			if (courseNotes.size() != 0) {
				for (Note n : courseNotes)
					if (n.isPublic() && n.isApproved())
						publicNotes.add(n);
			}
		}
		
		courseNotes = new JLabel();
		courseNotes.setBounds(340, 20, 100, 20);
		courseNotes.setText("course notes");
		this.add(courseNotes);
		
		String[] courseDocsStr = new String[publicNotes.size()];
		for (int i = 0; i<publicNotes.size(); i++) 
			courseDocsStr[i] = publicNotes.get(i).getDoc().getName() + " (" + publicNotes.get(i).getCourse().getCourseTitle()+ ")";
		
		courseDocNames = new JComboBox<String>(courseDocsStr);
		courseDocNames.setBounds(340, 50, 150, 30); 
		this.add(courseDocNames);
		
		courseButton = new JButton();
		courseButton.setText("Open Note");
		courseButton.setBounds(340, 100, 100, 30);
		courseButton.addActionListener(this);
		this.add(courseButton);
		
		addNote = new JButton();
		addNote.setText("Upload note");
		addNote.setBounds(250, 160, 120, 40);
		addNote.addActionListener(this);
		this.add(addNote);
		
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent click) {
		if (click.getSource() == courseButton) {
			int indexSelected = courseDocNames.getSelectedIndex();
			try {
				new ViewTextFile(publicNotes.get(indexSelected), this);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		else if (click.getSource() == personalButton) {
			int indexSelected = personalDocNames.getSelectedIndex();
			try {
				new ViewTextFile(privateNotes.get(indexSelected), this);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		else if (click.getSource() == addNote) {
			try {
				try {
					new InputNote(student, this);
				} catch (NumberFormatException | InvalidEmailException e) {
					e.printStackTrace();
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
}
