package services;

import java.util.ArrayList;

import enums.Roles;
import enums.Status;
import model.Host;
import model.Id;
import model.Unit;
import model.User;
import repository.UnitRepository;

public class UnitService {

	private UnitRepository unitRepository;

	public UnitService(UnitRepository unitRepository) {
		super();
		this.unitRepository = unitRepository;
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
		if(user.getRole().equals(Roles.ADMINISTRATOR))
			unitRepository.delete(unit);
		else if(unit.getHost().getId().equals(user.getId())) {
			unitRepository.delete(unit);
		}
	}
	
	public Unit create(Unit unit, User user) {
		if(user.getRole().equals(Roles.HOST)) {
			if(unit.getHost().getId().equals(user.getId())) {
				return unitRepository.create(unit);
			}
		}
		return null;
	}
	
	// TODO: napravi search po svim kriterijumima
	
}
