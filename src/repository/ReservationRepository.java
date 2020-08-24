package repository;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import converters.ReservationConverter;
import exceptions.IdWriteException;
import model.Id;
import model.Reservation;
import model.Unit;
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
	}*/
	
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
		
		System.out.println("There are: " + allReservationsString.size() + " strings");
		ArrayList<Reservation> allReservations= new ArrayList<Reservation>();
		
		for(String temp : allReservationsString) {
			System.out.println("Reading line");
			if(!reservationConverter.ConvertFromJSON(temp).isDeleted())
				allReservations.add(reservationConverter.ConvertFromJSON(temp));
		}
		return allReservations;
	}
	
	@Override
	public Iterable<Reservation> getAll() {
		System.out.println(unitRepository.getByPeopleCount(8));
		System.out.println("Pre UNBOUND");
		ArrayList<Reservation> allReservations = (ArrayList<Reservation>)getAllUnbound();
		System.out.println("Nabavio Unbound");
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
		
		stream.blankOutFile(reservationFile);
		stream.writeToFile(backup.toString(), reservationFile);
		
	}

	@Override
	public void delete(Reservation entity) {
		entity.Delete();
		update(entity);
	}

	public List<Reservation> getReservationsByUnit(Unit unit) {
		ArrayList<Reservation> allReservations = (ArrayList)getAll();
		ArrayList<Reservation> retVal = new ArrayList<Reservation>();
		for(Reservation temp : allReservations) {
			if(temp.getUnit().equals(unit)) {
				retVal.add(temp);
			}
		}
		return retVal;
	}

}
