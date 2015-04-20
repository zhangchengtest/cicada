package com.cicada.dao.spring;

import static com.cicada.dao.conf.DefaultConfig.getJDBCProps;
import static com.cicada.dao.conf.DefaultConfig.getMybatisConfFile;
import static com.cicada.dao.conf.DefaultConfig.getMybatisProps;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.sql.DataSource;

import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.type.EnumOrdinalTypeHandler;
import org.apache.ibatis.type.TypeHandler;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.cicada.core.inernal.UseTypeHandler;
import com.cicada.dao.Dao;
import com.cicada.utils.ScannerUtils;

/**
 * Spring AOP config file for mapper injection.
 * 
 * @author hermano
 *
 */
@Configuration
@MapperScan(basePackageClasses = { Dao.class })
@EnableTransactionManagement
// @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class PersistConfig {

	private static final String ENUM_SCAN_PKG = "com.cicada";
	private static final String ENTITY_SCAN_PKG = "com.cicada.core";
	private static final Logger logger = LoggerFactory.getLogger(PersistConfig.class);

	private static final Map<Class<? extends Enum<?>>, Class<? extends TypeHandler<?>>> DEFAULT_TYPE_HANDLER_MAP = getTypeHandlerMap();
	
	@Bean(name="springContext")
	public SpringContext getSpringContext() throws Exception {
		return new SpringContext();
	}

	@Bean(name = "dataSource", destroyMethod = "forceCloseAll")
	public DataSource dataSource() throws SQLException {

		Properties jdbcProps = getJDBCProps();

		PooledDataSource ds = new PooledDataSource(jdbcProps.getProperty("jdbc.driverClassName"),
				jdbcProps.getProperty("jdbc.url"), jdbcProps.getProperty("jdbc.username"),
				jdbcProps.getProperty("jdbc.password"));
		ds.setLoginTimeout(30);
		return ds;
	}

	@Bean
	public DataSourceTransactionManager getTransactionManager() throws Exception {
		DataSourceTransactionManager manager = new DataSourceTransactionManager(dataSource());
		return manager;
	}

	@Bean
	public SqlSessionFactoryBean sqlSessionFactory() throws Exception {
		SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
		sqlSessionFactory.setDataSource(dataSource());
		sqlSessionFactory.setTypeAliasesPackage(ENTITY_SCAN_PKG);

		sqlSessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(getMybatisProps()
				.getProperty("mapperLcationPattern")));

		sqlSessionFactory.setTypeHandlerMap(DEFAULT_TYPE_HANDLER_MAP);

		// // TODO: use programmatic configuration over xml files
		sqlSessionFactory.setConfigLocation(new ClassPathResource(getMybatisConfFile()));

		return sqlSessionFactory;
	}

	@SuppressWarnings("unchecked")
	private static Map<Class<? extends Enum<?>>, Class<? extends TypeHandler<?>>> getTypeHandlerMap() {

		Map<Class<? extends Enum<?>>, Class<? extends TypeHandler<?>>> typeHandlerMap = new HashMap<Class<? extends Enum<?>>, Class<? extends TypeHandler<?>>>();

		Set<Class<?>> enumTypes = ScannerUtils.scan4Annotation(ENUM_SCAN_PKG, UseTypeHandler.class, false);

		Class<? extends Enum<?>> clazz;
		Class<? extends TypeHandler<?>> th;
		UseTypeHandler anno;
		for (Class<?> cls : enumTypes) {
			clazz = (Class<? extends Enum<?>>) cls;
			anno = clazz.getAnnotation(UseTypeHandler.class);
			if (EnumOrdinalTypeHandler.class.getName().equals(anno.value())) {
				th = (Class<? extends TypeHandler<?>>) (Class<?>) EnumOrdinalTypeHandler.class;
			} else {
				try {
					th = (Class<? extends TypeHandler<?>>) (Class<?>) Class.forName(anno.value());
				} catch (ClassNotFoundException e) {
					th = null;
					logger.warn("can not add custom type handler: {}", anno.value(), e);
				}
			}
			if (th != null) {
				typeHandlerMap.put(clazz, th);
			}
		}

		return typeHandlerMap;
	}
}
