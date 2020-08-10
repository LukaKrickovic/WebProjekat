package Model;
import Model.Guest;

public class ApartmentComment extends Model{

	private Guest Guest;
	private Apartment Apartment;
	private String Text;
	private double Grade;

	//	Constructors
	
	public ApartmentComment() {
		super();
	}

	public ApartmentComment(Guest guest, Model.Apartment apartment, String text, double grade) {
		super();
		Guest = guest;
		Apartment = apartment;
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

	public Apartment getApartment() {
		return this.Apartment;
	}

	public void setApartment(Apartment apartment) {
		this.Apartment = apartment;
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
