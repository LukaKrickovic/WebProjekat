package model;

import enums.Gender;
import enums.Roles;

public class Administrator extends User{
	
	public Administrator() {
		super();
	}
	
	public Administrator(Id id, String username, String password, String name, String surname, Gender gender, Roles role) {
		super(id, username, password, name, surname, gender, role);
	}
	
	public Administrator(String username, String password, String name, String surname, Gender gender, Roles role) {
		super(username, password, name, surname, gender, role);
	}
	
}
