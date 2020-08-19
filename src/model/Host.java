package model;

import java.util.ArrayList;
import java.util.List;

import enums.Gender;
import enums.Roles;

public class Host extends User {

	private List<Unit> allUnits;
	private List<Reservation> allReservations;

	public Host() {
		super();
	}
	
	public Host(Id id) {
		super(id);
	}
	
	public Host(Id id, List<Unit> allUnits, List<Reservation> allReservations, String username, String password, String name, String surname, Gender gender) {
		super(id, username, password, name, surname, gender, Roles.HOST);
		this.allUnits = allUnits;
		this.allReservations = allReservations;
	}	
	
	public Host(Id id, String username, String password, String name, String surname, Gender gender) {
		super(id, username, password, name, surname, gender, Roles.HOST);
		this.allUnits = new ArrayList<Unit>();
		this.allReservations = new ArrayList<Reservation>();
	}
	
	public Host(List<Unit> availableUnits, List<Reservation> allReservations, String username, String password, String name, String surname, Gender gender) {
		super(username, password, name, surname, gender, Roles.HOST);
		this.allUnits = availableUnits;
		this.allReservations = allReservations;
	}	
	
	public Host(List<Unit> allUnits, List<Reservation> allReservations) {
		super();
		this.allUnits = allUnits;
		this.allReservations = allReservations;
	}

	public List<Unit> getAllUnits() {
		return allUnits;
	}

	public void setAllUnits(List<Unit> allUnits) {
		this.allUnits = allUnits;
	}

	public List<Reservation> getAllReservations() {
		return allReservations;
	}

	public void setAllReservations(List<Reservation> allReservations) {
		this.allReservations = allReservations;
	}
	
	
}
