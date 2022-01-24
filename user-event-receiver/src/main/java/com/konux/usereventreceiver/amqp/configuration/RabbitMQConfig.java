package com.konux.usereventreceiver.amqp.configuration;

import com.konux.usereventreceiver.amqp.handler.CustomConfirmCallBackHandler;
import com.konux.usereventreceiver.amqp.handler.CustomReturnCallBackHandler;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.boot.autoconfigure.amqp.RabbitTemplateConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public RabbitTemplate createRabbitTemplate(ConnectionFactory connectionFactory, RabbitProperties rabbitProperties){
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setConfirmCallback(customConfirmCallbackHandler());
        rabbitTemplate.setReturnsCallback(customReturnCallbackHandler());

        RabbitTemplateConfigurer rabbitTemplateConfigurer = new RabbitTemplateConfigurer(rabbitProperties);
        rabbitTemplateConfigurer.configure(rabbitTemplate, connectionFactory);
        return rabbitTemplate;
    }

    private RabbitTemplate.ReturnsCallback customReturnCallbackHandler() {
        return new CustomReturnCallBackHandler();
    }

    private CustomConfirmCallBackHandler customConfirmCallbackHandler() {
        return new CustomConfirmCallBackHandler();
    }
}