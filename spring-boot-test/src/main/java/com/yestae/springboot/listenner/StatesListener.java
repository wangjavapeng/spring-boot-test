package com.yestae.springboot.listenner;

import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.annotation.WithStateMachine;

/**
 * 状态变更监听器，用于解耦服务，
 * 例如订单：
 * 	用户创建订单发送，处理完业务流程，发送更改状态消息，监听到之后做相应其他的异步处理
 * @author wangpeng
 *
 */
@WithStateMachine(id = "StateMachineFactory")
public class StatesListener {
	
	@OnTransition(source = "UNLOCKED", target = "LOCKED")
    public void toState1() {
		System.err.println("监听器 LOCKED");
    }

    @OnTransition(source = "LOCKED",target = "UNLOCKED")
    public void toState2() {
    	System.err.println("监听器 UNLOCKED");
    }
}
