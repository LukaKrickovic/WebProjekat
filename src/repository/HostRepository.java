package repository;

import java.io.File;
import java.util.ArrayList;

import converters.HostConverter;
import enums.Gender;
import model.Administrator;
import model.Guest;
import model.Host;
import model.Id;
import stream.Stream;

public class HostRepository implements IRepository<Host, Id>, IUserRepository<Host, Id>{

	private Stream stream;
	private HostConverter hostConverter;
	private String hostFilePath = "data/Hosts.txt";
	private File hostFile;
	
	public HostRepository(Stream stream) {
		this.stream = stream;
		this.hostConverter = new HostConverter();
		hostFile = new File(hostFilePath);
	}
	
	@Override
	public Host create(Host entity) {
		stream.writeToFile(hostConverter.ConvertToJSON(entity), hostFile);
		return entity;
	}

	@Override
	public Host getById(Id id) {
		ArrayList<Host> allHosts= new ArrayList<Host>();
		for(Host temp : allHosts) {
			if(temp.getId().equals(id)) {
				return temp;
			}
		}
		
		return null;
	}

	@Override
	public Iterable<Host> getAll() {
		ArrayList<String> allHostsString = (ArrayList)stream.readFromFile(hostFile);
		ArrayList<Host> allHosts= new ArrayList<Host>();
		for(String temp : allHostsString) {
			allHosts.add(hostConverter.ConvertFromJSON(temp));
		}
		return allHosts;
	}

	@Override
	public void update(Host entity) {
		ArrayList<Host> allHosts= (ArrayList)getAll();
		StringBuilder backup = new StringBuilder();
		for(Host temp : allHosts) {
			if(!temp.getId().equals(entity.getId())) {
				backup.append(hostConverter.ConvertToJSON(temp));
				backup.append("\n");
			} else {
				backup.append(hostConverter.ConvertToJSON(entity));
				backup.append("\n");
			}
		}
		
		stream.blankOutFile(hostFile);
		stream.writeToFile(backup.toString(), hostFile);
		
	}

	@Override
	public void delete(Host entity) {
		entity.Delete();
		update(entity);
		
	}

	@Override
	public Host getUserByUsername(String username) {
		ArrayList<Host> allHosts= (ArrayList)getAll();
		for(Host temp : allHosts) {
			if(temp.getUsername().equals(username))
				return temp;
		}
		
		return null;
	}

	@Override
	public Iterable<Host> getUsersByName(String name) {
		ArrayList<Host> retVal = new ArrayList<Host>();
		ArrayList<Host> allHosts= (ArrayList)getAll();
		for(Host temp : allHosts) {
			if(temp.getName().equals(name)) {
				retVal.add(temp);
			}
		}
		
		return retVal;
	}

	@Override
	public Iterable<Host> getUsersBySurname(String surname) {
		ArrayList<Host> retVal = new ArrayList<Host>();
		ArrayList<Host> allHosts= (ArrayList)getAll();
		for(Host temp : allHosts) {
			if(temp.getSurname().equals(surname)) {
				retVal.add(temp);
			}
		}
		
		return retVal;
	}

	@Override
	public Iterable<Host> getUsersByGender(Gender gender) {
		ArrayList<Host> retVal = new ArrayList<Host>();
		ArrayList<Host> allHosts= (ArrayList)getAll();
		for(Host temp : allHosts) {
			if(temp.getGender().equals(gender)) {
				retVal.add(temp);
			}
		}
		
		return retVal;
	}

	@Override
	public Iterable<Host> getUsersByNameAndSurname(String name, String surname) {
		ArrayList<Host> retVal = new ArrayList<Host>();
		ArrayList<Host> allHosts= (ArrayList)getAll();
		for(Host temp : allHosts) {
			if(temp.getName().equals(name)) {
				if(temp.getSurname().equals(surname)) {
					retVal.add(temp);	
				}	
			}
		}
		
		return retVal;
	}
	

}
