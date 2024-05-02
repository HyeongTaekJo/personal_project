package com.oracle.personal_project.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.oracle.personal_project.Service.LoginInterCeptor;


@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
	
	@Override					//bean 안걸어줘도 됨
	public void addInterceptors(InterceptorRegistry registry) {
		// 로그인 관련 InterCeptor (비로그인 시 접근 막을 페이지)					
		registry.addInterceptor(new LoginInterCeptor()).addPathPatterns("/productList");					// 마이페이지
		registry.addInterceptor(new LoginInterCeptor()).addPathPatterns("/customerList");					// 마이페이지
		registry.addInterceptor(new LoginInterCeptor()).addPathPatterns("/shipList");					// 마이페이지
		registry.addInterceptor(new LoginInterCeptor()).addPathPatterns("/planOrderList");					// 마이페이지
		registry.addInterceptor(new LoginInterCeptor()).addPathPatterns("/workOrderList");					// 마이페이지
		registry.addInterceptor(new LoginInterCeptor()).addPathPatterns("/performanceList");					// 마이페이지
		registry.addInterceptor(new LoginInterCeptor()).addPathPatterns("/defectList");					// 마이페이지
	}
}
