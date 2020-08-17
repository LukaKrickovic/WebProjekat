package model;

import java.util.List;

import enums.Gender;
import enums.Roles;

public class Host extends User {

	private List<Unit> allUnits;

	public Host() {
		super();
	}
	
	public Host(Id id) {
		super(id);
	}
	
	public Host(Id id, List<Unit> allUnits, String username, String password, String name, String surname, Gender gender) {
		super(id, username, password, name, surname, gender, Roles.HOST);
		this.allUnits = allUnits;
	}	
	
	public Host(List<Unit> availableUnits, String username, String password, String name, String surname, Gender gender) {
		super(username, password, name, surname, gender, Roles.HOST);
		this.allUnits = availableUnits;
	}	
	
	public Host(List<Unit> allUnits) {
		super();
		this.allUnits = allUnits;
	}

	public List<Unit> getAllUnits() {
		return allUnits;
	}

	public void setAllUnits(List<Unit> allUnits) {
		this.allUnits = allUnits;
	}
	
	
}
