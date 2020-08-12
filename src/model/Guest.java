package model;

import java.util.List;

import enums.Gender;
import enums.Roles;

public class Guest extends User{
	private List<Unit> rentedUnits;
	private List<Reservation> reservations;
	
	public Guest() {
		super();
	}
	
	public Guest(Id id, List<Unit> rentedUnits, List<Reservation> reservations, String username, String password, String name, String surname, Gender gender) {
		super(id, username, password, name, surname, gender, Roles.GUEST);
		this.rentedUnits = rentedUnits;
		this.reservations = reservations;
	}
	
	public Guest(List<Unit> rentedUnits, List<Reservation> reservations, String username, String password, String name, String surname, Gender gender) {
		super(username, password, name, surname, gender, Roles.GUEST);
		this.rentedUnits = rentedUnits;
		this.reservations = reservations;
	}
	
	public Guest(List<Unit> rentedUnits, List<Reservation> reservations) {
		super();
		this.rentedUnits = rentedUnits;
		this.reservations = reservations;
	}

	public List<Unit> getRentedUnits() {
		return rentedUnits;
	}

	public void setRentedUnits(List<Unit> rentedUnits) {
		this.rentedUnits = rentedUnits;
	}

	public List<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}
	
	
}
