package com.cicada.service.impl;

import static org.apache.commons.lang3.StringUtils.capitalize;
import static org.apache.commons.lang3.StringUtils.isBlank;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cicada.core.model.Model;
import com.cicada.core.model.page.Pagination;
import com.cicada.dao.CRUDDao;
import com.cicada.service.api.CRUDService;
import com.cicada.service.cache.CacheableService;
import com.cicada.service.exception.ObjectNotFoundException;
import com.cicada.service.exception.RuntimeServiceException;
import com.cicada.service.exception.ServiceException;
import com.cicada.service.impl.helper.MapperRegistry;
import com.cicada.utils.ReflectionUtils;
import com.cicada.utils.SecurityUtils;
import com.cicada.utils.StringUtils;

@Service("crudService")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CRUDServiceImpl extends BaseService implements CRUDService, CacheableService<Model> {

	private static final Logger logger = LoggerFactory.getLogger(CRUDServiceImpl.class);

	@Autowired
	private MapperRegistry registry;

	// @Autowired
	// @Qualifier(DEFAULT_CACHE_PROVIDER)
	// private HazeCacheProvider<UserBean> cacheProvider;
	//
	// private LoadingCache<String, UserBean> cache;

	public CRUDServiceImpl() {
	}

	@PostConstruct
	private void initCache() {
		// cache =
		// cacheProvider.getCache(cacheProvider.getConfigByConvention(UserBean.class),
		// this);
	}

	/**
	 * update and set ID/createDate/updateDate to default value if necessary.<br />
	 * std = standard
	 * 
	 * @param entity
	 */
	public static void updateStdProperties(Model entity) {
		updateProperty(entity, "ID", SecurityUtils.UUID(), String.class);
		updateDateProperties(entity);
	}

	public static void updateDateProperties(Model entity) {
		updateProperty(entity, "createDate", new Date(), Date.class);
		updateProperty(entity, "updateDate", new Date(), Date.class);
	}

	public static void updateProperty(Model entity, String propertyName, Object newValue, Class<?> parameterTypes) {
		if (updateRequired(entity, propertyName)) {
			try {
				ReflectionUtils.invokeSetter(entity, propertyName, newValue, parameterTypes);
			} catch (Exception e) {
				logger.warn("set{} failed for {}", capitalize(propertyName), entity.getClass().getSimpleName(), e);
			}
		}
	}

	/**
	 * check if auto-update is required for the given property.
	 * 
	 * @param entity
	 * @param propertyName
	 * @return <code>true</code> if ID/createDate/updateDate is null or empty, <code>false</code> otherwise.
	 */
	public static <T> boolean updateRequired(Model entity, String propertyName) {
		try {
			T t = ReflectionUtils.invokeGetter(entity, propertyName);
			if (t == null) {
				return true;
			} else if (t instanceof String) {
				return isBlank((String) t);
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	@Transactional
	public <T extends Model> T create(T model) throws ServiceException, RuntimeServiceException {

		CRUDDao mapper = registry.get(model.getClass());

		int size = mapper.insert(model);
		logger.debug("create new {}, affected row={}", model.getClass().getSimpleName(), size);

		return model;
	}

	// TODO: set update_date automatically; retrieve from cache
	@Override
	@Transactional
	public <T extends Model> T retrieve(Class<T> modelClass, String id) throws ServiceException,
			RuntimeServiceException {

		CRUDDao mapper = registry.get(modelClass);

		T model = mapper.selectOne(id);

		if (model == null) {
			final String msg = StringUtils.formatMessage("{}(id={}) not found.", modelClass.getSimpleName(), id);
			logger.warn(msg);
			throw new ObjectNotFoundException(msg);
		}
		logger.trace("retrieve {}: {}", modelClass.getSimpleName(), model.toString());

		return model;
	}

	// TODO: retrieve from cache
	@Override
	@Transactional
	public <T extends Model> Pagination<T> retrieve(Class<T> modelClass, int pageNum, int pageSize)
			throws ServiceException, RuntimeServiceException {
		Class<? extends Model> entityClass = MapperRegistry.getModelClass(modelClass);
		CRUDDao mapper = registry.get(entityClass);

		Pagination<T> pagination = new Pagination<T>(mapper.countAll(), pageNum, pageSize);
		Model filter = ReflectionUtils.newInstance(entityClass);
		List<T> entities = mapper.selectBy(null, pagination.getStartRow(), pagination.getPageSize());

		if (entities == null || entities.size() == 0) {
			logger.warn("no pagination data found for {}: {}", modelClass.getSimpleName(), pagination.toString());
			pagination.setPageData(new ArrayList<T>(0));
		} else {
			pagination.setPageData(entities);
		}
		return pagination;
	}

	// TODO: retrieve from cache
	@Override
	@Transactional
	public <T extends Model> List<T> retrieveAll(Class<T> modelClass) throws ServiceException, RuntimeServiceException {

		Class<? extends Model> entityClass = MapperRegistry.getModelClass(modelClass);
		CRUDDao mapper = registry.get(entityClass);

		List<T> entities = mapper.selectAll();
		if (entities == null || entities.size() == 0) {
			logger.warn("no data found for {}", modelClass.getSimpleName());
			return new ArrayList<T>(0);
		}

		return entities;
	}

	// TODO: retrieve from cache
	@Override
	@Transactional
	public <T extends Model> Pagination<T> retrieveBy(Class<T> modelClass, T filterBean, int pageNum, int pageSize)
			throws ServiceException, RuntimeServiceException {
		Class<? extends Model> entityClass = MapperRegistry.getModelClass(modelClass);
		CRUDDao mapper = registry.get(entityClass);

		Pagination<T> pagination = new Pagination<T>(mapper.countBy(filterBean), pageNum, pageSize);
		List<T> entities = mapper.selectBy(filterBean, pagination.getStartRow(), pagination.getPageSize());

		if (entities == null || entities.size() == 0) {
			logger.warn("no pagination data found for {}: {}", modelClass.getSimpleName(), pagination.toString());
			pagination.setPageData(new ArrayList<T>(0));
		} else {

			pagination.setPageData(entities);
		}
		return pagination;
	}

	@Override
	@Transactional
	public Model update(Model model) throws ServiceException, RuntimeServiceException {

		CRUDDao mapper = registry.get(model.getClass());

		// ID will always be kept

		int size = mapper.update(model);
		logger.debug("update {}, affected row={}", model.getClass().getSimpleName(), size);

		return model;
	}

	@Override
	public boolean delete(Class<? extends Model> modelClass, String id) throws ServiceException,
			RuntimeServiceException {

		Class<? extends Model> entityClass = MapperRegistry.getModelClass(modelClass);
		CRUDDao mapper = registry.get(entityClass);

		int size = mapper.delete(id);
		logger.debug("delete {}, affected row={}", modelClass.getSimpleName(), size);

		return size == 1;
	}

	@Override
	public Model singleLoad4Cache(String key) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Model> bulkLoad4Cache(Iterable<? extends String> keys) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	// TODO: retrieve from cache
	@Override
	@Transactional
	public <T extends Model> List<T> retrieveBy(T filterBean) throws ServiceException, RuntimeServiceException {
		CRUDDao mapper = registry.get(filterBean.getClass());

		List<T> entities = mapper.selectBy(filterBean, 0, Integer.MAX_VALUE);
		if (entities == null || entities.size() == 0) {
			logger.warn("no pagination data found for {}", filterBean.getClass().getSimpleName());
		}
		return entities;
	}

}
