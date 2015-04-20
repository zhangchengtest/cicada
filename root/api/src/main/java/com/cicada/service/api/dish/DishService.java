package com.cicada.service.api.dish;

import java.util.List;

import com.cicada.core.model.dish.Dish;
import com.cicada.service.api.Service;
import com.cicada.service.exception.ServiceException;

public interface DishService extends Service{

	/**
	 * 随意搜索一个不属于menuIds中的menu,
	 * @param menuIds
	 * @return
	 */
	public Dish randomSearch(List<String> dishIds) throws ServiceException;

	/**
	 * 
	 * @param names
	 * @param isBreakfast
	 * @throws ServiceException 
	 */
	public void create(String names, boolean isBreakfast) throws ServiceException;
	
}
