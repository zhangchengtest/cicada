package com.cicada.service.impl.helper;

import static com.cicada.utils.ScannerUtils.scan4Subclasses;
import static org.apache.commons.lang3.StringUtils.removeEnd;
import static org.apache.commons.lang3.StringUtils.uncapitalize;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.cicada.core.model.Model;
import com.cicada.dao.CRUDDao;
import com.cicada.dao.Dao;
import com.cicada.service.exception.RuntimeServiceException;
import com.cicada.utils.ScannerUtils;

/**
 * Haze mapper registry by using spring application context.
 * 
 * @author hermano
 *
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class MapperRegistry {

	private static final Logger logger = LoggerFactory.getLogger(MapperRegistry.class);
	private static final String MAPPER_SUFFIX = "Dao";

	private static Map<String, RegistryEntry> registryMapping = new ConcurrentHashMap<String, RegistryEntry>();
	private static boolean earlyFail = true;

	@Resource
	private ApplicationContext appContext;

	static {
		createMapping();
		addCustomMapping();

		StringBuffer buffer = new StringBuffer("create mapping for MapperRegistry: \n");
		for (RegistryEntry entry : registryMapping.values()) {
			buffer.append('\t');
			buffer.append(StringUtils.rightPad(entry.getPojoName(), 20));
			buffer.append("=> [ModelClass=");
			buffer.append(getClassName(entry.getModelClass()));
			buffer.append(", mapperClass=");
			buffer.append(getClassName(entry.getMapperClass()));
			buffer.append(", mapperName=");
			buffer.append(entry.getMapperName());
			buffer.append(']');
			buffer.append('\n');
		}
		logger.debug(buffer.toString());
	}

	private static void checkEarlyFail(String msg, Object... objects) {
		FormattingTuple ft = MessageFormatter.arrayFormat(msg, objects);
		logger.error(ft.getMessage(), ft.getThrowable());
		if (earlyFail) {
			throw new RuntimeServiceException(ft.getMessage(), ft.getThrowable());
		}
	}

	/**
	 * create mapper,bean,entity mapping using scanner.
	 */
	@SuppressWarnings("unchecked")
	private static void createMapping() {

		Set<Class<? extends CRUDDao>> mappers = ScannerUtils.scan4Subclasses(CRUDDao.class, CRUDDao.class.getPackage().getName(), true);

		// bean classes
		Set<Class<? extends Model>> beanClasses = scan4Subclasses(Model.class, Model.class.getPackage().getName(), false);
		Map<String, Class<? extends Model>> beanMap = new HashMap<String, Class<? extends Model>>(beanClasses.size());
		// explain: possible key overwrite for identical bean name, but we won't have this scenario in reality, so just
		// ignore this.
		for (Class<? extends Model> clazz : beanClasses) {
			beanMap.put(class2PojoName(clazz), clazz);
		}
		logger.debug("scanned bean classes: {}", beanMap.keySet());

		RegistryEntry entry;
		String pojoName;
		for (Class<? extends Dao> mapperClass : mappers) {
			entry = new RegistryEntry();

			// mapper name
			String aopMapperName ;
			
			pojoName = mapper2PojoName(mapperClass);
			
			aopMapperName = getMapperNameByConvention(pojoName);
			
			entry.setMapperName(aopMapperName);
			entry.setPojoName(pojoName);
			entry.setModelClass(beanMap.get(pojoName));
			entry.setMapperClass((Class<? extends CRUDDao>) mapperClass);
			
			registryMapping.put(pojoName, entry);
		}

	}

	/**
	 * add any custom case for mapping
	 */
	private static void addCustomMapping() {
		// registryMapping.put(pojoName, entry);
	}


	public static String mapper2PojoName(Class<? extends Dao> mapperClass) {
		return removeEnd(mapperClass.getSimpleName(), MAPPER_SUFFIX);
	}

	private static String getMapperNameByConvention(String pojoName) {
		return uncapitalize(pojoName) + MAPPER_SUFFIX;
	}

	private MapperRegistry() {
	}

	/**
	 * get mapper by its AOP name
	 * 
	 * @param mapperName
	 * @return
	 */
	public CRUDDao get(String mapperName) {
		return (CRUDDao) appContext.getBean(mapperName);
	}

	public static String class2PojoName(Class<? extends Model> clazz) {
		return clazz.getSimpleName();
	}
	/**
	 * get mapper by its corresponding entity class
	 * 
	 * @param entity
	 * @return
	 */
	public CRUDDao get(Class<? extends Model> entityClass) {
		return get(getMapperNameByConvention(class2PojoName(entityClass)));
	}

	public static Class<? extends Model> getModelClass(Class<? extends Model> entityClass) {
		String pojo = class2PojoName(entityClass);
		if (registryMapping.containsKey(pojo)) {
			return registryMapping.get(pojo).getModelClass();
		}
		return null;
	}

	private static String getClassName(Class<?> clazz) {
		return clazz == null ? "<null>" : clazz.getName();
	}

}
