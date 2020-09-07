package test;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import enums.*;
import model.*;
import repository.*;
import sequencers.GuestSequencer;
import sequencers.HostSequencer;
import sequencers.ReservationSequencer;
import sequencers.UnitSequencer;
import services.ReservationService;
import services.UnitService;
import services.UserService;
import stream.Stream;
import util.UnitSearchCriteria;
import validations.UserUpdateValidation;
import validations.UserValidation;

import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.time.LocalDate;
import java.time.LocalTime;

public class UserServiceTest {
    public static void main(String[] args){
        Stream stream = new Stream();
        HostRepository hostRepository = new HostRepository(stream);
        GuestRepository guestRepository = new GuestRepository(stream);
        AdministratorRepository administratorRepository = new AdministratorRepository(stream);
        UserValidation userValidation = new UserValidation();
        UserUpdateValidation userUpdateValidation = new UserUpdateValidation(guestRepository, hostRepository, administratorRepository);
        UserService userService = new UserService(hostRepository, guestRepository, administratorRepository);

        Host host1 = new Host(new HostSequencer().initialize(), "hosthost", "Host1234!", "Luka", "Kraickovic", Gender.MALE);

        userService.registerHost(host1);
        Guest guest1 = new Guest(new GuestSequencer().initialize(), "guestguest", "Guest123!", "Luka", "Krickovic", Gender.MALE);
        userService.registerGuest(guest1);
        System.out.println(userService.login("guestguest", "Guest123!", Roles.GUEST).getName());
        guest1.setPassword("Guest1234!");
        userService.changeData(guest1);
        System.out.println(userService.searchByCriteria("Luka Krickovic", Roles.ADMINISTRATOR, null));

        UnitRepository unitRepository = new UnitRepository(stream, hostRepository);
        ReservationRepository reservationRepository = new ReservationRepository(stream, unitRepository, guestRepository);

        UnitService unitService = new UnitService(unitRepository, reservationRepository);

        Location location = new Location("44.000", "55.000", new Address("Tolstojeva", "15", "Novi Sad", "21000", "Srbija"));
        Unit unit1 = new Unit(new UnitSequencer().initialize(), RoomType.APARTMENT, 5, 5, location, host1, 40, LocalTime.of(14, 0), LocalTime.of(15,0),
                Status.ACTIVE);
        Reservation res1 = new Reservation(new ReservationSequencer().initialize(), unit1, LocalDate.of(2020, 8, 18), LocalDate.of(2020, 8, 19), 40, "MSG1",
                guest1, ReservationStatus.CREATED);

        unitService.create(unit1, host1);
        reservationRepository.create(res1);

        System.out.println(unitService.getAllActiveUnits());
        System.out.println(unitService.getAllInactiveUnits());
        System.out.println("Search " + unitService.searchByCriteria(new UnitSearchCriteria(LocalDate.of(2020, 9, 1), LocalDate.of(2020, 9, 8), "", "", "", "", "", "", "")));
    }
}
