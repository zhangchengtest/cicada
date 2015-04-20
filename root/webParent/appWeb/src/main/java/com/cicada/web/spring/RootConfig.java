package com.cicada.web.spring;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.cicada.dao.Dao;
import com.cicada.dao.spring.PersistConfig;
import com.cicada.service.impl.BaseService;

@Configuration
@ComponentScan(basePackageClasses = { BaseService.class })
@MapperScan(basePackageClasses = { Dao.class })
@EnableTransactionManagement
public class RootConfig  extends PersistConfig{

	private static final Logger logger = LoggerFactory.getLogger(RootConfig.class);

}
