package com.example.ecom.config;

import com.example.ecom.interceptor.AuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {
                "com.example.ecom.controller",
                "com.example.ecom.config" // ← added so @ControllerAdvice is picked up
})
public class WebConfig implements WebMvcConfigurer {

        @Autowired
        private AuthInterceptor authInterceptor;

        @Bean
        public ViewResolver viewResolver() {
                InternalResourceViewResolver resolver = new InternalResourceViewResolver();
                resolver.setViewClass(JstlView.class);
                resolver.setPrefix("/WEB-INF/jsp/");
                resolver.setSuffix(".jsp");
                return resolver;
        }

        @Override
        public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(authInterceptor)
                                .addPathPatterns("/**")
                                .excludePathPatterns(
                                                "/auth/**",
                                                "/",
                                                "/css/**",
                                                "/js/**",
                                                "/images/**",
                                                "/webjars/**");
        }

        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
                registry.addResourceHandler("/css/**")
                                .addResourceLocations("/resources/css/");
                registry.addResourceHandler("/js/**")
                                .addResourceLocations("/resources/js/");
                registry.addResourceHandler("/images/**")
                                .addResourceLocations("/resources/images/");
        }
}