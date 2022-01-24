package com.konux.usereventreceiver.amqp.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class CustomConfirmCallBackHandler implements RabbitTemplate.ConfirmCallback {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomConfirmCallBackHandler.class);

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        LOGGER.info("Message delivery confirmed with correlationData [{}], ack [{}], cause [{}]", correlationData, ack, cause);
    }
}
