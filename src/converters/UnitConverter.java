package converters;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import model.Reservation;
import model.Unit;

public class UnitConverter {

	public UnitConverter() {}
	
	public String ConvertToJSON(Unit entity) {
		entity.setReservations(saveReservationIds(entity.getReservations()));
		return new Gson().toJson(entity);
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
