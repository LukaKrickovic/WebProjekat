package Model;

import java.util.Date;
import java.util.List;

import Enums.ReservationStatus;

public class Reservation extends Model{
	private Unit unit;
	private Date startDate;
	private int length;
	private double price;
	private String message;
	private Guest guest;
	private ReservationStatus reservationStatus;
	private List<Reservation> reservations;
	
	public Reservation() {}
	
	public Reservation(Unit unit, Date startDate, int length, double price, String message, Guest guest,
			ReservationStatus reservationStatus, List<Reservation> reservations) {
		super();
		this.unit = unit;
		this.startDate = startDate;
		this.length = length;
		this.price = price;
		this.message = message;
		this.guest = guest;
		this.reservationStatus = reservationStatus;
		this.reservations = reservations;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Guest getGuest() {
		return guest;
	}

	public void setGuest(Guest guest) {
		this.guest = guest;
	}

	public ReservationStatus getReservationStatus() {
		return reservationStatus;
	}

	public void setReservationStatus(ReservationStatus reservationStatus) {
		this.reservationStatus = reservationStatus;
	}

	public List<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}
	
	
}
