package test;

import enums.Gender;
import model.Administrator;
import repository.AdministratorRepository;
import sequencers.AdministratorSequencer;
import stream.Stream;

public class UniqueIdTest {

	public static void main(String[] args) {
		Stream stream = new Stream();
		AdministratorRepository ar = new AdministratorRepository(stream);

		Administrator a1 = new Administrator(new AdministratorSequencer().initialize(), "admin", "admin", "Luka", "Krickovic", Gender.MALE);
		
		ar.create(a1);
	}

}
