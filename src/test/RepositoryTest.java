package test;

import java.awt.List;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import enums.Gender;
import enums.ReservationStatus;
import enums.RoomType;
import enums.Status;
import model.Address;
import model.Guest;
import model.Host;
import model.Location;
import model.Reservation;
import model.Unit;
import repository.ApartmentCommentRepository;
import repository.GuestRepository;
import repository.HostRepository;
import repository.ReservationRepository;
import repository.UnitAndReservationRepository;
import repository.UnitRepository;
import sequencers.GuestSequencer;
import sequencers.HostSequencer;
import sequencers.ReservationSequencer;
import sequencers.UnitSequencer;
import stream.Stream;

public class RepositoryTest {

	public static void main(String[] args) {
	
	Stream stream = new Stream();
	
	GuestRepository gr = new GuestRepository(stream);
	HostRepository hr = new HostRepository(stream);
	ApartmentCommentRepository arc = new ApartmentCommentRepository(stream, gr);
	
	//UnitRepository ur = new UnitRepository(stream, gr, hr, arc);
	
	//ReservationRepository rr = new ReservationRepository(stream, ur, gr);
	//ReservationRepository rr = ur.getReservationRepository();
	
	Location l1 = new Location("44.000", "45.000", new Address("Tolstojeva", "15", "Novi Sad", "21000", "Srbijica"));
	
	Host h1 = new Host(new HostSequencer().initialize(), "host1", "password", "Luka", "Krickovic", Gender.MALE);
	Guest g1 = new Guest(new GuestSequencer().initialize(), "guest1", "password", "Mihajlo", "Jaric", Gender.FEMALE);

	/*
	Amenity am1 = new Amenity("Polica", true);
	Amenity am2 = new Amenity("Stolica", true);

	ArrayList<Amenity> amenities = new ArrayList<Amenity>();
	*/

	//ApartmentComment ac = new ApartmentComment(new ApartmentCommentSequencer().initialize(), g1, null, "Text", 5.0);
	
	Unit unit1 = new Unit(new UnitSequencer().initialize(), RoomType.APARTMENT, 4, 8, l1, h1, 40, LocalTime.of(14, 0), LocalTime.of(15, 0, 0, 0),Status.ACTIVE);
	
	hr.create(h1);
	gr.create(g1);
	
	//ur.create(unit1);
	
	UnitAndReservationRepository urr = new UnitAndReservationRepository(stream, hr, arc, gr);
	
	urr.create(unit1);
	
	Reservation res1 = new Reservation(new ReservationSequencer().initialize(), unit1, LocalDate.of(2020, 8, 18), LocalDate.of(2020, 8, 20), 2, 40, "Porukica", g1, ReservationStatus.ACCEPTED);
	//rr.create(res1);
	urr.create(res1);
	
	ArrayList<Reservation> res = new ArrayList<Reservation>(); 
	res.add(res1);
	
	unit1.setReservations(res);
	urr.update(unit1);
	//System.out.println(((ArrayList<Unit>)urr.getAll()).get(0).getId());
	//urr.update(unit1);
	//ur.getByDates(LocalDate.of(2020, 8, 18), LocalDate.of(2020, 8, 19));
	//unit1.setCheckinTime(LocalTime.of(10, 15));
	//ur.update(unit1);
	//ur.delete(unit1);
	/*
	System.out.println(ur.getAll());
	
	
	System.out.println(ur.getByLocation("Novi Sad", "Srbijica"));
	System.out.println(ur.getByPeopleCount(8));
	System.out.println(ur.getByPrice(0, 1000000));
	System.out.println(ur.getByRoomCount(0, 10));
	*/
	
	//Iterable<Reservation> allRes = rr.getAll();
	
	//System.out.println(allRes);
	
	//rr.getReservationsByUnit(unit1);
	
	}
}
