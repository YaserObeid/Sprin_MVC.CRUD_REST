package com.obeid.springrest.crud.config;

import java.beans.PropertyVetoException;
import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
@EnableWebMvc
@EnableTransactionManagement
@ComponentScan("com.obeid.springrest.crud")
@PropertySource("classpath.properties")
public class AppWebConfig implements WebMvcConfigurer {

	// load properties
	@Autowired
	private Environment env;
	// define Logger for monitoring
	private Logger logger = Logger.getLogger(getClass().getName());

	// with rest app we don't need viewResolver

	// define dataSource
	@Bean
	public DataSource dataSource() {
		// create connection pool
		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		// set the jdbc driver
		try {
			dataSource.setDriverClass(env.getProperty("jdbc.driver"));
		} catch (PropertyVetoException e) {
			throw new RuntimeException(e);
		}

		/**
		 * let's log url and user just to make sure we are reading the data
		 */
		logger.info("jdbc.url: " + env.getProperty("jdbc.url"));
		logger.info("jdbc.user: " + env.getProperty("jdbc.user"));

		// set database connection props

		dataSource.setJdbcUrl(env.getProperty("jdbc.url"));
		dataSource.setUser(env.getProperty("jdbc,user"));
		dataSource.setPassword(env.getProperty("jdbc.password"));

		// set database connection props

		dataSource.setInitialPoolSize(getIntProperty("connection.pool.initialPoolSize"));
		dataSource.setMinPoolSize(getIntProperty("connection.pool.minPoolSize"));
		dataSource.setMaxPoolSize(getIntProperty("connection.pool.maxPoolSize"));
		dataSource.setMaxIdleTime(getIntProperty("connection.pool.maxIdleTime"));

		return dataSource;
	}

	// define hibernate_properties

	private Properties getHibernateProperties() {

		Properties props = new Properties();

		// set hibernate properties
		props.setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));

		props.setProperty("hibernate.show_sql", env.getProperty("hibernate.show_sql"));

		return props;
	}

	// helper method
	private int getIntProperty(String property) {

		return Integer.getInteger(env.getProperty(property));
	}

	// define SessionFactory

	@Bean
	public LocalSessionFactoryBean sessionFactory() {

		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();

		sessionFactory.setDataSource(dataSource());
		sessionFactory.setPackagesToScan(env.getProperty("hibernate.packagesToScan"));
		sessionFactory.setHibernateProperties(getHibernateProperties());

		return sessionFactory;
	}

	// define transactionManager
	@Bean
	@Autowired
	public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {

		HibernateTransactionManager txManager = new HibernateTransactionManager();
		txManager.setSessionFactory(sessionFactory);

		return txManager;
	}

}