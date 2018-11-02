package com.yestae.springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.yestae.springboot.entity.User;

@Configuration
public class MyConfig {
	
	@Bean(initMethod="init", destroyMethod="destory")
	public User user(){
		return new User();
	}
}
