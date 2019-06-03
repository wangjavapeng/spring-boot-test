package com.yestae.springboot.listenner;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.yestae.springboot.vo.EventVO;

@Component
public class EventBusListener {
	
	@Autowired
	private EventBus asyncEventBus;
	
	@PostConstruct
	public void init(){
		System.err.println("init");
		
		asyncEventBus.register(this);
	}
	
	@PreDestroy
	public void destory(){
		asyncEventBus.unregister(this);
	}
	
	@Subscribe
	@AllowConcurrentEvents
	public void listener(EventVO eventVO){
		System.err.println(Thread.currentThread().getName());
		System.err.println(eventVO.getUids());
	}
}
