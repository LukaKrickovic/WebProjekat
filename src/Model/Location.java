package Model;
import Model.Address;

public class Location {

	private String Latitude;
	private String Longitude;
	private Address Address;
	
	// Constructors
	
	public Location() {}
	
	public Location(String latitude, String longitude, Address address) {
		super();
		Latitude = latitude;
		Longitude = longitude;
		Address = address;
	}

	
	//	Getters and Setters
	
	public String getLatitude() {
		return Latitude;
	}

	public void setLatitude(String latitude) {
		Latitude = latitude;
	}

	public String getLongitude() {
		return Longitude;
	}

	public void setLongitude(String longitude) {
		Longitude = longitude;
	}

	public Address getAddress() {
		return Address;
	}

	public void setAddress(Address address) {
		Address = address;
	}
	
	
	
}
