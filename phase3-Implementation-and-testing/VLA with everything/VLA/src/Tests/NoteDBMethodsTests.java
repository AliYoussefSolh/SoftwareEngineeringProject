package Tests;

import static org.junit.Assert.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;
import LMS_data.LMS_API;
import org.junit.jupiter.api.Test;
import Course.Course;
import Data.Database;
import Exceptions.InvalidEmailException;
import Exceptions.InvalidFileFormatException;
import Exceptions.InvalidRatingValueException;
import NoteUploading.Note;
import Users.Student;

public class NoteDBMethodsTests {
	
	@Test
	void checkInsertNote() throws NumberFormatException, InvalidEmailException, 
	InvalidFileFormatException, IOException {
		File testFile = new File("TestNoteFile.txt");
		Course c = LMS_API.getCourse("111490");
		Student s = LMS_API.getStudent("202004410");
		Note n = new Note(testFile, s, c , false);
		n.setNoteID(420987);
		String expAddedLineOnFile = String.join(" | ",
				Integer.toString(n.getNoteID()),
				n.getDoc().getName(),
				Integer.toString(n.getAuthor().getID()),
				Integer.toString(n.getCourse().getCourseID()),
				Boolean.toString(n.isPublic()),
				Boolean.toString(n.isApproved()));
		Database.insertNote(n);
		//check if note added to the file by reading last line
		Scanner scan = new Scanner(new File("./src/Data/Notes.txt"));
		String lineWrittenToFile = "";
		while (scan.hasNextLine()) 
			lineWrittenToFile = scan.nextLine();
		scan.close();
		assertEquals(expAddedLineOnFile, lineWrittenToFile);
	}
	
	@Test
    void CheckReadingGetAllTasksIDsNotNull() throws FileNotFoundException {
    	ArrayList<Integer> methodList = Database.getAllNoteIDs();
    	assertNotNull(methodList);
    }
	
	@Test
    void CheckReadingGetAllTasksIDs() throws FileNotFoundException {
    	Scanner scan = new Scanner(new File("./src/Data/Notes.txt"));
    	ArrayList<Integer> expectedList = new ArrayList<Integer>();
    	while (scan.hasNext()) {
			String line = scan.nextLine();
			String[] args = line.split(" | ");
			expectedList.add(Integer.parseInt(args[0]));
		}
    	ArrayList<Integer> methodList = Database.getAllNoteIDs();
    	assertEquals(expectedList, methodList);
    }
	    
    @Test
    void CheckGetNotesByStudent() throws FileNotFoundException, NumberFormatException, ParseException, InvalidRatingValueException,
    InvalidEmailException, InvalidFileFormatException {
    	Scanner scan = new Scanner(new File("./src/Data/Notes.txt"));
    	ArrayList<Note> expectedList = new ArrayList<Note>();
    	Student student = LMS_API.getStudent("202004410");
		int studentID = student.getID();
		while (scan.hasNext()) {
			Note n = Database.parseNote(scan.nextLine());
			if (n.getAuthor().getID() == studentID)
				expectedList.add(n);
		}
    	ArrayList<Note> methodList = Database.selectStudentNotes(student);
    	for (int i=0; i<methodList.size(); i++) {
    		assertEquals(expectedList.get(i).getDoc().getName(), methodList.get(i).getDoc().getName());
    		assertEquals(expectedList.get(i).getNoteID(), methodList.get(i).getNoteID());
    		assertEquals(expectedList.get(i).getAuthor().getID(), methodList.get(i).getAuthor().getID());
    		assertEquals(expectedList.get(i).getCourse().getCourseID(), methodList.get(i).getCourse().getCourseID());
    		assertEquals(expectedList.get(i).isPublic(), methodList.get(i).isPublic());
    	}
    }
    
    @Test
    void CheckGetNotesByCourse() throws FileNotFoundException, NumberFormatException, ParseException, InvalidRatingValueException,
    InvalidEmailException, InvalidFileFormatException {
    	Scanner scan = new Scanner(new File("./src/Data/Notes.txt"));
    	ArrayList<Note> expectedList = new ArrayList<Note>();
    	Course course = LMS_API.getCourse("111490");
		int courseID = course.getCourseID();
		while (scan.hasNext()) {
			Note n = Database.parseNote(scan.nextLine());
			if (n.getCourse().getCourseID() == courseID)
				expectedList.add(n);
		}
    	ArrayList<Note> methodList = Database.selectCourseNotes(course);
    	for (int i=0; i<methodList.size(); i++) {
    		assertEquals(expectedList.get(i).getDoc().getName(), methodList.get(i).getDoc().getName());
    		assertEquals(expectedList.get(i).getNoteID(), methodList.get(i).getNoteID());
    		assertEquals(expectedList.get(i).getAuthor().getID(), methodList.get(i).getAuthor().getID());
    		assertEquals(expectedList.get(i).getCourse().getCourseID(), methodList.get(i).getCourse().getCourseID());
    		assertEquals(expectedList.get(i).isPublic(), methodList.get(i).isPublic());
    	}
    }
    
    @Test
    void CheckModifyingNoteApproval() 
    throws IOException, InvalidEmailException, NumberFormatException, ParseException, InvalidRatingValueException, InvalidFileFormatException  {
    	String idTest = "1021472702"; //taken from file and has state false
    	Scanner scan = new Scanner(new File("./src/Data/Notes.txt")); 
 		String gettingNoteFromDB = ""; 
 		Note n = null;
 		//initially is complete==false; modify to true
 		while (scan.hasNextLine()) {
 			gettingNoteFromDB = scan.nextLine(); 
 			if (gettingNoteFromDB.split(" \\| ")[0].equals(idTest)) {
 			    n = Database.parseNote(gettingNoteFromDB);
 			    break;
 			}
 		}
 		scan.close(); 

 		Database.modifyNoteApproval(n, true);
 		
 		Scanner sc = new Scanner(new File("./src/Data/Notes.txt"));
 		String modifiedNoteInDB = "";
 		while (sc.hasNextLine()) {
 			modifiedNoteInDB = sc.nextLine();
 			if (modifiedNoteInDB.split(" \\| ")[0].equals(idTest)) {
 			    n = Database.parseNote(modifiedNoteInDB);
 			    break;
 			}
 		}
 		sc.close();
 		assertEquals("true", modifiedNoteInDB.split(" \\| ")[5]);
     }
}
