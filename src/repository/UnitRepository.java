package repository;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import converters.UnitConverter;
import exceptions.IdWriteException;
import model.*;
import sequencers.UnitSequencer;
import stream.Stream;

public class UnitRepository implements IRepository<Unit, Id>{

	private Stream stream;
	private UnitConverter unitConverter;
	private String unitFilePath = "data/Units.dat";
	private File unitFile;
	private HostRepository hostRepository;

	
	public UnitRepository(Stream stream, HostRepository hostRepository) {
		this.stream = stream;
		unitFile = new File(unitFilePath);
		unitConverter = new UnitConverter();
		this.hostRepository = hostRepository;
	}

	private Iterable<Unit> bindWithHost(Iterable<Unit> allUnits){
		ArrayList<Unit> retVal = new ArrayList<Unit>();
		for(Unit temp : allUnits) {
			Host tempHost = temp.getHost();
			Id tempId = tempHost.getId();
			tempHost = hostRepository.getById(tempId);
			temp.setHost(tempHost);
			retVal.add(temp);
		}
		return retVal;
	}

	
	@Override
	public Unit create(Unit entity) {
		try {
			checkId(entity.getId());
			stream.writeToFile(unitConverter.ConvertToJSON(entity), unitFile);
			return entity;
		} catch (IdWriteException e) {
			e.printStackTrace();
		}
		return null;
	}

	private void checkId(Id id) throws IdWriteException {
		if(getById(id) != null)
			throw new IdWriteException("Unit id already in use: " + id.toString());
	}

	@Override
	public Unit getById(Id id) {
		for(Unit temp : getAll()) {
			if(temp.getId().equals(id))
				return temp;
		}
		return null;
	}

	public Iterable<Unit> getAllUnbound() {
		ArrayList<String> allUnitsString = (ArrayList)stream.readFromFile(unitFile);
		ArrayList<Unit> allUnits= new ArrayList<Unit>();
		for(String temp : allUnitsString) {
			if(!unitConverter.ConvertFromJSON(temp).isDeleted()) {
				allUnits.add(unitConverter.ConvertFromJSON(temp));
			}
		}
		return allUnits;
	}
	
	@Override
	public Iterable<Unit> getAll() {
		ArrayList<Unit> allUnits = (ArrayList)getAllUnbound();
		if(empty(allUnits)) {
			return new ArrayList<Unit>();
		}
		allUnits = (ArrayList<Unit>) bindWithHost(allUnits);
		return allUnits;
	}

	private boolean empty(ArrayList<Unit> allUnits) {
		for(Unit temp : allUnits) {
			if(!temp.isDeleted()) {
				return false;
			}
		}
		return true;
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

		if(backup.length() > 0)
			backup.deleteCharAt(backup.length()-1);
		stream.blankOutFile(unitFile);
		stream.writeToFile(backup.toString(), unitFile);
		
	}

	@Override
	public void delete(Unit entity) {
		entity.Delete();
		update(entity);
	}


	// TODO: ReservationRepository.getByDate
	// TODO: Izmestiti funkciju u ReservationRepository i pozvati iz servisa
	/*
	public ArrayList<Unit> getByDates(LocalDate startDate, LocalDate endDate) {
		ArrayList<Unit> retVal = new ArrayList<Unit>();
		for(Unit temp : getAll()) {
			for(Reservation reservation : temp.getReservations()) {
				if(checkDates(startDate, endDate, reservation))
					retVal.add(temp);
			}
		}
		
		return retVal;
	}


	private boolean checkDates(LocalDate startDate, LocalDate endDate, Reservation reservation) {
		if(reservation.getStartDate().equals(startDate) && (reservation.getEndDate().equals(endDate)))
			return false;
		else if(reservation.getStartDate().isAfter(startDate) && reservation.getEndDate().isBefore(endDate))
			return false;
		else if(reservation.getStartDate().equals(startDate))
			return false;
		
		return true;
	}
	*/

	public ArrayList<Unit> getByLocation(String cityString, String countryString) {
		ArrayList<Unit> results = new ArrayList<Unit>();
		results = checkLocation(cityString, countryString, results);
		return results;
	}

	private ArrayList<Unit> checkLocation(String cityString, String countryString, ArrayList<Unit> results) {
		for(Unit temp : getAll()) {
			if(temp.getLocation().getAddress().getCountry().trim().equalsIgnoreCase(countryString.trim()) && temp.getLocation().getAddress().getCity().trim().equalsIgnoreCase(cityString.trim())) {
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

	@Override
	public Id findHighestId() {
		ArrayList<Unit> allUnits = (ArrayList<Unit>) getAll();
		if(allUnits.isEmpty())
			return new UnitSequencer().initialize();
		Id highestId = allUnits.get(0).getId();
		for(Unit temp : allUnits){
			if(temp.getId().getSuffix() > highestId.getSuffix()){
				highestId = temp.getId();
			}
		}
		return highestId;
	}

	public Iterable<Unit> getUnitByName(String name){
		ArrayList<Unit> allUnits = (ArrayList<Unit>) getAll();
		ArrayList<Unit> retVal = new ArrayList<Unit>();
		if(allUnits.isEmpty())
			return null;
		for(Unit temp : allUnits){
			if(temp.getName().trim().equalsIgnoreCase(name.trim()))
				retVal.add(temp);
		}

		return retVal;
	}

}
