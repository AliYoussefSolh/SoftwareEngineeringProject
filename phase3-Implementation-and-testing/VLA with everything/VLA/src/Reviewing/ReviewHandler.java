package Reviewing;

import java.io.IOException;
import Data.Database;
import Interfaces.*;
import Users.*;

public class ReviewHandler implements Uploadable, Notifier{

	private Review review;
	private Student student;
	private Administrator admin;
		
	public ReviewHandler(Review r) {
		setReview(r);
	}
	
	public Review getReview() {
		return review;
	}

	public void setReview(Review review) {
		this.review = review;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Administrator getAdmin() {
		return admin;
	}

	public void setAdmin(Administrator admin) {
		this.admin = admin;
	}

	@Override
	public void notifyUser() {
		//handled in GUI
	}
	
	@Override
	public void uploadToDB() throws IOException{
		Database.insertReview(review);		
	}
}
