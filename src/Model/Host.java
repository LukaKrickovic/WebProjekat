package Model;

import java.util.List;

public class Host extends User {

	private List<Unit> availableUnits;

	public Host() {}
	
	public Host(List<Unit> availableUnits) {
		super();
		this.availableUnits = availableUnits;
	}

	public List<Unit> getAvailableUnits() {
		return availableUnits;
	}

	public void setAvailableUnits(List<Unit> availableUnits) {
		this.availableUnits = availableUnits;
	}
	
	
}
