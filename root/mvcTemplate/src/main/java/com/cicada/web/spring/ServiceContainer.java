package com.cicada.web.spring;

import java.net.MalformedURLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;

import com.caucho.hessian.client.HessianProxyFactory;
import com.cicada.service.api.CRUDService;
import com.cicada.service.api.dish.DishService;
import com.cicada.service.api.user.UserService;
import com.cicada.service.constant.ServiceConstant;
import com.cicada.service.exception.ServiceException;
import com.cicada.utils.StringUtils;

public class ServiceContainer extends WebConfigTemplate{

	private static final Logger logger = LoggerFactory.getLogger(ServiceContainer.class);
	private static HessianProxyFactory factory = createProxyFactory();
	public static final String SVR_BASE_URL = "http://127.0.0.1:8080/appWeb";
	
	private static HessianProxyFactory createProxyFactory() {
		HessianProxyFactory factory = new HessianProxyFactory();
		factory.setHessian2Request(true);
		factory.setHessian2Reply(true);
		factory.setOverloadEnabled(true);
		factory.setDebug(true);
		factory.setReadTimeout(30000);
		factory.getSerializerFactory().setAllowNonSerializable(true);
		return factory;
	}
	
	@Bean(name = ServiceConstant.AOP_NAME.CRUD_SERVICE)
	public CRUDService createCRUDService() throws ServiceException {
		return createServiceClient(CRUDService.class, ServiceConstant.Hessian.CRUD_SERVICE_URL);
	}
	
	@Bean(name = ServiceConstant.AOP_NAME.DISH_SERVICE)
	public DishService createDishService() throws ServiceException {
		return createServiceClient(DishService.class, ServiceConstant.Hessian.DISH_SERVICE_URL);
	}
	
	@Bean
	public UserService createUserService() throws ServiceException {
		return createServiceClient(UserService.class, ServiceConstant.Hessian.USER_SERVICE_URL);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T createServiceClient(Class<T> clazz, String service_url)
			throws ServiceException {
		try {
			return (T) factory.create(clazz, SVR_BASE_URL + service_url);
		} catch (MalformedURLException e) {
			final String msg = StringUtils.formatMessage("create client for ''{0}'' failed", clazz.getSimpleName());
			logger.error(msg, e);
			throw new ServiceException(msg, e);
		}
	}
	
}
