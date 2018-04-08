package com.springjpa;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.springjpa.interceptor.SessionValidationInterceptorAdapter;
import com.springjpa.service.CustomerDb2ReponseConverter;
import com.springjpa.service.RoleDb2ResponseConverter;

@EnableWebMvc
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    @Bean
    SessionValidationInterceptorAdapter sessionInterceptor() {
         return new SessionValidationInterceptorAdapter();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(sessionInterceptor());
    }

    @Bean
    public RoleDb2ResponseConverter getRoleConverter() {
    		return new RoleDb2ResponseConverter();
    }
    
    @Bean
    public CustomerDb2ReponseConverter getCustomerConverter() {
    		return new CustomerDb2ReponseConverter();
    }
    
}