package model;

import enums.Gender;
import enums.Roles;

public class Administrator extends User{
	
	public Administrator() {
		super();
	}
	
	public Administrator(Id id) {
		super(id);
	}
	
	public Administrator(Id id, String username, String password, String name, String surname, Gender gender) {
		super(id, username, password, name, surname, gender, Roles.ADMINISTRATOR);
	}
	
	public Administrator(String username, String password, String name, String surname, Gender gender) {
		super(username, password, name, surname, gender, Roles.ADMINISTRATOR);
	}
	
}
