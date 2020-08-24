package test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import enums.Gender;
import enums.ReservationStatus;
import enums.RoomType;
import enums.Status;
import model.*;
import repository.*;
import sequencers.*;
import services.ApartmentCommentService;
import stream.Stream;

public class ServiceTest {

	public static void main(String[] args){
		Stream stream = new Stream();
		GuestRepository guestRepository = new GuestRepository(stream);
		ApartmentCommentRepository apartmentCommentRepository = new ApartmentCommentRepository(stream, guestRepository);
		AdministratorRepository administratorRepository = new AdministratorRepository(stream);
		HostRepository hostRepository = new HostRepository(stream);
		//UnitAndReservationRepository urr = new UnitAndReservationRepository(stream, hostRepository, apartmentCommentRepository, guestRepository);
		UnitRepository unitRepository = new UnitRepository(stream, hostRepository);
		ReservationRepository reservationRepository = new ReservationRepository(stream, unitRepository, guestRepository);


		Administrator admin1 = new Administrator(new AdministratorSequencer().initialize(), "admin", "admin", "Luka", "Krickovic", Gender.MALE);
		Host host1 = new Host(new HostSequencer().initialize(), "host", "host", "Luka", "Kraickovic", Gender.MALE);
		Guest guest1 = new Guest(new GuestSequencer().initialize(), "guest", "guest", "Luka", "Krickovic", Gender.MALE);

		Location location = new Location("44.000", "55.000", new Address("Tolstojeva", "15", "Novi Sad", "21000", "Srbija"));
		Unit unit1 = new Unit(new UnitSequencer().initialize(), RoomType.APARTMENT, 5, 5, location, host1, 40, LocalTime.of(14, 0), LocalTime.of(15,0),
				Status.ACTIVE);

		ApartmentComment comment1 = new ApartmentComment(new ApartmentCommentSequencer().initialize(), guest1, unit1, "Vozdra", 5.0);

		ApartmentCommentService acs = new ApartmentCommentService(apartmentCommentRepository, reservationRepository);

		Reservation res1 = new Reservation(new ReservationSequencer().initialize(), unit1, LocalDate.of(2020, 8, 18), LocalDate.of(2020, 8, 19), 40, "MSG1",
				guest1, ReservationStatus.COMPLETED);

		//List<Reservation> allReservations = new ArrayList<Reservation>();
		//allReservations.add(res1);

		//guest1.setReservations(allReservations);
		//guestRepository.update(guest1);
		//urr.create(unit1);

		apartmentCommentRepository.create(comment1);
		comment1.setGrade(4);
		apartmentCommentRepository.update(comment1);
		System.out.println(apartmentCommentRepository.getApartmentCommentsByUnit(unit1));
		System.out.println(apartmentCommentRepository.getById(comment1.getId()).getId().toString());
		guestRepository.create(guest1);
		System.out.println(guestRepository.getById(guest1.getId()).getId());
		guest1.setGender(Gender.NONBINARY);
		guestRepository.update(guest1);
		System.out.println(guestRepository.getUserByUsername("guest").toString());
		System.out.println(guestRepository.getUsersByNameAndSurname("Luka", "Krickovic").toString());
		System.out.println(guestRepository.getUsersByGender(Gender.NONBINARY).toString());
		System.out.println(guestRepository.getAll().toString());

		hostRepository.create(host1);
		host1.setGender(Gender.NONBINARY);
		hostRepository.update(host1);
		System.out.println(hostRepository.getUserByUsername("host"));
		System.out.println(hostRepository.getById(host1.getId()));
		System.out.println(hostRepository.getUsersByNameAndSurname("Luka", "Kraickovic"));
		System.out.println(hostRepository.getUsersByGender(Gender.NONBINARY));
		//acs.leaveComment(comment1, guest1);
		//System.out.println(acs.getAllApartmentComments());

		unitRepository.create(unit1);

		reservationRepository.create(res1);
		res1.setReservationStatus(ReservationStatus.COMPLETED);
		reservationRepository.update(res1);
		System.out.println(reservationRepository.getById(res1.getId()));
		System.out.println(reservationRepository.getReservationsByGuest(guest1));
		System.out.println(reservationRepository.getReservationsByUnit(unit1) + "ByUnit");
		System.out.println(reservationRepository.getReservationsByHost(host1) + "ByHost");
		System.out.println(reservationRepository.getUnitsByDate(LocalDate.of(2020, 8, 18), LocalDate.of(2020, 8, 19)) + " ByDate");
		System.out.println(reservationRepository.getUnitsByGuest(guest1) + " ByGuest");
		System.out.println(reservationRepository.getGuestsByRentedUnit(unit1) + " GuestByUnit");

		unit1.setStatus(Status.ACTIVE);
		unitRepository.update(unit1);
		System.out.println(unitRepository.getById(unit1.getId()));
		System.out.println(unitRepository.getByLocation("Novi Sad", "Srbija"));
		System.out.println(unitRepository.getByPeopleCount(5));
		System.out.println(unitRepository.getByPrice(0, 50));
		System.out.println(unitRepository.getByRoomCount(0, 10));
	}
}
