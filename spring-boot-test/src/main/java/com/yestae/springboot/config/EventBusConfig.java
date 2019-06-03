package com.yestae.springboot.config;

import java.util.concurrent.Executors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;

/**
 * 异步事件总线
 * @author wangpeng
 *
 */
@Configuration
public class EventBusConfig {
	
	@Bean(name = "asyncEventBus")
	public EventBus eventBus(){
		return new AsyncEventBus(Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1));
	}
}
