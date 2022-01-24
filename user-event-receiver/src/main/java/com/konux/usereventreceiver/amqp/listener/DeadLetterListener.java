package com.konux.usereventreceiver.amqp.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;

public class DeadLetterListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeadLetterListener.class);

    //@RabbitListener(queues = "")
    public void processFailedMessages(Message message) {
        LOGGER.info("Received failed message: {}", message.toString());
    }
}
