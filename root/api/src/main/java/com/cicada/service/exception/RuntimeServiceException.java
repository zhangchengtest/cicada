package com.cicada.service.exception;

public class RuntimeServiceException extends RuntimeException {

	private static final long serialVersionUID = 71155263998913441L;

	public RuntimeServiceException() {
	}

	public RuntimeServiceException(String message) {
		super(message);
	}

	public RuntimeServiceException(Throwable cause) {
		super(cause);
	}

	public RuntimeServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public RuntimeServiceException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
