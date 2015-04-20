package com.cicada.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//copied from HazeUtils project since we don't want to depend on that whole project
public final class ResourceUtils {

	private static final Logger log = LoggerFactory.getLogger(ResourceUtils.class);
	public static final String DEFAULT_CHARSET = "UTF-8";

	public static InputStream getResourceAsStream(String resource, ClassLoader loader) {

		InputStream in = null;

		if (loader != null) {
			if (log.isDebugEnabled()) {
				log.debug("Loading resource '{}' from class loader '{}'.", resource, loader.getClass().getSimpleName());
			}
			in = loader.getResourceAsStream(resource);
		} else {
			if (log.isDebugEnabled()) {
				log.debug("Loading resource '{}' from default system class loader.", resource);
			}
			in = ClassLoader.getSystemResourceAsStream(resource);
		}

		return in;
	}

	public static Properties getResourceAsProperty(String resource, ClassLoader loader, String charset) {
		Properties props = new Properties();
		InputStream in = null;
		try {
			in = getResourceAsStream(resource, loader);
			props.load(new BufferedReader(new InputStreamReader(in, charset)));
			return props;
		} catch (IOException e) {
			log.error("Error load resource as property", e);
			throw new RuntimeException(e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					log.error("Error closing InputStream for resource: " + resource, e);
				}
			}
		}
	}

	public static Properties getResourceAsProperty(String resource, ClassLoader loader) {
		return getResourceAsProperty(resource, loader, DEFAULT_CHARSET);
	}

	public static Properties getResourceAsProperty(String resource) {
		return getResourceAsProperty(resource, null);
	}

}