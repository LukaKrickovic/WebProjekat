package test;

import java.util.ArrayList;

import enums.RoomType;
import model.Guest;
import model.Host;
import model.Id;
import model.Location;
import model.Unit;
import repository.GuestRepository;
import repository.ReservationRepository;
import repository.UnitRepository;
import sequencers.GuestSequencer;
import sequencers.HostSequencer;
import sequencers.UnitSequencer;
import stream.Stream;

public class WriterTest {

	public static void main(String[] args) {
		
/*
		File file = new File("/data/Administrators.txt");
		System.out.println(file.getAbsolutePath());
		System.out.println(file.exists());*/
		/*
		Id a1Id = new AdministratorSequencer().initialize();
		Id a2Id = new AdministratorSequencer().next(a1Id);
		Administrator a1 = new Administrator(a1Id, "admin", "admin", "Luka", "Krickovic", Gender.MALE);
		*/
		/*AdministratorStream as = new AdministratorStream(new AdministratorConverter());
		as.create(a1);
		ArrayList<Administrator> list = (ArrayList) as.getAll();
		System.out.println(list.get(0).getName());
		System.out.println(as.getById(new Id("A", 0)).getName());
		
		a1.setUsername("admin1");
		as.update(a1);
		System.out.println(as.getById(new Id("A", 0)).getName());
		
		as.delete(a1);
		System.out.println(((ArrayList) as.getAll()).get(0));
		*/
		/*
		Administrator a2 = new Administrator(a2Id, "admin1", "admin1", "Luka1", "Krickovic1", Gender.MALE);
		AdministratorRepository ar = new AdministratorRepository(new Stream());
		ar.create(a1);
		ar.create(a2);
		ArrayList<Administrator> allAdmins = (ArrayList)ar.getAll();
		for(Administrator temp : allAdmins) {
			System.out.println(temp.getName() + " " + temp.getSurname());
		}
		a2.setUsername("gari");
		ar.update(a2);
		ar.getAll();
		*/
		
		GuestRepository gr = new GuestRepository(new Stream());
		UnitRepository ur = new UnitRepository(new Stream(), gr);
		ReservationRepository rR = new ReservationRepository(new Stream(), ur, gr);
		
		GuestSequencer gs = new GuestSequencer();
		ArrayList<Unit> rentedUnits = new ArrayList<Unit>();
		/*
		Unit u1 = new Unit(new UnitSequencer().initialize(), RoomType.APARTMENT, 4, 4, new Location(new Id("L", 0)), new Host(new HostSequencer().initialize()), 
				);
		rentedUnits.add(e)*/
		
		Guest g1 = new Guest(gs.initialize(), )
		
	}

}
