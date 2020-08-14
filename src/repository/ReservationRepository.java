package repository;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import converters.ReservationConverter;
import model.Id;
import model.Reservation;
import model.Unit;
import stream.Stream;

public class ReservationRepository implements IRepository<Reservation, Id>{

	private Stream stream;
	private ReservationConverter reservationConverter;
	private String reservationFilePath = "data/Reservations.txt";
	private File reservationFile;
	private UnitRepository unitRepository;
	private GuestRepository guestRepository;
	
	public ReservationRepository(Stream stream, UnitRepository unitRepository, GuestRepository guestRepository) {
		this.stream = stream;
		this.unitRepository = unitRepository;
		this.guestRepository = guestRepository;
	}
	
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
		stream.writeToFile(reservationConverter.ConvertToJSON(entity), reservationFile);
		return entity;
	}

	@Override
	public Reservation getById(Id id) {
		ArrayList<Reservation> allReservations= (ArrayList)getAll();
		for(Reservation temp : allReservations) {
			if(temp.getId().equals(id))
				return temp;
		}
		return null;
	}

	public Iterable<Reservation> getAllUnbound() {
		ArrayList<String> allReservationsString = (ArrayList)stream.readFromFile(reservationFile);
		ArrayList<Reservation> allReservations= new ArrayList<Reservation>();
		for(String temp : allReservationsString) {
			allReservations.add(reservationConverter.ConvertFromJSON(temp));
		}
		return allReservations;
	}
	
	@Override
	public Iterable<Reservation> getAll() {
		ArrayList<Reservation> allReservations= (ArrayList)getAllUnbound();
		allReservations= (ArrayList) bindWithUnits(allReservations);
		allReservations= (ArrayList) bindWithGuests(allReservations);
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
