package converters;

import com.google.gson.Gson;

import model.Guest;
import model.Reservation;
import model.Unit;
import util.UnitAndResRepositoryStrings;

public class ReservationConverter {

	public ReservationConverter() {}
	
	public String ConvertToJSON(Reservation entity) {
		entity.setGuest(saveGuestId(entity.getGuest()));
		return UnitAndResRepositoryStrings.reservationString + new Gson().toJson(entity);
	}
	
	private Unit saveUnitId(Unit unit) {
		return new Unit(unit.getId());
	}

	private Guest saveGuestId(Guest guest) {
		return new Guest(guest.getId());
	}

	public Reservation ConvertFromJSON(String JSON) {
		return new Gson().fromJson(JSON, Reservation.class);
	}
}
