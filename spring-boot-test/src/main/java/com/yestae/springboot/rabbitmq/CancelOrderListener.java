package com.yestae.modules.listener;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author wangpeng
 * @title: CancelOrderListener
 * @projectName yestae-kill-rest
 * @description: 订单超时时间点取消校验
 * @date 2019-09-20 16:15
 */
@Component
@Slf4j
public class CancelOrderListener {

    @RabbitListener(queues = "kill_cancel_order_queue", containerFactory = "listenerFactory")
    public void createOrder(Channel channel, Message message) throws Exception {
        String result = new String(message.getBody(), "UTF-8");
        String messageId = message.getMessageProperties().getMessageId();
        log.info("messageId#[{}], queue#[{}], result#[{}]", messageId, "kill_cancel_order_queue", result);
    }
}
