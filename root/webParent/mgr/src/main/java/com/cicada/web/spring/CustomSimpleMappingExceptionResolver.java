package com.cicada.web.spring;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.cicada.utils.StringUtils;

public class CustomSimpleMappingExceptionResolver extends SimpleMappingExceptionResolver {

	private static final Logger logger = LoggerFactory.getLogger(WebConfig.class);

	@Override
	protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {

		// VM返回
		if (!(request.getHeader("accept").indexOf("application/json") > -1 || (request.getHeader("X-Requested-With") != null && request
				.getHeader("X-Requested-With").indexOf("XMLHttpRequest") > -1))) {
			if (ex instanceof NoHandlerFoundException) {
				return new ModelAndView("exception/404");
			}

			// 如果不是异步请求
			// Apply HTTP status code for error views, if specified.
			// Only apply it if we're processing a top-level request.

			JSONObject json = new JSONObject();
			json.put("isSuccess", false);
			if (StringUtils.equals(ex.getClass().getSimpleName(), "BussinessException")) {
				json.put("message", ex.getMessage());
			} else {
				json.put("message", "系统出现异常");
				json.put("hiddenMessage", ex.getMessage() + "-----" + ex.getCause());
			}

			return new ModelAndView("exception/500", "data", json);
		} else {
			// JSON格式返回
			JSONObject json = new JSONObject();
			json.put("isSuccess", false);
			if (StringUtils.equals(ex.getClass().getSimpleName(), "BussinessException")) {
				json.put("message", ex.getMessage());
			} else {
				json.put("message", "系统出现异常");
			}

			return new ModelAndView("exception/ajax", "data", json);
		}
	}
}