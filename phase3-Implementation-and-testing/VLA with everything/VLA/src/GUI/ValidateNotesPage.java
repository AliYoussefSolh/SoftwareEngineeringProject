package GUI;

import Users.Instructor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.*;
import Course.Course;
import Data.Database;
import Exceptions.InvalidEmailException;
import Exceptions.InvalidFileFormatException;
import NoteUploading.Note;

@SuppressWarnings("serial")
public class ValidateNotesPage extends JFrame {
	private Instructor instructor; 
	private JLabel head, nothingHere;
	private ImageIcon checked, unchecked;
	JCheckBox[] isValidNote = null;
	public ValidateNotesPage(Instructor i, InstructorHomePage instructorHomePage) throws NumberFormatException, InvalidEmailException{
		this.instructor = i;

		this.setTitle("Validate notes Page");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setResizable(false);
		instructorHomePage.setVisible(false);
		
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
		
		if (notesToValidate.size() == 0) {
			JPanel noAssignments = new JPanel(new GridLayout(1, 1));
			noAssignments.setPreferredSize(new Dimension(400, 200));
			nothingHere = new JLabel("No notes yet....", SwingConstants.CENTER);
			nothingHere.setFont(new Font(null, Font.PLAIN, 14));
			noAssignments.add(nothingHere);
			this.add(noAssignments, BorderLayout.SOUTH);
			this.pack();
		}
		else {
			JPanel header = new JPanel(new GridLayout(1, 1));
			header.setPreferredSize(new Dimension(400, 40));
			head = new JLabel("Notes to validate:", SwingConstants.CENTER);
			head.setFont(new Font(null, Font.ROMAN_BASELINE, 14));
			head.setForeground(Color.blue);
			head.setBounds(0, 10, 500, 30);
			header.add(head);
			this.add(header, BorderLayout.NORTH);
			this.pack();
			checked = new ImageIcon("./src/GUI/true.png");
			unchecked = new ImageIcon("./src/GUI/false.png");
			isValidNote = new JCheckBox[notesToValidate.size()];
			JPanel panel = new JPanel(new GridLayout((notesToValidate.size() % 2 == 0 ? notesToValidate.size() /2 : notesToValidate.size()/2 + 1), 2));
			for (int j = 0; j < notesToValidate.size(); j++) {
				File noteDoc = notesToValidate.get(j).getDoc();
				String noteContent = "";
				Scanner sc = null;
				try {
					sc = new Scanner(noteDoc);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				while (sc.hasNextLine()) 
					noteContent += (sc.nextLine() + "\n");
				sc.close();
				String description = "<html>" + noteContent.replace("\n", "<br>");
				isValidNote[j] = new JCheckBox(description);
				isValidNote[j].setIcon(unchecked);
				isValidNote[j].setSelectedIcon(checked);
				panel.add(isValidNote[j]);
				isValidNote[j].setSelected(notesToValidate.get(j).isApproved());
			}
			this.add(panel, BorderLayout.SOUTH);
			this.pack();
		}
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				for (int i = 0; i< notesToValidate.size(); i++) {
					try {
						Database.modifyNoteApproval(notesToValidate.get(i), isValidNote[i].isSelected());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				instructorHomePage.setVisible(true);
		    }  
		});
						
		this.setVisible(true);
	}

}
