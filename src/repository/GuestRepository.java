package repository;

import java.io.File;
import java.util.ArrayList;

import converters.GuestConverter;
import enums.Gender;
import model.Guest;
import model.Id;
import model.Unit;
import stream.Stream;

public class GuestRepository implements IRepository<Guest, Id>, IUserRepository<Guest, Id>{	
	private Stream stream;
	private GuestConverter guestConverter;
	private String guestFilePath = "data/Guests.txt";
	private File guestFile;
	
	public GuestRepository(Stream stream) {
		this.stream = stream;
		guestFile = new File(guestFilePath);
		guestConverter = new GuestConverter();
	}
	

	@Override
	public Guest create(Guest entity) {
		stream.writeToFile(guestConverter.ConvertToJSON(entity), guestFile);
		return entity;
	}

	@Override
	public Guest getById(Id id) {
		ArrayList<Guest> allGuests= new ArrayList<Guest>();
		for(Guest temp : allGuests) {
			if(temp.getId().equals(id)) {
				return temp;
			}
		}
		
		return null;
	}

	@Override
	public void update(Guest entity) {
		ArrayList<Guest> allGuests= (ArrayList)getAll();
		StringBuilder backup = new StringBuilder();
		for(Guest temp : allGuests) {
			if(!temp.getId().equals(entity.getId())) {
				backup.append(guestConverter.ConvertToJSON(temp));
				backup.append("\n");
			} else {
				backup.append(guestConverter.ConvertToJSON(entity));
				backup.append("\n");
			}
		}
		
		stream.blankOutFile(guestFile);
		stream.writeToFile(backup.toString(), guestFile);
		
	}

	@Override
	public void delete(Guest entity) {
		entity.Delete();
		update(entity);
				
	}


	@Override
	public Iterable<Guest> getAll() {
		ArrayList<String> allGuestsString = (ArrayList)stream.readFromFile(guestFile);
		ArrayList<Guest> allGuests= new ArrayList<Guest>();
		for(String temp : allGuestsString) {
			allGuests.add(guestConverter.ConvertFromJSON(temp));
		}
		return allGuests;
	}



	@Override
	public Guest getUserByUsername(String username) {
		ArrayList<Guest> allGuests = (ArrayList)getAll();
		for(Guest temp : allGuests) {
			if(temp.getUsername().equals(username))
				return temp;
		}
		
		return null;
	}


	@Override
	public Iterable<Guest> getUsersByName(String name) {
		ArrayList<Guest> retVal = new ArrayList<Guest>();
		ArrayList<Guest> allGuests = (ArrayList)getAll();
		for(Guest temp : allGuests) {
			if(temp.getName().equals(name)) {
				retVal.add(temp);
			}
		}
		
		return retVal;
	}


	@Override
	public Iterable<Guest> getUsersBySurname(String surname) {
		ArrayList<Guest> retVal = new ArrayList<Guest>();
		ArrayList<Guest> allGuests = (ArrayList)getAll();
		for(Guest temp : allGuests) {
			if(temp.getSurname().equals(surname)) {
				retVal.add(temp);
			}
		}
		
		return retVal;
	}


	@Override
	public Iterable<Guest> getUsersByGender(Gender gender) {
		ArrayList<Guest> retVal = new ArrayList<Guest>();
		ArrayList<Guest> allGuests = (ArrayList)getAll();
		for(Guest temp : allGuests) {
			if(temp.getGender().equals(gender)) {
				retVal.add(temp);
			}
		}
		
		return retVal;
	}
	
	public Iterable<Guest> getGuestsByRentedUnit(Unit unit){
		ArrayList<Guest> retVal = new ArrayList<Guest>();
		ArrayList<Guest> allGuests = (ArrayList)getAll();
		for(Guest temp : allGuests) {
			if(temp.getRentedUnits().contains(unit))
				retVal.add(temp);
		}
		return retVal;
	}

}
