package services;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

import enums.Roles;
import enums.Status;
import model.Host;
import model.Id;
import model.Unit;
import model.User;
import repository.ReservationRepository;
import repository.UnitRepository;
import util.UnitReservationDTO;
import util.UnitSearchCriteria;

public class UnitService {

	private UnitRepository unitRepository;
	private ReservationRepository reservationRepository;

	public UnitService(UnitRepository unitRepository, ReservationRepository reservationRepository) {
		super();
		this.unitRepository = unitRepository;
		this.reservationRepository = reservationRepository;
	}
	
	public Unit getUnitById(Id id) {
		return unitRepository.getById(id);
	}
	
	public Iterable<Unit> getAllUnits(){
		return unitRepository.getAll();
	}
	
	public Iterable<Unit> getAllActiveUnits(){
		ArrayList<Unit> retVal = new ArrayList<Unit>();
		for(Unit temp : unitRepository.getAll()) {
			if(temp.getStatus().equals(Status.ACTIVE)) {
				retVal.add(temp);
			}
		}
		return retVal;
	}
	
	public Iterable<Unit> getAllInactiveUnits(){
		ArrayList<Unit> retVal = new ArrayList<Unit>();
		for(Unit temp : unitRepository.getAll()) {
			if(temp.getStatus().equals(Status.INACTIVE)) {
				retVal.add(temp);
			}
		}
		return retVal;
	}
	
	public void update(Unit unit, User user) {
		if(user.getRole().equals(Roles.ADMINISTRATOR))
			unitRepository.update(unit);
		else if(unit.getHost().getId().equals(user.getId())) {
			unitRepository.update(unit);
		}
	}
	
	
	public void delete(Unit unit, User user) {
		if(user.getId().getPrefix().equalsIgnoreCase("A"))
			unitRepository.delete(unit);
		else if(unit.getHost().getId().equals(user.getId())) {
			unitRepository.delete(unit);
		}
	}
	
	public Unit create(Unit unit, User user) {
			return unitRepository.create(unit);
	}

	public ArrayList<Unit> copy(ArrayList<Unit> source){
		ArrayList<Unit> retVal = new ArrayList<Unit>();

		for(Unit temp : source){
			retVal.add(temp);
		}

		return retVal;
	}

	public Iterable<Unit> searchByCriteria(UnitSearchCriteria unitSearchCriteria){
		ArrayList<Unit> retVal = new ArrayList<Unit>();
		ArrayList<Unit> backup = new ArrayList<Unit>();
		ArrayList<Unit> backup2 = new ArrayList<Unit>();

		retVal = checkLocation(unitSearchCriteria, retVal);
		backup= copy(retVal);
		backup2 = new ArrayList<Unit>();
		if (unitSearchCriteria.getStartDate() != null && unitSearchCriteria.getEndDate() != null) {
			retVal = and(backup, checkDate(unitSearchCriteria, backup2));
			backup = copy(retVal);
			backup2 = new ArrayList<Unit>();
		}
		if(unitSearchCriteria.getPriceLower() != 0.0 && unitSearchCriteria.getPriceUpper() != 0.0) {
			retVal = and(backup, checkPrice(unitSearchCriteria, backup2));
			backup = copy(retVal);
			backup2 = new ArrayList<Unit>();
		}

		if(unitSearchCriteria.getRoomCountLower() != 0 && unitSearchCriteria.getRoomCountUpper() != 0) {
			retVal = and(backup, checkRoomCount(unitSearchCriteria, backup2));
			backup = copy(retVal);
			backup2 = new ArrayList<Unit>();
		}
		if(unitSearchCriteria.getPeopleCount() != 0) {
			retVal = and(backup, checkPeopleCount(unitSearchCriteria, backup2));
		}
		
		return unique(retVal);
	}

	private Iterable<Unit> unique(ArrayList<Unit> retVal) {
		ArrayList<Unit> result = new ArrayList<Unit>();
		for(Unit temp : retVal) {
			if(!result.contains(temp)) {
				result.add(temp);
			}
		}
		return result;
	}

	private ArrayList<Unit> checkPeopleCount(UnitSearchCriteria unitSearchCriteria, ArrayList<Unit> retVal) {
		if(unitSearchCriteria.getPeopleCount() != 0) {
			retVal.addAll(and(retVal, unitRepository.getByPeopleCount(unitSearchCriteria.getPeopleCount())));
		}
		return retVal;
	}

	private boolean containsUnit(Unit temp, ArrayList<Unit> list){
		for(Unit u : list){
			if(u.getId().equals(temp.getId()))
				return true;
		}

		return false;
	}

	private ArrayList<Unit> and(ArrayList<Unit> previousResult, ArrayList<Unit> newList) {
		ArrayList<Unit> result = new ArrayList<Unit>();
		if(previousResult.isEmpty())
			return new ArrayList<Unit>();
		if(newList.isEmpty())
			return new ArrayList<Unit>();
		
		for(Unit temp : newList) {
			if(containsUnit(temp, previousResult)) {
				result.add(temp);
			}
		}

		for(Unit temp : previousResult){
			if(!newList.contains(temp)){
				result.remove(temp);
			}
		}

		return result;
	}

	private ArrayList<Unit> checkRoomCount(UnitSearchCriteria unitSearchCriteria, ArrayList<Unit> retVal) {
		if(unitSearchCriteria.getRoomCountLower() != 0 || unitSearchCriteria.getRoomCountUpper() != 0) {
			retVal.addAll(unitRepository.getByRoomCount(unitSearchCriteria.getRoomCountLower(), unitSearchCriteria.getRoomCountUpper()));
		}
		return retVal;
	}

	private ArrayList<Unit> checkPrice(UnitSearchCriteria unitSearchCriteria, ArrayList<Unit> retVal) {
		if(unitSearchCriteria.getPriceLower() != 0.0 || unitSearchCriteria.getPriceUpper() != 0.0) {
			retVal.addAll(unitRepository.getByPrice(unitSearchCriteria.getPriceLower(), unitSearchCriteria.getPriceUpper()));
		}
		return retVal;
	}

	private ArrayList<Unit> checkLocation(UnitSearchCriteria unitSearchCriteria, ArrayList<Unit> retVal) {
		if(!unitSearchCriteria.getCityString().isEmpty() && !unitSearchCriteria.getCountryString().isEmpty()) {
			retVal.addAll(unitRepository.getByLocation(unitSearchCriteria.getCityString(), unitSearchCriteria.getCountryString()));
		}
		return retVal;
	}

	private ArrayList<Unit> checkDate(UnitSearchCriteria unitSearchCriteria, ArrayList<Unit> retVal) {
		if(unitSearchCriteria.getStartDate() != null && unitSearchCriteria.getEndDate() != null) {
			retVal.addAll(reservationRepository.getUnitsByDate(unitSearchCriteria.getStartDate(), unitSearchCriteria.getEndDate()));
		}
		return retVal;
	}

	private Iterable<Unit> getUnitByName(String name){
		return unitRepository.getUnitByName(name);
	}

	public Iterable<Unit> getUnitsByHost(Host user){
		return unitRepository.getUnitsByHost(user);
	}

	public ArrayList<UnitReservationDTO> getNewReservationCount(Host host){
		ArrayList<UnitReservationDTO> retVal = new ArrayList<UnitReservationDTO>();
		for(Unit temp : unitRepository.getUnitsByHost(host)){
			retVal.add(new UnitReservationDTO(temp.getId(), reservationRepository.getReservationsByUnit(temp).size()));
		}

		return retVal;
	}

}
