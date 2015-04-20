package com.cicada.web.spring.rest;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.cicada.utils.StringUtils;
import com.cicada.web.exceptions.InvalidParameterException;
import com.cicada.web.exceptions.NotFoundException;
import com.cicada.web.exceptions.UnprocessableException;
import com.cicada.web.exceptions.WebException;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	public RestResponseEntityExceptionHandler() {
		super();
	}

	@ExceptionHandler(value = { WebException.class, InvalidParameterException.class, NotFoundException.class,
			UnprocessableException.class, })
	public final ResponseEntity<Object> handleWebException(WebException ex, HttpServletRequest req,
			HttpServletResponse resp) {
		ResponseStatus respStatus = ex.getClass().getAnnotation(ResponseStatus.class);
		HttpStatus httpStatus = (respStatus == null ? HttpStatus.INTERNAL_SERVER_ERROR : respStatus.value());
		return handleExceptionInternal(ex, null, new HttpHeaders(), httpStatus, new ServletWebRequest(req, resp));
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		if (body == null) {
			body = new RestResponseInfo(status, ex);
		}
		return super.handleExceptionInternal(ex, body, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		Map<String, String> msgMap = new HashMap<String, String>();
		String fieldName, errMsg;
		for (ObjectError err : ex.getBindingResult().getAllErrors()) {
			fieldName = ((DefaultMessageSourceResolvable) err.getArguments()[0]).getDefaultMessage();
			errMsg = err.getDefaultMessage();
			msgMap.put(fieldName, errMsg);
		}
		RestResponseInfo body = new RestResponseInfo(status, ex.getClass().getSimpleName() + ": "
				+ StringUtils.joinMapValue(msgMap, ','));
		return handleExceptionInternal(ex, body, headers, status, request);
	}

	public static WebException wrapException(Throwable t, String safeMessage) {
		if (t instanceof WebException) {
			return WebException.class.cast(t);
		} else {
			return new WebException(safeMessage, t);
		}
	}
}
