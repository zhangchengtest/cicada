package com.cicada.web.view;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.servlet.ModelAndView;

public class PlainTextView {

	private static final Logger logger = LoggerFactory.getLogger(PlainTextView.class);

	public static ModelAndView Render(String content, HttpServletResponse response) {

		StringHttpMessageConverter converter = new StringHttpMessageConverter();

		try {
			converter.write(content, MediaType.TEXT_PLAIN, new ServletServerHttpResponse(response));
		} catch (Exception e) {
			logger.error("Error rending plain text page", e);
		}

		return null;
	}
}
