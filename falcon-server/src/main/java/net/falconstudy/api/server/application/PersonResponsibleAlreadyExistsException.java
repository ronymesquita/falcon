package net.falconstudy.api.server.application;

public class PersonResponsibleAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public PersonResponsibleAlreadyExistsException(String message) {
		super(message);
	}

}
