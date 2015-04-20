package com.cicada.web.spring.rest;

import java.io.Serializable;

import org.springframework.http.HttpStatus;

import com.cicada.utils.StringUtils;

public class RestResponseInfo implements Serializable {

	private static final long serialVersionUID = 3128990494181244645L;

	private int code;
	private String status;
	private String message;

	public RestResponseInfo() {
		this(0, null, null);
	}

	public RestResponseInfo(int code, String status, String message) {
		super();
		this.code = code;
		this.status = status;
		this.message = message;
	}

	public RestResponseInfo(HttpStatus httpStatus, String message) {
		this(httpStatus.value(), httpStatus.name(), message);
	}

	public RestResponseInfo(HttpStatus httpStatus, Throwable t) {
		this(httpStatus, getExceptionMessage(t));
	}

	private static boolean isDevMode() {
		// TODO: return WebSetting.getInstance().isDevMode()
		return true;
	}

	private static String getExceptionMessage(Throwable t) {
		if (isDevMode()) {
			StringBuilder builder = new StringBuilder();
			builder.append(t.getClass().getName());
			builder.append(": ");
			builder.append(t.getMessage());
			builder.append('\n');
			// find root cause
			Throwable cause = t.getCause();
			while (cause != null && !cause.equals(cause.getCause())) {
				builder.append(cause.getMessage());
				builder.append('\n');
				cause = cause.getCause();
			}
			builder.deleteCharAt(builder.length() - 1);
			return builder.toString();
		} else {
			return t.getMessage();
		}
	}

	// return the first line of the message (before the first '\n' if any)
	private static String getFirstLine(String msg) {
		int index = StringUtils.indexOf(msg, '\n');
		if (index > -1) {
			return msg.substring(0, index);
		}
		return msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
