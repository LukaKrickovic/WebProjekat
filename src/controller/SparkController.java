package controller;

import repository.*;
import services.ApartmentCommentService;
import services.ReservationService;
import services.UnitService;
import services.UserService;
import stream.Stream;
import ws.WsHandler;

import java.io.File;

import static spark.Spark.*;
import static spark.Spark.get;

public class SparkController {
    public static void main(String[] args) throws Exception {
        port(8080);

        staticFiles.externalLocation(new File("./static").getCanonicalPath());
        webSocket("/ws", WsHandler.class);

        //  Initialization

        //  Repositories
        Stream stream = new Stream();
        AdministratorRepository administratorRepository = new AdministratorRepository(stream);
        GuestRepository guestRepository = new GuestRepository(stream);
        HostRepository hostRepository = new HostRepository(stream);
        ApartmentCommentRepository apartmentCommentRepository = new ApartmentCommentRepository(stream, guestRepository);
        UnitRepository unitRepository = new UnitRepository(stream, hostRepository);
        ReservationRepository reservationRepository = new ReservationRepository(stream, unitRepository, guestRepository);

        //  Services
        ApartmentCommentService apartmentCommentService = new ApartmentCommentService(apartmentCommentRepository, reservationRepository);
        ReservationService reservationService = new ReservationService(reservationRepository);
        UnitService unitService = new UnitService(unitRepository, reservationRepository);
        UserService userService = new UserService(hostRepository, guestRepository, administratorRepository);

    }
}
