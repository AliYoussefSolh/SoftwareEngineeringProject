package Tests;

import static org.junit.Assert.*;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import Course.Course;
import Data.Database;
import Exceptions.InvalidEmailException;
import Exceptions.InvalidRatingValueException;
import LMS_data.LMS_API;
import Reviewing.Rating;
import Reviewing.Review;
import TodoList.Task;
import Users.Instructor;
import Users.Student;
//these tests are to satisfy this non functional performance requirement:
/*
SNFP01- The system should have good performance and a maximum delay time of 2 seconds when 
performing any operation.
*/

public class SystemPerformanceTests {
	
	final int MAX_DELAY_TIME = 2;
	
	//this test will measure the time for creating review (it takes time due sensor the text, 
	//function which has to scan all text for bads words and replaces them, this will take time especially 
	//when the review text is large) 
	// and uploading it to database which takes time to write to file and creating ID
	// it also takes some time due to the many queries it has with LMS
	@Test
	void CheckIfBelowTimeLimit1() 
	throws NumberFormatException, InvalidEmailException, IOException {
		double startTime = System.nanoTime();
		Rating[] ratings = new Rating[4];
		String reviewText = "Lorem ipsum dolor sit amet consectetur adipisicing elit. Maxime mollitia,"
				+ "molestiae quas vel sint commodi repudiandae consequuntur voluptatum laborum"
				+ "numquam blanditiis harum quisquam eius sed odit fugiat iusto fuga praesentium"
				+ "optio, eaque rerum! Provident similique accusantium nemo autem. Veritatis"
				+ "obcaecati tenetur iure eius earum ut molestias architecto voluptate aliquam"
				+ "nihil, eveniet aliquid culpa officia aut! Impedit sit sunt quaerat, odit"
				+ "tenetur error, harum nesciunt ipsum debitis quas aliquid. Reprehenderit, "
				+ "quia. Quo neque error repudiandae hell fuga? Ipsa laudantium molestias eos "
				+ "sapiente officiis modi at sunt fuck excepturi expedita sint? Sed quibusdam "
				+ "recusandae alias error harum maxime adipisci amet laborum. Perspiciatis "
				+ "minima nesciunt dolorem! Officiis iure rerum voluptates a cumque velit "
				+ "quibusdam sed amet tempora. Sit laborum ab, eius fugit doloribus tenetur "  
				+ "fugiat, temporibus enim bitch commodi iusto libero magni deleniti quod quam " 
				+ "consequuntur! Commodi ass minima excepturi repudiandae velit hic maxime "
				+ "doloremque. Quaerat provident commodi consectetur veniam similique ad "
				+ "earum omnis ipsum saepe, voluptas, hic voluptates pariatur est explicabo "  
				+ "fugiat, dolorum eligendi quam cupiditate excepturi mollitia maiores labore "  
				+ "suscipit quas? Nulla, placeat. Voluptatem quaerat non architecto ab laudantium " 
				+ "modi minima sunt esse temporibus sint culpa, recusandae aliquam numquam "
				+ "totam ratione voluptas quod exercitationem fuga. Possimus quis earum veniam "  
				+ "quasi aliquam eligendi, placeat qui corporis!";
    	ratings = new Rating[4];
		ratings[0] = new Rating("Explanation");
		ratings[1] = new Rating("Grading");
		ratings[2] = new Rating("Communication");
		ratings[3] = new Rating("Flexibility");
		Instructor instructor = LMS_API.getInstructor("202004405");
		Student student = LMS_API.getStudent("202004410");
		Course course = LMS_API.getCourse("111490");
		Review r = new Review(reviewText, "04/10/2021", ratings, instructor, 
				student, course, false);
		Database.insertReview(r);
		double endTime = System.nanoTime();
		double durationInSeconds =  ((endTime - startTime) / Math.pow(10, 9));
		assertTrue(durationInSeconds < MAX_DELAY_TIME);	
	}
		
	//This operation should take some time since it is going through all the reviews text file
	//and making modification to some of them. So the slow down should be from the opening and 
	//reading and modification to the files
	@Test
	void CheckIfBelowTimeLimit2() 
	throws NumberFormatException, InvalidEmailException, IOException, ParseException, InvalidRatingValueException {
		double startTime = System.nanoTime();
		ArrayList<Review> reviews = new ArrayList<Review>();
		reviews = Database.getAllReviews(); 
		Database.deleteReview(reviews.get(0));
		Database.deleteReview(reviews.get(1));
		Database.setReviewValidation(reviews.get(2), true);
		Database.setReviewValidation(reviews.get(3), false);
		double endTime = System.nanoTime(); 
		double durationInSeconds =  ((endTime - startTime) / Math.pow(10, 9));
		assertTrue(durationInSeconds < MAX_DELAY_TIME);
	}

	//this is another operation that could take some time is querying tasks from database 
	//and modifying them a lot and saving these modifications to the DB
	@Test
	void CheckIfBelowTimeLimit3() 
	throws NumberFormatException, InvalidEmailException, IOException, ParseException, InvalidRatingValueException {
		double startTime = System.nanoTime();
		Student s = LMS_API.getStudent("202004410");
		ArrayList<Task> tasks = Database.selectStudentTasks(s);
		for (Task t : tasks) 
			Database.modifyTaskCompleteness(t, !t.isComplete());
		Student s2 = LMS_API.getStudent("202004415");
		ArrayList<Task> tasks2 = Database.selectStudentTasks(s2);
		for (Task t : tasks2) 
			Database.modifyTaskCompleteness(t, !t.isComplete());
		double endTime = System.nanoTime(); 
		double durationInSeconds =  ((endTime - startTime) / Math.pow(10, 9));
		assertTrue(durationInSeconds < MAX_DELAY_TIME);
	}
}
