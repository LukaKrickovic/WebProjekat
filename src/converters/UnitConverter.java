package converters;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import com.google.gson.reflect.TypeToken;
import model.ApartmentComment;
import model.Host;
import model.Reservation;
import model.Unit;
import util.UnitAndResRepositoryStrings;

public class UnitConverter {

	public UnitConverter() {}
	
	public String ConvertToJSON(Unit entity) {
		if(entity.getHost() != null)
			entity.setHost(saveHostId(entity.getHost()));
		String JSON = new Gson().toJson(entity);
		return JSON;
	}

	private Host saveHostId(Host host) {
		return new Host(host.getId());
	}

	public ArrayList<Unit> ConvertFromJSON(String JSON) {
		Type listType = new TypeToken<ArrayList<Unit>>(){}.getType();
		if(JSON.charAt(0) == '[')
			return (new Gson().fromJson(JSON, listType));
		else {
			ArrayList<Unit> retVal = new ArrayList<Unit>();
			retVal.add(new Gson().fromJson(JSON, Unit.class));
			return retVal;
		}
	}

}
