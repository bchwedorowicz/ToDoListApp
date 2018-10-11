package pl.beata.todolist.exceptions;

public class ValidationException extends Exception {

	private static final long serialVersionUID = -4371441290256387460L;

	public ValidationException(String message) {
		super(message);
	}
}
