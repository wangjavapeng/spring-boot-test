package com.yestae.springboot.rabbitmq.topic;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class TopicReceice {

	@RabbitListener(queues = "topic.message")
	public void receice(String message){
		System.err.println(message);
	}
}
