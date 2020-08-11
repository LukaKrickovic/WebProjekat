package converters;

import com.google.gson.Gson;

import model.Administrator;

public class AdministratorConverter {

	public AdministratorConverter() {}
	
	public String ConvertToJSON(Administrator entity) {
		return new Gson().toJson(entity);
	}
	
	public Administrator ConvertFromJSON(String JSON) {
		return new Gson().fromJson(JSON, Administrator.class);
	}
	
}
