package com.yestae.springboot.config;

import java.util.EnumSet;

import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import com.yestae.springboot.enums.Events;
import com.yestae.springboot.enums.States;

/**
 * 
 * 状态机工厂配置
 *
 */
@Configuration
@EnableStateMachineFactory
public class StateMachineFactoryConfig  extends EnumStateMachineConfigurerAdapter<States, Events> {
	
	//配置状态机初始状态
    @Override
   public void configure(StateMachineStateConfigurer<States, Events> states)
           throws Exception {
    		states
               .withStates()
               // 定义初始状态
               .initial(States.LOCKED)
               // 定义所有状态集合
               .states(EnumSet.allOf(States.class));
   }
//	public StateMachine<States, Events> buildMachine1() throws Exception {
//	    Builder<States, Events> builder = StateMachineBuilder.builder();
//	    builder.configureStates()
//	        .withStates()
//	            .initial(States.LOCKED)
//	            .end(States.UNLOCKED)
//	            .states(EnumSet.allOf(States.class));
//	    return builder.build();
//	}
    
   //配置状态机转移
   @Override
   public void configure(StateMachineTransitionConfigurer<States, Events> transitions)
           throws Exception {
       transitions
               .withExternal()
               .source(States.LOCKED).target(States.UNLOCKED)
               .event(Events.COIN)
//               .action(turnstileUnlock())
               .and()
               .withExternal()
               .source(States.UNLOCKED).target(States.LOCKED)
               .event(Events.PUSH);
//               .action(customerPassAndLock());
   }
   
   @Override
   public void configure(StateMachineConfigurationConfigurer<States, Events> config)
           throws Exception {
       config.withConfiguration()
               .machineId("StateMachineFactory")
       ;
   }

   /**
    * 事件转移触发动作
    * @return
    */
   public Action<States, Events> turnstileUnlock() {
       return context -> System.out.println("解锁旋转门，以便游客能够通过" );
   }

   /**
    * 事件转移触发动作
    * @return
    */
   public Action<States, Events> customerPassAndLock() {
       return context -> System.out.println("当游客通过，锁定旋转门" );
   }
}
