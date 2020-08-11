package converters;

import com.google.gson.Gson;
import model.Host;

public class HostConverter {

	public HostConverter() {}
	
	public String ConvertToJSON(Host entity) {
		return new Gson().toJson(entity);
	}
	
	public Host ConvertFromJSON(String JSON) {
		return new Gson().fromJson(JSON, Host.class);
	}
}
