package NoteUploading;

import java.io.File;
import Users.Student;
import Course.Course;
import Exceptions.InvalidFileFormatException;

public class Note {
	private File doc;
	private Student author;
	private Course course;
	private boolean isPublic;
	private boolean isApproved = false;
	private int noteID;
	
	public Note() {
		
	}
	public Note(File doc, Student s, Course c, boolean isPublic) throws InvalidFileFormatException {
		setDoc(doc);
		setAuthor(s);
		setCourse(c);
		setPublic(isPublic);
	}
	
	public File getDoc() {
		return doc;
	}
	
	public void setDoc(File doc) throws InvalidFileFormatException {
		if (doc != null && doc.getName().length() >= 4 && 
				doc.getName().substring(doc.getName().length() - 4).equals(".txt"))
			this.doc = doc;
		else throw new InvalidFileFormatException();
	}	
	
	public void setAuthor(Student author) {
		this.author = author;
	}
	public Student getAuthor() {
		return author;
	}
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}
	public boolean isPublic() {
		return isPublic;
	}
	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}
	public boolean isApproved() {
		return isApproved;
	}
	public void setApproved(boolean isApproved) {
		this.isApproved = isApproved;
	}
	public int getNoteID() {
		return noteID;
	}
	public void setNoteID(int noteID) {
		this.noteID = noteID;
	}
	
	public String toString() {
		return String.format("%s %s %s %d %b %b", doc.getName(), author.getName(), 
				course.getCourseTitle(), noteID, isPublic, isApproved);
	}
}
