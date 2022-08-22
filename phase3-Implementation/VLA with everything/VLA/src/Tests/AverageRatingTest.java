package Tests;

import static org.junit.Assert.*;
import java.io.FileNotFoundException;
import java.text.ParseException;
import org.junit.jupiter.api.Test;
import Exceptions.InvalidEmailException;
import Exceptions.InvalidRatingValueException;
import Reviewing.Rating;
import Reviewing.Review;
import Users.Instructor;

public class AverageRatingTest {
	
	@Test
	void CheckAverageRatingFloat_ONE() throws InvalidRatingValueException, InvalidEmailException, NumberFormatException, FileNotFoundException, ParseException {
		Rating r1=new Rating("",1);
		Rating r2=new Rating("",2);
		Rating r3=new Rating("",1);
		Rating r4=new Rating("",1);
		Rating[] array1= {r1,r2,r3,r4};
		Rating rr1=new Rating("",1);
		Rating rr2=new Rating("",2);
		Rating rr3=new Rating("",3);
		Rating rr4=new Rating("",1);
		Rating[] array2= {rr1,rr2,rr3,rr4};
		Rating rrr1=new Rating("",1);
		Rating rrr2=new Rating("",2);
		Rating rrr3=new Rating("",3);
		Rating rrr4=new Rating("",2);
		Rating[] array3= {rrr1,rrr2,rrr3,rrr4};
		Review R1=new Review("","",array1,null,null,null,false);
		Review R2=new Review("","",array2,null,null,null,false);
		Review R3=new Review("","",array3,null,null,null,false);
		Review[] reviewss= {R1,R2,R3};
		Instructor is=new Instructor("",0,"ali.solh@lau.edu.lb");
		Rating[] method_result=is.computeAverTest(reviewss);
		double[] method_result_int=new double[4];
		for (int i=0;i<4;i++) {
			method_result_int[i]=method_result[i].getValue();
		}
		double[] expected_result= {3/3.0,6/3.0,7/3.0,4/3.0};
		for (int i=0;i<4;i++) {
			assertEquals(expected_result[i],method_result_int[i],0.01);
		}
	}
	
	@Test
	void CheckAverageRatingFloat_TWO() throws InvalidRatingValueException, InvalidEmailException, NumberFormatException, FileNotFoundException, ParseException {
		Rating r1=new Rating("",2);
		Rating r2=new Rating("",2);
		Rating r3=new Rating("",3);
		Rating r4=new Rating("",1);
		Rating[] array1= {r1,r2,r3,r4};
		Rating rr1=new Rating("",1);
		Rating rr2=new Rating("",2);
		Rating rr3=new Rating("",3);
		Rating rr4=new Rating("",5);
		Rating[] array2= {rr1,rr2,rr3,rr4};
		Rating rrr1=new Rating("",1);
		Rating rrr2=new Rating("",2);
		Rating rrr3=new Rating("",3);
		Rating rrr4=new Rating("",4);
		Rating[] array3= {rrr1,rrr2,rrr3,rrr4};
		Review R1=new Review("","",array1,null,null,null,false);
		Review R2=new Review("","",array2,null,null,null,false);
		Review R3=new Review("","",array3,null,null,null,false);
		Review[] reviewss= {R1,R2,R3};
		Instructor is=new Instructor("",0,"ali.solh@lau.edu.lb");
		Rating[] method_result=is.computeAverTest(reviewss);
		double[] method_result_int=new double[4];
		for (int i=0;i<4;i++) {
			method_result_int[i]=method_result[i].getValue();
		}
		double[] expected_result= {4/3.0,6/3.0,9/3.0,10/3.0};
		for (int i=0;i<4;i++) {
			assertEquals(expected_result[i],method_result_int[i],0.01);
		}
	}
	
	@Test
	void CheckAverageRatingInt() throws InvalidRatingValueException, InvalidEmailException, NumberFormatException, FileNotFoundException, ParseException {
		Rating r1=new Rating("",1);
		Rating r2=new Rating("",2);
		Rating r3=new Rating("",1);
		Rating r4=new Rating("",1);
		Rating[] array1= {r1,r2,r3,r4};
		Rating rr1=new Rating("",1);
		Rating rr2=new Rating("",2);
		Rating rr3=new Rating("",1);
		Rating rr4=new Rating("",1);
		Rating[] array2= {rr1,rr2,rr3,rr4};
		Rating rrr1=new Rating("",1);
		Rating rrr2=new Rating("",2);
		Rating rrr3=new Rating("",1);
		Rating rrr4=new Rating("",1);
		Rating[] array3= {rrr1,rrr2,rrr3,rrr4};
		Review R1=new Review("","",array1,null,null,null,false);
		Review R2=new Review("","",array2,null,null,null,false);
		Review R3=new Review("","",array3,null,null,null,false);
		Review[] reviewss= {R1,R2,R3};
		Instructor is=new Instructor("",0,"ali.solh@lau.edu.lb");
		Rating[] method_result=is.computeAverTest(reviewss);
		double[] method_result_int=new double[4];
		for (int i=0;i<4;i++) {
			method_result_int[i]=method_result[i].getValue();
		}
		int[] expected_result= {3/3,6/3,3/3,3/3};
		for (int i=0;i<4;i++) {
			assertEquals(expected_result[i],method_result_int[i],0.01);
		}
	}
	
	@Test
	void CheckAverageRatingIntTWO() throws InvalidRatingValueException, InvalidEmailException, NumberFormatException, FileNotFoundException, ParseException {
		Rating r1=new Rating("",5);
		Rating r2=new Rating("",5);
		Rating r3=new Rating("",5);
		Rating r4=new Rating("",5);
		Rating[] array1= {r1,r2,r3,r4};
		Rating rr1=new Rating("",5);
		Rating rr2=new Rating("",5);
		Rating rr3=new Rating("",5);
		Rating rr4=new Rating("",5);
		Rating[] array2= {rr1,rr2,rr3,rr4};
		Rating rrr1=new Rating("",5);
		Rating rrr2=new Rating("",5);
		Rating rrr3=new Rating("",5);
		Rating rrr4=new Rating("",5);
		Rating[] array3= {rrr1,rrr2,rrr3,rrr4};
		Review R1=new Review("","",array1,null,null,null,false);
		Review R2=new Review("","",array2,null,null,null,false);
		Review R3=new Review("","",array3,null,null,null,false);
		Review[] reviewss= {R1,R2,R3};
		Instructor is=new Instructor("",0,"ali.solh@lau.edu.lb");
		Rating[] method_result=is.computeAverTest(reviewss);
		double[] method_result_int=new double[4];
		for (int i=0;i<4;i++) {
			method_result_int[i]=method_result[i].getValue();
		}
		int[] expected_result= {15/3,15/3,15/3,15/3};
		for (int i=0;i<4;i++) {
			assertEquals(expected_result[i],method_result_int[i],0.01);
		}
		
	}
	
	@Test
	void CheckAverageRating_ZERO() throws InvalidRatingValueException, InvalidEmailException, NumberFormatException, FileNotFoundException, ParseException {
		Review[] reviewss= {};
		Instructor is=new Instructor("",0,"ali.solh@lau.edu.lb");
		Rating[] method_result=is.computeAverTest(reviewss);
		assertNull(method_result);
	}
	
}
