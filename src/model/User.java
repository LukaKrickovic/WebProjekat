package model;

import java.util.ArrayList;

import enums.Gender;
import enums.Roles;

public class User extends Model{
	private String username;
	private String password;
	private String name;
	private String surname;
	private Gender gender;
	private Roles role;
	private ArrayList<User> searchHistory;
	private String JWTToken;
	private boolean isBlocked;
	
	public User() {
		super();
	}
	
	public User(Id id) {
		super(id);
	}
	
	public User(Id id, String username, String password, String name, String surname, Gender gender, Roles role) {
		super(id);
		this.username = username;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.gender = gender;
		this.role = role;
		this.searchHistory = new ArrayList<User>();
		isBlocked = false;
	}



	public User(String username, String password, String name, String surname, Gender gender, Roles role) {
		super();
		this.username = username;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.gender = gender;
		this.role = role;
		this.searchHistory = new ArrayList<User>();
		isBlocked = false;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Roles getRole() {
		return role;
	}

	public ArrayList<User> getSearchHistory() {
		return searchHistory;
	}

	public void setSearchHistory(ArrayList<User> searchHistory) {
		this.searchHistory = searchHistory;
	}

	public String getJWTToken() {
		return JWTToken;
	}

	public void setJWTToken(String JWTToken) {
		this.JWTToken = JWTToken;
	}

	public boolean isBlocked() {
		return isBlocked;
	}

	public void setBlocked(boolean blocked) {
		isBlocked = blocked;
	}
}
