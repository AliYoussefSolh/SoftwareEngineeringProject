package Reviewing;

import Users.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import Course.Course;
import Exceptions.InvalidRatingValueException;;

public class Review {
	private String reviewText;
	private String dateTimePosted; 
	private Rating[] ratings;
	private Instructor instructor;
	private Student author;
	private boolean isValid;
	private Course course;
	private int ID;

	public Review() {
		ratings = new Rating[4];
		ratings[0] = new Rating("Explanation");
		ratings[1] = new Rating("Grading");
		ratings[2] = new Rating("Communication");
		ratings[3] = new Rating("Flexibility");
	}
	
	public Review(String comment, String d, Rating[] ratings, Instructor instructor, 
			Student student, Course course, boolean isValid) {
		setReviewText(comment);
		setDateTimePosted(d);
		setRatings(ratings);
		setInstructor(instructor);
		setAuthor(student);
		setCourse(course);
		setValid(isValid);
	}
	
	public Student getAuthor() {
		return author;
	}
	public void setAuthor(Student author) {
		this.author = author;
	}
	public String getReviewText() {
		return reviewText;
	}
	public void setReviewText(String reviewreviewText) {
		this.reviewText = reviewreviewText;
		sensorReviewText();
	}
	public Rating[] getRatings() {
		return ratings;
	}
	public void setRatings(Rating[] ratings) {
		this.ratings = ratings;
	}
	public void addRating(Rating newRating) {
		for (Rating r : ratings) {
			if (r.getSkill().equals(newRating.getSkill()))
				try {
					r.setValue(newRating.getValue());
				} catch (InvalidRatingValueException e) {
					e.printStackTrace();
				}
		}
	}
	public Instructor getInstructor() {
		return instructor;
	}
	public void setInstructor(Instructor instructor) {
		this.instructor = instructor;
	}
	public boolean isValid() {
		return isValid;
	}
	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}
	public String getDateTimePosted() {
		return dateTimePosted;
	}
	public void setDateTimePosted(String dateTimePosted) {
		this.dateTimePosted = dateTimePosted;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	
	public String[] getBadWords() {
		File file = new File("./src/Reviewing/badwords.txt");
        String s = "";
        Scanner sc = null;
		try {
			sc = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
        while (sc.hasNextLine())
        	s += sc.nextLine();
        String[] badWords = s.split(", ");
        sc.close();
        return badWords;
	}
	
	public void sensorReviewText() {
		String words[] = this.reviewText.split(" ");
		String newWord = "";
		String[] filters = getBadWords();
		for(int i=0; i<words.length; i++) {
		    for (String filter : filters) {
		    	String replacement ="";
		    	for(int h = 0; h<words[i].length(); h++) {
		    		replacement += "*";
		    	}
		    	if (words[i].equals(filter)) {
		    		words[i] = replacement;
		    	}
		    }
	    	newWord += words[i] + " ";
		}
	    this.reviewText = newWord.substring(0, newWord.length()-1);
	}
	
	public String toString() {
		String rating = "";
		for (int i = 0; i<ratings.length; i++)
			rating += ratings[i].toString() + " ";
			return "\nContent: "+this.reviewText+"\nRating: "+rating + "\nCourse: "+ course.getCourseTitle();
	}
}
