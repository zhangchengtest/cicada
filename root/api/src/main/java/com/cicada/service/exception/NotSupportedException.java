package com.cicada.service.exception;

public class NotSupportedException extends RuntimeServiceException {

	private static final long serialVersionUID = 9117462005164737322L;

	public NotSupportedException() {
	}

	public NotSupportedException(String message) {
		super(message);
	}

	public NotSupportedException(Throwable cause) {
		super(cause);
	}

	public NotSupportedException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotSupportedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
