package repository;

import java.io.File;
import java.util.ArrayList;

import converters.AdministratorConverter;
import enums.Gender;
import exceptions.IdWriteException;
import model.Administrator;
import model.Guest;
import model.Id;
import stream.Stream;

public class AdministratorRepository implements IRepository<Administrator, Id>, IUserRepository<Administrator, Id>{

	private Stream stream;
	private AdministratorConverter administratorConverter;
	private String administratorFilePath = "data/Administrators.dat";
	private File administratorFile;
	
	public AdministratorRepository(Stream stream) {
		this.stream = stream;
		administratorFile = new File(administratorFilePath);
		administratorConverter = new AdministratorConverter();
	}
	
	@Override
	public Administrator create(Administrator entity) {
		try {
			checkId(entity.getId());
			stream.writeToFile(administratorConverter.ConvertToJSON(entity), administratorFile);
			return entity;
		} catch (IdWriteException e) {
			e.printStackTrace();
		}
		return null;
	}

	private void checkId(Id id) throws IdWriteException{
		if(getById(id) != null)
			throw new IdWriteException("Administrator id already in use: " + id.toString());
		
	}

	@Override
	public Administrator getById(Id id) {
		for(Administrator temp : getAll()) {
			if(temp.getId().equals(id)) {
				return temp;
			}
		}
		
		return null;
	}

	@Override
	public Iterable<Administrator> getAll() {
		ArrayList<String> allAdministratorsString = (ArrayList)stream.readFromFile(administratorFile);
		ArrayList<Administrator> allAdministrators = new ArrayList<Administrator>();
		for(String temp : allAdministratorsString) {
			if(!administratorConverter.ConvertFromJSON(temp).isDeleted()) {
				allAdministrators.add(administratorConverter.ConvertFromJSON(temp));
			}
		}
		return allAdministrators;
	}

	@Override
	public void update(Administrator entity) {
		ArrayList<Administrator> allAdministrators = (ArrayList<Administrator>)getAll();
		StringBuilder backup = new StringBuilder();
		for(Administrator temp : allAdministrators) {
			if(!temp.getId().equals(entity.getId())) {
				backup.append(administratorConverter.ConvertToJSON(temp));
				backup.append("\n");
			} else {
				backup.append(administratorConverter.ConvertToJSON(entity));
				backup.append("\n");
			}
		}

		backup.deleteCharAt(backup.length()-1);
		stream.blankOutFile(administratorFile);
		stream.writeToFile(backup.toString(), administratorFile);
		
	}

	@Override
	public void delete(Administrator entity) {
		entity.Delete();
		update(entity);
	}

	@Override
	public Administrator getUserByUsername(String username) {
		ArrayList<Administrator> allAdministrators= (ArrayList)getAll();
		for(Administrator temp : allAdministrators) {
			if(temp.getUsername().equals(username))
				return temp;
		}
		
		return null;
	}

	@Override
	public Iterable<Administrator> getUsersByName(String name) {
		ArrayList<Administrator> retVal = new ArrayList<Administrator>();
		ArrayList<Administrator> allAdministrators= (ArrayList)getAll();
		for(Administrator temp : allAdministrators) {
			if(temp.getName().equals(name)) {
				retVal.add(temp);
			}
		}
		
		return retVal;
	}

	@Override
	public Iterable<Administrator> getUsersBySurname(String surname) {
		ArrayList<Administrator> retVal = new ArrayList<Administrator>();
		ArrayList<Administrator> allAdministrators= (ArrayList)getAll();
		for(Administrator temp : allAdministrators) {
			if(temp.getSurname().equals(surname)) {
				retVal.add(temp);
			}
		}
		
		return retVal;
	}

	@Override
	public Iterable<Administrator> getUsersByGender(Gender gender) {
		ArrayList<Administrator> retVal = new ArrayList<Administrator>();
		ArrayList<Administrator> allAdministrators= (ArrayList)getAll();
		for(Administrator temp : allAdministrators) {
			if(temp.getGender().equals(gender)) {
				retVal.add(temp);
			}
		}
		
		return retVal;
	}

	@Override
	public Iterable<Administrator> getUsersByNameAndSurname(String name, String surname) {
		ArrayList<Administrator> retVal = new ArrayList<Administrator>();
		ArrayList<Administrator> allAdministrators= (ArrayList)getAll();
		for(Administrator temp : allAdministrators) {
			if(temp.getName().equals(name)) {
				if(temp.getSurname().equals(surname)) {
					retVal.add(temp);	
				}	
			}
		}
		
		return retVal;
	}

	
	
}
