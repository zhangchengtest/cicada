package com.cicada.service.exception;

public class ParameterValidationException extends ServiceException {

	private static final long serialVersionUID = 3243427333464292986L;

	public ParameterValidationException() {
	}

	public ParameterValidationException(String message) {
		super(message);
	}

	public ParameterValidationException(Throwable cause) {
		super(cause);
	}

	public ParameterValidationException(String message, Throwable cause) {
		super(message, cause);
	}

	public ParameterValidationException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
