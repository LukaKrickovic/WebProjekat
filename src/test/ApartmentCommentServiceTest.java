package test;

import enums.Gender;
import enums.ReservationStatus;
import enums.RoomType;
import enums.Status;
import model.*;
import repository.*;
import sequencers.*;
import services.ApartmentCommentService;
import services.ReservationService;
import services.UnitService;
import stream.Stream;

import java.time.LocalDate;
import java.time.LocalTime;

public class ApartmentCommentServiceTest {
    public static void main(String[] args){
 /*       Stream stream = new Stream();

        GuestRepository guestRepository = new GuestRepository(stream);
        ApartmentCommentRepository apartmentCommentRepository = new ApartmentCommentRepository(stream, guestRepository);
        HostRepository hostRepository = new HostRepository(stream);
        UnitRepository unitRepository = new UnitRepository(stream, hostRepository);
        ReservationRepository reservationRepository = new ReservationRepository(stream, unitRepository, guestRepository);

        ApartmentCommentService apartmentCommentService = new ApartmentCommentService(apartmentCommentRepository, reservationRepository);


        Guest guest1 = new Guest(new GuestSequencer().initialize(), "guest", "guest", "Luka", "Krickovic", Gender.MALE);
        Host host1 = new Host(new HostSequencer().initialize(), "host", "host", "Luka", "Kraickovic", Gender.MALE);
        Location location = new Location("44.000", "55.000", new Address("Tolstojeva", "15", "Novi Sad", "21000", "Srbija"));
        Unit unit1 = new Unit(new UnitSequencer().initialize(), RoomType.APARTMENT, 5, 5, location, host1, 40, LocalTime.of(14, 0), LocalTime.of(15,0),
                Status.ACTIVE);
        Reservation res1 = new Reservation(new ReservationSequencer().initialize(), unit1, LocalDate.of(2020, 8, 18), LocalDate.of(2020, 8, 19), 40, "MSG1",
                guest1, ReservationStatus.CREATED);

        UnitService unitService = new UnitService(unitRepository, reservationRepository);
        unitService.create(unit1, host1);

        ApartmentComment comment1 = new ApartmentComment(new ApartmentCommentSequencer().initialize(), guest1, unit1, "Vozdra", 5.0);

        guestRepository.create(guest1);

        ReservationService reservationService = new ReservationService(reservationRepository);
        //System.out.println(reservationService.createReservation(res1, guest1).getUnit().getHost());
        //res1.getUnit().getHost();
        reservationService.acceptReservationByHost(res1, host1);

        //apartmentCommentService.leaveComment(comment1, guest1);
        apartmentCommentRepository.create(comment1);
        System.out.println(apartmentCommentService.getAllApartmentComments());

*/
    }
}
