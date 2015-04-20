package com.cicada.dao.dish;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.cicada.core.model.dish.Dish;
import com.cicada.dao.PersistUnitTest;
import com.cicada.utils.SecurityUtils;

/**
 * 
 * @author zhangcheng
 *
 */
public class DishDaoTest extends PersistUnitTest {

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

	public Dish internalCreate() {
		Dish dish = newDish();
		mapper.insert(dish);
		return dish;
	}

	@Test
	@Transactional
	public void testInsert() {
		internalCreate();
	}

	@Test
	@Transactional
	public void testSelectOne() {
		Dish dish = internalCreate();
		Dish newAcc = mapper.selectOne(dish.getID());
		assertTrue(dish.equals(newAcc));
	}

	@Test
	@Transactional
	public void testCountAll() {
		int oldSize = mapper.countAll();
		for (int i = 0; i < 10; i++) {
			internalCreate();
		}

		int size = mapper.countAll();
		assertTrue(10 == (size - oldSize));
	}

	@Test
	@Transactional
	public void testSelectAll() {
		int oldSize = mapper.countAll();
		for (int i = 0; i < 10; i++) {
			internalCreate();

		}
		List<Dish> list = mapper.selectAll();

		assertNotNull(list);
		assertTrue((list.size() - oldSize) == 10);
	}
	
	@Test
	@Transactional
	public void tesSearchIdsReverse() {
		
		List<String> ids = new ArrayList<String>();
		List<Dish> list = mapper.selectIdsReverse(ids, false);

		assertNotNull(list);
	}


	@Test
	@Transactional
	public void testCountBy() {
		Dish filter = new Dish();
		int oldSize = mapper.countBy(filter);
		for (int i = 0; i < 10; i++) {
			internalCreate();
		}

		int size = mapper.countBy(filter);
		assertTrue(10 == (size - oldSize));
	}

	@Test
	@Transactional
	public void testSelectBy() {
		for (int i = 0; i < 10; i++) {
			internalCreate();

		}
		Dish filter = new Dish();
		List<Dish> list = mapper.selectBy(filter, 0, 10);

		assertNotNull(list);
		assertTrue(list.size() == 10);
	}

	@Test
	@Transactional
	public void testUpdate() {
		Dish dish = internalCreate();

		String random = "Update_";
		dish.setName(random + RandomStringUtils.randomAlphanumeric(10));
		dish.setPhoto(random + RandomStringUtils.randomAlphanumeric(10));
		dish.setOwnerId(random + RandomStringUtils.randomAlphanumeric(10));
		dish.setUpdateDate(new Date());

		int count = mapper.update(dish);
		assertTrue(count == 1);
		Dish newAcq = mapper.selectOne(dish.getID());
		assertTrue(dish.equals(newAcq));
	}

	@Test
	@Transactional
	public void testDelete() {
		Dish dish = internalCreate();
		int count = mapper.delete(dish.getID());
		assertTrue(count == 1);
		Dish dish2 = mapper.selectOne(dish.getID());
		assertTrue(dish2 == null);
	}

}
