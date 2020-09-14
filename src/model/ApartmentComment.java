package model;
import model.Guest;

public class ApartmentComment extends Model{

	private Guest guest;
	private Unit unit;
	private String text;
	private double grade;
	private boolean isApproved;

	//	Constructors
	
	public ApartmentComment(Id id) {
		super(id);
	}
	
	public ApartmentComment() {
		super();
	}

	public ApartmentComment(Guest guest, Unit unit, String text, double grade) {
		super();
		this.guest = guest;
		this.unit = unit;
		this.text = text;
		this.grade = grade;
		this.isApproved = false;
	}
	
	public ApartmentComment(Id id, Guest guest, Unit unit, String text, double grade) {
		super(id);
		this.guest = guest;
		this.unit = unit;
		this.text = text;
		this.grade = grade;
		this.isApproved = false;
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

	public boolean isApproved() {
		return isApproved;
	}

	public void setApproved(boolean approved) {
		isApproved = approved;
	}
}
