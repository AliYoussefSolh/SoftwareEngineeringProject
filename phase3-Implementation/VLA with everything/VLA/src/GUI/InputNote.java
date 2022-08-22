package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import Course.Course;
import Exceptions.InvalidEmailException;
import Exceptions.InvalidFileFormatException;
import Users.Student;
import LMS_data.LMS_API;
import NoteUploading.*;

@SuppressWarnings("serial")
public class InputNote extends JFrame implements ActionListener{
	private JLabel courseLabel, chapterLabel, modelLabel; 
	private JComboBox<String> courseNames, courseChapters;
	private JRadioButton privateButton, publicButton;
	private ButtonGroup bg;
	private JFileChooser fileChooser;
	private JButton chooseFile, uploadNote;
	private Student student;
	private NotesPage notespage;
	
	public InputNote(Student student, NotesPage notesPage) throws FileNotFoundException, NumberFormatException, InvalidEmailException {
		this.student = student;
		this.notespage = notesPage;
		this.setTitle("Note upload");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.setSize(500, 500);
		this.setLayout(null);
		notesPage.setVisible(false);
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				notesPage.setVisible(true);
		    }  
		});
		
		ArrayList<Course> courses = LMS_API.coursesRegisteredByStudent(student);
		String[] courseArray = new String[courses.size()];
		for (int i=0; i<courseArray.length; i++) 
			courseArray[i] = courses.get(i).getCourseTitle();
		
		courseNames = new JComboBox<String>(courseArray);
		courseNames.setBounds(200, 50, 160, 30); 
		this.add(courseNames);
		
		courseLabel = new JLabel();
		courseLabel.setText("Select course");
		courseLabel.setBounds(50, 50, 150, 30);
		this.add(courseLabel);
		
		String[] chapters = {"Chapter 1", "Chapter 2", "Chapter 3", "Chapter 4", "Chapter 5"};
		courseChapters = new JComboBox<String>(chapters);
		courseChapters.setBounds(200, 150, 160, 30); 
		this.add(courseChapters);
	
		chapterLabel = new JLabel();
		chapterLabel.setText("Select chapter");
		chapterLabel.setBounds(50, 150, 150, 30);
		this.add(chapterLabel);
	
		
		modelLabel = new JLabel();
		modelLabel.setText("Select privacy mode");
		modelLabel.setBounds(30, 350, 150, 25);
		this.add(modelLabel);
		
		chooseFile = new JButton();
		chooseFile.setText("Choose file to upload");
		chooseFile.setBounds(175, 250, 180, 40);
		chooseFile.addActionListener(this);
		this.add(chooseFile);
		fileChooser = new JFileChooser();
		
		privateButton = new JRadioButton("Private");    
		publicButton = new JRadioButton("Public");  
		privateButton.setBounds(270, 350, 70, 25);   
		publicButton.setBounds(190, 350, 80, 25);
		bg = new ButtonGroup();
		bg.add(privateButton);
		bg.add(publicButton);
		privateButton.setSelected(true);
		this.add(privateButton);
		this.add(publicButton);
				
		uploadNote = new JButton();
		uploadNote.setText("upload note");
		uploadNote.setBounds(135, 415, 180, 40);
		uploadNote.addActionListener(this);
		this.add(uploadNote);
		
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent click) {
		if (click.getSource() == chooseFile) {
			fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			fileChooser.setFileFilter(new TxtFileFilter());
			fileChooser.showOpenDialog(this);
		}
		else if(click.getSource() == uploadNote) {
			File selectedFile = fileChooser.getSelectedFile();
			if (selectedFile != null) {
				createNewFile(selectedFile);
				int i = courseNames.getSelectedIndex();
				try {
					Note n = new Note(selectedFile, student, student.getEnrolledCourses().get(i), publicButton.isSelected());
					NoteHandler nh = new NoteHandler(n);
					nh.uploadToDB();
					notespage.setVisible(true);
					this.dispose();
				} catch (InvalidFileFormatException | IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private static void createNewFile(File toCopy) {
		File newFile = new File(toCopy.getName());
		try {
			newFile.createNewFile();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Scanner sc = null;
		FileWriter fw = null;
		try {
			fw = new FileWriter(newFile);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			sc = new Scanner(toCopy);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String temp = "";
		while (sc.hasNextLine()) {
			temp = sc.nextLine();
			try {
				fw.write(temp + "\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		sc.close();
	}
	
}

class TxtFileFilter extends FileFilter {
    private String extension = ".txt";
    private String description = "Text File document";
 
    public TxtFileFilter() {
    }
 
    public boolean accept(File file) {
        if (file.isDirectory()) {
            return true;
        }
        return file.getName().endsWith(extension);
    }
 
    public String getDescription() {
        return description + String.format(" (*%s)", extension);
    }
}
