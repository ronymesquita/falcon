package net.falconstudy.api.server.application;

public class StudentDoesNotExistsException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public StudentDoesNotExistsException(String message) {
		super(message);
	}
	
}
