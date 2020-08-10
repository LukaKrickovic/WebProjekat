package Model;

public class Address {

	private String Street;
	private String Address;
	private String Municipality;
	private String ZipCode;
	private boolean isDeleted;
	
	//	Constructors
	
	public Address() {
		isDeleted = false;
	}

	public Address(String street, String address, String municipality, String zipCode) {
		super();
		Street = street;
		Address = address;
		Municipality = municipality;
		ZipCode = zipCode;
		isDeleted = false;
	}

	//	Getters and Setters
	
	public String getStreet() {
		return Street;
	}

	public void setStreet(String street) {
		Street = street;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public String getMunicipality() {
		return Municipality;
	}

	public void setMunicipality(String municipality) {
		Municipality = municipality;
	}

	public String getZipCode() {
		return ZipCode;
	}

	public void setZipCode(String zipCode) {
		ZipCode = zipCode;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	
}
