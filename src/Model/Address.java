package Model;

public class Address extends Model{

	private String Street;
	private String Number;
	private String Municipality;
	private String ZipCode;
	
	//	Constructors
	
	public Address() {
		super();
	}

	public Address(String street, String number, String municipality, String zipCode) {
		super();
		Street = street;
		Number = number;
		Municipality = municipality;
		ZipCode = zipCode;
	}

	//	Getters and Setters
	
	public String getStreet() {
		return Street;
	}

	public void setStreet(String street) {
		Street = street;
	}

	public String getNumber() {
		return Number;
	}

	public void setNumber(String number) {
		Number = Number;
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


	
}
