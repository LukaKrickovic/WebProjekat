package converters;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import model.ApartmentComment;
import model.Host;
import model.Reservation;
import model.Unit;

public class UnitConverter {

	public UnitConverter() {}
	
	public String ConvertToJSON(Unit entity) {
		entity.setReservations(saveReservationIds(entity.getReservations()));
		entity.setHost(saveHostId(entity.getHost()));
		entity.setApartmentComment(saveApartmentComment(entity.getApartmentComment()));
		return new Gson().toJson(entity);
	}
	
	private List<ApartmentComment> saveApartmentComment(List<ApartmentComment> apartmentComments) {
		ArrayList<ApartmentComment> retVal = new ArrayList<ApartmentComment>();
		for(ApartmentComment temp : apartmentComments) {
			retVal.add(new ApartmentComment(temp.getId()));
		}
		return retVal;
	}

	private Host saveHostId(Host host) {
		return new Host(host.getId());
	}

	private List<Reservation> saveReservationIds(List<Reservation> reservations) {
		ArrayList<Reservation> retVal = new ArrayList<Reservation>();
		for(Reservation temp : reservations) {
			retVal.add(new Reservation(temp.getId()));
		}
		return retVal;
	}

	public Unit ConvertFromJSON(String JSON) {
		return new Gson().fromJson(JSON, Unit.class);
	}
}
