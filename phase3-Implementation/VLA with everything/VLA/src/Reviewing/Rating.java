package Reviewing;
import Exceptions.InvalidRatingValueException;

public class Rating {
	private String skill;
	private double value;
	
	public Rating() {
		skill = "";
		value = 0;
	}
	public Rating(String skill) {
		setSkill(skill);
	}
	public Rating(String skill, int value) throws InvalidRatingValueException {
		setSkill(skill);
		setValue(value);
	}
	public String getSkill() {
		return skill;
	}
	public void setSkill(String skill) {
		this.skill = skill;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) throws InvalidRatingValueException {
		if (0 <= value && value <= 5) {
			this.value = value;
		}
		else throw new InvalidRatingValueException();
	}

	public String toString(){
		return skill + " " +  value;
	}
}
