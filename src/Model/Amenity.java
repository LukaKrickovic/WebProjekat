package Model;

public class Amenity {

	// Ne nasledjuje Model jer se ne brise logicki nego fizicki
	
	private String Description;
	private boolean Value;
	
	
	//	Constructors
	
	public Amenity(String description, boolean value) {
		super();
		Description = description;
		Value = value;
	}

	//	Getters and Setters
	
	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public boolean isValue() {
		return Value;
	}

	public void setValue(boolean value) {
		Value = value;
	}
	
	
	
}
