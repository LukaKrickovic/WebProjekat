package util;

import java.time.LocalDate;

import model.Location;
import services.UnitService;

public class UnitSearchCriteria {

	private LocalDate date;
	private Location location;
	private double priceLower, priceUpper;
	private int roomCountLower, roomCountUpper;
	private int peopleCount;
	
	private UnitService unitService;
	
	private String dateString, cityString, countryString, priceLowerString, priceUpperString, roomCountLowerString, roomCountUpperString, peopleCountString;

	public UnitSearchCriteria(String cityString, String countryString, String priceLowerString, String priceUpperString,
			String roomCountLowerString, String roomCOuntUpperString, String peopleCountString, UnitService unitService) {
		super();
		this.cityString = cityString;
		this.countryString = countryString;
		this.priceLowerString = priceLowerString;
		this.priceUpperString = priceUpperString;
		this.roomCountLowerString = roomCountLowerString;
		this.roomCountUpperString = roomCountUpperString;
		this.peopleCountString = peopleCountString;
		this.unitService = unitService;
	}

	public UnitSearchCriteria(LocalDate date, Location location, double priceLower, double priceUpper,
			int roomCountLower, int roomCountUpper, int peopleCount, UnitService unitService) {
		super();
		this.date = date;
		this.location = location;
		this.priceLower = priceLower;
		this.priceUpper = priceUpper;
		this.roomCountLower = roomCountLower;
		this.roomCountUpper = roomCountUpper;
		this.peopleCount = peopleCount;
	}
	
	/*
	public void search() {
		List retVal;
		
		if(date != null)
			retVal.add(pretragaDate);
		if(location != null)
			retVal.add(retVal.pretragaDate);
	}*/
	
	
	
	
}
