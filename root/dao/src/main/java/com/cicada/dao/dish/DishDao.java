package com.cicada.dao.dish;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cicada.core.model.dish.Dish;
import com.cicada.dao.CRUDDao;

public interface DishDao extends CRUDDao{

	/**
	 * 查找不在此的Ids
	 * @return
	 */
	List<Dish> selectIdsReverse(@Param("ids") List<String> ids, @Param("isBreakfast") boolean isBreakfast);
}
