package net.falconstudy.api.server.application;

public class CourseDoesNotExistsException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CourseDoesNotExistsException(String message) {
		super(message);
	}

}
