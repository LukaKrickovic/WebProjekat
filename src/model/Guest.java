package model;

import java.util.ArrayList;
import java.util.List;

import enums.Gender;
import enums.Roles;

public class Guest extends User{
	public Guest(Id id) {
		super(id);
	}
	
	public Guest() {
		super();
	}
	
	public Guest(Id id, String username, String password, String name, String surname, Gender gender) {
		super(id, username, password, name, surname, gender, Roles.GUEST);
	}

	
	public Guest(String username, String password, String name, String surname, Gender gender) {
		super(username, password, name, surname, gender, Roles.GUEST);
	}

}
