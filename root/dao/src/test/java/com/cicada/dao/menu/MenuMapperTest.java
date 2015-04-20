package com.cicada.dao.menu;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.cicada.core.model.menu.Menu;
import com.cicada.core.model.menu.MenuStatus;
import com.cicada.core.model.menu.WebappEnum;
import com.cicada.dao.PersistUnitTest;
import com.cicada.utils.SecurityUtils;

public class MenuMapperTest extends PersistUnitTest {

	@Autowired
	private MenuDao mapper;

	public Menu newMenu() {
		Menu menu = new Menu();

		String random = "Junit_";
		menu.setID(RandomStringUtils.randomAlphanumeric(10));
		menu.setName(random + RandomStringUtils.randomAlphanumeric(10));
		menu.setKey(random + RandomStringUtils.randomAlphanumeric(10));
		menu.setCss("hello");
		menu.setLink("www.baidu.com");
		menu.setParentId(RandomStringUtils.randomAlphanumeric(10));
		menu.setStatus(MenuStatus.ACTIVE);
		menu.setSortOrder(2);
		menu.setWebapp(WebappEnum.CONSOLE);
		menu.setCreateDate(new Date());
		menu.setUpdateDate(new Date());

		return menu;
	}

	public Menu internalCreateEntity() {
		Menu menu = newMenu();
		mapper.insert(menu);
		return menu;
	}

	@Test
	@Transactional
	public void testInsert() {
		internalCreateEntity();
	}

	@Test
	@Transactional
	public void testSelectOne() {
		Menu menu = internalCreateEntity();
		Menu newAcc = mapper.selectOne(menu.getID());
		assertTrue(menu.equals(newAcc));
	}

	@Test
	@Transactional
	public void testCountAll() {
		int oldSize = mapper.countAll();
		for (int i = 0; i < 10; i++) {
			internalCreateEntity();
		}

		int size = mapper.countAll();
		assertTrue(10 == (size - oldSize));
	}

	@Test
	@Transactional
	public void testSelectAll() {
		int oldSize = mapper.countAll();
		for (int i = 0; i < 10; i++) {
			internalCreateEntity();

		}
		List<Menu> list = mapper.selectAll();

		assertNotNull(list);
		assertTrue((list.size() - oldSize) == 10);
	}

	@Test
	@Transactional
	public void testCountBy() {
		Menu filterEntity = new Menu();
		int oldSize = mapper.countBy(filterEntity);
		for (int i = 0; i < 10; i++) {
			internalCreateEntity();
		}

		int size = mapper.countBy(filterEntity);
		assertTrue(10 == (size - oldSize));
	}

	@Test
	@Transactional
	public void testSelectBy() {
		for (int i = 0; i < 10; i++) {
			internalCreateEntity();

		}
		Menu filterEntity = new Menu();
		List<Menu> list = mapper.selectBy(filterEntity, 0, 10);

		assertNotNull(list);
		assertTrue(list.size() == 10);
	}

	@Test
	@Transactional
	public void testUpdate() {
		Menu menu = internalCreateEntity();

		String random = "Update_";
		menu.setName(random + RandomStringUtils.randomAlphanumeric(10));
		menu.setKey(random + RandomStringUtils.randomAlphanumeric(10));
		menu.setCss("hello");
		menu.setLink("www.baidu.com");
		menu.setParentId(SecurityUtils.UUID());
		menu.setStatus(MenuStatus.ACTIVE);
		menu.setSortOrder(2);
		menu.setUpdateDate(new Date());

		int count = mapper.update(menu);
		assertTrue(count == 1);
		Menu newAcq = mapper.selectOne(menu.getID());
		assertTrue(menu.equals(newAcq));
	}

	@Test
	@Transactional
	public void testDelete() {
		Menu menu = internalCreateEntity();
		int count = mapper.delete(menu.getID());
		assertTrue(count == 1);
		Menu menu2 = mapper.selectOne(menu.getID());
		assertTrue(menu2 == null);
	}

}
