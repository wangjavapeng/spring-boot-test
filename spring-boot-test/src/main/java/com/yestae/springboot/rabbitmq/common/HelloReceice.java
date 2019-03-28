package com.yestae.springboot.rabbitmq.common;

import java.io.UnsupportedEncodingException;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 普通监听器
 * @author wangpeng
 *
 */
@Component
public class HelloReceice {
	
//	@RabbitHandler
	@RabbitListener(queues = {"hello"})	//该注解不要放在类上，直接写到方法上，不然在mq的管理后台发送消息会报错
	public void process(String hello) throws UnsupportedEncodingException {
        System.out.println("Receiver  : " + new String(hello.getBytes(), "UTF-8"));
    }
}
