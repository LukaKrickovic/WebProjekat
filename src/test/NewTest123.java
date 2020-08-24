package test;

import java.time.LocalDate;
import java.time.LocalTime;

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
import repository.UnitAndReservationRepository;
import sequencers.GuestSequencer;
import sequencers.HostSequencer;
import sequencers.ReservationSequencer;
import sequencers.UnitSequencer;
import stream.Stream;

public class NewTest123 {

	Stream stream = new Stream();
	HostRepository hostRepository = new HostRepository(stream);
	GuestRepository guestRepository = new GuestRepository(stream);
	ApartmentCommentRepository apartmentCommentRepository = new ApartmentCommentRepository(stream, guestRepository);
	
	
	Host h1 = new Host(new HostSequencer().initialize(), "kalu", "kalu", "Luka", "Krickovic", Gender.NONBINARY);
	Guest g1 = new Guest(new GuestSequencer().initialize(), "kaluG", "kaluG", "Lule", "Krickovic", Gender.MALE);
	
	Location loc1 = new Location("44.000", "45.000", new Address("Tolstojeva", "15", "Novi Sad", "21000", "Srbija"));
	
	Unit unit1 = new Unit(new UnitSequencer().initialize(), RoomType.APARTMENT, 5, 5, loc1, h1, 40, LocalTime.of(15, 0), LocalTime.of(16, 0), Status.ACTIVE);
	
	Reservation res1 = new Reservation(new ReservationSequencer().initialize(), unit1, LocalDate.of(2020, 8, 22), LocalDate.of(2020, 8, 23), 1, 200, "Vozdra", 
			g1, ReservationStatus.ACCEPTED);
	
	//UnitAndReservationRepository urr = new UnitAndReservationRepository(stream, hostRepository, apartmentCommentRepository, guestRepository);
	
	
	
}
