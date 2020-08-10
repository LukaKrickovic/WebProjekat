package Model;

import java.util.List;

public class Guest extends User{
	private List<Unit> rentedUnits;
	private List<Reservation> reservations;
	
	public Guest() {}
	
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
