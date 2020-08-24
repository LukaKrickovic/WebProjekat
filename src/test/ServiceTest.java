package test;

import java.time.LocalTime;

import enums.Gender;
import enums.RoomType;
import enums.Status;
import model.Address;
import model.Administrator;
import model.ApartmentComment;
import model.Guest;
import model.Host;
import model.Location;
import model.Unit;
import repository.AdministratorRepository;
import repository.ApartmentCommentRepository;
import repository.GuestRepository;
import repository.HostRepository;
import repository.UnitAndReservationRepository;
import sequencers.AdministratorSequencer;
import sequencers.ApartmentCommentSequencer;
import sequencers.GuestSequencer;
import sequencers.HostSequencer;
import sequencers.UnitSequencer;
import services.ApartmentCommentService;
import stream.Stream;

public class ServiceTest {

	Stream stream = new Stream();
	GuestRepository guestRepository = new GuestRepository(stream);
	ApartmentCommentRepository apartmentCommentRepository = new ApartmentCommentRepository(stream, guestRepository);
	AdministratorRepository administratorRepository = new AdministratorRepository(stream);
	HostRepository hostRepository = new HostRepository(stream);
	UnitAndReservationRepository urr = new UnitAndReservationRepository(stream, hostRepository, apartmentCommentRepository, guestRepository);
	

	
	Administrator admin1 = new Administrator(new AdministratorSequencer().initialize(), "admin", "admin", "Luka", "Krickovic", Gender.MALE);
	Host host1 = new Host(new HostSequencer().initialize(), "host", "host", "Luka", "Kraickovic", Gender.MALE);
	Guest guest1 = new Guest(new GuestSequencer().initialize(), "guest", "guest", "Luka", "Krickovic", Gender.MALE);
	// TODO: Uvesti proveru za broj noci (da se automatski length racuna u odnosu na enddate - startdate)
	// TODO: Uvesti proveru imena i prezimena kroz regex u CREATE, ne samo update
	Location location = new Location("44.000", "55.000", new Address("Tolstojeva", "15", "Novi Sad", "21000", "Srbija"));
	Unit unit1 = new Unit(new UnitSequencer().initialize(), RoomType.APARTMENT, 5, 5, location, host1, 40, LocalTime.of(14, 0), LocalTime.of(15,0), 
			Status.ACTIVE);
	
	ApartmentComment comment1 = new ApartmentComment(new ApartmentCommentSequencer().initialize(), guest1, unit1, "Vozdra", 5.0);
	
	ApartmentCommentService acs = new ApartmentCommentService(apartmentCommentRepository);
	acs.leaveComment(comment1, guest1);
	
}
