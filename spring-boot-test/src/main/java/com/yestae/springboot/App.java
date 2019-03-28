package com.yestae.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;

import com.yestae.springboot.enums.Events;
import com.yestae.springboot.enums.States;
import com.yestae.springboot.rabbitmq.common.HelloSender;
import com.yestae.springboot.rabbitmq.topic.TopicSender;

/**
 *	CommandLineRunner 实现该接口的run方法，可以作相应的操作
 */
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
	
    public static void main( String[] args ){
    	SpringApplication.run(App.class);
    	//测试bean被销毁
//    	context.close();
    }

	@Override
	public void run(String... arg0) throws Exception {
		//mq消息
//		helloSender.send();
		
		//状态机
//		StateMachine<States,Events> stateMachine2 = factory.getStateMachine("StateMachineFactory");
//    	stateMachine2.start();
//        System.out.println("--- coin ---");
//        stateMachine2.sendEvent(Events.COIN);
//        System.out.println("--- coin ---");
//        stateMachine.sendEvent(Events.COIN);
//        System.out.println("--- push ---");
//        stateMachine.sendEvent(Events.PUSH);
//        System.out.println("--- push ---");
//        stateMachine2.sendEvent(Events.PUSH);
//        stateMachine2.stop();
		
		topicSender.send();
		topicSender.send1();
		topicSender.send2();
	}
    
}
