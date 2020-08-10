package Model;
import Model.Guest;

public class ApartmentComment extends Model{

	private Guest Guest;
	private Unit Unit;
	private String Text;
	private double Grade;

	//	Constructors
	
	public ApartmentComment() {
		super();
	}

	public ApartmentComment(Guest guest, Unit unit, String text, double grade) {
		super();
		Guest = guest;
		Unit = unit;
		Text = text;
		Grade = grade;
	}

	//	Getters and Setters
	
	public Guest getGuest() {
		return Guest;
	}

	public void setGuest(Guest guest) {
		Guest = guest;
	}

	public Unit getApartment() {
		return this.Unit;
	}

	public void setApartment(Unit unit) {
		this.Unit = unit;
	}

	public String getText() {
		return Text;
	}

	public void setText(String text) {
		Text = text;
	}

	public double getGrade() {
		return Grade;
	}

	public void setGrade(double grade) {
		Grade = grade;
	}
	
	
}
