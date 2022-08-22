package Users;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;
import Course.Course;
import Data.Database;
import Exceptions.InvalidEmailException;
import Exceptions.InvalidRatingValueException;
import Reviewing.Rating;
import Reviewing.Review;

public class Instructor extends User{
	private Rating[] averageRatings;
	private ArrayList<Course> courseTaught;
	
	public Instructor() {
		super();
		courseTaught = new ArrayList<Course>();
		averageRatings = new Rating[4];
		averageRatings[0] = new Rating("Explanation");
		averageRatings[1] = new Rating("Grading");
		averageRatings[2] = new Rating("Communication");
		averageRatings[3] = new Rating("Flexibility");
	}
	
	public Instructor(String name, int ID, String email) throws InvalidEmailException {
		super(name, ID, email);
		courseTaught = new ArrayList<Course>();
		averageRatings = new Rating[4];
		averageRatings[0] = new Rating("Explanation");
		averageRatings[1] = new Rating("Grading");
		averageRatings[2] = new Rating("Communication");
		averageRatings[3] = new Rating("Flexibility");
		
	}
	
	public Rating[] getAVGRatings() {
		return averageRatings;
	}

	public void setAVGRatings(Rating[] ratings) {
		this.averageRatings = ratings;
	}

	public ArrayList<Course> getCourseTaught() {
		return courseTaught;
	}

	public void setCourseTaught(ArrayList<Course> courseTaught) {
		this.courseTaught = courseTaught;
	}

	public void sendNotification(String message) {
		//handled in GUI
	}
	
	public void checkNote() {
		//handled in GUI
	}

	public void computeAverageRating() throws NumberFormatException, FileNotFoundException, ParseException, InvalidRatingValueException, InvalidEmailException {
		ArrayList<Review> allReviews = Database.selectInstructorReviews(this);
		ArrayList<Review> validReviews = new ArrayList<Review>();
		for (Review r : allReviews)
			if (r.isValid())
				validReviews.add(r);
		double nbOfReviews = validReviews.size();
		if (nbOfReviews==0) {
			averageRatings=null;
		}
		int[] sumOfRatings = new int[averageRatings.length];
		for (Review r : validReviews) {
			Rating[] currRatings = r.getRatings();
			for (int i=0; i<averageRatings.length; i++) {
				sumOfRatings[i] += currRatings[i].getValue();
			}
		}
		for (int i=0; i<averageRatings.length; i++) {
			averageRatings[i].setValue(sumOfRatings[i] / nbOfReviews);
		}
	}
	
	public void sendApproval(boolean isApproved) {
		//TODO
	}
	
	// clone for above method for testing purposes only
	public Rating[] computeAverTest(Review[] reviews) throws NumberFormatException, FileNotFoundException, ParseException, InvalidRatingValueException, InvalidEmailException {
		Rating[] averageresult=new Rating[4];
		averageresult[0] = new Rating("");
		averageresult[1] = new Rating("");
		averageresult[2] = new Rating("");
		averageresult[3] = new Rating("");
		
		double nbOfReviews = reviews.length;
		if (nbOfReviews==0) {
			return null;
		}
		int[] sumOfRatings = new int[averageRatings.length];
		for (Review r :reviews) {
			Rating[] currRatings = r.getRatings();
			for (int i=0; i<averageRatings.length; i++) {
				sumOfRatings[i] += currRatings[i].getValue();
			}
		}
		for (int i=0; i<averageRatings.length; i++) {
			averageresult[i].setValue(sumOfRatings[i] / nbOfReviews);
		}
		return averageresult;
	}
	
}
