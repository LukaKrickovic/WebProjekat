package model;

import java.time.LocalTime;
import java.util.ArrayList;
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
	private List<Amenity> amenities;
	private List<String> imageSources;
	private String name;
	
	
	public Unit() {
		super();
	}
	
	public Unit(Id id) {
		super(id);
	}
	
	public Unit(String name, RoomType roomType, int numOfRooms, int numOfGuests, Location location, Host host, double pricePerNight,
			LocalTime checkinTime, LocalTime checkoutTime, Status status, List<Amenity> amenities, List<String> imageSources) {
		super();
		this.name = name;
		this.roomType = roomType;
		this.numOfRooms = numOfRooms;
		this.numOfGuests = numOfGuests;
		this.location = location;
		this.host = host;
		this.pricePerNight = pricePerNight;
		this.checkinTime = checkinTime;
		this.checkoutTime = checkoutTime;
		this.status = status;
		this.amenities = amenities;
		this.imageSources = imageSources;
	}
	
	public Unit(String name, RoomType roomType, int numOfRooms, int numOfGuests, Location location, Host host, double pricePerNight,
			Status status, List<Amenity> amenities, List<String> imageSources) {
		super();
		this.name = name;
		this.roomType = roomType;
		this.numOfRooms = numOfRooms;
		this.numOfGuests = numOfGuests;
		this.location = location;
		this.host = host;
		this.pricePerNight = pricePerNight;
		this.checkinTime = LocalTime.of(14, 0);
		this.checkoutTime = LocalTime.of(10, 0);
		this.status = status;
		this.amenities = amenities;
		this.imageSources = imageSources;
	}
	
	public Unit(Id id, String name, RoomType roomType, int numOfRooms, int numOfGuests, Location location, Host host, double pricePerNight,
			LocalTime checkinTime, LocalTime checkoutTime, Status status, List<Amenity> amenities, List<String> imageSources) {
		super(id);
		this.name = name;
		this.roomType = roomType;
		this.numOfRooms = numOfRooms;
		this.numOfGuests = numOfGuests;
		this.location = location;
		this.host = host;
		this.pricePerNight = pricePerNight;
		this.checkinTime = checkinTime;
		this.checkoutTime = checkoutTime;
		this.status = status;
		this.amenities = amenities;
		this.imageSources = imageSources;
	}
	
	public Unit(Id id, String name, RoomType roomType, int numOfRooms, int numOfGuests, Location location, Host host, double pricePerNight,
			LocalTime checkinTime, LocalTime checkoutTime, Status status) {
		super(id);
		this.name = name;
		this.roomType = roomType;
		this.numOfRooms = numOfRooms;
		this.numOfGuests = numOfGuests;
		this.location = location;
		this.host = host;
		this.pricePerNight = pricePerNight;
		this.checkinTime = checkinTime;
		this.checkoutTime = checkoutTime;
		this.status = status;
		this.amenities = new ArrayList<Amenity>();
		this.imageSources = new ArrayList<String>();
	}
	
	public Unit(Id id, String name, RoomType roomType, int numOfRooms, int numOfGuests, Location location, Host host, double pricePerNight,
			Status status, List<Amenity> amenities, List<String> imageSources) {
		super(id);
		this.name = name;
		this.roomType = roomType;
		this.numOfRooms = numOfRooms;
		this.numOfGuests = numOfGuests;
		this.location = location;
		this.host = host;
		this.pricePerNight = pricePerNight;
		this.checkinTime = LocalTime.of(14, 0);
		this.checkoutTime = LocalTime.of(10, 0);
		this.status = status;
		this.amenities = amenities;
		this.imageSources = imageSources;
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

	public List<String> getImageSources() {
		return imageSources;
	}

	public void setImageSources(List<String> imageSources) {
		this.imageSources = imageSources;
	}

	public List<Amenity> getAmenities() {
		return amenities;
	}
	public void setAmenities(List<Amenity> amenities) {
		this.amenities = amenities;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
