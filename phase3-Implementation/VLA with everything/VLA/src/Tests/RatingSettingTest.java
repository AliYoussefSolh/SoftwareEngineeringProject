package Tests;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import Exceptions.InvalidRatingValueException;
import Reviewing.Rating;

public class RatingSettingTest {
	
	@Test
	void checkingLargerValueRating(){
		int value = 6;
		Rating r = new Rating();
		assertThrows(InvalidRatingValueException.class, () ->{
			r.setValue(value);
		});
	}
	
	@Test
	void checkingNegativeValueRating(){
		int value = -1;
		Rating r = new Rating();
		assertThrows(InvalidRatingValueException.class, () ->{
			r.setValue(value);
		});
	}
	
	@Test
	void checkingInRangeValueRating() throws InvalidRatingValueException{
		int value = 3;
		Rating r = new Rating();
		r.setValue(value);
		assertEquals(value, r.getValue(), 0.01);
	}
	
	@Test
	void checkingEqualToRangeFromBelowValueRating() throws InvalidRatingValueException{
		int value = 1;
		Rating r = new Rating();
		r.setValue(value);
		assertEquals(value, r.getValue(), 0.01);
	}
	
	@Test
	void checkingEqualToRangeFromAboveValueRating() throws InvalidRatingValueException{
		int value = 5;
		Rating r = new Rating();
		r.setValue(value);
		assertEquals(value, r.getValue(), 0.01);
	}
}