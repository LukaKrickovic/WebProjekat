package model;

import java.util.List;

import enums.Gender;
import enums.Roles;

public class Host extends User {

	private List<Unit> availableUnits;

	public Host() {
		super();
	}
	
	public Host(Id id) {
		super(id);
	}
	
	public Host(Id id, List<Unit> availableUnits, String username, String password, String name, String surname, Gender gender) {
		super(id, username, password, name, surname, gender, Roles.HOST);
		this.availableUnits = availableUnits;
	}	
	
	public Host(List<Unit> availableUnits, String username, String password, String name, String surname, Gender gender) {
		super(username, password, name, surname, gender, Roles.HOST);
		this.availableUnits = availableUnits;
	}	
	
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
