package converters;

import com.google.gson.Gson;
import model.Reservation;

public class ReservationConverter {

	public ReservationConverter() {}
	
	public String ConvertToJSON(Reservation entity) {
		return new Gson().toJson(entity);
	}
	
	public Reservation ConvertFromJSON(String JSON) {
		return new Gson().fromJson(JSON, Reservation.class);
	}
}
