package stream;

import converters.AdministratorConverter;
import model.Administrator;
import model.Id;

public class AdministratorStream implements IStream<Administrator, Id>{

	private AdministratorConverter administratorConverter;
	
	public AdministratorStream(AdministratorConverter administratorConverter) {
		this.administratorConverter = administratorConverter;
	}

	@Override
	public Administrator create(Administrator entity) {
		String toSave = administratorConverter.ConvertToJSON(entity);
		
		return null;
	}

	@Override
	public Administrator getById(Id id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<Administrator> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub
		
	}
	
	

}
