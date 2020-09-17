package services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import enums.ReservationStatus;
import enums.Roles;
import model.Guest;
import model.Host;
import model.Reservation;
import model.User;
import repository.ReservationRepository;

public class ReservationService {

	private ReservationRepository reservationRepository;
	
	public ReservationService(ReservationRepository reservationRepository) {
		super();
		this.reservationRepository = reservationRepository;
	}
	
//	provera za admina??
	public Iterable<Reservation> getAllReservations() { 
		return reservationRepository.getAll();
	}
	
	// guest
	
	public Reservation createReservation(Reservation reservation, User user) {
		if(user.getRole().equals(Roles.HOST)){
			if(reservation.getUser().getId().equals(user.getId())){
				if(reservation.getStartDate().isBefore(reservation.getEndDate())) {
					reservation.setReservationStatus(ReservationStatus.CREATED);
					return reservationRepository.create(reservation);
				}
			}
		}

		if(user.getRole().equals(Roles.GUEST)) {
//			if(reservation.getGuest().getId().equals(user.getId())) {
				if(reservation.getStartDate().isBefore(reservation.getEndDate())) {
					reservation.setReservationStatus(ReservationStatus.CREATED);
					return reservationRepository.create(reservation);
				}
			}
//		}
		return null;
	}
	
	public Iterable<Reservation> getAllReservationsOfGuest(Guest guest) {
		return reservationRepository.getReservationsByGuest(guest);
	}

	public Iterable<Reservation> getAllReservationsOfUser(User guest) {
		return reservationRepository.getReservationsByUser(guest);
	}
	
	public void cancelReservationByGuest(Reservation reservation, User user) {
		if(user.getRole().equals(Roles.GUEST)) {
			if(reservation.getGuest().getId().equals(user.getId())) {
				if((reservation.getReservationStatus().equals(ReservationStatus.CREATED)) || (reservation.getReservationStatus().equals(ReservationStatus.ACCEPTED))){
					reservation.setReservationStatus(ReservationStatus.CANCELLED);
					reservationRepository.update(reservation);
				} else {
					System.out.println("Status rezervacije nije CREATED ili ACCEPTED");
				}
			} else {
				System.out.println("Ova rezervacija nije od ovog gosta");
			}
		} else {
			System.out.println("Uloga nije GUEST");
		}
	}
	
	//host
	
	public Iterable<Reservation> getAllReservationsOfHost(Host host) {
		return reservationRepository.getReservationsByHost(host);
	}

	public void acceptReservationByHost(Reservation reservation, User user) {
		if(user.getRole().equals(Roles.HOST)) {
			if(reservation.getUnit().getHost().getId().equals(user.getId())) {
				if(reservation.getReservationStatus().equals(ReservationStatus.CREATED)){
					reservation.setReservationStatus(ReservationStatus.ACCEPTED);
					reservationRepository.update(reservation);
				} else {
					System.out.println("Status rezervacije nije CREATED");
				}
			} else {
				System.out.println("Rezervacija ne pripada datom hostu");
			}
		} else {
			System.out.println("Uloga nije HOST");
		}
	}
	
	public void declineReservationByHost(Reservation reservation, User user) {
		if(user.getRole().equals(Roles.HOST)) {
			if(reservation.getUnit().getHost().getId().equals(user.getId())) {
				if((reservation.getReservationStatus().equals(ReservationStatus.CREATED)) || (reservation.getReservationStatus().equals(ReservationStatus.ACCEPTED))){
					reservation.setReservationStatus(ReservationStatus.DECLINED);
					reservationRepository.update(reservation);
				} else {
					System.out.println("Status rezervacije nije CREATED ili ACCEPTED");
				}
			} else {
				System.out.println("Rezervacija ne pripada datom hostu");
			}
			
		} else {
			System.out.println("Uloga nije HOST");
		}

	}

	public void endReservationByHost(Reservation reservation, User user) {
		if(user.getRole().equals(Roles.HOST)) {
			if(reservation.getUnit().getHost().getId().equals(user.getId())) {
				
				LocalDate now = LocalDate.now();
				//Date nowDate = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
				
				if(reservation.getEndDate().isBefore(now)) {
					reservation.setReservationStatus(ReservationStatus.COMPLETED);
					reservationRepository.update(reservation);
				} else {
					System.out.println("Nije zavrsena rezervacija");
				}
			} else {
				System.out.println("Rezervacija ne pripada datom hostu");
			}
			
		} else {
			System.out.println("Uloga nije HOST");
		}
	}


	public void update(Reservation reservation) {
		reservationRepository.update(reservation);
	}
}
