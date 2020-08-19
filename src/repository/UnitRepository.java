package repository;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

import converters.UnitConverter;
import model.Id;
import model.Reservation;
import model.Unit;
import stream.Stream;

public class UnitRepository implements IRepository<Unit, Id>{

	private Stream stream;
	private UnitConverter unitConverter;
	private String unitFilePath = "data/ApartmentComments.txt";
	private File unitFile;
	private ReservationRepository reservationRepository;
	
	public UnitRepository(Stream stream, ReservationRepository reservationRepository) {
		this.stream = stream;
		unitFile = new File(unitFilePath);
		unitConverter = new UnitConverter();
		this.reservationRepository = reservationRepository;
	}
	
	public UnitRepository(Stream stream, GuestRepository guestRepository) {
		this.stream = stream;
		unitFile = new File(unitFilePath);
		unitConverter = new UnitConverter();
		this.reservationRepository = new ReservationRepository(this.stream, this, guestRepository);
	}
	
	private Iterable<Unit> bindWithReservations(Iterable<Unit> allUnits){
		ArrayList<Unit> retVal = new ArrayList<Unit>();
		for(Unit temp : allUnits) {
			temp.setReservations(reservationRepository.getReservationsByUnit(temp));
			retVal.add(temp);
		}
		return retVal;
	}
	
	@Override
	public Unit create(Unit entity) {
		stream.writeToFile(unitConverter.ConvertToJSON(entity), unitFile);
		return entity;
	}

	@Override
	public Unit getById(Id id) {
		ArrayList<Unit> allUnits = (ArrayList)getAll();
		for(Unit temp : allUnits) {
			if(temp.getId().equals(id))
				return temp;
		}
		return null;
	}

	public Iterable<Unit> getAllUnbound() {
		ArrayList<String> allUnitsString = (ArrayList)stream.readFromFile(unitFile);
		ArrayList<Unit> allUnits= new ArrayList<Unit>();
		for(String temp : allUnitsString) {
			allUnits.add(unitConverter.ConvertFromJSON(temp));
		}
		return allUnits;
	}
	
	@Override
	public Iterable<Unit> getAll() {
		ArrayList<Unit> allUnits = (ArrayList)getAllUnbound();
		allUnits = (ArrayList) bindWithReservations(allUnits);
		return allUnits;
	}

	@Override
	public void update(Unit entity) {
		ArrayList<Unit> allUnits= (ArrayList)getAll();
		StringBuilder backup = new StringBuilder();
		for(Unit temp : allUnits) {
			if(!temp.getId().equals(entity.getId())) {
				backup.append(unitConverter.ConvertToJSON(temp));
				backup.append("\n");
			} else {
				backup.append(unitConverter.ConvertToJSON(entity));
				backup.append("\n");
			}
		}
		
		stream.blankOutFile(unitFile);
		stream.writeToFile(backup.toString(), unitFile);
		
	}

	@Override
	public void delete(Unit entity) {
		entity.Delete();
		update(entity);
	}



	public ArrayList<Unit> getByDates(Date startDate, Date endDate) {
		ArrayList<Unit> retVal = new ArrayList<Unit>();
		for(Unit temp : getAll()) {
			for(Reservation reservation : temp.getReservations()) {
				if(checkDates(startDate, endDate, reservation))
					retVal.add(temp);
			}
		}
		
		return retVal;
	}

	private boolean checkDates(Date startDate, Date endDate, Reservation reservation) {
		if(reservation.getStartDate().equals(startDate) && (reservation.getEndDate().equals(endDate)))
			return false;
		else if(reservation.getStartDate().after(startDate) && reservation.getEndDate().before(endDate))
			return false;
		else if(reservation.getStartDate().equals(startDate))
			return false;
		
		return true;
	}

	public ArrayList<Unit> getByLocation(String cityString, String countryString) {
		ArrayList<Unit> results = new ArrayList<Unit>();
		results = checkLocation(cityString, countryString, results);
		return null;
	}

	private ArrayList<Unit> checkLocation(String cityString, String countryString, ArrayList<Unit> results) {
		for(Unit temp : getAll()) {
			if(temp.getLocation().getAddress().getCountry().equals(countryString) && temp.getLocation().getAddress().getCity().equals(cityString)) {
				results.add(temp);
			}
		}
		return results;
	}


	public ArrayList<Unit> getByPrice(double priceLower, double priceUpper) {
		ArrayList<Unit> retVal = new ArrayList<Unit>();
		for(Unit temp : getAll()) {
			if(temp.getPricePerNight() >= priceLower && temp.getPricePerNight() <= priceUpper)
				retVal.add(temp);
		}
		return retVal;
	}

	public ArrayList<Unit> getByRoomCount(int roomCountLower, int roomCountUpper) {
		ArrayList<Unit> retVal = new ArrayList<Unit>();
		for(Unit temp : getAll()) {
			if(temp.getNumOfRooms() >= roomCountLower && temp.getNumOfRooms() <= roomCountUpper)
				retVal.add(temp);
		}
		return retVal;
	}

	public ArrayList<Unit> getByPeopleCount(int peopleCount) {
		ArrayList<Unit> retVal = new ArrayList<Unit>();
		for(Unit temp : getAll()) {
			if(temp.getNumOfGuests() == peopleCount)
				retVal.add(temp);
		}
		return retVal;
	}

}
