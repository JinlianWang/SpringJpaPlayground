package com.springjpa.aop;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

@Aspect
@Component
public class LoggingAspect {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Pointcut("target(com.springjpa.controller.WebController)")
    public void controllerMethods() {};
    
	@Pointcut("execution(public com.springjpa.model.core.Customer com.springjpa.service.CustomerService.locateCustomer(String))")
    public void customerServiceMethod() {};
    
    @Around("@annotation(LogExecutionTime)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
    	//print out all parameter names
    	MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
    	for(String param:methodSignature.getParameterNames()) {
    		logger.info("Parameter name: " + param);
    	}
    	
    	//print out all parameter values
    	for(Object arg: joinPoint.getArgs()) {
    		logger.info("Param Value: " + arg);
    	}
    	
    	//print out value of PathVariable annotation if it exists
    	Method method = methodSignature.getMethod();
    	for (Annotation ann : method.getParameterAnnotations()[0]) {
            if(PathVariable.class.isInstance(ann)) {
            	PathVariable pathVariable = (PathVariable) ann;
                logger.info("Found PathVariable annotation with value: " + pathVariable.value());
            }
        }
    	
    	//Print out execution time
        final long start = System.currentTimeMillis();

        final Object proceed = joinPoint.proceed();

        final long executionTime = System.currentTimeMillis() - start;

        System.out.println(joinPoint.getSignature() + " executed in " + executionTime + "ms! Spring AOP in action. ");

        return proceed;
    }
    
    @Before("controllerMethods()")
    public void logMethodCall(JoinPoint jp) {
        String methodName = jp.getSignature().getName();
        logger.info("Before calling method: " + methodName);
    }
    
    @AfterReturning(value="customerServiceMethod()", returning="returnValue")
    public void afterReturn(JoinPoint jp, Object returnValue) throws Throwable {
        logger.info("Calling method with return value: {}",  returnValue);
    }
}
