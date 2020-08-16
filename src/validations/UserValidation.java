package validations;

import java.util.regex.Pattern;

import exceptions.UserValidationException;
import model.User;
import util.Regexes;

public class UserValidation implements IValidation<User>{

	
	public UserValidation() {

	}
	
	public void validate(User entity) {
		try {
			validateUsername(entity.getUsername());
			validatePassword(entity.getPassword());
			validateName(entity.getName());
			validateSurname(entity.getSurname());
		} catch (UserValidationException e) {
			e.printStackTrace();
		}
	}
	
	private void validateSurname(String surname) throws UserValidationException {
		if(!Pattern.matches(Regexes.surnamePattern, surname)) {
			throw new UserValidationException("Surname is not valid!");
		}
	}

	private void validateName(String name) throws UserValidationException {
		if(!Pattern.matches(Regexes.namePattern, name)) {
			throw new UserValidationException("Name is not valid!");
		}
		
	}

	private void validatePassword(String password) throws UserValidationException {
		if(!Pattern.matches(Regexes.passwordPattern, password)) {
			throw new UserValidationException("Password is not valid!");
		}
		
	}

	private void validateUsername(String username) throws UserValidationException {
		if(!Pattern.matches(Regexes.usernamePattern, username)) {
			throw new UserValidationException("Username is not valid!");
		}
	}
	
}
