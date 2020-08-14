package model;
import model.Address;

public class Location extends Model{

	private String latitude;
	private String longitude;
	private Address address;
	
	// Constructors
	
	public Location() {
		super();
	}
	
	public Location(Id id) {
		super(id);
	}
	
	public Location(String latitude, String longitude, Address address) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
		this.address = address;
	}
	
	public Location(Id id, String latitude, String longitude, Address address) {
		super(id);
		this.latitude = latitude;
		this.longitude = longitude;
		this.address = address;
	}
	
	//	Getters and Setters

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}


	
}
