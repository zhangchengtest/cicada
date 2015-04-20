package com.cicada.web.spring;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.servlet.support.RequestContextUtils;

public class SpringContext implements ApplicationContextAware {

	private static ApplicationContext applicationContext;

	public SpringContext() {
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	public static Object getBean(String name) throws BeansException {
		if (applicationContext != null) {
			return applicationContext.getBean(name);
		}
		return null;
	}

	public static Object getBean(String name, Class requiredType) throws BeansException {
		return applicationContext.getBean(name, requiredType);
	}

	public static boolean containsBean(String name) {
		return applicationContext.containsBean(name);
	}

	public static boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
		return applicationContext.isSingleton(name);
	}

	public static Class getType(String name) throws NoSuchBeanDefinitionException {
		return applicationContext.getType(name);
	}

	public static String[] getAliases(String name) throws NoSuchBeanDefinitionException {
		return applicationContext.getAliases(name);
	}

	public static String getMessage(String code, HttpServletRequest request) {
		Locale locale = RequestContextUtils.getLocaleResolver(request).resolveLocale(request);
		return applicationContext.getMessage(code, null, locale);
	}

	public static String getMessage(String code, String defaultMsg, HttpServletRequest request) {
		Locale locale = RequestContextUtils.getLocaleResolver(request).resolveLocale(request);
		return applicationContext.getMessage(code, null, defaultMsg, locale);
	}

}
