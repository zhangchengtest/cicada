package com.cicada.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.cicada.core.model.user.User;
import com.cicada.web.constant.WebConstant;

public class LoginInterceptor extends HandlerInterceptorAdapter {

	private static final Logger log = LoggerFactory.getLogger(LoginInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		User userContext = (User) request.getSession().getAttribute(WebConstant.Session.USER_CONTEXT);
		if (userContext == null) {
			response.sendRedirect(request.getContextPath() + "/tologin");
			return false;
		}

		return true;
	}
}
