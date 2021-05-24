package com.ssafy.happyhouse.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.ssafy.happyhouse.interceptor.ConfirmInterceptor;

public class WebConfiguration extends WebMvcConfigurerAdapter{

	@Autowired
	private ConfirmInterceptor confirmInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(confirmInterceptor).addPathPatterns("/article/**");
	}

	
}
