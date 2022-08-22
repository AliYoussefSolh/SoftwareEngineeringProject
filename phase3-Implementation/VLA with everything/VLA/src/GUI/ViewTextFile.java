package GUI;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.*;

import NoteUploading.Note;

@SuppressWarnings("serial")
public class ViewTextFile extends JFrame {
	private Note note;
	private JScrollPane display;
	
	public ViewTextFile(Note n, NotesPage notesPage) throws FileNotFoundException {
		note = n;
		this.setTitle("View notes");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setResizable(true);
		this.setSize(300, 200);
		notesPage.setVisible(false);
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				notesPage.setVisible(true);
		    }  
		});
		
		JTextArea textArea = new JTextArea();
		File textFile = note.getDoc();
		String textToDisplay = "";
		Scanner sc = new Scanner(textFile);
		while (sc.hasNextLine()) 
			textToDisplay += (sc.nextLine() + "\n");
		sc.close();
		textArea.setText(textToDisplay);
		display = new JScrollPane(textArea);
		this.getContentPane().add(display, BorderLayout.CENTER);
		this.setVisible(true);
	}
}



