package controller;

import com.google.gson.Gson;
import enums.*;
import exceptions.BadRequestException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import model.*;
import repository.*;
import sequencers.GuestSequencer;
import sequencers.HostSequencer;
import sequencers.ReservationSequencer;
import sequencers.UnitSequencer;
import services.ApartmentCommentService;
import services.ReservationService;
import services.UnitService;
import services.UserService;
import spark.Request;
import spark.Session;
import stream.Stream;
import util.UnitSearchCriteria;
import ws.WsHandler;

import java.io.File;
import java.security.Key;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static spark.Spark.*;
import static spark.Spark.get;

public class SparkController {

    static Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

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
        ReservationRepository reservationRepository = new ReservationRepository(stream, unitRepository, guestRepository, hostRepository, administratorRepository);

        //  Services
        ApartmentCommentService apartmentCommentService = new ApartmentCommentService(apartmentCommentRepository, reservationRepository);
        ReservationService reservationService = new ReservationService(reservationRepository);
        UnitService unitService = new UnitService(unitRepository, reservationRepository);
        UserService userService = new UserService(hostRepository, guestRepository, administratorRepository);

        // GSON
        Gson gson = new Gson();

        //  TEST
        /*
        Guest guest1 = new Guest(new GuestSequencer().initialize(), "guestguest", "Guest123!", "Luka", "Krickovic", Gender.MALE);
        userService.registerGuest(guest1);


        Host host1 = new Host(new HostSequencer().initialize(), "hosthost12345", "Host1234!", "Luka", "Kraickovic", Gender.MALE);
        Location location = new Location("44.000", "55.000", new Address("Tolstojeva", "15", "Novi Sad", "21000", "Srbija"));
        Unit unit1 = new Unit(new UnitSequencer().initialize(), "unit1", RoomType.APARTMENT, 5, 5, location, host1, 40, LocalTime.of(14, 0), LocalTime.of(15,0),
                Status.ACTIVE);


        Location location2 = new Location("44.000", "55.000", new Address("Tolstojeva", "15", "Novi Sad", "21000", "Srbija"));
        Unit unit2 = new Unit(new UnitSequencer().next(unit1.getId()), "unit2", RoomType.APARTMENT, 5, 5, location, host1, 40, LocalTime.of(14, 0), LocalTime.of(15,0),
                Status.ACTIVE);

        unitRepository.create(unit2);

        Amenity am1 = new Amenity("Air conditioning", true);
        Amenity am2 = new Amenity("Free parking on site", true);
        Amenity am3 = new Amenity("Swimming pool", true);
        Amenity am4 = new Amenity("Sauna", true);

        ArrayList<Amenity> amenities = new ArrayList<Amenity>();
        amenities.add(am1);
        amenities.add(am2);
        amenities.add(am3);
        amenities.add(am4);

        unit1.setAmenities(amenities);
        unitRepository.create(unit1);

         */
        Host hostLuka = new Host(new HostSequencer().next(hostRepository.findHighestId()), "lukakrickovic", "Host1234!", "Luka", "Krickovic", Gender.MALE);
        Location location = new Location("44.000", "55.000", new Address("Tolstojeva", "15", "Novi Sad", "21000", "Srbija"));
        Unit unit1 = new Unit(new UnitSequencer().next(unitRepository.findHighestId()), "gajba", RoomType.APARTMENT, 5, 5, location, hostLuka, 40, LocalTime.of(14, 0), LocalTime.of(15,0),
                Status.ACTIVE);

        //hostRepository.create(hostLuka);
        //unitRepository.create(unit1);
        /*
        get("/home", (req, res) -> {
            return index.html;
        });*/

        delete("/rest/delete-unit", (req, res) -> {
            res.type("application/json");
            Unit unit = gson.fromJson(req.queryParams("unit"), Unit.class);
            User user = gson.fromJson(req.queryParams("user"), User.class);
            unitService.delete(unit, user);
            return true;
        });

        get("/rest/get-units-for-host", (req, res) -> {
            String username = getUser(req.queryParams("Auth"));
            User user = userService.getByUsername(username);
            res.type("application/json");

            System.out.println(((ArrayList<Unit>)unitService.getUnitsByHost((Host) user)).size());
            return gson.toJson(unitService.getUnitsByHost((Host) user));
        });

        get("/book-form", (req, res) -> {
            Session ss = req.session(true);
            List<Unit> results = (ArrayList<Unit>) ss.attribute("searchResults");

            if(results == null) {
                System.out.println(req.queryParams("checkIn"));
                System.out.println(req.queryParams("checkOut"));
                System.out.println(req.queryParams("children"));
                System.out.println(req.queryParams("adults"));

                LocalDate checkIn = LocalDate.parse(req.queryParams("checkIn"));
                LocalDate checkOut = LocalDate.parse(req.queryParams("checkOut"));

                if(checkIn.isAfter(checkOut))
                    return gson.toJson("failed");

                if(checkIn.isBefore(LocalDate.now()))
                    return gson.toJson("failed");

                if(checkOut.isBefore(LocalDate.now()))
                    return gson.toJson("failed");

                String city = "";
                String country = "";
                String[] returns = processLocationParameter(req, city, country);
                city = returns[0];
                country = returns[1];
                System.out.println(city + " " + country);
                UnitSearchCriteria usc = new UnitSearchCriteria(req.queryParams("checkIn"), req.queryParams("checkOut"), city, country,
                        "", "", "", "", processPeopleCountParameter(req.queryParams("children"), req.queryParams("adults")));
                results = (ArrayList<Unit>) unitService.searchByCriteria(usc);
                ss.attribute("searchResults", results);
                //res.redirect("/search-results.html");
                return gson.toJson("ok");
            }

            return gson.toJson("failed");
        });

        get("rest/search-units", (req, res) -> {
            List<Unit> results = new ArrayList<Unit>();

                System.out.println(req.queryParams("checkIn"));
                System.out.println(req.queryParams("checkOut"));
                System.out.println(req.queryParams("childrenCount"));
                System.out.println(req.queryParams("adultCount"));

                LocalDate checkIn = LocalDate.parse(req.queryParams("checkIn"));
                LocalDate checkOut = LocalDate.parse(req.queryParams("checkOut"));

                if(checkIn.isAfter(checkOut))
                    return gson.toJson("failed");

                if(checkIn.isBefore(LocalDate.now()))
                    return gson.toJson("failed");

                if(checkOut.isBefore(LocalDate.now()))
                    return gson.toJson("failed");

                String city = "";
                String country = "";
                String[] returns = processLocationParameter(req, city, country);
                city = returns[0];
                country = returns[1];
                System.out.println(city + " " + country);
                UnitSearchCriteria usc = new UnitSearchCriteria(req.queryParams("checkIn"), req.queryParams("checkOut"), city, country,
                        "", "", "", "", processPeopleCountParameter(req.queryParams("children"), req.queryParams("adults")));
                results = (ArrayList<Unit>) unitService.searchByCriteria(usc);

                if(results == null)
                    return new ArrayList<Unit>();
                //res.redirect("/search-results.html");
                return gson.toJson(results);

        });

        get("rest/search-units/:destination:checkIn:checkOut:adultCount:childrenCount", (req, res) -> {
            List<Unit> results = new ArrayList<Unit>();

            System.out.println(req.params("checkIn"));
            System.out.println(req.params("checkOut"));
            System.out.println(req.params("children"));
            System.out.println(req.params("adults"));

            LocalDate checkIn = LocalDate.parse(req.queryParams("checkIn"));
            LocalDate checkOut = LocalDate.parse(req.queryParams("checkOut"));

            if(checkIn.isAfter(checkOut))
                return gson.toJson("failed");

            if(checkIn.isBefore(LocalDate.now()))
                return gson.toJson("failed");

            if(checkOut.isBefore(LocalDate.now()))
                return gson.toJson("failed");

            String city = "";
            String country = "";
            String[] returns = processLocationParameter(req, city, country);
            city = returns[0];
            country = returns[1];
            System.out.println(city + " " + country);
            UnitSearchCriteria usc = new UnitSearchCriteria(req.queryParams("checkIn"), req.queryParams("checkOut"), city, country,
                    "", "", "", "", processPeopleCountParameter(req.queryParams("children"), req.queryParams("adults")));
            results = (ArrayList<Unit>) unitService.searchByCriteria(usc);

            if(results == null)
                return new ArrayList<Unit>();
            //res.redirect("/search-results.html");
            return gson.toJson(results);

        });


        get("/do-results-exist", (req, res) -> {
            Session ss = req.session(true);
            List<Unit> results = (ArrayList<Unit>) ss.attribute("searchResults");

            if(results == null)
                return "noresults";
            if(results.isEmpty())
                return "noresults";
            return "ok";
        });

        get("/read-form-results", (req, res) -> {
            Session ss = req.session(true);
            List<Unit> results = (ArrayList<Unit>) ss.attribute("searchResults");

            if(results == null){
                return new ArrayList<Unit>();
            }

            if(results.size() > 0) {
                for (Unit temp : results) {
                    System.out.println(temp.getId());
                }
            }

            ss.attribute("searchResults", null);
            ss.removeAttribute("searchResults");
            return gson.toJson(results);
        });

        get("/rest/getLoggedInUser", (req, res)->{
            String username = getUser(req.queryParams("Auth"));

            return gson.toJson(userService.getByUsername(username));
        });

        post("/rest/login", (req, res) -> {
            //System.out.println(req.queryParams("username") + " " + req.queryParams("password") + " " + req.queryParams("role"));
            //Roles requestRole = convertToRole(req.queryParams("role"));
            User user = null;

            //Session ss = req.session(true);
            //user = ss.attribute("user");

            if(user == null) {
                System.out.println(req.queryParams("username"));
                user = userService.login(req.queryParams("username"), req.queryParams("password")/*, requestRole*/);

                if (user != null) {
                    String jwt = Jwts.builder().setSubject(user.getUsername()).setExpiration(new Date(2020,12,31)).setIssuedAt(new Date()).signWith(key).compact();
                    System.out.println("User " + user.getUsername() + " just logged in!");
                    user.setJWTToken(jwt);
                    System.out.println(jwt);
                    //ss.attribute("user", user);
                    return gson.toJson(user);
                } else {
                    System.out.println("Invalid credentials!");

                    return gson.toJson(new User());
                }
            } else
                return gson.toJson(new User());
        });

        post("/rest/register", (req, res) -> {
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
            System.out.println(user);

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

        get("/rest/logout", (req, res) -> {
            res.type("application/json");
            User user = userService.getByUsername(getUser(req.queryParams("Auth")));
            if(user != null) {
                user.setJWTToken("");
                System.out.println(user.getId() + ", " + user.getJWTToken());
            }
            return gson.toJson(true);
        });

        post("/rest/make-reservation", (req, res) -> { res.type("application/json");
           User user = gson.fromJson(req.queryParams("user"), User.class);
            System.out.println(user.getName());
           Unit unit = gson.fromJson(req.queryParams("unit"), Unit.class);
           LocalDate checkIn = convertToDate(req.queryParams("checkIn"));
           LocalDate checkOut = convertToDate(req.queryParams("checkOut"));
           double price = Double.parseDouble(req.queryParams("pricePerNight"));
           if(user != null){
               Reservation reservation = new Reservation(ReservationSequencer.nextStatic(reservationRepository.findHighestId()), unit, checkIn, checkOut, price, req.queryParams("message"), user, ReservationStatus.CREATED);
               Reservation created =  reservationService.createReservation(reservation, user);
               System.out.println("Reservation just made: " + created);
               if(created != null)
                   return gson.toJson(true);
           }
           return gson.toJson(false);
        });

    }

    private static LocalDate convertToDate(String date) {
        return LocalDate.parse(date);
    }

    private static String processPeopleCountParameter(String children, String adults) {
        int childrenNumber = 0;
        int adultNumber = 0;

        if(children!=null) {
            if (!children.isEmpty())
                childrenNumber = Integer.parseInt(children);
        }
        if(adults!=null) {
            if (!adults.isEmpty())
                adultNumber = Integer.parseInt(adults);
        }
        return Integer.toString(childrenNumber + adultNumber);
    }

    private static String[] processLocationParameter(Request req, String city, String country) {
        if(req.queryParams("destination").contains(",")) {
            String[] locationBits = req.queryParams("destination").split(",");
            for(String bit : locationBits)
                bit = bit.toLowerCase().trim();
            if(locationBits.length == 1){
                city = locationBits[0];
                country = "";
            } else if(locationBits.length == 0){
                city = "";
                country = "";
            } else {
                city = locationBits[0];
                country = locationBits[locationBits.length - 1];
            }
        }
        String[] retVal = {city.trim(), country.trim()};
        System.out.println(city.trim() + " " + country.trim());
        return retVal;
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

    public static String getUser(String auth) {
        if ((auth != null) && (auth.contains("Sender "))) {
            String jwt = auth.substring(auth.indexOf("Sender ") + 7);
            try {
                Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt);
                return claims.getBody().getSubject();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return "";
    }
}
