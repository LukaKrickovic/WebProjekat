package Model;
import Model.Guest;

public class AppartmentComment extends Model{

	private Guest Guest;
	private Appartment Appartment;
	private String Text;
	private double Grade;

	public AppartmentComment() {}

	public AppartmentComment(Guest guest, Model.Appartment appartment, String text, double grade) {
		super();
		Guest = guest;
		Appartment = appartment;
		Text = text;
		Grade = grade;
	}

	public Guest getGuest() {
		return Guest;
	}

	public void setGuest(Guest guest) {
		Guest = guest;
	}

	public Appartment getAppartment() {
		return Appartment;
	}

	public void setAppartment(Appartment appartment) {
		Appartment = appartment;
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
