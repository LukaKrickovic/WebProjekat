package services;

import java.util.ArrayList;
import java.util.List;

import enums.ReservationStatus;
import enums.Roles;
import model.ApartmentComment;
import model.Reservation;
import model.Unit;
import model.User;
import repository.ApartmentCommentRepository;
import repository.ReservationRepository;

public class ApartmentCommentService {

		private ApartmentCommentRepository apartmentCommentRepository;
		private ReservationRepository reservationRepository;
		public ApartmentCommentService(ApartmentCommentRepository apartmentCommentRepository, ReservationRepository reservationRepository) {
			super();
			this.apartmentCommentRepository = apartmentCommentRepository;
			this.reservationRepository = reservationRepository;
		}
		
		// TODO: proveri ovo detaljnije
		public void leaveComment(ApartmentComment apartmentComment, User user) {
			if(user.getRole().equals(Roles.GUEST)) {
				if(apartmentComment.getGuest().getId().equals(user.getId())) {
					
					List<Reservation> reservations = (ArrayList<Reservation>)reservationRepository.getReservationsByGuest(apartmentComment.getGuest());
					
					for(Reservation res : reservations) {
						if((res.getReservationStatus().equals(ReservationStatus.DECLINED)) || (res.getReservationStatus().equals(ReservationStatus.COMPLETED))) {
							apartmentCommentRepository.create(apartmentComment);
						}
					}
				}
			}
		}
		
		//provera za admina??
		public Iterable<ApartmentComment> getAllApartmentComments(){
			return apartmentCommentRepository.getAll();
		}

		public Iterable<ApartmentComment> getAllApprovedCommentsByUnit(Unit unit){
			ArrayList<ApartmentComment> allComments = (ArrayList<ApartmentComment>) apartmentCommentRepository.getAllApprovedComments();
			ArrayList<ApartmentComment> retVal = new ArrayList<ApartmentComment>();

			if(allComments != null){
				if(!allComments.isEmpty()){
					for(ApartmentComment temp : allComments){
						if(temp.getUnit().getId().equals(unit.getId())){
							retVal.add(temp);
						}
					}
				}
			}

			return retVal;
		}

		public Iterable<ApartmentComment> getAllApartmentCommentsByUnit(Unit unit){
			ArrayList<ApartmentComment> allComments = (ArrayList<ApartmentComment>) apartmentCommentRepository.getAll();
			ArrayList<ApartmentComment> retVal = new ArrayList<ApartmentComment>();

			if(allComments != null){
				if(!allComments.isEmpty()){
					for(ApartmentComment temp : allComments){
						if(temp.getUnit().getId().equals(unit.getId())){
							retVal.add(temp);
						}
					}
				}
			}

			return retVal;
		}
}
