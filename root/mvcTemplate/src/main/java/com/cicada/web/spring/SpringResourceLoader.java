package com.cicada.web.spring;

import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

public class SpringResourceLoader implements ResourceLoaderAware {

	private static ResourceLoader resourceLoader = new DefaultResourceLoader();

	/**
	 * Set the Spring ResourceLoader to use for loading Velocity files. The default is DefaultResourceLoader. Will get
	 * overridden by the ApplicationContext if running in a context.
	 * 
	 * @see org.springframework.core.io.DefaultResourceLoader
	 * @see org.springframework.context.ApplicationContext
	 */
	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}

	/**
	 * Return the Spring ResourceLoader to use for loading Velocity files.
	 */
	public ResourceLoader getResourceLoader() {
		return this.resourceLoader;
	}

	public static Resource getResource(String location) {
		return resourceLoader.getResource(location);
	}
}
