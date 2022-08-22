package Tests;

import static org.junit.Assert.*;
import org.junit.jupiter.api.Test;
import Reviewing.Review;

public class SensorTextTests {
	
	@Test
	void getBadWordsListNotReturningNull() {
		Review r = new Review();
		String[] listOfBadWords = r.getBadWords(); 
		assertNotEquals(null, listOfBadWords);
	}
	
	@Test
	void getBadWordsListReadAllWordsFromFile() {
		Review r = new Review();
		String[] listOfBadWords = r.getBadWords(); 
		assertTrue(listOfBadWords.length > 500);
	}
	
	@Test
	void checkStringWithCurseWord() {
		String input = "this ass person";
		String expectedOutput = "this *** person";
		Review r = new Review();
		r.setReviewText(input); //sensor function is called when setting review Text 
		assertEquals(expectedOutput, r.getReviewText());
	}
	
	@Test 
	void checkStringWithoutCurseWord() {
		String input = "this is a good person";
		String expectedOutput = "this is a good person";
		Review r = new Review();
		r.setReviewText(input); //sensor function is called when setting review Text 
		assertEquals(expectedOutput, r.getReviewText());
	}
	
	@Test
	void checkStringWithMultipleCurseWords() {
		String input = "fuck hi hell";
		String expectedOutput = "**** hi ****";
		Review r = new Review();
		r.setReviewText(input); //sensor function is called when setting review Text 
		assertEquals(expectedOutput, r.getReviewText());
	}
	
	@Test
	void checkEmptyString() {
		String input = "";
		String expectedOutput = "";
		Review r = new Review();
		r.setReviewText(input); //sensor function is called when setting review Text 
		assertEquals(expectedOutput, r.getReviewText());
	}
	
	@Test 
	void wordThatCouldBeMistakenForBadWord() {
		String input = "asset";
		String expectedOutput = "asset";
		Review r = new Review();
		r.setReviewText(input); //sensor function is called when setting review Text 
		assertEquals(expectedOutput, r.getReviewText());

		// a bad program could output ***et
		assertNotEquals("***et", r.getReviewText());
	} 
}

