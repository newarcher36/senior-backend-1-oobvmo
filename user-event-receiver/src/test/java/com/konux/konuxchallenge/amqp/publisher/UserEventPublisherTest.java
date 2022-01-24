package com.konux.konuxchallenge.amqp.publisher;

import com.konux.usereventreceiver.amqp.event.UserEvent;
import com.konux.usereventreceiver.amqp.publisher.UserEventPublisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.converter.ProtobufMessageConverter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.verify;
import static org.mockito.Mockito.doNothing;
import static org.mockito.MockitoAnnotations.openMocks;

class UserEventPublisherTest {

    @Mock
    private RabbitTemplate rabbitTemplate;

    @Captor
    private ArgumentCaptor<Message> messageCaptor;

    @BeforeEach
    void setup() {
        openMocks(this);
    }

    @Test
    void publishUserEvent() {
        doNothing().when(rabbitTemplate).send(any(Message.class));

        UserEvent userEvent = UserEvent.newBuilder()
                .setTimestamp(123)
                .setUserId(1L)
                .setMessage("Test")
                .build();

        new UserEventPublisher(rabbitTemplate).publish(userEvent);

        verify(rabbitTemplate).send(messageCaptor.capture());

        Message actualMessage = messageCaptor.getValue();
        Message expectedMessage = expectedMessage(userEvent);

        assertThat(actualMessage).usingRecursiveComparison().isEqualTo(expectedMessage);
    }

    private Message expectedMessage(UserEvent userEvent) {
        byte[] bytes = userEvent.toByteArray();
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentLength(bytes.length);
        messageProperties.setContentType(ProtobufMessageConverter.PROTOBUF.getType());
        return new Message(bytes, messageProperties);
    }
}