package model;

import java.util.Date;
import java.util.List;

import enums.ReservationStatus;

public class Reservation extends Model{
	private Unit unit;
	private Date startDate;
	private Date endDate;
	private int length;
	private double price;
	private String message;
	private Guest guest;
	private ReservationStatus reservationStatus;
	
	// TODO: mozda da stavimo hosta kao polje? da bi smo mogli da proverimo da li je rezervacija njegova
	
	public Reservation() {
		super();
	}
	
	public Reservation(Id id) {
		super(id);
	}
	
	public Reservation(Unit unit, Date startDate, Date endDate, int length, double price, String message, Guest guest,
			ReservationStatus reservationStatus) {
		super();
		this.unit = unit;
		this.startDate = startDate;
		this.endDate = endDate;
		this.length = length;
		this.price = price;
		this.message = message;
		this.guest = guest;
		this.reservationStatus = reservationStatus;
	}
	
	public Reservation(Id id, Unit unit, Date startDate, Date endDate, int length, double price, String message, Guest guest,
			ReservationStatus reservationStatus) {
		super(id);
		this.unit = unit;
		this.startDate = startDate;
		this.endDate = endDate;
		this.length = length;
		this.price = price;
		this.message = message;
		this.guest = guest;
		this.reservationStatus = reservationStatus;
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
	
	

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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
	
}
