package com.konux.usereventreceiver.amqp.publisher;

import com.konux.usereventreceiver.amqp.event.UserEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.converter.ProtobufMessageConverter;
import org.springframework.stereotype.Component;

@Component
public class UserEventPublisher {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserEventPublisher.class);
    private final RabbitTemplate rabbitTemplate;

    public UserEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publish(UserEvent userEvent) {
        byte[] bytes = userEvent.toByteArray();

        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentLength(bytes.length);
        messageProperties.setContentType(ProtobufMessageConverter.PROTOBUF.getType());

        Message userEventMessage = new Message(bytes, messageProperties);

        LOGGER.info("Sending user event message [{}] with exchange [{}] and routing-key [{}]", userEventMessage, rabbitTemplate.getExchange(), rabbitTemplate.getRoutingKey());
        rabbitTemplate.send(userEventMessage);
    }
}
