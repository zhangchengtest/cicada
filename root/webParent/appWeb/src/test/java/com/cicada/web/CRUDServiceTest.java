package com.cicada.web;


import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.apache.commons.lang3.NotImplementedException;
import org.junit.Test;
import org.springframework.remoting.caucho.HessianProxyFactoryBean;

import com.cicada.core.model.user.User;
import com.cicada.service.api.CRUDService;
import com.cicada.service.constant.ServiceConstant;
import com.cicada.service.exception.RuntimeServiceException;
import com.cicada.service.exception.ServiceException;

public class CRUDServiceTest {

	public static final String SVR_BASE_URL = "http://127.0.0.1:8080/appWeb";
	
	protected CRUDService createCRUDServiceBySpringProxy() throws RuntimeServiceException, ServiceException {
		HessianProxyFactoryBean bean = new HessianProxyFactoryBean();
		bean.setServiceUrl(SVR_BASE_URL + ServiceConstant.Hessian.CRUD_SERVICE_URL);
		bean.setServiceInterface(CRUDService.class);
		bean.setAllowNonSerializable(true);
		bean.setHessian2(true);
		bean.setOverloadEnabled(true);
		bean.setDebug(true);
		bean.afterPropertiesSet();

		CRUDService service = (CRUDService) bean.getObject();
		service.retrieve(User.class, "123");
		assertNotNull("get remote merchant service proxy failed", service);
		return service;
	}

	protected void testCRUDService(CRUDService service) {
		try {
			// MerchantUser user = service.//dosomething
		} catch (Exception e) {
			if (e instanceof NotImplementedException) {
				// ignore
			} else {
				fail("call CRUDService failed: " + e.getMessage());
			}
		}
	}

	@Test
	public void testWithSpringProxy() throws RuntimeServiceException, ServiceException {
		testCRUDService(createCRUDServiceBySpringProxy());
	}

}
