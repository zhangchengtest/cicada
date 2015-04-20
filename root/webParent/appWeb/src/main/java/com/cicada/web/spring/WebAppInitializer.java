package com.cicada.web.spring;

import javax.servlet.ServletContext;

import org.springframework.core.annotation.Order;

@Order(1)
public class WebAppInitializer extends WebAppInitializerTemplate {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[] { RootConfig.class };
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[] { WebConfig.class };
	}

	@Override
	protected void registerContextLoaderListener(ServletContext servletContext) {
		super.registerContextLoaderListener(servletContext);
		servletContext.addListener(new HazeServletContextListener());
	}

}
