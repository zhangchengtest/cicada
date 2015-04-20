package com.cicada.web.vo;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.BindingResult;

import com.cicada.core.constant.MessageEnum;
import com.cicada.web.spring.SpringContext;

public class BaseResult implements VO {

	private HttpServletRequest request;
	private BindingResult errors;
	private Object obj;
	

	/**
	 * 默认为false
	 */
	private boolean isSuccess = false;

	/**
	 * 错误信息code
	 */
	private MessageEnum message;
	
	
	/**
	 * 当成功的时候用这个构造器
	 */
	public BaseResult(HttpServletRequest request, MessageEnum message, Object obj)
	{
		this.request = request;
		this.message = message;
		this.isSuccess = true;
		this.obj = obj;
	}
	/**
	 * 当成功的时候用这个构造器
	 */
	public BaseResult(Object obj)
	{
		this.isSuccess = true;
		this.obj = obj;
	}
	
	/**
	 * 当成功的时候用这个构造器
	 */
	public BaseResult()
	{
		this.isSuccess = true;
	}
	

	/**
	 * 当有springmvc 验证错误的时候，用这个构造器
	 * 
	 * @param request
	 * @param errors
	 */
	public BaseResult(HttpServletRequest request, BindingResult errors) {
		this.request = request;
		this.errors = errors;
	}

	/**
	 * 当自定义错误的时候
	 * 
	 * @param request
	 * @param code
	 */
	public BaseResult(HttpServletRequest request, MessageEnum message, boolean isSuccess) {
		this.request = request;
		this.message = message;
		this.isSuccess = isSuccess;
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public String getMessage() {
		if (message != null) {
			return SpringContext.getMessage(message.getCode(), message.getDefaultMsg(), request);
		} else if (errors != null && request != null) {
			return SpringContext.getMessage(errors.getAllErrors().get(0).getCode(), errors.getAllErrors().get(0).getDefaultMessage(), request);
		} else {
			return "";
		}
	}

	public Object getObj() {
		return obj;
	}
	
}
