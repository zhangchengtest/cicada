package com.cicada.web.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.caucho.HessianServiceExporter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.cicada.service.api.CRUDService;
import com.cicada.service.api.Service;
import com.cicada.service.api.dish.DishService;
import com.cicada.service.api.user.UserService;
import com.cicada.service.constant.ServiceConstant;
import com.cicada.web.controller.HomeController;

@Configuration
@EnableWebMvc
@ComponentScan(basePackageClasses = { HomeController.class })
public class WebConfig extends WebConfigTemplate {

	// private static final Logger logger = LoggerFactory.getLogger(WebConfig.class);

	@Autowired
	@Bean(name = ServiceConstant.Hessian.CRUD_SERVICE_URL)
	public HessianServiceExporter exportCRUDService( /* @Qualifier("crudService") */CRUDService service) {
		return getHessianServiceExporter(CRUDService.class, service);
	}
	
	@Autowired
	@Bean(name = ServiceConstant.Hessian.USER_SERVICE_URL)
	public HessianServiceExporter exportUserService( /* @Qualifier("userService") */UserService service) {
		return getHessianServiceExporter(UserService.class, service);
	}
	
	@Autowired
	@Bean(name = ServiceConstant.Hessian.DISH_SERVICE_URL)
	public HessianServiceExporter exportDishService( /* @Qualifier("userService") */DishService service) {
		return getHessianServiceExporter(DishService.class, service);
	}

	
	private HessianServiceExporter getHessianServiceExporter(Class<?> clz, Service service) {
		HessianServiceExporter exporter = new HessianServiceExporter();
		exporter.setServiceInterface(clz);
		exporter.setService(service);
		exporter.setAllowNonSerializable(true);
		exporter.setDebug(true);
		exporter.afterPropertiesSet();
		return exporter;
	}

}
