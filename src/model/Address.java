package model;

public class Address extends Model{

	private String street;
	private String number;
	private String municipality;
	private String zipCode;
	
	//	Constructors
	
	public Address() {
		super();
	}

	

	public Address(String street, String number, String municipality, String zipCode) {
		super();
		this.street = street;
		this.number = number;
		this.municipality = municipality;
		this.zipCode = zipCode;
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



	public String getMunicipality() {
		return municipality;
	}



	public void setMunicipality(String municipality) {
		this.municipality = municipality;
	}



	public String getZipCode() {
		return zipCode;
	}



	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	
}
