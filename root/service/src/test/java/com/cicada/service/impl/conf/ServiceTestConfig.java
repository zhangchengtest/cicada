package com.cicada.service.impl.conf;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.cicada.dao.spring.PersistConfig;
import com.cicada.service.impl.BaseService;

/**
 * 
 * the annotation <code>@Configuration</code> does not have <code>@Inherited</code>,<br/>
 * so you must add it in this class again.
 * 
 * @author rocket
 *
 */
@Configuration
@ComponentScan(basePackageClasses = { BaseService.class })
@EnableTransactionManagement
public class ServiceTestConfig extends PersistConfig {

}
