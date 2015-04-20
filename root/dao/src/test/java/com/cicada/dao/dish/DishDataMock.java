package com.cicada.dao.dish;

import java.util.Date;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.cicada.core.model.dish.Dish;
import com.cicada.dao.PersistUnitTest;
import com.cicada.utils.SecurityUtils;

public class DishDataMock  extends PersistUnitTest {

	@Autowired
	private DishDao mapper;
	
	public Dish newDish() {
		Dish dish = new Dish();

		String random = "Junit_";
		dish.setID(SecurityUtils.UUID());
		dish.setBreakfast(false);
		dish.setName(random + RandomStringUtils.randomAlphanumeric(10));
		dish.setPhoto(random + RandomStringUtils.randomAlphanumeric(10));
		dish.setOwnerId(SecurityUtils.UUID());
		dish.setCreateDate(new Date());
		dish.setUpdateDate(new Date());

		return dish;
	}
	
	@Before
	public void setup()
	{
		String str = "麻辣子鸡、辣味合蒸、东安子鸡、洞庭野鸭、霸王别姬、金钱鱼、腊味合蒸、组底鱼翅、冰糖湘莲、红椒腊牛肉、发丝牛百页、火宫殿臭豆腐、吉首酸肉、换心蛋辣椒炒";
		String[] list = str.split("、");
		for(int i = 0; i < list.length; i ++)
		{
			Dish dish = newDish();
			dish.setName(list[i]);
			mapper.insert(dish);
		}
	}
	
}
