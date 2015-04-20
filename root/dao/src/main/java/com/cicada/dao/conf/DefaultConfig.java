package com.cicada.dao.conf;

import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import com.cicada.utils.ResourceUtils;

/**
 * for resource loading only purpose.
 * 
 * @author hermano
 *
 */
// TODO: consider using SoftReference
public final class DefaultConfig {

	private static Map<String, Properties> propertyCache;

	private static final String JDBC_PROP_FILE = "persist/jdbc.properties";
	private static final String MYBATIS_PROP_FILE = "persist/ibatis.properties";
	private static final String MYBATIS_CONF_FILE = "persist/mybatis-configuration.xml";
	private static final String APP_WEB_PROP_FILE = "web/app.properties";
	private static final String REDIS_PROP_FILE = "impl/redis.properties";
	private static final String SVR_CACHE_PROP_FILE = "impl/svr_cache.properties";
	private static final String THREAD_POOL_PROP_FILE = "impl/thread_pool.properties";
	private static final String SECURITY_PROP_FILE = "impl/security.properties";
	private static final String Mail_PROP_FILE = "impl/mail.properties";

	static {
		propertyCache = new ConcurrentHashMap<String, Properties>(11);
	}

	private DefaultConfig() {
	}

	public static Properties getProperties(final String resource) {
		if (propertyCache.containsKey(resource)) {
			return propertyCache.get(resource);
		} else {
			synchronized (DefaultConfig.class) {
				Properties props = ResourceUtils.getResourceAsProperty(resource,
						DefaultConfig.class.getClassLoader());
				propertyCache.put(resource, props);
				return props;
			}
		}
	}

	public static Properties getJDBCProps() {
		return getProperties(JDBC_PROP_FILE);
	}

	public static Properties getMybatisProps() {
		return getProperties(MYBATIS_PROP_FILE);
	}

	public static String getMybatisConfFile() {
		return MYBATIS_CONF_FILE;
	}

	public static Properties getAppWebProps() {
		return getProperties(APP_WEB_PROP_FILE);
	}

	public static Properties getRedisProps() {
		return getProperties(REDIS_PROP_FILE);
	}

	public static Properties getServiceCacheProps() {
		return getProperties(SVR_CACHE_PROP_FILE);
	}

	public static Properties getThreadPoolProps() {
		return getProperties(THREAD_POOL_PROP_FILE);
	}

	public static Properties getSecurityProps() {
		return getProperties(SECURITY_PROP_FILE);
	}

	public static Properties getMailProps() {
		return getProperties(Mail_PROP_FILE);
	}

}
