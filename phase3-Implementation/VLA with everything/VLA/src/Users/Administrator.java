package Users;

import java.util.ArrayList;

import Exceptions.InvalidEmailException;
import Reviewing.Review;

public class Administrator extends User{
	private ArrayList<Review> reviewsToEvaluate;

	public Administrator() {
		super();
	}
	public Administrator(String name, int ID, String email) throws InvalidEmailException {
		super(name, ID, email);
	}
	
	public void evaluate() {
		//handled in GUI
	}
	
	public void setReviewsToEvaluate(ArrayList<Review> reviewsToEvaluate) {
		this.reviewsToEvaluate = reviewsToEvaluate;
	}

	public ArrayList<Review> getReviewsToEvaluate() {
		return reviewsToEvaluate;
	}

	public void receiveReviews(ArrayList<Review> reviewsToEvaluate) {
		this.reviewsToEvaluate = reviewsToEvaluate;
	}

}
