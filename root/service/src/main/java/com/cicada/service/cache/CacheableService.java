package com.cicada.service.cache;

import java.util.Map;

/**
 * cacheable service interface.
 * 
 * @author hermano
 *
 * @param <T>
 */
public interface CacheableService<T> {

	public T singleLoad4Cache(String key) throws Exception;

	public Map<String, T> bulkLoad4Cache(Iterable<? extends String> keys) throws Exception;
}
