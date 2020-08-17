package model;

public class Address extends Model{

	private String street;
	private String number;
	private String city;
	private String zipCode;
	private String country;
	
	//	Constructors
	
	public Address() {
		super();
	}

	public Address(Id id) {
		super(id);
	}

	public Address(String street, String number, String city, String zipCode, String country) {
		super();
		this.street = street;
		this.number = number;
		this.city = city;
		this.zipCode = zipCode;
		this.country = country;
	}


	//	Getters and Setters

	public String getStreet() {
		return street;
	}



	public void setStreet(String street) {
		this.street = street;
	}



	public String getNumber() {
		return number;
	}



	public void setNumber(String number) {
		this.number = number;
	}



	public String getCity() {
		return city;
	}



	public void setCity(String city) {
		this.city = city;
	}



	public String getZipCode() {
		return zipCode;
	}



	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	
	
}
