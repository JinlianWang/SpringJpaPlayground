package com.springjpa;

import java.util.HashMap;
import java.util.Map;


import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.core.env.Environment;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;



@Configuration
@EnableTransactionManagement 
@EnableJpaRepositories(basePackages = "com.springjpa.repo")
public class SpringJpaConfig {
    private static final String PROPERTY_NAME_DATABASE_DRIVER = "org.postgresql.Driver";
    private static final String PROPERTY_NAME_DATABASE_PASSWORD = "postgresql.password";
    private static final String PROPERTY_NAME_DATABASE_URL = "postgresql.url";
    private static final String PROPERTY_NAME_DATABASE_USERNAME = "postgresql.username";
    private static final String PROPERTY_NAME_DATABASE_INITSIZE = "postgresql.initialSize";
    private static final String PROPERTY_NAME_DATABASE_MINIDLE = "postgresql.minIdle";
    private static final String PROPERTY_NAME_DATABASE_MAXIDLE = "postgresql.maxIdle";
    private static final String PROPERTY_NAME_DATABASE_MAXTOTAL = "postgresql.maxTotal";
    
    @Autowired
    private Environment env;
    
    @Bean
    public DataSource datasource() {
        BasicDataSource dataSource = new BasicDataSource();
        
        dataSource.setDriverClassName(PROPERTY_NAME_DATABASE_DRIVER);
        dataSource.setUrl(env.getProperty(PROPERTY_NAME_DATABASE_URL));
        dataSource.setUsername(env.getProperty(PROPERTY_NAME_DATABASE_USERNAME));
        dataSource.setPassword(env.getProperty(PROPERTY_NAME_DATABASE_PASSWORD));
        dataSource.setInitialSize(env.getProperty(PROPERTY_NAME_DATABASE_INITSIZE, Integer.class));
        dataSource.setMinIdle(env.getProperty(PROPERTY_NAME_DATABASE_MINIDLE, Integer.class));
        dataSource.setMaxIdle(env.getProperty(PROPERTY_NAME_DATABASE_MAXIDLE, Integer.class));
        dataSource.setMaxTotal(env.getProperty(PROPERTY_NAME_DATABASE_MAXTOTAL, Integer.class));

        return dataSource;
    }

    @Bean
    public EntityManagerFactory entityManagerFactory(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("com.springjpa.model.db");
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        em.setJpaPropertyMap(hibernateProperties());
        em.afterPropertiesSet();
        return em.getObject();
    }

    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(entityManagerFactory);
        return jpaTransactionManager;
    }

    private Map<String, Object> hibernateProperties() {
        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.show_sql", "true");
        properties.put("hibernate.format_sql", "true");
        properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        return properties;
    }
}
