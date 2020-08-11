package model;

public class Model {

	private boolean isDeleted;
	
	public Model() {
		isDeleted = false;
	}
	
	public void Delete() {
		isDeleted = true;
	}
	
	public Model Recover() {
		isDeleted = false;
		return this;
	}
	
	
}
