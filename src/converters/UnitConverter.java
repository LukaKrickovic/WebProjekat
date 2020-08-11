package converters;

import com.google.gson.Gson;
import model.Unit;

public class UnitConverter {

	public UnitConverter() {}
	
	public String ConvertToJSON(Unit entity) {
		return new Gson().toJson(entity);
	}
	
	public Unit ConvertFromJSON(String JSON) {
		return new Gson().fromJson(JSON, Unit.class);
	}
}
