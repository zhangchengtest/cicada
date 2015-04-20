package com.cicada.dao;

import static org.junit.Assert.assertTrue;

import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.cicada.dao.conf.DAOTestConfig;
import com.cicada.utils.ReflectionUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = DAOTestConfig.class)
@TransactionConfiguration(defaultRollback = true)
// @Transactional
public class PersistUnitTest {

	@SuppressWarnings("unchecked")
	protected static <E extends Enum<E>> E getRandomEnum(Class<E> clazz) {
		E[] array = (E[]) ReflectionUtils.invokeStaticMethod(clazz, "values", null);
		if (array != null && array.length > 0) {
			return array[RandomUtils.nextInt(0, array.length)];
		} else {
			return null;
		}
	}

	@Test
	public void testEmpty() {
		assertTrue(true);
	}

}
