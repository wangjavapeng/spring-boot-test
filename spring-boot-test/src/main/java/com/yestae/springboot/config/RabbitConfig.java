package com.yestae.springboot.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Configuration;

/**
 * 创建队列
 * @author wangpeng
 *
 */
@Configuration
public class RabbitConfig {

	public Queue helloQueue(){
		return new Queue("hello");
	}
}
