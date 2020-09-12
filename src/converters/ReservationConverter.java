package converters;

import com.google.gson.Gson;

import model.Guest;
import model.Reservation;
import model.Unit;
import model.User;
import util.UnitAndResRepositoryStrings;

public class ReservationConverter {

	public ReservationConverter() {}
	
	public String ConvertToJSON(Reservation entity) {
		if(entity.getUser() != null) {
			entity.setUser(saveUserId(entity.getUser()));
		}
		entity.setUnit(saveUnitId(entity.getUnit()));

		return new Gson().toJson(entity);
	}
	
	private Unit saveUnitId(Unit unit) {
		return new Unit(unit.getId());
	}

	private User saveUserId(User user){
		return new User(user.getId());
	}

	private Guest saveGuestId(Guest guest) {
		return new Guest(guest.getId());
	}

	public Reservation ConvertFromJSON(String JSON) {
		return new Gson().fromJson(JSON, Reservation.class);
	}
}
