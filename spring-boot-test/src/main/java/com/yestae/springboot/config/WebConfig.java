package com.yestae.springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.yestae.springboot.interceptor.LimitInterceptor;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
	@Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(limitInterceptor()) 
                .addPathPatterns("/**"); 
    }
	
	//注册拦截器到spring中，否则无法在拦截器中注入其他bean
	@Bean
	public LimitInterceptor limitInterceptor(){
		return new LimitInterceptor();
	}

}
