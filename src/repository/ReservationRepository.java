package repository;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import converters.ReservationConverter;
import exceptions.IdWriteException;
import model.*;
import stream.Stream;

public class ReservationRepository implements IRepository<Reservation, Id>{

	private Stream stream;
	private ReservationConverter reservationConverter;
	private String reservationFilePath = "data/Reservations.dat";
	private File reservationFile;
	private UnitRepository unitRepository;
	private GuestRepository guestRepository;
	
	public ReservationRepository(Stream stream, UnitRepository unitRepository, GuestRepository guestRepository) {
		this.stream = stream;
		this.unitRepository = unitRepository;
		this.guestRepository = guestRepository;
		reservationConverter = new ReservationConverter();
		reservationFile = new File(reservationFilePath);
	}
	/*
	private Iterable<Reservation> bindWithUnits(Iterable<Reservation> allReservations){
		ArrayList<Reservation> retVal = new ArrayList<Reservation>();
		for(Reservation temp : allReservations) {
			System.out.println(((ArrayList<Reservation>)allReservations).size() + " Rezervacija");
			temp.setUnit(unitRepository.getById(temp.getUnit().getId()));
			retVal.add(temp);
		}
		return retVal;
	}
	*/
	private Iterable<Reservation> bindWithUnits(Iterable<Reservation> allReservations){
		ArrayList<Reservation> retVal = new ArrayList<Reservation>();
		for(Reservation temp : allReservations) {
			temp.setUnit(unitRepository.getById(temp.getUnit().getId()));
			retVal.add(temp);
		}
		return retVal;
	}
	
	
	private Iterable<Reservation> bindWithGuests(Iterable<Reservation> allReservations){
		ArrayList<Reservation> retVal = new ArrayList<Reservation>();
		for(Reservation temp : allReservations) {
			temp.setGuest(guestRepository.getById(temp.getGuest().getId()));
			retVal.add(temp);
		}
		return retVal;
	}
	
	@Override
	public Reservation create(Reservation entity) {
		try {
			checkId(entity.getId());
			stream.writeToFile(reservationConverter.ConvertToJSON(entity), reservationFile);
			return entity;
		} catch (IdWriteException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	private void checkId(Id id) throws IdWriteException {
		if(getById(id) != null)
			throw new IdWriteException("Reservation id already in use: " + id.toString());
	}

	@Override
	public Reservation getById(Id id) {
		for(Reservation temp : getAll()) {
			if(temp.getId().equals(id))
				return temp;
		}
		return null;
	}

	public Iterable<Reservation> getAllUnbound() {
		ArrayList<String> allReservationsString = (ArrayList<String>)stream.readFromFile(reservationFile);
		
		//System.out.println("There are: " + allReservationsString.size() + " strings");
		ArrayList<Reservation> allReservations= new ArrayList<Reservation>();
		
		for(String temp : allReservationsString) {
			//System.out.println("Reading line");
			if(!reservationConverter.ConvertFromJSON(temp).isDeleted())
				allReservations.add(reservationConverter.ConvertFromJSON(temp));
		}
		return allReservations;
	}
	
	@Override
	public Iterable<Reservation> getAll() {
		//System.out.println(unitRepository.getByPeopleCount(8));
		//System.out.println("Pre UNBOUND");
		ArrayList<Reservation> allReservations = (ArrayList<Reservation>)getAllUnbound();
		//System.out.println("Nabavio Unbound");
		allReservations = (ArrayList<Reservation>) bindWithUnits(allReservations);
		allReservations = (ArrayList<Reservation>) bindWithGuests(allReservations);
		return allReservations;
	}

	@Override
	public void update(Reservation entity) {
		ArrayList<Reservation> allReservations= (ArrayList)getAll();
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

		if(backup.length() > 0)
			backup.deleteCharAt(backup.length()-1);
		stream.blankOutFile(reservationFile);
		stream.writeToFile(backup.toString(), reservationFile);
		
	}

	@Override
	public void delete(Reservation entity) {
		entity.Delete();
		update(entity);
	}

	public List<Reservation> getReservationsByUnit(Unit unit) {
		ArrayList<Reservation> retVal = new ArrayList<Reservation>();
		for(Reservation temp : getAll()) {
			if(temp.getUnit().getId().equals(unit.getId())) {
				retVal.add(temp);
			}
		}
		return retVal;
	}

	public List<Unit> getUnitsByDate(LocalDate startDate, LocalDate endDate){
		List<Unit> allUnits = (ArrayList<Unit>)unitRepository.getAll();
		List<Unit> retVal = new ArrayList<Unit>();
		for(Unit temp : allUnits){
			if(checkIfUnitIsAvailable(temp, startDate, endDate))
				retVal.add(temp);
		}
		return retVal;
	}

	public boolean checkIfUnitIsAvailable(Unit unit, LocalDate startDate, LocalDate endDate){
		List<Reservation> allReservationsForUnit = getReservationsByUnit(unit);
		for(Reservation temp : allReservationsForUnit){
			if(overlapping(temp, startDate, endDate))
				return false;
		}
		return true;
	}

	private boolean overlapping(Reservation reservation, LocalDate startDate, LocalDate endDate) {
		if(reservation.getStartDate().equals(startDate) && (reservation.getEndDate().equals(endDate)))
			return true;
		else if(reservation.getStartDate().isAfter(startDate) && reservation.getEndDate().isBefore(endDate))
			return true;
		else if(reservation.getStartDate().equals(startDate))
			return true;

		return false;
	}


	public Iterable<Unit> getUnitsByGuest(Guest guest){
		List<Unit> retVal = new ArrayList<Unit>();
		for(Unit temp : unitRepository.getAll()){
			for(Reservation reservation : getReservationsByUnit(temp)){
				if(reservation.getGuest().getId().equals(guest.getId())){
					retVal.add(temp);
					break;
				}
			}
		}
		return retVal;
	}

	public Iterable<Reservation> getReservationsByGuest(Guest guest){
		List<Reservation> retVal = new ArrayList<Reservation>();
		for(Reservation temp : getAll()){
			if(temp.getGuest().getId().equals(guest.getId()))
				retVal.add(temp);
		}
		return retVal;
	}

	public Iterable<Reservation> getReservationsByGuestUsername(String username){
		List<Reservation> retVal = new ArrayList<Reservation>();
		for(Reservation temp : getAll()){
			if(temp.getGuest().getUsername().equals(username))
				retVal.add(temp);
		}
		return retVal;
	}

	public Iterable<Guest> getGuestsByRentedUnit(Unit unit){
		ArrayList<Guest> retVal = new ArrayList<Guest>();
		for(Guest temp : guestRepository.getAll()){
			for(Unit tempUnit : getUnitsByGuest(temp)){
				if(tempUnit.getId().equals(unit.getId()))
					retVal.add(temp);
			}
		}
		return retVal;
	}

	public List<Reservation> getReservationsByHost(Host host){
		List<Reservation> retVal = new ArrayList<Reservation>();
		for(Reservation temp : getAll()){
			if(temp.getUnit().getHost().getId().equals(host.getId()))
				retVal.add(temp);
		}
		return retVal;
	}

}
