package Data;

import Users.Student;
import Users.Instructor;
import Reviewing.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import NoteUploading.Note;
import TodoList.Task;
import Course.Course;
import Exceptions.InvalidEmailException;
import Exceptions.InvalidFileFormatException;
import Exceptions.InvalidRatingValueException;
import LMS_data.LMS_API;

public class Database {

	// ----------------------------------------------------------------------------------
	// Review related queries
	// ----------------------------------------------------------------------------------

	private static final String ReviewTable = "./src/Data/Reviews.txt";

	// helper functions
	private static ArrayList<Integer> getAllReviewIDs() throws FileNotFoundException {
		ArrayList<Integer> IDs = new ArrayList<Integer>();
		Scanner sc = new Scanner(new File(ReviewTable));
		while (sc.hasNext()) {
			String line = sc.nextLine();
			String[] args = line.split(" | ");
			IDs.add(Integer.parseInt(args[0]));
		}
		sc.close();
		return IDs;
	}

	private static boolean isValidReviewID(int newID) throws FileNotFoundException {
		ArrayList<Integer> IDs = getAllReviewIDs();
		for (int id : IDs)
			if (id == newID)
				return false;
		return true;
	}

	private static String ratingsToString(Rating[] ratings) {
		String output = "";
		for (Rating r : ratings)
			output += (Integer.toString((int) r.getValue()) + ",");
		return output.substring(0, output.length() - 1);
	}
	public static Review parseReview(String line)
			throws ParseException, NumberFormatException, InvalidRatingValueException, FileNotFoundException, InvalidEmailException {
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
		Review r = new Review(text, date, ratings, i, std, crs, b);
		r.setID(id);
		return r;
	}

	// Queries
	public static void insertReview(Review r) throws IOException {
		Random rand = new Random();
		int newID;
		do {
			newID = rand.nextInt(Integer.MAX_VALUE);
		} while ((!isValidReviewID(newID)));
		if (r.getID()==0)
		     r.setID(newID);
		FileWriter fw = new FileWriter(ReviewTable, true);
		String lineToWrite = String.join(" | ", Integer.toString(r.getID()), r.getReviewText(), r.getDateTimePosted(),
				ratingsToString(r.getRatings()), Integer.toString(r.getInstructor().getID()),
				Integer.toString(r.getAuthor().getID()), Integer.toString(r.getCourse().getCourseID()),
				Boolean.toString(r.isValid())) + "\n";
		fw.write(lineToWrite);
		fw.close();
	}

	public static ArrayList<Review> getAllReviews()
			throws FileNotFoundException, NumberFormatException, ParseException, InvalidRatingValueException, InvalidEmailException {
		ArrayList<Review> reviews = new ArrayList<Review>();
		Scanner sc = new Scanner(new File(ReviewTable));
		while (sc.hasNext())
			reviews.add(parseReview(sc.nextLine()));
		sc.close();
		return reviews;
	}

	public static ArrayList<Review> selectInstructorReviews(Instructor i)
			throws FileNotFoundException, NumberFormatException, ParseException, InvalidRatingValueException, InvalidEmailException {
		ArrayList<Review> reviews = new ArrayList<Review>();
		Scanner sc = new Scanner(new File(ReviewTable));
		while (sc.hasNext()) {
			Review r = parseReview(sc.nextLine());
			if (r.getInstructor().getID() == i.getID()) 
				reviews.add(r);
		}
		sc.close();
		return reviews;
	}

	public static void deleteReview(Review r)
			throws IOException, NumberFormatException, ParseException, InvalidRatingValueException, InvalidEmailException {
		RandomAccessFile file = new RandomAccessFile(ReviewTable, "rw");
	    String delete;
	    String task="";
	    while ((delete = file.readLine()) != null) {
	    	if (r.getID() == parseReview(delete).getID()) 
	    		continue;
            task += delete + "\n";
	    }  
        BufferedWriter writer = new BufferedWriter(new FileWriter(ReviewTable));
        writer.write(task);
        file.close();
        writer.close();
	}
	
	public static void setReviewValidation(Review r, boolean b) throws IOException {
		Scanner sc = new Scanner(new File(ReviewTable));
		StringBuffer buffer = new StringBuffer();
		while (sc.hasNext())
			buffer.append(sc.nextLine() + System.lineSeparator());
		sc.close();
		String fileContents = buffer.toString();
		String lineToModify = String.join(" | ", Integer.toString(r.getID()), r.getReviewText(), r.getDateTimePosted(),
				ratingsToString(r.getRatings()), Integer.toString(r.getInstructor().getID()),
				Integer.toString(r.getAuthor().getID()), Integer.toString(r.getCourse().getCourseID()),
				Boolean.toString(r.isValid()));
				 
		String newLine = String.join(" | ", Integer.toString(r.getID()), r.getReviewText(), r.getDateTimePosted(),
				ratingsToString(r.getRatings()), Integer.toString(r.getInstructor().getID()),
				Integer.toString(r.getAuthor().getID()), Integer.toString(r.getCourse().getCourseID()),
				Boolean.toString(b));
		fileContents = fileContents.replace(lineToModify, newLine);
		FileWriter writer = new FileWriter(ReviewTable);
		writer.append(fileContents);
		writer.flush();
		writer.close();
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// task related queries
	
	private static final String TaskTable = "./src/Data/Tasks.txt";

	//helper functions
	public static Task parseTask(String line)
			throws ParseException, 
			NumberFormatException, InvalidRatingValueException, FileNotFoundException, InvalidEmailException {
		String[] args = line.split(" \\| ");
		int id = Integer.parseInt(args[0]);
		String text = args[1];
		String d = args[2];
		Student s = LMS_API.getStudent(args[4]);		
		Task t = new Task(text, d, s);
		t.setTaskID(id);
		t.setComplete(Boolean.valueOf(args[3]));
		return t;
	}
	
	public static ArrayList<Integer> getAllTaskIDs() throws FileNotFoundException {
		ArrayList<Integer> IDs = new ArrayList<Integer>();
		Scanner sc = new Scanner(new File(TaskTable));
		while (sc.hasNext()) {
			String line = sc.nextLine();
			String[] args = line.split(" | ");
			IDs.add(Integer.parseInt(args[0]));
		}
		sc.close();
		return IDs;
	}
	private static boolean isValidTaskID(int newID) throws FileNotFoundException {
		ArrayList<Integer> IDs = getAllTaskIDs();
		for (int id : IDs)
			if (newID == id)
				return false;
		return true;
	}
	
	public static void insertTask(Task t) throws IOException {
		if (t.getTaskID()==0) {
			Random rand = new Random();
			int newID;
			do {
				newID = rand.nextInt(Integer.MAX_VALUE);
			} while ((!isValidTaskID(newID)));
			t.setTaskID(newID);
		}
		FileWriter fw = new FileWriter(TaskTable, true);
		String lineToWrite = String.join(" | ", 
				Integer.toString(t.getTaskID()), 
				t.getTaskText(), 
				t.getDeadline().toString(),
				Boolean.toString(t.isComplete()),
				Integer.toString(t.getStudent().getID())) + "\n";
		fw.write(lineToWrite);
		fw.close();	
	}

	public static void modifyTaskCompleteness(Task t, boolean b) 
			throws NumberFormatException, IOException, ParseException, InvalidRatingValueException {
		Scanner sc = new Scanner(new File(TaskTable));
		StringBuffer buffer = new StringBuffer();
		while (sc.hasNext())
			buffer.append(sc.nextLine() + System.lineSeparator());
		sc.close();
		String fileContents = buffer.toString();
		String lineToModify = String.join(" | ", Integer.toString(t.getTaskID()), 
				t.getTaskText(), t.getDeadline(), 
				Boolean.toString(t.isComplete()), 
				 
				Integer.toString(t.getStudent().getID()));
		String newLine = String.join(" | ", Integer.toString(t.getTaskID()), 
				t.getTaskText(), t.getDeadline(), 
				Boolean.toString(b), 
				 
				Integer.toString(t.getStudent().getID()));
		fileContents = fileContents.replace(lineToModify, newLine);
		FileWriter writer = new FileWriter(TaskTable);
		writer.append(fileContents);
		writer.flush();
		writer.close();
	}
	
	public static void deleteTask(Task t) throws NumberFormatException, IOException, ParseException, InvalidRatingValueException {
		Scanner sc = new Scanner(new File(TaskTable));
		StringBuffer buffer = new StringBuffer();
		while (sc.hasNext())
			buffer.append(sc.nextLine() + System.lineSeparator());
		sc.close();
		String fileContents = buffer.toString();
		String lineToDelete = String.join(" | ", 
				Integer.toString(t.getTaskID()), 
				t.getTaskText(), t.getDeadline(), 
				Boolean.toString(t.isComplete()), 
				Integer.toString(t.getStudent().getID())) + System.lineSeparator();
		fileContents = fileContents.replace(lineToDelete, "");
		FileWriter writer = new FileWriter(TaskTable);
		writer.append(fileContents);
		writer.flush();
		writer.close();
	}
	
	public static ArrayList<Task> selectStudentTasks(Student s) throws 
	FileNotFoundException, NumberFormatException, ParseException, InvalidRatingValueException, InvalidEmailException {
		ArrayList<Task> tasks = new ArrayList<Task>();
		int studentID = s.getID();
		Scanner sc = new Scanner(new File(TaskTable));
		while (sc.hasNext()) {
			Task t = parseTask(sc.nextLine());
			if (t.getStudent().getID() == studentID)
				tasks.add(t);
		}
		sc.close();
		return tasks;
	}
	
	// ----------------------------------------------------------------------------------
	// Note related queries
	// ----------------------------------------------------------------------------------

	private static final String NoteTable = "./src/Data/Notes.txt";

	// helper functions
	public static ArrayList<Integer> getAllNoteIDs() throws FileNotFoundException {
		ArrayList<Integer> IDs = new ArrayList<Integer>();
		Scanner sc = new Scanner(new File(NoteTable));
		while (sc.hasNext()) {
			String line = sc.nextLine();
			String[] args = line.split(" | ");
			IDs.add(Integer.parseInt(args[0]));
		}
		sc.close();
		return IDs;
	}

	private static boolean isValidNoteID(int newID) throws FileNotFoundException {
		ArrayList<Integer> IDs = getAllNoteIDs();
		for (int id : IDs)
			if (id == newID)
				return false;
		return true;
	}

	public static Note parseNote(String line) throws InvalidFileFormatException, FileNotFoundException, NumberFormatException, InvalidEmailException {
		String[] args = line.split(" \\| ");
		int noteID = Integer.parseInt(args[0]);
		File doc = new File(args[1]);
		Student s = LMS_API.getStudent(args[2]);
		Course c = LMS_API.getCourse(args[3]);
		boolean isPublic = Boolean.parseBoolean(args[4]);
		boolean isApproved = Boolean.parseBoolean(args[5]);
		Note n = new Note(doc, s, c, isPublic);
		n.setApproved(isApproved);
		n.setNoteID(noteID);
		return n;
	}

	// Queries
	public static void insertNote(Note n) throws IOException {
		if (n.getNoteID() == 0) {
			Random rand = new Random();
			int newID;
			do {
				newID = rand.nextInt(Integer.MAX_VALUE);
			} while ((!isValidNoteID(newID)));
			n.setNoteID(newID);
		}
		FileWriter fw = new FileWriter(NoteTable, true);
		String lineToWrite = String.join(" | ", Integer.toString(n.getNoteID()), n.getDoc().getName(),
				Integer.toString(n.getAuthor().getID()), Integer.toString(n.getCourse().getCourseID()),
				Boolean.toString(n.isPublic()), Boolean.toString(n.isApproved())) + "\n";
		fw.write(lineToWrite);
		fw.close();
	}

	public static ArrayList<Note> selectStudentNotes(Student s)
			throws FileNotFoundException, InvalidFileFormatException, NumberFormatException, InvalidEmailException {
		ArrayList<Note> notes = new ArrayList<Note>();
		int studentID = s.getID();
		Scanner sc = new Scanner(new File(NoteTable));
		while (sc.hasNext()) {
			Note n = parseNote(sc.nextLine());
			if (n.getAuthor().getID() == studentID)
				notes.add(n);
		}
		sc.close();
		return notes;
	}

	public static ArrayList<Note> selectCourseNotes(Course c) throws InvalidFileFormatException, FileNotFoundException, NumberFormatException, InvalidEmailException {
		ArrayList<Note> notes = new ArrayList<Note>();
		int courseID = c.getCourseID();
		Scanner sc = new Scanner(new File(NoteTable));
		while (sc.hasNext()) {
			Note n = parseNote(sc.nextLine());
			if (n.getCourse().getCourseID() == courseID)
				notes.add(n);
		}
		return notes;
	}

	public static void modifyNoteApproval(Note n, boolean b) throws IOException {
		Scanner sc = new Scanner(new File(NoteTable));
		StringBuffer buffer = new StringBuffer();
		while (sc.hasNext())
			buffer.append(sc.nextLine() + System.lineSeparator());
		sc.close();
		String fileContents = buffer.toString();
		String lineToModify = String.join(" | ", Integer.toString(n.getNoteID()), n.getDoc().getName(),
				Integer.toString(n.getAuthor().getID()), Integer.toString(n.getCourse().getCourseID()),
				Boolean.toString(n.isPublic()), Boolean.toString(n.isApproved()));
		String newLine = String.join(" | ", Integer.toString(n.getNoteID()), n.getDoc().getName(),
				Integer.toString(n.getAuthor().getID()), Integer.toString(n.getCourse().getCourseID()),
				Boolean.toString(n.isPublic()), Boolean.toString(b));
		fileContents = fileContents.replace(lineToModify, newLine);
		FileWriter writer = new FileWriter(NoteTable);
		writer.append(fileContents);
		writer.flush();
		writer.close();
	}
}
