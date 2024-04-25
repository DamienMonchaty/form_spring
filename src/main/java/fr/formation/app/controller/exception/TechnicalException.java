package fr.formation.app.controller.exception;

public class TechnicalException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public TechnicalException(String message) {
		super(message);
	}

}
