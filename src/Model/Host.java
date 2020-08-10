package Model;

public class Host extends User {

	private Unit availableUnits;

	public Host() {}
	
	public Host(Unit availableUnits) {
		super();
		this.availableUnits = availableUnits;
	}

	public Unit getAvailableUnits() {
		return availableUnits;
	}

	public void setAvailableUnits(Unit availableUnits) {
		this.availableUnits = availableUnits;
	}
	
	
}
