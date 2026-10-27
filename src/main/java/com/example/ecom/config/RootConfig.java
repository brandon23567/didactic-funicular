package com.example.ecom.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Root application context.
 *
 * Owns beans that are NOT web-specific:
 * - services (@Service) under com.example.ecom.service
 * - repositories (@Repository) under com.example.ecom.repository
 * - Hibernate + DB wiring (imported)
 *
 * The DispatcherServlet context (WebConfig) is a child of this root
 * context, so controllers can inject services defined here.
 */
@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = {
        "com.example.ecom.service",
        "com.example.ecom.repository",
        "com.example.ecom.interceptor"
})
@Import({ DatabaseConfig.class, HibernateConfig.class })
public class RootConfig {
}