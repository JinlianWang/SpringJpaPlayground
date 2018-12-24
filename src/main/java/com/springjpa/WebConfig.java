package com.springjpa;

import java.util.concurrent.Executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.springjpa.converter.CustomerDbEntity2CustomerConverter;
import com.springjpa.converter.Order2OrderDbEntityConverter;
import com.springjpa.converter.OrderDbEntity2OrderConverter;
import com.springjpa.converter.RoleDbEntity2RoleConverter;
import com.springjpa.interceptor.SessionValidationInterceptorAdapter;

@EnableWebMvc
@Configuration
@EnableAsync
public class WebConfig extends WebMvcConfigurerAdapter {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
    @Bean
    SessionValidationInterceptorAdapter sessionInterceptor() {
         return new SessionValidationInterceptorAdapter();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(sessionInterceptor());
    }

    @Bean
    public RoleDbEntity2RoleConverter getRoleConverter() {
    		return new RoleDbEntity2RoleConverter();
    }
    
    @Bean
    public CustomerDbEntity2CustomerConverter getCustomerConverter() {
    		return new CustomerDbEntity2CustomerConverter();
    }
    
    @Bean
    public OrderDbEntity2OrderConverter getOrderConverter() {
    		return new OrderDbEntity2OrderConverter();
    }
    
    @Bean
    public Order2OrderDbEntityConverter getOrderDbConverter() {
    	return new Order2OrderDbEntityConverter();
    }
    
    @Bean
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(3);
        executor.setMaxPoolSize(3);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("GithubLookup-");
        executor.initialize();
        logger.debug("TaskExecutor is initialized with core pool size:{} and max pool size:{}", executor.getCorePoolSize(), executor.getMaxPoolSize());
        return executor;
    }
}