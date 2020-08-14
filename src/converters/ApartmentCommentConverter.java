package converters;

import com.google.gson.Gson;

import model.ApartmentComment;
import model.Guest;
import model.Unit;

public class ApartmentCommentConverter {
	
	public ApartmentCommentConverter() {}
	
	public String ConvertToJSON(ApartmentComment entity) {
		entity.setUnit(saveUnitId(entity));
		entity.setGuest(saveGuestId(entity));
		return new Gson().toJson(entity);
	}
	
	private Guest saveGuestId(ApartmentComment entity) {
		return new Guest(entity.getGuest().getId());
	}

	private Unit saveUnitId(ApartmentComment entity) {
		return new Unit(entity.getUnit().getId());
		
	}

	public ApartmentComment ConvertFromJSON(String JSON) {
		return new Gson().fromJson(JSON, ApartmentComment.class);
	}
}
