package Tests;

import static org.junit.Assert.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;
import org.junit.jupiter.api.Test;
import Course.Course;
import Data.Database;
import Exceptions.InvalidEmailException;
import Exceptions.InvalidFileFormatException;
import Exceptions.InvalidRatingValueException;
import Reviewing.Rating;
import Reviewing.Review;
import Users.Instructor;
import Users.Student;

public class ReviewDBMethodsTests {
	
	@Test
    void CheckInsertReview() throws IOException, InvalidEmailException  {
		Student s = new Student("Ali Solh",202000888,"ali.solh@lau.edu");
    	Instructor instructor = new Instructor("kahlil Solh",202005510,"khalil.solh@lau.edu");
    	Course c = new Course(instructor, 111490, "Music");
    	Rating[] ratings = new Rating[4];
    	ratings = new Rating[4];
		ratings[0] = new Rating("Explanation");
		ratings[1] = new Rating("Grading");
		ratings[2] = new Rating("Communication");
		ratings[3] = new Rating("Flexibility");
		Review review=new Review("comment", "dd-dd-dd", ratings, instructor, s, c,false);
		review.setID(202001);
		String expAddedLineOnFile = String.join(" | ", Integer.toString(review.getID()), review.getReviewText(), review.getDateTimePosted(),
				ratingsToString(review.getRatings()), Integer.toString(review.getInstructor().getID()),
				Integer.toString(review.getAuthor().getID()), Integer.toString(review.getCourse().getCourseID()),
				Boolean.toString(review.isValid()));
		Database.insertReview(review);  //insert the task
		//check if review added on the file  y reading last line
		Scanner scan = new Scanner(new File("./src/Data/Reviews.txt"));
		String lineWrittenToFile = "";
		while (scan.hasNextLine()) {
			lineWrittenToFile = scan.nextLine();
		}
		scan.close();
		assertEquals(expAddedLineOnFile, lineWrittenToFile);
   }
   
	private static String ratingsToString(Rating[] ratings) {
		String output = "";
		for (Rating r : ratings)
			output += (Integer.toString((int) r.getValue()) + ",");
		return output.substring(0, output.length() - 1);
	}

	@Test
    void CheckReadingGetAllReviews() throws FileNotFoundException, NumberFormatException, ParseException, InvalidRatingValueException, InvalidEmailException {
		Scanner scan = new Scanner(new File("./src/Data/Reviews.txt"));
		ArrayList<Review> expectedList = new ArrayList<Review>();
		while (scan.hasNext()) {
			String line = scan.nextLine();
		    expectedList.add(Database.parseReview(line));
	    }
   	   	ArrayList<Review> methodList = Database.getAllReviews();
   	   	for (int i=0;i<methodList.size();i++) 
   	   		assertEquals(expectedList.get(i).getID(), methodList.get(i).getID());
   }
    
    @Test
    void CheckReadingGetAllReviewsNotNull() throws FileNotFoundException, NumberFormatException, ParseException, InvalidRatingValueException, InvalidEmailException {
    	ArrayList<Review> methodList = Database.getAllReviews();
    	assertNotNull(methodList);
    }

    @Test
    void CheckReviewDelete() throws InvalidEmailException, IOException, NumberFormatException, ParseException, InvalidRatingValueException {
    	Student s = new Student("Ali Ali",202000888,"ali.ali@lau.edu");
    	Instructor ins = new Instructor("kahlil Ali",202005510,"khalil.ali@lau.edu");
    	Course c = new Course(ins,111490,"Musiccc");
    	Rating[] ratings =new Rating[4];
    	ratings = new Rating[4];
		ratings[0] = new Rating("Explanation");
		ratings[1] = new Rating("Grading");
		ratings[2] = new Rating("Communication");
		ratings[3] = new Rating("Flexibility");
		Review review=new Review("comment is a ","dd-dd-dd",ratings,ins,s,c,false);
		review.setID(202001);
		String expAddedLineOnFile = String.join(" | ", Integer.toString(review.getID()), review.getReviewText(), review.getDateTimePosted(),
				ratingsToString(review.getRatings()), Integer.toString(review.getInstructor().getID()),
				Integer.toString(review.getAuthor().getID()), Integer.toString(review.getCourse().getCourseID()),
				Boolean.toString(review.isValid()));
		Database.insertReview(review);
		Database.deleteReview(review); //delete it
		Scanner scan = new Scanner(new File("./src/Data/Reviews.txt"));
		String lineWrittenToFile = "";
		while (scan.hasNextLine()) {
			lineWrittenToFile =scan.nextLine();
		}
		assertNotEquals(expAddedLineOnFile, lineWrittenToFile);
    }
    
    @Test
    void CheckModifyReviewValidity() 
    throws IOException, InvalidEmailException, NumberFormatException, ParseException, InvalidRatingValueException, InvalidFileFormatException  {
    	String idTest = "279570992"; //taken from file and has state false
    	Scanner scan = new Scanner(new File("./src/Data/Reviews.txt")); 
 		String gettingReviewFromDB = ""; 
 		Review r = null;
 		//initially is complete==false; modify to true
 		while (scan.hasNextLine()) {
 			gettingReviewFromDB = scan.nextLine(); 
 			if (gettingReviewFromDB.split(" \\| ")[0].equals(idTest)) {
 			    r = Database.parseReview(gettingReviewFromDB);
 			    break;
 			}
 		}
 		scan.close(); 

 		Database.setReviewValidation(r, true);
 		
 		Scanner sc = new Scanner(new File("./src/Data/Reviews.txt"));
 		String modifiedReviewInDB = "";
 		while (sc.hasNextLine()) {
 			modifiedReviewInDB = sc.nextLine();
 			if (modifiedReviewInDB.split(" \\| ")[0].equals(idTest)) {
 			    break;
 			}
 		}
 		sc.close();
 		assertEquals("true", modifiedReviewInDB.split(" \\| ")[7]);
    }
   
}