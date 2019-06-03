package com.yestae.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;

import com.google.common.eventbus.EventBus;
import com.yestae.springboot.enums.Events;
import com.yestae.springboot.enums.States;
import com.yestae.springboot.mapper.test1.User1Mapper;
import com.yestae.springboot.mapper.test2.User2Mapper;
import com.yestae.springboot.rabbitmq.common.HelloSender;
import com.yestae.springboot.rabbitmq.topic.TopicSender;
import com.yestae.springboot.service.RedisService;

/**
 *	CommandLineRunner 实现该接口的run方法，可以作相应的操作
 */
@EnableScheduling
@SpringBootApplication
public class App implements CommandLineRunner{
	@Autowired
	private HelloSender helloSender;
	
	@Autowired
	private TopicSender topicSender;
	
	//只有一个实例
	@Autowired
    private StateMachine<States, Events> stateMachine;
	
	//状态机工厂，可以生成多个状态机实例
	@Autowired
    StateMachineFactory<States, Events> factory;
	
	@Autowired
	private User1Mapper user1Mapper;
	@Autowired
	private User2Mapper user2Mapper;
	
	@Autowired
	private EventBus asyncEventBus;
	
	@Autowired
	private RedisService redisService;
	
    public static void main( String[] args ){
    	SpringApplication.run(App.class);
    	//测试bean被销毁
//    	context.close();
    }

	@Override
	public void run(String... arg0) throws Exception {
		//mq消息
//		helloSender.send();
		
		//topic交换机
//		topicSender.send();
//		topicSender.send1();
//		topicSender.send2();
		
		//多数据源配置
		//获取数据源
//		int n = 10;
//		BaseMapper mapper = n % 2 == 0 ? user1Mapper : user2Mapper;
//		mapper.insert(new User("haha", "123", UserSexEnum.MAN));
	}
    
}
