package Tests;

import static org.junit.Assert.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import org.junit.jupiter.api.Test;
import Course.Course;
import Data.Database;
import Exceptions.InvalidEmailException;
import Exceptions.InvalidFileFormatException;
import Exceptions.InvalidRatingValueException;
import LMS_data.LMS_API;
import NoteUploading.Note;
import Reviewing.Rating;
import Reviewing.Review;
import TodoList.Task;
import Users.Instructor;
import Users.Student;


public class ParseMethodsTests {
	
	@Test
	void TestParseTask() throws NumberFormatException, FileNotFoundException, ParseException, InvalidRatingValueException, InvalidEmailException {
		String line=String.join(" | ", "1234", "text", "12-01-202", "false", "202004410");
		Task gotByMethodTask=Database.parseTask(line);
		String[] args = line.split(" \\| ");
		int id = Integer.parseInt(args[0]);
		String text = args[1];
		String d = args[2];
		Student s = LMS_API.getStudent(args[4]);	//getstudenBy id is tested befpre	
		Task expectedTask = new Task(text, d, s);
		expectedTask.setTaskID(id);
		expectedTask.setComplete(Boolean.valueOf(args[3]));
		assertEquals(expectedTask.getTaskID(),gotByMethodTask.getTaskID());
		assertEquals(expectedTask.getTaskText(),gotByMethodTask.getTaskText());
		assertEquals(expectedTask.getDeadline(),gotByMethodTask.getDeadline());
	}
	
	@Test
	void TestParseNote() throws NumberFormatException, FileNotFoundException, ParseException, InvalidRatingValueException, InvalidEmailException, InvalidFileFormatException {
		String line = String.join(" | ", "00000", "ali.txt",
				"202004405", "1234",
				"false", "false");
		Note gotByMethodNote=Database.parseNote(line);
		String[] args = line.split(" \\| ");
		int noteID = Integer.parseInt(args[0]);
		File doc = new File(args[1]);
		Student s = LMS_API.getStudent(args[2]);
		Course c = LMS_API.getCourse(args[3]);
		boolean isPublic = Boolean.parseBoolean(args[4]);
		boolean isApproved = Boolean.parseBoolean(args[5]);	//getstudenBy id is tested before	
	    Note expectedNote = new Note(doc, s, c, isPublic);
	    expectedNote.setApproved(isApproved);
	    expectedNote.setNoteID(noteID);
		assertEquals(expectedNote.getNoteID(),gotByMethodNote.getNoteID());
		assertEquals(expectedNote.getDoc().getName(),gotByMethodNote.getDoc().getName());
		assertEquals(expectedNote.isApproved(),gotByMethodNote.isApproved());
	}

	@Test
	void TestParseReview() throws NumberFormatException, FileNotFoundException, ParseException, InvalidRatingValueException, InvalidEmailException {
		String line=String.join(" | ", "1111","text", "1-01-2022",
				"1,1,1,1", "202004405",
				"202004410", "11490",
				"false");
		Review gotByMethodReview=Database.parseReview(line);
		String[] args = line.split(" \\| ");
		int id = Integer.parseInt(args[0]);
		String text = args[1];
		String date = args[2];
		String ratingStr = args[3];
		String[] values = ratingStr.split(",");
		Rating[] ratings = { new Rating("Explanation", (int) Double.parseDouble(values[0])),
				new Rating("Grading", Integer.parseInt(values[1])),
				new Rating("Communication", Integer.parseInt(values[2])),
				new Rating("Flexibility", Integer.parseInt(values[3])) };
		Instructor i = LMS_API.getInstructor(args[4]);
		Student std = LMS_API.getStudent(args[5]);
		Course crs = LMS_API.getCourse(args[6]);
		boolean b = Boolean.parseBoolean(args[7]);
		Review expected_Review = new Review(text, date, ratings, i, std, crs, b);
		expected_Review.setID(id);
		assertEquals(expected_Review.getAuthor().getID(),gotByMethodReview.getAuthor().getID());
		assertEquals(expected_Review.getReviewText(),gotByMethodReview.getReviewText());
		assertEquals(expected_Review.getID(),gotByMethodReview.getID());
	}

}