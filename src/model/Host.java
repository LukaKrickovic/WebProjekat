package model;

import java.util.ArrayList;
import java.util.List;

import enums.Gender;
import enums.Roles;

public class Host extends User {
	// Reservation -> Unit -> Host
	// Reservation -> Host

	public Host() {
		super();
	}
	
	public Host(Id id) {
		super(id);
	}
	
	public Host(Id id, String username, String password, String name, String surname, Gender gender) {
		super(id, username, password, name, surname, gender, Roles.HOST);

	}

	public Host(String username, String password, String name, String surname, Gender gender) {
		super(username, password, name, surname, gender, Roles.HOST);

	}	

	
	
}
