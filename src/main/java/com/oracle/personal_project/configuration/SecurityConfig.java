package com.oracle.personal_project.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class SecurityConfig {
	
	// 해쉬 암호화 방식을 사용하겠다.
	@Bean
	public BCryptPasswordEncoder encodePwd() {
		return new BCryptPasswordEncoder();
	}
	
	// Security 모듈을 비활성화 하겠다.
	@Bean
	protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeRequests()
			.anyRequest()
			.permitAll();
		
		return http.build();
	}
	


}
