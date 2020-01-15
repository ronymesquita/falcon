package net.falconstudy.api.server.application;

public class PersonResponsibleDoesNotExistsException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public PersonResponsibleDoesNotExistsException(String message) {
		super(message);
	}

}
