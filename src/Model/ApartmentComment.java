package model;
import model.Guest;

public class ApartmentComment extends Model{

	private Guest guest;
	private Unit unit;
	private String text;
	private double grade;

	//	Constructors
	
	public ApartmentComment() {
		super();
	}

	public ApartmentComment(Guest guest, Unit unit, String text, double grade) {
		super();
		this.guest = guest;
		this.unit = unit;
		this.text = text;
		this.grade = grade;
	}

	//	Getters and Setters
	
	public Guest getGuest() {
		return guest;
	}

	public void setGuest(Guest guest) {
		this.guest = guest;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public double getGrade() {
		return grade;
	}

	public void setGrade(double grade) {
		this.grade = grade;
	}
	
}
