package NoteUploading;

import java.io.IOException;
import Data.Database;
import Interfaces.*;
import Users.Student;

public class NoteHandler implements Uploadable, Notifier{

	private Student student;
	private Note note;	
	
	public NoteHandler(Note n){
		note = n;
	}
	
	@Override
	public void notifyUser() {
		//handled in GUI
	}

	@Override
	public void uploadToDB() throws IOException {
		Database.insertNote(note);
	}
	
	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Note getNote() {
		return note;
	}

	public void setNote(Note note) {
		this.note = note;
	}
}
