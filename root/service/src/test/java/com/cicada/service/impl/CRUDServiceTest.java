package com.cicada.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.cicada.core.model.dish.Dish;
import com.cicada.core.model.page.Pagination;
import com.cicada.service.api.CRUDService;
import com.cicada.service.exception.RuntimeServiceException;
import com.cicada.service.exception.ServiceException;
import com.cicada.utils.SecurityUtils;

public class CRUDServiceTest extends ServiceUnitTest {

	@Autowired
	private CRUDService service;

	private Dish randomDish() {
		Dish bean = new Dish();
		bean.setID(SecurityUtils.UUID());
		bean.setName("Junit_" + RandomStringUtils.randomAlphanumeric(10));
		bean.setPhoto("Junit_" + RandomStringUtils.randomAlphanumeric(10));
		bean.setOwnerId("Junit_" + RandomStringUtils.randomAlphanumeric(10));
		bean.setCreateDate(new Date());
		bean.setUpdateDate(new Date());
		return bean;
	}


	private Dish internalCreate() throws RuntimeServiceException, ServiceException {
		return (Dish) service.create(randomDish());
	}

	@Test
	@Transactional
	public void testCreate() {
		try {
			Dish bean = internalCreate();
			assertNotNull(bean);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	@Transactional
	public void testRetrieve() {
		try {
			Dish bean = internalCreate();
			Dish result = (Dish) service.retrieve(Dish.class, bean.getID());
			assertNotNull(result);
			assertEquals(bean, result);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	@Transactional
	public void testRetrieveWithPagination() {
		try {
			int oldSize = service.retrieveAll(Dish.class).size();
			int randomSize = RandomUtils.nextInt(1, 10);
			for (int i = 0; i < randomSize; ++i) {
				internalCreate();
			}
			Pagination<Dish> list = service.retrieve(Dish.class, 1, 10 + oldSize);
			assertNotNull(list.getPageData());
			assertEquals(randomSize + oldSize, list.getPageData().size());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	@Transactional
	public void testRetrieveAll() {
		try {
			List<Dish> listInital = service.retrieveAll(Dish.class);
			int randomSize = RandomUtils.nextInt(1, 10);
			for (int i = 0; i < randomSize; ++i) {
				internalCreate();
			}
			List<Dish> list = service.retrieveAll(Dish.class);
			assertNotNull(list);
			assertEquals(randomSize+listInital.size(), list.size());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	@Transactional
	public void testUpdate() {
		final String prefix = "Updated_";
		try {
			Dish bean = internalCreate();
			Assert.assertNotNull(bean);
			Date oldDate = bean.getUpdateDate();

			bean.setName(prefix + RandomStringUtils.randomAlphanumeric(10));
			bean.setPhoto(prefix + RandomStringUtils.randomAlphanumeric(10));
			bean.setOwnerId(prefix + RandomStringUtils.randomAlphanumeric(10));
			bean.setUpdateDate(new Date());
			Dish result = (Dish) service.update(bean);

			assertNotNull(result);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	@Transactional
	public void testDelete() {
		try {
			Dish bean = internalCreate();
			assertNotNull(bean);
			assertTrue(service.delete(Dish.class, bean.getID()));
			//
			assertFalse(service.delete(Dish.class, "non_exist"));
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
}
