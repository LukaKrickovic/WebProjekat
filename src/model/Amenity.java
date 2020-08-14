package model;

public class Amenity extends Model{	
	private String description;
	private boolean value;
	
	public Amenity(Id id) {
		super(id);
	}
	
	//	Constructors
	
	public Amenity(String description, boolean value) {
		super();
		this.description = description;
		this.value = value;
	}
	
	public Amenity(Id id, String description, boolean value) {
		super(id);
		this.description = description;
		this.value = value;
	}



	//	Getters and Setters
	
	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public boolean isValue() {
		return value;
	}


	public void setValue(boolean value) {
		this.value = value;
	}
	
	
}
