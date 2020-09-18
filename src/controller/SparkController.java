package controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import enums.*;
import exceptions.BadRequestException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import model.*;
import org.apache.commons.io.FilenameUtils;
import repository.*;
import sequencers.*;
import services.ApartmentCommentService;
import services.ReservationService;
import services.UnitService;
import services.UserService;
import spark.Request;
import spark.Session;
import stream.Stream;
import util.UnitSearchCriteria;
import ws.WsHandler;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
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

        UnitRepository unitRepository = new UnitRepository(stream, hostRepository);
        ApartmentCommentRepository apartmentCommentRepository = new ApartmentCommentRepository(stream, guestRepository, hostRepository, administratorRepository, unitRepository);
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

        List<String> images = new ArrayList<String>();
        images.add("data/images/UN1/room_1.jpg");
        images.add("data/images/UN1/room_2.jpg");
        images.add("data/images/UN1/room_3.jpg");
        images.add("data/images/UN1/room_4.jpg");
        images.add("data/images/UN1/room_5.jpg");
        images.add("data/images/UN1/room_6.jpg");
        images.add("data/images/UN1/room_7.jpg");
        images.add("data/images/UN1/room_8.jpg");
        images.add("data/images/UN1/room_9.jpg");
        images.add("data/images/UN1/room_10.jpg");

        unit1.setImageSources(images);

        ApartmentComment ac1 = new ApartmentComment(new ApartmentCommentSequencer().next(apartmentCommentRepository.findHighestId()), hostLuka, unit1, "Bilo super", 5);
        ApartmentComment ac2 = new ApartmentComment(new ApartmentCommentSequencer().next(apartmentCommentRepository.findHighestId()), hostLuka, unit1, "Bilo super realno", 5);

        //apartmentCommentRepository.create(ac1);
        //apartmentCommentRepository.create(ac2);
        //unitRepository.create(unit1);


        //hostRepository.create(hostLuka);
        //unitRepository.create(unit1);
        /*
        get("/home", (req, res) -> {
            return index.html;
        });*/

        get("/rest/get-all-admins", (req, res) -> {
            String username = getUser(req.queryParams("Auth"));
            res.type("application/json");

            Iterable<Administrator> allAdmins = gson.fromJson(req.queryParams("admins"), new TypeToken<List<Administrator>>() {}.getType());
            allAdmins = userService.getAllAdministrators();

            return gson.toJson(allAdmins);
        });

        get("/rest/get-all-hosts", (req, res) -> {
            String username = getUser(req.queryParams("Auth"));
            res.type("application/json");

            Iterable<Host> allHosts = gson.fromJson(req.queryParams("hosts"), new TypeToken<List<Host>>() {}.getType());
            allHosts = userService.getAllHosts();

            return gson.toJson(allHosts);
        });

        get("/rest/get-all-guests", (req, res) -> {
            String username = getUser(req.queryParams("Auth"));
            res.type("application/json");

            Iterable<Guest> allGuests = gson.fromJson(req.queryParams("guests"), new TypeToken<List<Guest>>() {}.getType());
            allGuests = userService.getAllGuests();

            return gson.toJson(allGuests);
        });

        post("/rest/make-host-user", (req, res) -> {
            res.type("application/json");
            Guest guest = gson.fromJson(req.queryParams("user"), Guest.class);
            Host host = new Host(new HostSequencer().next(hostRepository.findHighestId()), guest.getUsername(), guest.getPassword(), guest.getName(), guest.getSurname(), guest.getGender());
            hostRepository.create(host);
            guestRepository.delete(guest);
            return true;
        });

        post("/rest/block-user", (req, res) -> {
            res.type("application/json");
            User user = gson.fromJson(req.queryParams("user"), User.class);
            if(user.getRole().equals(Roles.GUEST)){
                Guest guest = gson.fromJson(req.queryParams("user"), Guest.class);
                guest.setBlocked(true);
                userService.changeData(guest);
            }

            if(user.getRole().equals(Roles.HOST)){
                Host host = gson.fromJson(req.queryParams("user"), Host.class);
                host.setBlocked(true);
                userService.changeData(host);
            }
            return true;
        });

        post("/rest/un-block-user", (req, res) -> {
            res.type("application/json");
            User user = gson.fromJson(req.queryParams("user"), User.class);
            if(user.getRole().equals(Roles.GUEST)){
                Guest guest = gson.fromJson(req.queryParams("user"), Guest.class);
                guest.setBlocked(false);
                userService.changeData(guest);
            }

            if(user.getRole().equals(Roles.HOST)){
                Host host = gson.fromJson(req.queryParams("user"), Host.class);
                host.setBlocked(false);
                userService.changeData(host);
            }
            return true;
        });

        delete("/rest/delete-unit", (req, res) -> {
            res.type("application/json");
            Unit unit = gson.fromJson(req.queryParams("unit"), Unit.class);
            User user = gson.fromJson(req.queryParams("user"), User.class);
            unitService.delete(unit, user);
            return true;
        });

        delete("/rest/remove-amenity", (req, res) -> {
            Unit unit = gson.fromJson(req.queryParams("unit"), Unit.class);
            User user = gson.fromJson(req.queryParams("user"), User.class);
            Amenity amenity = gson.fromJson(req.queryParams("amenity"), Amenity.class);
            List<Amenity> ams = unit.getAmenities();
            ams = removeFromList(amenity, ams);
            unit.setAmenities(ams);
            unitService.update(unit, user);
            return true;
        });

        delete("/rest/cancel-reservation", (req, res) -> {
            res.type("application/json");
            Reservation reservation = gson.fromJson(req.queryParams("reservation"), Reservation.class);
            User user = gson.fromJson(req.queryParams("user"), User.class);
            reservation.setReservationStatus(ReservationStatus.CANCELLED);
            reservationService.update(reservation);
            return true;
        });

        post("/rest/edit-unit", (req, res)->{
            Unit unit = gson.fromJson(req.queryParams("unit"), Unit.class);
            User user = gson.fromJson(req.queryParams("user"), User.class);
            RoomType type = getRoomTypeFromRequest(req.queryParams("roomType"));
            unit.setRoomType(type);
            Status status = getStatusFromRequest(req.queryParams("status"));
            unit.setStatus(status);
            unit.setNumOfRooms(Integer.parseInt(req.queryParams("numOfRooms")));
            unit.setNumOfGuests(Integer.parseInt(req.queryParams("numOfGuests")));
            unit.setPricePerNight(Double.parseDouble(req.queryParams("pricePerNight")));
            unit.setName(req.queryParams("name"));
            //  TODO: dodati konvertor adresa -> koordinate na frontendu
            unit.setLocation(new Location(unit.getLocation().getLatitude(), unit.getLocation().getLongitude(), new Address(
                    req.queryParams("street"), req.queryParams("buildingNumber"), req.queryParams("city"), req.queryParams("zipCode"), req.queryParams("country")
            )));
            List<Amenity> ams = getAmenitiesFromRequest(req.queryParams("newAmenities"));
            List<Amenity> amsBackup = unit.getAmenities();
            amsBackup.addAll(ams);
            unit.setAmenities(amsBackup);
            unitService.update(unit, user);
            return true;
        });

        get("/rest/get-comments-for-apartment-guest", (req, res)->{
            res.type("application/json");
            Unit unit = gson.fromJson(req.queryParams("unit"), Unit.class);
            return gson.toJson(apartmentCommentService.getAllApprovedCommentsByUnit(unit));
        });
        get("/rest/get-comments-for-apartment-host", (req, res)->{
            //System.out.println(gson.fromJson(req.queryParams("unit"), Unit.class).getName());
            res.type("application/json");
            Unit unit = gson.fromJson(req.queryParams("unit"), Unit.class);
            User user = gson.fromJson(req.queryParams("user"), User.class);
            System.out.println(unit.getId());
            if(user != null) {
                if (user.getRole().equals(Roles.HOST)) {
                    if (user.getId().equals(unit.getHost().getId())) {
                        ArrayList<ApartmentComment> comms = (ArrayList<ApartmentComment>)apartmentCommentService.getAllApartmentCommentsByUnit(unit);
                        return gson.toJson(comms);
                    }
                }
            }
            return gson.toJson(apartmentCommentService.getAllApprovedCommentsByUnit(unit));
        });

        post("/rest/approve-comment", (req, res)->{
            User user = gson.fromJson(req.queryParams("user"), User.class);
            ApartmentComment comment = gson.fromJson(req.queryParams("aptComment"), ApartmentComment.class);

            return apartmentCommentService.approveComment(comment, user);
        });

        get("/rest/get-comments-for-host", (req, res)->{
            User user = gson.fromJson(req.queryParams("user"), User.class);
            Unit unit = gson.fromJson(req.queryParams("unit"), Unit.class);

            if(user.getId().equals(unit.getHost().getId()))
                return gson.toJson(apartmentCommentService.getAllApartmentCommentsByUnit(unit));
            else{
                res.status(403);
                return gson.toJson(new ArrayList<ApartmentComment>());
            }
        });

        post("/rest/post-comment", (req, res)->{
            User user = gson.fromJson(req.queryParams("user"), User.class);
            if(user.getRole().equals(Roles.GUEST)){
                Guest guest = gson.fromJson(req.queryParams("user"), Guest.class);
                Unit unit = gson.fromJson(req.queryParams("unit"), Unit.class);

                String grade = req.queryParams("grade");
                String comment = req.queryParams("comment");
                ApartmentComment ac = new ApartmentComment(new ApartmentCommentSequencer().next(apartmentCommentRepository.findHighestId()), guest, unit, comment, Double.parseDouble(grade));

                apartmentCommentService.leaveComment(ac, guest);
            }

            if(user.getRole().equals(Roles.ADMINISTRATOR)){
                Administrator admin = gson.fromJson(req.queryParams("user"), Administrator.class);

                Unit unit = gson.fromJson(req.queryParams("unit"), Unit.class);

                String grade = req.queryParams("grade");
                String comment = req.queryParams("comment");
                ApartmentComment ac = new ApartmentComment(new ApartmentCommentSequencer().next(apartmentCommentRepository.findHighestId()), admin, unit, comment, Double.parseDouble(grade));

                ac.setApproved(true);

                apartmentCommentService.leaveComment(ac, admin);
            }
            if(user.getRole().equals(Roles.HOST)){
                Host host = gson.fromJson(req.queryParams("user"), Host.class);

                Unit unit = gson.fromJson(req.queryParams("unit"), Unit.class);

                String grade = req.queryParams("grade");
                String comment = req.queryParams("comment");
                ApartmentComment ac = new ApartmentComment(new ApartmentCommentSequencer().next(apartmentCommentRepository.findHighestId()), host, unit, comment, Double.parseDouble(grade));

                if(host.getId().equals(unit.getHost().getId()))
                    ac.setApproved(true);

                apartmentCommentService.leaveComment(ac, host);
            }


            return true;
        });

        get("/rest/get-units-for-host", (req, res) -> {
            String username = getUser(req.queryParams("Auth"));
            User user = userService.getByUsername(username);
            res.type("application/json");

            System.out.println(((ArrayList<Unit>)unitService.getUnitsByHost((Host) user)).size());
            return gson.toJson(unitService.getUnitsByHost((Host) user));
        });



        get("/rest/get-image", (req, res) ->{

            String temp = req.queryParams("imgPath");
                File file = new File(temp);
                RenderedImage rI = ImageIO.read(file);
                String ext = FilenameUtils.getExtension(temp);
                if(ext.equalsIgnoreCase("jpg"))
                    res.type("image/jpeg");
                else
                    res.type("image/"+ext);
                /*try {
                    OutputStream out = res.raw().getOutputStream();
                    String ext = FilenameUtils.getExtension(temp);
                    ImageIO.write(rI, ext, out);
                    HttpServletResponse raw = res.raw();
                    res.header("Content-Disposition", "attachment; filename=image."+ext);
                    //raw.getOutputStream().write(getData(image));
                    raw.getOutputStream().flush();
                    raw.getOutputStream().close();
                    return raw;

                } catch (IOException ex) {
                    halt();

                }*/
                byte[] rawImage = null;
                try(ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                    ImageIO.write( rI, ext, baos );

                    baos.flush();
                    rawImage = baos.toByteArray();
                }

                return rawImage;

        });

        get("/rest/get-bookings-for-user", (req, res)->{
            res.type("application/json");
            String username = getUser(req.queryParams("Auth"));
            User user = userService.getByUsername(username);

            return gson.toJson(reservationService.getAllReservationsOfUser(user));
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

    private static List<Amenity> removeFromList(Amenity amenity, List<Amenity> ams) {
        ArrayList<Amenity> result = new ArrayList<Amenity>();
        for(Amenity temp : ams){
            if(!temp.getDescription().trim().equalsIgnoreCase(amenity.getDescription().trim())){
                result.add(temp);
            }
        }
        return result;
    }


    private static List<Amenity> getAmenitiesFromRequest(String newAmenities) {
        ArrayList<Amenity> retVal = new ArrayList<Amenity>();
        if(newAmenities != null){
            if(!newAmenities.isEmpty()){
                String[] bits = newAmenities.split(",");
                for(String bit : bits){
                    retVal.add(new Amenity(bit.trim(), true));
                }
            }
        }
        return retVal;
    }

    private static Status getStatusFromRequest(String status) {
        if(status.equalsIgnoreCase("ACTIVE"))
            return Status.ACTIVE;
        else
            return Status.INACTIVE;

    }

    private static RoomType getRoomTypeFromRequest(String roomType) {
        if(roomType.equalsIgnoreCase("APARTMENT"))
            return RoomType.APARTMENT;
        else
            return RoomType.ROOM;

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
