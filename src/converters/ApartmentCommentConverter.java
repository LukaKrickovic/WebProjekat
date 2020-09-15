package converters;

import com.google.gson.Gson;

import model.ApartmentComment;
import model.Guest;
import model.Unit;
import model.User;

public class ApartmentCommentConverter {
	
	public ApartmentCommentConverter() {}
	
	public String ConvertToJSON(ApartmentComment entity) {
		entity.setUnit(saveUnitId(entity));
		if(entity.getGuest() != null)
			entity.setGuest(saveGuestId(entity));
		else if (entity.getUser() != null)
			entity.setUser(saveUserId(entity));
		return new Gson().toJson(entity);
	}
	
	private Guest saveGuestId(ApartmentComment entity) {
		return new Guest(entity.getGuest().getId());
	}
	private User saveUserId(ApartmentComment entity) {
		return new Guest(entity.getUser().getId());
	}

	private Unit saveUnitId(ApartmentComment entity) {
		return new Unit(entity.getUnit().getId());
		
	}

	public ApartmentComment ConvertFromJSON(String JSON) {
		return new Gson().fromJson(JSON, ApartmentComment.class);
	}
}
