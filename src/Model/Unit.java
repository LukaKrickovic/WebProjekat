package model;

import java.time.LocalTime;
import java.util.List;

import enums.RoomType;
import enums.Status;

public class Unit extends Model{
	private RoomType roomType;
	private int numOfRooms;
	private int numOfGuests;
	private Location location;
	private Host host;
	private double pricePerNight;
	private LocalTime checkinTime;
	private LocalTime checkoutTime;
	private Status status;
	private ApartmentComment apartmentComment;
	private List<Amenity> amenities;
	private List<Reservation> reservations;
	
	//TODO: DODATI INICIJALNO VREME PRIJAVE/ODJAVE
	
	public Unit() {}
	
	public Unit(RoomType roomType, int numOfRooms, int numOfGuests, Location location, Host host, double pricePerNight,
			LocalTime checkinTime, LocalTime checkoutTime, Status status, ApartmentComment apartmentComment, 
			List<Amenity> amenities, List<Reservation> reservations) {
		super();
		this.roomType = roomType;
		this.numOfRooms = numOfRooms;
		this.numOfGuests = numOfGuests;
		this.location = location;
		this.host = host;
		this.pricePerNight = pricePerNight;
		this.checkinTime = checkinTime;
		this.checkoutTime = checkoutTime;
		this.status = status;
		this.apartmentComment = apartmentComment;
		this.amenities = amenities;
		this.reservations = reservations;
	}
	
	public Unit(Id id, RoomType roomType, int numOfRooms, int numOfGuests, Location location, Host host, double pricePerNight,
			LocalTime checkinTime, LocalTime checkoutTime, Status status, ApartmentComment apartmentComment, 
			List<Amenity> amenities, List<Reservation> reservations) {
		super(id);
		this.roomType = roomType;
		this.numOfRooms = numOfRooms;
		this.numOfGuests = numOfGuests;
		this.location = location;
		this.host = host;
		this.pricePerNight = pricePerNight;
		this.checkinTime = checkinTime;
		this.checkoutTime = checkoutTime;
		this.status = status;
		this.apartmentComment = apartmentComment;
		this.amenities = amenities;
		this.reservations = reservations;
	}

	public RoomType getRoomType() {
		return roomType;
	}

	public void setRoomType(RoomType roomType) {
		this.roomType = roomType;
	}

	public int getNumOfRooms() {
		return numOfRooms;
	}

	public void setNumOfRooms(int numOfRooms) {
		this.numOfRooms = numOfRooms;
	}

	public int getNumOfGuests() {
		return numOfGuests;
	}

	public void setNumOfGuests(int numOfGuests) {
		this.numOfGuests = numOfGuests;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Host getHost() {
		return host;
	}

	public void setHost(Host host) {
		this.host = host;
	}

	public double getPricePerNight() {
		return pricePerNight;
	}

	public void setPricePerNight(double pricePerNight) {
		this.pricePerNight = pricePerNight;
	}

	public LocalTime getCheckinTime() {
		return checkinTime;
	}

	public void setCheckinTime(LocalTime checkinTime) {
		this.checkinTime = checkinTime;
	}

	public LocalTime getCheckoutTime() {
		return checkoutTime;
	}

	public void setCheckoutTime(LocalTime checkoutTime) {
		this.checkoutTime = checkoutTime;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public ApartmentComment getApartmentComment() {
		return apartmentComment;
	}

	public void setApartmentComment(ApartmentComment apartmentComment) {
		this.apartmentComment = apartmentComment;
	}

	public List<Amenity> getAmenities() {
		return amenities;
	}

	public void setAmenities(List<Amenity> amenities) {
		this.amenities = amenities;
	}

	public List<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}
	
	
	
	
}
