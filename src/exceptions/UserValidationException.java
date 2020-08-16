package exceptions;

public class UserValidationException extends Exception{

	public UserValidationException() {
		super();
	}
	
	public UserValidationException(String message) {
		super(message);
	}
	
}
