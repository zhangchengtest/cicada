package com.cicada.service.api;

import java.util.List;

import com.cicada.core.model.Model;
import com.cicada.core.model.page.Pagination;
import com.cicada.service.exception.RuntimeServiceException;
import com.cicada.service.exception.ServiceException;

/**
 * generic CRUD service for beans that allowed.
 * 
 * @author hermano
 *
 */
public interface CRUDService extends Service {

	/**
	 * create a new bean
	 * 
	 * @param bean
	 * @return
	 * @throws ServiceException
	 * @throws RuntimeServiceException
	 */
	public <T extends Model> T create(T bean) throws ServiceException, RuntimeServiceException;

	/**
	 * retrieve a Bean from database by the primary key.
	 * 
	 * @param beanClass
	 * @param id
	 * @return
	 * @throws ServiceException
	 * @throws RuntimeServiceException
	 */
	public <T extends Model> T retrieve(Class<T> beanClass, String id) throws ServiceException,
			RuntimeServiceException;

	/**
	 * search the beans in page
	 * 
	 * @param beanClass
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @throws ServiceException
	 * @throws RuntimeServiceException
	 */
	public <T extends Model>  Pagination<T> retrieve(Class<T> beanClass, int pageNum, int pageSize)
			throws ServiceException, RuntimeServiceException;

	/**
	 * retrieve all the data of this bean (without any pagination).
	 * 
	 * @param beanClass
	 * @return
	 * @throws ServiceException
	 * @throws RuntimeServiceException
	 */
	public <T extends Model> List<T> retrieveAll(Class<T> beanClass) throws ServiceException,
			RuntimeServiceException;

	/**
	 * search the beans in page by params.
	 * 
	 * @param beanClass
	 * @return
	 * @throws ServiceException
	 * @throws RuntimeServiceException
	 */
	public <T extends Model> Pagination<T> retrieveBy(Class<T> beanClass, T filterBean, int pageNum, int pageSize)
			throws ServiceException, RuntimeServiceException;

	/**
	 * search the beans by params.
	 * 
	 * @param beanClass
	 * @param filterBean
	 * @return
	 * @throws ServiceException
	 * @throws RuntimeServiceException
	 */
	public <T extends Model> List<T> retrieveBy(T filterBean) throws ServiceException,
			RuntimeServiceException;

	/**
	 * update the bean info
	 * 
	 * @param bean
	 * @return
	 * @throws ServiceException
	 * @throws RuntimeServiceException
	 */
	public Model update(Model bean) throws ServiceException, RuntimeServiceException;

	/**
	 * remove a bean from database by the primary key.
	 * 
	 * @param beanClass
	 * @param id
	 * @return
	 * @throws ServiceException
	 * @throws RuntimeServiceException
	 */
	public boolean delete(Class<? extends Model> beanClass, String id) throws ServiceException,
			RuntimeServiceException;

}
