package com.yestae.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wangpeng
 * @title: RabbitMqConfig
 * @projectName yestae-kill-rest
 * @description: 相关描述
 * @date 2019-09-18 10:49
 */
@Configuration
public class RabbitMqConfig {

    /**
     * 连接工厂
     *
     * @param addresses
     * @param username
     * @param password
     * @param publisherConfirms
     * @param publisherReturns
     * @return
     */
    @Bean
    public ConnectionFactory connectionFactory(@Value("${spring.rabbitmq.addresses}") String addresses,
                                               @Value("${spring.rabbitmq.username}") String username,
                                               @Value("${spring.rabbitmq.password}") String password,
                                               @Value("${spring.rabbitmq.publisherConfirms}") boolean publisherConfirms,
                                               @Value("${spring.rabbitmq.publisherReturns}") boolean publisherReturns
    ) {
        return connectionFactory(username, password, publisherConfirms, publisherReturns, addresses);
    }

    private CachingConnectionFactory connectionFactory(String username, String password, boolean publisherConfirms, boolean publisherReturns, String addresses) {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setPublisherConfirms(publisherConfirms);
        connectionFactory.setPublisherReturns(publisherReturns);
        connectionFactory.setAddresses(addresses);
        return connectionFactory;
    }

    /**
     * 创建发送消息模板
     *
     * @param connectionFactory
     * @param mandatory
     * @return
     */
    @Bean
    public RabbitTemplate rabbitTemplate(@Qualifier("connectionFactory") ConnectionFactory connectionFactory,
                                         @Value("${spring.rabbitmq.template.mandatory}") Boolean mandatory
    ) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMandatory(mandatory);

        return rabbitTemplate;
    }

    //*********************发送消息需要创建交换机，队列，然后交换机和队列绑定
    // **********************rabbitTemplate.convertSendAndReceive("orderDelayDirectExchange", "kill_cancel_order_queue", msg) 在发送时指定发送队列及记交换机
    /**
     * 创建延迟交换机
     *
     * @return
     */
    @Bean
    public DirectExchange directExchange() {
        DirectExchange directExchange = new DirectExchange("orderDelayDirectExchange");
        directExchange.setDelayed(true);
        return directExchange;
    }

    /**
     * 创建发送消息队列
     *
     * @return
     */
    @Bean
    Queue cancelOrderQueue() {
        return new Queue("kill_cancel_order_queue");
    }

    @Bean
    public Binding bindingCreateOrder() {
        return BindingBuilder.bind(cancelOrderQueue()).to(directExchange()).with("kill_cancel_order_queue");
    }


    //****************MQ监听配置，无需配置队列，只需要配置监听
    // ***************在@RabbitListener(queues = "kill_create_order_queue", containerFactory = "listenerFactory") 中配置队列即可
    /**
     * 消息监听
     *
     * @param configurer
     * @param connectionFactory
     * @return
     */
    @Bean("listenerFactory")
    public SimpleRabbitListenerContainerFactory listenerFactory(
            SimpleRabbitListenerContainerFactoryConfigurer configurer,
            @Qualifier("connectionFactory") ConnectionFactory connectionFactory
    ) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        return factory;
    }

}
