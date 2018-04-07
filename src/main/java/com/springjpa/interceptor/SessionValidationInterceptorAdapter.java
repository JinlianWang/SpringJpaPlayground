package com.springjpa.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.springjpa.annotation.ClassSessionValidationAnnotation;
import com.springjpa.annotation.MethodSessionValidationAnnotation;

public class SessionValidationInterceptorAdapter extends HandlerInterceptorAdapter {
	private static final Logger logger = Logger.getLogger(SessionValidationInterceptorAdapter.class);


	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		HandlerMethod method = (HandlerMethod) handler;
		MethodSessionValidationAnnotation methodAnnotation = method.getMethodAnnotation(MethodSessionValidationAnnotation.class);

		ClassSessionValidationAnnotation classAnnotation = method.getMethod().getDeclaringClass()
				.getAnnotation(ClassSessionValidationAnnotation.class);

		if (methodAnnotation != null || classAnnotation != null) {

			String token = request.getHeader("Authorization");

			if (this.isValid(token)) {
				return true;
			} else {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				return false;
			}
		} else {
			return true;
		}
	}

	  @Override
	  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
	      ModelAndView modelAndView) throws Exception {

	  }

	  boolean isValid(String token) {
		  // decrypt the token and check its validity; here we only check whether it is empty or not for demo. 
		  return token != null && !token.isEmpty();
	  }
}
