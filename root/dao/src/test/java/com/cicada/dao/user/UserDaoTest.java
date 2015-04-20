package com.cicada.dao.user;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.cicada.core.model.user.User;
import com.cicada.core.model.user.UserStatusEnum;
import com.cicada.dao.PersistUnitTest;
import com.cicada.utils.SecurityUtils;

public class UserDaoTest extends PersistUnitTest {

	@Autowired
	private UserDao mapper;

	public User createUser() {
		User entityUsr = new User();
		// no user id set
		entityUsr.setID(SecurityUtils.UUID());
		entityUsr.setName("JunitName_" + RandomStringUtils.randomAlphanumeric(6));
		final String salt = RandomStringUtils.randomAlphanumeric(6);
		entityUsr.setEncryptedPasswd(SecurityUtils.MD5("111111"));
		entityUsr.setPasswdSalt(salt);
		entityUsr.setEmail(RandomStringUtils.randomAlphanumeric(5) + "@aurfy.com");
		entityUsr.setLicenseAgreed(false);
		entityUsr.setStatus(UserStatusEnum.ACTIVE);
		entityUsr.setRegIP("127.0.0.1");
		entityUsr.setComments("Random user created by JUnit dao test");
		entityUsr.setCreateDate(new Date());
		entityUsr.setUpdateDate(entityUsr.getCreateDate());
		return entityUsr;
	}

	private User newUser() {
		User entityUsr = createUser();
		// no user id set
		String oldId = entityUsr.getID();
		int count = mapper.insert(entityUsr);
		String newId = entityUsr.getID();
		assertEquals(oldId, newId);
		assertTrue(count == 1);
		return entityUsr;
	}

	@Test
	@Transactional
	public void testInsert() {
		try {
			newUser();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	@Transactional
	public void testSelectOne() {
		try {
			User entityUsr = newUser();
			User user = mapper.selectOne(entityUsr.getID());
			assertNotNull(user);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	@Transactional
	public void testSelectBy() {
		try {
			User entityUsr = createUser();
			List<User> user = mapper.selectBy(entityUsr, 0, 10);
			assertNotNull(user);
			mapper.insert(entityUsr);
			user = mapper.selectBy(entityUsr, 0, 10);
			assertNotNull(user);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	@Transactional
	public void testSelectAll() {
		int oldSize = mapper.selectAll().size();
		int randomSize = RandomUtils.nextInt(1, 10);
		for (int i = 0; i < randomSize; i++) {
			mapper.insert(createUser());
		}
		int newSize = mapper.selectAll().size();
		assertEquals(oldSize + randomSize, newSize);
	}

	@Test
	@Transactional
	public void testUpdate() {
		try {
			// create a user
			User oldUser = newUser();

			// update information
			User updatedUser = createUser();
			updatedUser.setID(oldUser.getID());
			mapper.update(updatedUser);

			// compare
			User selectedUser = mapper.selectOne(oldUser.getID());

			assertEquals(oldUser.getID(), selectedUser.getID());
			assertNotEquals(oldUser.getName(), selectedUser.getName());
			assertNotEquals(oldUser, selectedUser);
			assertEquals(updatedUser, selectedUser);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	@Transactional
	public void testDelete() {
		try {
			// create a user
			User oldUser = newUser();
			String id = oldUser.getID();

			mapper.delete(id);

			User selectedUser = mapper.selectOne(id);
			assertNull(selectedUser);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

}
