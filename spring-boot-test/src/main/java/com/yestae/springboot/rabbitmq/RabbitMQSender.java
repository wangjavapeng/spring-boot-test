package com.yestae.modules.sender;

import com.yestae.common.utils.UuidUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author wangpeng
 * @title: RabbitMQSender
 * @projectName yestae-kill-rest
 * @description: mq消息发送类
 * @date 2019-09-18 11:34
 */
@Component
@Slf4j
public class RabbitMQSender implements  RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback  {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void init() {
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnCallback(this);
    }

    /**
     * 消息发送到RabbitMQ交换器后接收ack回调
     *
     * @param correlationData
     * @param ack
     * @param cause
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (ack) {
            log.info("取消订单延迟队列发送成功：" + correlationData);
            log.info("");
        } else {
            log.info("取消订单延迟队列发送失败：" + cause);
            log.info("");
        }
    }

    /**
     * 消息发送到RabbitMQ交换器，无相应队列与交换器绑定时的回调
     *
     * @param message
     * @param i
     * @param s
     * @param s1
     * @param s2
     */
    @Override
    public void returnedMessage(Message message, int i, String s, String s1, String s2) {
        log.info("消息发送到RabbitMQ交换器，无相应队列与交换器绑定时的回调");
        log.info("reson：" + s);
        log.info("exchange：" + s1);
        log.info("route：" + s2);
    }

    public void sendOrderMsg(String msg, Integer delay) {
        log.info("发送取消订单消息 : {}", msg);
        rabbitTemplate.convertSendAndReceive("orderDelayDirectExchange", "kill_cancel_order_route", msg, (message) ->{
            String msgId = UuidUtils.uuid32();
            message.getMessageProperties().setMessageId(msgId);
            message.getMessageProperties().setDelay(delay);
            return message;
        });
    }


}
