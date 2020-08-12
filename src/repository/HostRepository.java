package repository;

import java.io.File;
import java.util.ArrayList;

import converters.HostConverter;
import model.Administrator;
import model.Host;
import model.Id;
import stream.Stream;

public class HostRepository implements IRepository<Host, Id>{

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

}
