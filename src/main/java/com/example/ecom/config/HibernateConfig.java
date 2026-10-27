package com.example.ecom.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Wires Hibernate 6's SessionFactory into Spring's container.
 *
 * Despite the package name "org.springframework.orm.hibernate5",
 * this works correctly with Hibernate 6 (Spring's naming just hasn't
 * caught up — the API is stable across both versions).
 *
 * Entities live under com.example.app.model (added in Phase 3).
 */
@Configuration
public class HibernateConfig {

    @Value("${hibernate.dialect}")
    private String dialect;
    @Value("${hibernate.hbm2ddl.auto}")
    private String hbm2ddl;
    @Value("${hibernate.show_sql}")
    private String showSql;
    @Value("${hibernate.format_sql}")
    private String formatSql;
    @Value("${hibernate.connection.pool_size}")
    private String poolSize;

    @Bean
    public LocalSessionFactoryBean sessionFactory(DataSource dataSource) {
        LocalSessionFactoryBean sf = new LocalSessionFactoryBean();
        sf.setDataSource(dataSource);
        sf.setPackagesToScan("com.example.ecom.model");
        sf.setHibernateProperties(hibernateProperties());
        return sf;
    }

    @Bean
    public PlatformTransactionManager transactionManager(
            LocalSessionFactoryBean sessionFactory) {
        HibernateTransactionManager tx = new HibernateTransactionManager();
        tx.setSessionFactory(sessionFactory.getObject());
        return tx;
    }

    private Properties hibernateProperties() {
        Properties props = new Properties();
        props.put("hibernate.dialect", dialect);
        props.put("hibernate.hbm2ddl.auto", hbm2ddl);
        props.put("hibernate.show_sql", showSql);
        props.put("hibernate.format_sql", formatSql);
        return props;
    }
}