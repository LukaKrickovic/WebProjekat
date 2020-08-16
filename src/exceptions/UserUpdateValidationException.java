package exceptions;

public class UserUpdateValidationException extends Exception{

	public UserUpdateValidationException() {
		super();
	}
	
	public UserUpdateValidationException(String message) {
		super(message);
	}
	
}
