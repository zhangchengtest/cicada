package com.cicada.dao.conf;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.cicada.dao.Dao;
import com.cicada.dao.spring.PersistConfig;

@Configuration
@MapperScan(basePackageClasses = { Dao.class })
@EnableTransactionManagement
// @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class DAOTestConfig extends PersistConfig {

}
