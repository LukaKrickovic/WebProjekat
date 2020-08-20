package converters;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import model.ApartmentComment;
import model.Host;
import model.Reservation;
import model.Unit;
import util.UnitAndResRepositoryStrings;

public class UnitConverter {

	public UnitConverter() {}
	
	public String ConvertToJSON(Unit entity) {
		entity.setHost(saveHostId(entity.getHost()));
		entity.setApartmentComment(saveApartmentComment(entity.getApartmentComment()));
		// TODO: Nije znalo da napravi JSON od Unita u update pozivu!
		String JSON = new Gson().toJson(entity);
		System.out.println(JSON);
		return UnitAndResRepositoryStrings.unitString + JSON;
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
		System.out.println(JSON);
		return (new Gson().fromJson(JSON, Unit.class));
	}
}
