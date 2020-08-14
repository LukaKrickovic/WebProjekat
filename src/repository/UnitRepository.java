package repository;

import java.io.File;
import java.util.ArrayList;

import converters.UnitConverter;
import model.ApartmentComment;
import model.Id;
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
			// TODO: Get reservations by unit u reservationRepository
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

}
