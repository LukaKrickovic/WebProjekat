package model;

import model.Id;

public class Model {

	private boolean isDeleted;
	private Id id;
	
	public Model() {
		isDeleted = false;
	}
	
	public Model(Id id) {
		isDeleted = false;
		this.id = id;
	}
	
	public void Delete() {
		isDeleted = true;
	}
	
	public Model Recover() {
		isDeleted = false;
		return this;
	}

	public Id getId() {
		return id;
	}

	public void setId(Id id) {
		this.id = id;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	
	
}
