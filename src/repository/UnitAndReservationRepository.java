package repository;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import converters.ReservationConverter;
import converters.UnitConverter;
import exceptions.IdWriteException;
import model.ApartmentComment;
import model.Host;
import model.Id;
import model.Reservation;
import model.Unit;
import sequencers.ReservationSequencer;
import stream.Stream;
import util.UnitAndResRepositoryStrings;

public class UnitAndReservationRepository implements IRepository<Unit, Id>{
	private Stream stream;
	private UnitConverter unitConverter;
	private String unitAndResFilePath = "data/UnitsAndReservations.dat";
	private File unitAndResFile;
	private HostRepository hostRepository;
	private ApartmentCommentRepository apartmentCommentRepository;
	
	private ReservationConverter reservationConverter;
	private GuestRepository guestRepository;
	
	public UnitAndReservationRepository(Stream stream, HostRepository hostRepository,
			ApartmentCommentRepository apartmentCommentRepository, GuestRepository guestRepository) {
		super();
		this.stream = stream;
		this.unitConverter = new UnitConverter();
		unitAndResFile = new File(unitAndResFilePath);
		this.hostRepository = hostRepository;
		this.apartmentCommentRepository = apartmentCommentRepository;
		this.reservationConverter = new ReservationConverter();
		this.guestRepository = guestRepository;
	}

	@Override
	public Unit create(Unit entity) {
		try {
			checkId(entity.getId());
			stream.writeToFile(unitConverter.ConvertToJSON(entity), unitAndResFile);
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
	
	public Reservation create(Reservation entity) {
		try {
			checkReservationId(entity.getId());
			stream.writeToFile(reservationConverter.ConvertToJSON(entity), unitAndResFile);
			return entity;
		} catch (IdWriteException e) {
			e.printStackTrace();
		}
		return null;
	}

	private void checkReservationId(Id id) throws IdWriteException {
		if(getReservationById(id) != null)
			throw new IdWriteException("Reservation id already in use: " + id.toString());
	}
	

	private Reservation getReservationById(Id id) {
		for(Reservation temp : getAllReservations()) {
			if(temp.getId().equals(id))
				return temp;
		}
		return null;
	}

	@Override
	public Unit getById(Id id) {
		for(Unit temp : getAll()) {
			if(temp.getId().equals(id))
				return temp;
		}
		return null;
	}

	private Iterable<Reservation> getAllUnboundReservations(){
		ArrayList<String> allObjectsString = (ArrayList<String>)stream.readFromFile(unitAndResFile);
		ArrayList<String> allReservationsString = new ArrayList<String>();
		ArrayList<Reservation> allReservations = new ArrayList<Reservation>();
		
		allReservationsString = getReservationStrings(allObjectsString, allReservationsString);	
		allReservations = getReservationObjects(allReservationsString, allReservations);
		
		return allReservations;
		
	}
	
	private ArrayList<Reservation> getReservationObjects(ArrayList<String> allReservationsString,
			ArrayList<Reservation> allReservations) {
		for(String line : allReservationsString) {
			if(!reservationConverter.ConvertFromJSON(line).isDeleted()) {
				allReservations.add(reservationConverter.ConvertFromJSON(line));
			}
		}
		return allReservations;
	}

	private ArrayList<String> getReservationStrings(ArrayList<String> allObjectsString,
			ArrayList<String> allReservationsString) {
		for(String object : allObjectsString) {
			String[] bits = object.split(UnitAndResRepositoryStrings.delimiter);
			System.out.println(bits[0]);
			if(bits[0].equals(UnitAndResRepositoryStrings.reservationStringSUB)) {
				allReservationsString.add(bits[1]);
			}
		}
		return allReservationsString;
	}

	private Iterable<Unit> getAllUnboundUnits(){
		ArrayList<String> allObjectsString = (ArrayList<String>)stream.readFromFile(unitAndResFile);
		ArrayList<String> allUnitsString = new ArrayList<String>();
		ArrayList<Unit> allUnits= new ArrayList<Unit>();
		
		allUnitsString = getUnitStrings(allObjectsString, allUnitsString);	
		allUnits = getUnitObjects(allUnitsString, allUnits);
		
		return allUnits;
		
	}

	private ArrayList<Unit> getUnitObjects(ArrayList<String> allUnitsString, ArrayList<Unit> allUnits) {
		for(String line : allUnitsString) {
			if(!unitConverter.ConvertFromJSON(line).isDeleted()) {
				allUnits.add(unitConverter.ConvertFromJSON(line));
			}
		}
		return allUnits;
	}

	private ArrayList<String> getUnitStrings(ArrayList<String> allObjectsString, ArrayList<String> allUnitsString) {
		for(String object : allObjectsString) {
			String[] bits = object.split(UnitAndResRepositoryStrings.delimiter);
			if(bits[0].equals(UnitAndResRepositoryStrings.unitStringSUB)) {
				allUnitsString.add(bits[1]);
			}
		}
		return allUnitsString;
	}
	
	@Override
	public Iterable<Unit> getAll() {
		Iterable<Unit> allUnits = getAllUnboundUnits();
		if(empty(allUnits)) {
			return new ArrayList<Unit>();
		}
		allUnits = bindUnitsWithHosts(allUnits);
		allUnits = bindUnitsWithApartmentComments(allUnits);
		return allUnits;
	}
	
	public Iterable<Reservation> getAllReservations() {
		Iterable<Reservation> allReservations = getAllUnboundReservations();
		allReservations = bindReservationsWithGuests(allReservations);
		return allReservations;
	}

	private Iterable<Reservation> bindReservationsWithGuests(Iterable<Reservation> allReservations) {
		ArrayList<Reservation> retVal = new ArrayList<Reservation>();
		for(Reservation temp : allReservations) {
			temp.setGuest(guestRepository.getById(temp.getGuest().getId()));
			retVal.add(temp);
		}
		return retVal;
	}

	private Iterable<Unit> bindUnitsWithApartmentComments(Iterable<Unit> allUnits) {
		ArrayList<Unit> retVal = new ArrayList<Unit>();
		for(Unit temp : allUnits) {
			ArrayList<ApartmentComment> comments = new ArrayList<ApartmentComment>();
			for(ApartmentComment comment : temp.getApartmentComment()) {
				comments.add(apartmentCommentRepository.getById(comment.getId()));
			}
			temp.setApartmentComment(comments);
			retVal.add(temp);
		}
		return retVal;
	}

	private Iterable<Unit> bindUnitsWithHosts(Iterable<Unit> allUnits) {
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
	public void update(Unit entity) {
		ArrayList<Unit> allUnits = (ArrayList<Unit>)getAll();
		ArrayList<Reservation> allReservations = (ArrayList<Reservation>)getAllReservations();

		StringBuilder backup = new StringBuilder();
		
		for(Unit temp : allUnits) {
			if(!temp.getId().equals(entity.getId())) {
				backup.append(unitConverter.ConvertToJSON(temp));
				backup.append("\n");
			} else {
				System.out.println("TIP: " + entity.toString());
				backup.append(unitConverter.ConvertToJSON(entity));
				backup.append("\n");
			}
		}
		
		for(Reservation res : allReservations) {
			backup.append(reservationConverter.ConvertToJSON(res));
			backup.append("\n");
		}
		
		backup.deleteCharAt(backup.length()-1);
		stream.blankOutFile(unitAndResFile);
		stream.writeToFile(backup.toString(), unitAndResFile);
		
	}

	@Override
	public void delete(Unit entity) {
		entity.Delete();
		update(entity);
	}
	
	/*
	public void updateReservation(Reservation entity) {
		ArrayList<Reservation> allReservations= (ArrayList<Reservation>)getAllReservations();
		StringBuilder backup = new StringBuilder();
		for(Reservation temp : allReservations) {
			if(!temp.getId().equals(entity.getId())) {
				backup.append(reservationConverter.ConvertToJSON(temp));
				backup.append("\n");
			} else {
				backup.append(reservationConverter.ConvertToJSON(entity));
				backup.append("\n");
			}
		}
		
		stream.blankOutFile(unitAndResFile);
		stream.writeToFile(backup.toString(), unitAndResFile);
		
	}
	*/
	
	public void updateReservation(Reservation entity) {
		ArrayList<Reservation> allReservations = (ArrayList<Reservation>)getAllReservations();
		ArrayList<Unit> allUnits = (ArrayList<Unit>)getAll();
		
		stream.blankOutFile(unitAndResFile);
		
		create(entity);
		
		for(Reservation temp : allReservations) {
			if(!temp.getId().equals(entity.getId())) {
				create(temp);
			}
		}
		
		for(Unit unit : allUnits) {
			create(unit);
		}
		
	}

	public void delete(Reservation entity) {
		entity.Delete();
		updateReservation(entity);
	}
	
	public List<Reservation> getReservationsByUnit(Unit unit) {
		ArrayList<Reservation> allReservations = (ArrayList<Reservation>)getAllReservations();
		ArrayList<Reservation> retVal = new ArrayList<Reservation>();
		for(Reservation temp : allReservations) {
			if(temp.getUnit().equals(unit)) {
				retVal.add(temp);
			}
		}
		return retVal;
	}
	
	private boolean empty(Iterable<Unit> allUnits) {
		for(Unit temp : allUnits) {
			if(!temp.isDeleted()) {
				return false;
			}
		}
		return true;
	}
	
	//	Search
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

	public ArrayList<Unit> getByLocation(String cityString, String countryString) {
		ArrayList<Unit> results = new ArrayList<Unit>();
		results = checkLocation(cityString, countryString, results);
		return results;
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
