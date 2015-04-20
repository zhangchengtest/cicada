package com.cicada.service.impl.dish;

import java.util.Calendar;
import java.util.List;
import java.util.Random;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.cicada.core.model.dish.Dish;
import com.cicada.dao.dish.DishDao;
import com.cicada.service.api.dish.DishService;
import com.cicada.service.exception.RuntimeServiceException;
import com.cicada.service.exception.ServiceException;
import com.cicada.utils.SecurityUtils;

@Service
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class DishServiceImpl implements DishService {

	private static final Logger logger = LoggerFactory.getLogger(DishServiceImpl.class);
	
	@Autowired
	private DishDao dishDao;

	@Override
	public Dish randomSearch(List<String> dishIds) {
		boolean isBreakfast = false;
		Calendar calendar = Calendar.getInstance();
		if (calendar.get(Calendar.HOUR_OF_DAY) > 5 && calendar.get(Calendar.HOUR_OF_DAY) < 9) {
			isBreakfast = true;
		}
		List<Dish> dishs = dishDao.selectIdsReverse(dishIds, isBreakfast);
		Dish theDish = null;
		if (CollectionUtils.isNotEmpty(dishs)) {
			theDish = dishs.get(new Random().nextInt(dishs.size()));
		}

		return theDish;
	}

	public static void main(String[] args) {
		Calendar calendar = Calendar.getInstance();
		System.out.println(calendar.get(Calendar.HOUR));
	}

	@Override
	public void create(String names, boolean isBreakfast) throws ServiceException{
		try
		{
			Dish dish = null;
			if(StringUtils.isNotEmpty(names))
			{
				String[] arrs = names.split("[、，。；？！,.;?!]");
				if(arrs.length > 0)
				{
					for(int i = 0; i < arrs.length; i ++)
					{
						dish =  new Dish();
						dish.setBreakfast(isBreakfast);
						dish.setName(arrs[i]);
						dish.setPhoto("empty");
						dish.setID(SecurityUtils.UUID());
						dish.setOwnerId("cheng");
						try
						{
							dishDao.insert(dish);	
						}
						catch(DuplicateKeyException e)
						{
							logger.warn("duplicate name");
						}
					}
				}
			}
		}catch(RuntimeException e)
		{
			logger.error("runtime exception ",e);
			throw new RuntimeServiceException("runtime exception", e);
		}
		
	}

}
