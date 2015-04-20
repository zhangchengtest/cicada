package com.cicada.web.spring;

import java.util.EventListener;

import javax.servlet.Filter;
import javax.servlet.ServletContext;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * <p>
 * WebApp Initializer Template<br />
 * Subclasses can add the following annotation to change the orders:<br/>
 * <code>@Order(1)</code><br />
 * </p>
 * 
 * @author hermano
 *
 */
public abstract class WebAppInitializerTemplate extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected abstract Class<?>[] getRootConfigClasses();

	@Override
	protected abstract Class<?>[] getServletConfigClasses();

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

	@Override
	protected Filter[] getServletFilters() {
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding("UTF-8");
		// characterEncodingFilter.setForceEncoding(true);
		return new Filter[] { characterEncodingFilter };
	}

	/**
	 * add customer listeners
	 * 
	 * @return
	 */
	protected Class<? extends EventListener>[] getEventListeners() {
		return null;
	}

	@Override
	protected void registerContextLoaderListener(ServletContext servletContext) {
		super.registerContextLoaderListener(servletContext);
		Class<? extends EventListener>[] listeners = getEventListeners();
		if (listeners != null) {
			for (Class<? extends EventListener> clazz : listeners) {
				servletContext.addListener(clazz);
			}
		}
	}
}
