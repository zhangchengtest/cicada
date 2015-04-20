package com.cicada.web.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
public class UnprocessableException extends WebException {

	private static final long serialVersionUID = -1751507249586744222L;

	public UnprocessableException() {
	}

	public UnprocessableException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public UnprocessableException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnprocessableException(String message) {
		super(message);
	}

	public UnprocessableException(Throwable cause) {
		super(cause);
	}

}
