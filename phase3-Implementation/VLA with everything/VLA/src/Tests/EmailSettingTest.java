package Tests;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import Exceptions.InvalidEmailException;
import Users.*;


public class EmailSettingTest {

	@Test
	void checkIfLAUstaffEmailAccepted() throws InvalidEmailException {
		User user = new Instructor();
		String email = "azzam.mourad@lau.edu.lb";
		user.setEmail(email);
		assertEquals(email, user.getEmail());
	}
	
	@Test
	void checkIfLAUStudentEmailAccepted() throws InvalidEmailException {
		User user = new Instructor();
		String email = "mohamad.khalifeh@lau.edu";
		user.setEmail(email);
		assertEquals(email, user.getEmail());
	}
	
	@Test
	void checkIfLAUemailWithNumberAccepted() throws InvalidEmailException {
		User user = new Instructor();
		String email = "mohamad.khalifeh03@lau.edu";
		user.setEmail(email); 
		assertEquals(email, user.getEmail());
	}
	
	@Test
	void nonEmailShouldThrowError() {
		User user = new Instructor();
		String email = "ranisalman";
		assertThrows(InvalidEmailException.class, () ->{
			user.setEmail(email);
		});
	}
	
	@Test
	void nonLAUemailShouldThrowError1() {
		User user = new Instructor();
		String email = "mohamad.khalifeh@gmail.com";
		assertThrows(InvalidEmailException.class, () ->{
			user.setEmail(email);
		});
	}
	
	
	@Test
	void nonLAUemailShouldThrowError2() {
		User user = new Instructor();
		String email = "mohamad.khalifeh@outlook.com";
		assertThrows(InvalidEmailException.class, () ->{
			user.setEmail(email);
		});
	}
	@Test
	void nullEmailShouldThrowError() {
		User user = new Instructor();
		assertThrows(InvalidEmailException.class, () ->{
			user.setEmail(null);
		});
	}
	
}
