package converters;

import com.google.gson.Gson;
import model.ApartmentComment;

public class ApartmentCommentConverter {
	
	public ApartmentCommentConverter() {}
	
	public String ConvertToJSON(ApartmentComment entity) {
		return new Gson().toJson(entity);
	}
	
	public ApartmentComment ConvertFromJSON(String JSON) {
		return new Gson().fromJson(JSON, ApartmentComment.class);
	}
}
