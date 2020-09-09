package controller;

import com.google.gson.Gson;
import enums.Gender;
import enums.Roles;
import exceptions.BadRequestException;
import model.Guest;
import model.Host;
import model.User;
import repository.*;
import sequencers.GuestSequencer;
import sequencers.HostSequencer;
import services.ApartmentCommentService;
import services.ReservationService;
import services.UnitService;
import services.UserService;
import spark.Request;
import spark.Session;
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

        // GSON
        Gson gson = new Gson();

        //  TEST
        Guest guest1 = new Guest(new GuestSequencer().initialize(), "guestguest", "Guest123!", "Luka", "Krickovic", Gender.MALE);
        userService.registerGuest(guest1);

        /*
        get("/home", (req, res) -> {
            return index.html;
        });*/

        post("/book-form", (req, res) -> {
            System.out.println("WORKS");
            return "Works";
        });

        post("/login", (req, res) -> {
            //System.out.println(req.queryParams("username") + " " + req.queryParams("password") + " " + req.queryParams("role"));
            Roles requestRole = convertToRole(req.queryParams("role"));
            User user = null;

            Session ss = req.session(true);
            user = ss.attribute("user");

            if(user == null) {
                user = userService.login(req.queryParams("username"), req.queryParams("password"), requestRole);

                if (user != null) {
                    System.out.println("User " + user.getUsername() + " just logged in!");
                    ss.attribute("user", user);
                    return gson.toJson("ok");
                } else {
                    System.out.println("Invalid credentials!");

                    return gson.toJson("failed");
                }
            } else
                return gson.toJson("failed");
        });

        post("/register", (req, res) -> {
            try{
                checkRequestParams(req);
            } catch (BadRequestException e){
                e.printStackTrace();
            }

            Roles requestRole = convertToRole(req.queryParams("role"));
            Gender requestGender = convertToGender(req.queryParams("gender"));
            User user = null;

            Session ss = req.session(true);
            user = ss.attribute("user");

            if(user == null) {
                if (requestRole.equals(Roles.GUEST)) {
                    user = userService.registerGuest(new Guest(new GuestSequencer().next(guestRepository.findHighestId()),
                            req.queryParams("username"), req.queryParams("password"), req.queryParams("name"), req.queryParams("surname"), requestGender));
                } else if (requestRole.equals(Roles.HOST)) {
                    user = userService.registerHost(new Host(new HostSequencer().next(hostRepository.findHighestId()),
                            req.queryParams("username"), req.queryParams("password"), req.queryParams("name"), req.queryParams("surname"), requestGender));
                }

                if (user != null) {
                    ss.attribute("user", user);
                    return gson.toJson("ok");
                }

            }
            return gson.toJson("failed");
        });

        get("/startup-index", (req, res) -> {
            Session ss = req.session(true);
            User user = ss.attribute("user");

            if(user == null){
                return gson.toJson("nouser");
            } else {
                return gson.toJson("userfound");
            }
        });

        get("/startup-user", (req, res) -> {
            Session ss = req.session(true);
            User user = ss.attribute("user");

            if(user == null){
                return gson.toJson("nouser");
            } else {
                return gson.toJson(user);
            }
        });

        get("/logout", (req, res) -> {
            Session ss = req.session(true);
            User user = ss.attribute("user");

            if(user == null){
                return false;
            }

            ss.invalidate();
            return true;
        });

    }

    private static void checkRequestParams(Request req) throws BadRequestException {
        if(req.queryParams("username") == null)
            throw new BadRequestException("Username is null");
        if(req.queryParams("password") == null)
            throw new BadRequestException("Password is null");
        if(req.queryParams("name") == null)
            throw new BadRequestException("Name is null");
        if(req.queryParams("surname") == null)
            throw new BadRequestException("Surname is null");
    }

    private static Gender convertToGender(String gender) {
        if(gender.trim().equalsIgnoreCase("MALE"))
            return Gender.values()[0];
        else if(gender.trim().equalsIgnoreCase("FEMALE"))
            return Gender.values()[1];
        else
            return Gender.values()[0];
    }

    private static Roles convertToRole(String role) {
        if(role.trim().equalsIgnoreCase("GUEST"))
            return Roles.values()[2];
        else if(role.trim().equalsIgnoreCase("HOST"))
            return Roles.values()[1];
        else
            return Roles.values()[0];
    }
}
