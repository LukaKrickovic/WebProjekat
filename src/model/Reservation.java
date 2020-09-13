package model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import enums.ReservationStatus;

public class Reservation extends Model{
	private Unit unit;
	private LocalDate startDate;
	private LocalDate endDate;
	private int length;
	private double price;
	private String message;
	private Guest guest;
	private User user;
	private ReservationStatus reservationStatus;

	
	public Reservation() {
		super();
	}
	
	public Reservation(Id id) {
		super(id);
	}
	
	public Reservation(Unit unit, LocalDate startDate, LocalDate endDate, int length, double price, String message, Guest guest,
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
	
	public Reservation(Id id, Unit unit, LocalDate startDate, LocalDate endDate, int length, double price, String message, Guest guest,
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
	
	public Reservation(Id id, Unit unit, LocalDate startDate, int length, double price, String message, Guest guest,
			ReservationStatus reservationStatus) {
		super(id);
		this.unit = unit;
		this.startDate = startDate;
		this.endDate = startDate.plusDays(length);
		this.length = length;
		this.price = price;
		this.message = message;
		this.guest = guest;
		this.reservationStatus = reservationStatus;
	}
	
	public Reservation(Id id, Unit unit, LocalDate startDate, LocalDate endDate, double price, String message, Guest guest,
			ReservationStatus reservationStatus) {
		super(id);
		this.unit = unit;
		this.startDate = startDate;
		this.endDate = endDate;
		this.length = (int)ChronoUnit.DAYS.between(startDate, endDate);
		this.price = price;
		this.message = message;
		this.guest = guest;
		this.reservationStatus = reservationStatus;
	}

	public Reservation(Id id, Unit unit, LocalDate startDate, LocalDate endDate, double price, String message, User guest,
			ReservationStatus reservationStatus) {
		super(id);
		this.unit = unit;
		this.startDate = startDate;
		this.endDate = endDate;
		this.length = (int)ChronoUnit.DAYS.between(startDate, endDate);
		this.price = price;
		this.message = message;
		this.user = guest;
		this.reservationStatus = reservationStatus;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}


	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
