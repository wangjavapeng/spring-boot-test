package com.yestae.springboot.rabbitmq.common;

import java.util.Date;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 普通发送消息
 * @author wangpeng
 *
 */
@Component
public class HelloSender {
	@Autowired
	private AmqpTemplate rabbitTemplate;
	
	public void send() {
		String context = "hello " + new Date();
		System.out.println("Sender : " + context);
		this.rabbitTemplate.convertAndSend("hello", context);
	}
}
