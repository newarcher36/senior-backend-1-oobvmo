package com.konux.usereventreceiver.amqp.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class CustomReturnCallBackHandler implements RabbitTemplate.ReturnsCallback {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomReturnCallBackHandler.class);

    @Override
    public void returnedMessage(ReturnedMessage returnedMessage) {
        String returnedMessageLog = "Message [{}] was returned! Reply code [{}], replyText [{}], exchange [{}], routing key [{}]";
        LOGGER.error(returnedMessageLog, returnedMessage.getMessage(), returnedMessage.getReplyCode(), returnedMessage.getReplyText(), returnedMessage.getExchange(), returnedMessage.getRoutingKey());
    }
}
