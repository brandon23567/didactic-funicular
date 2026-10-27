package com.example.ecom.config;

import jakarta.servlet.Filter;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * Replacement for web.xml.
 *
 * Spring's ServletContainerInitializer scans the classpath for
 * WebApplicationInitializer implementations on startup. This class
 * registers the root + servlet application contexts, maps the
 * DispatcherServlet to "/", and adds an encoding filter for UTF-8.
 */
public class WebAppInitializer
        extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[] { RootConfig.class };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[] { WebConfig.class };
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }

    @Override
    protected Filter[] getServletFilters() {
        CharacterEncodingFilter enc = new CharacterEncodingFilter();
        enc.setEncoding("UTF-8");
        enc.setForceEncoding(true);
        return new Filter[] { enc };
    }

    @Override
    protected void customizeRegistration(jakarta.servlet.ServletRegistration.Dynamic registration) {
        registration.setInitParameter("throwExceptionIfNoHandlerFound", "true");
    }
}