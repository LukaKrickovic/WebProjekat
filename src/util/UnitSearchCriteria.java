package util;

import java.time.LocalDate;
import java.util.Date;

import model.Location;
import services.UnitService;

public class UnitSearchCriteria {

	private LocalDate startDate, endDate;
	private Location location;
	private double priceLower, priceUpper;
	private int roomCountLower, roomCountUpper;
	private int peopleCount;
	
	private String dateString, cityString, countryString, priceLowerString, priceUpperString, roomCountLowerString, roomCountUpperString, peopleCountString;

	public UnitSearchCriteria(LocalDate startDate, LocalDate endDate, String cityString, String countryString, String priceLowerString, String priceUpperString,
							  String roomCountLowerString, String roomCountUpperString, String peopleCountString) {
		super();
		if(startDate != null)
			this.startDate = startDate;
		if(endDate != null)
			this.endDate = endDate;
		this.cityString = cityString;
		this.countryString = countryString;
		if(!priceLowerString.isEmpty())
			this.priceLower = convertToDouble(priceLowerString);
		if(!priceUpperString.isEmpty())
			this.priceUpper = convertToDouble(priceUpperString);
		if(!roomCountLowerString.isEmpty())
			this.roomCountLower = convertToInt(roomCountLowerString);
		if(!roomCountUpperString.isEmpty())
			this.roomCountUpper = convertToInt(roomCountUpperString);
		if(!peopleCountString.isEmpty())
			this.peopleCount = convertToInt(peopleCountString);
	}

	public UnitSearchCriteria(String startDate, String endDate, String cityString, String countryString, String priceLowerString, String priceUpperString,
			String roomCountLowerString, String roomCountUpperString, String peopleCountString) {
		super();
		if(!startDate.isEmpty())
			this.startDate = convertToDate(startDate);
		if(!endDate.isEmpty())
			this.endDate = convertToDate(endDate);
		this.cityString = cityString;
		this.countryString = countryString;
		if(!priceLowerString.isEmpty())
			this.priceLower = convertToDouble(priceLowerString);
		if(!priceUpperString.isEmpty())
			this.priceUpper = convertToDouble(priceUpperString);
		if(!roomCountLowerString.isEmpty())
			this.roomCountLower = convertToInt(roomCountLowerString);
		if(!roomCountUpperString.isEmpty())
			this.roomCountUpper = convertToInt(roomCountUpperString);
		if(!peopleCountString.isEmpty())
			this.peopleCount = convertToInt(peopleCountString);
	}

	private LocalDate convertToDate(String string) {
		if(string != null)
			return LocalDate.parse(string);
		else
			return null;
	}

	private int convertToInt(String string) {
		if(string != null)
			return Integer.parseInt(string);
		else
			return 0;
	}

	private double convertToDouble(String string) {
		if(string != null)
			return Double.parseDouble(string);
		else
			return 0.0;
	}

	public UnitSearchCriteria(LocalDate startDate, LocalDate endDate, Location location, double priceLower, double priceUpper,
			int roomCountLower, int roomCountUpper, int peopleCount, UnitService unitService) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
		this.location = location;
		this.priceLower = priceLower;
		this.priceUpper = priceUpper;
		this.roomCountLower = roomCountLower;
		this.roomCountUpper = roomCountUpper;
		this.peopleCount = peopleCount;
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

	public double getPriceLower() {
		return priceLower;
	}

	public void setPriceLower(double priceLower) {
		this.priceLower = priceLower;
	}

	public double getPriceUpper() {
		return priceUpper;
	}

	public void setPriceUpper(double priceUpper) {
		this.priceUpper = priceUpper;
	}

	public int getRoomCountLower() {
		return roomCountLower;
	}

	public void setRoomCountLower(int roomCountLower) {
		this.roomCountLower = roomCountLower;
	}

	public int getRoomCountUpper() {
		return roomCountUpper;
	}

	public void setRoomCountUpper(int roomCountUpper) {
		this.roomCountUpper = roomCountUpper;
	}

	public int getPeopleCount() {
		return peopleCount;
	}

	public void setPeopleCount(int peopleCount) {
		this.peopleCount = peopleCount;
	}

	public String getCityString() {
		return cityString;
	}

	public void setCityString(String cityString) {
		this.cityString = cityString;
	}

	public String getCountryString() {
		return countryString;
	}

	public void setCountryString(String countryString) {
		this.countryString = countryString;
	}
	
}
