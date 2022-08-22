package Users;

import Exceptions.InvalidEmailException;

public abstract class User {
	protected String name;
	protected int ID;
	protected String email;
	
	public User() {
		name = "";
		ID = -1;
		email = "";
	}
	
	public User(String name, int ID, String email) throws InvalidEmailException {
		setName(name);
		setID(ID);
		setEmail(email);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) throws InvalidEmailException {
		if (email != null && (email.trim().matches("[a-z]+\\.[a-z0-9]+(@lau.edu)") 
				|| email.trim().matches("[a-z]+\\.[a-z0-9]+(@lau.edu.lb)"))) {
			email = email.trim();
			this.email = email;
		}
		else
			throw new InvalidEmailException();
	}
}
