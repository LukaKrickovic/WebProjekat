package converters;

import com.google.gson.Gson;
import model.Guest;

public class GuestConverter {
	
	public GuestConverter() {}
	
	public String ConvertToJSON(Guest entity) {
		return new Gson().toJson(entity);
	}
	
	public Guest ConvertFromJSON(String JSON) {
		return new Gson().fromJson(JSON, Guest.class);
	}
}
