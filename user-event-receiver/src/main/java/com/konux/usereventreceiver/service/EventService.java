package com.konux.usereventreceiver.service;

import com.konux.usereventreceiver.amqp.event.UserEvent;
import com.konux.usereventreceiver.amqp.publisher.UserEventPublisher;
import com.konux.usereventreceiver.api.controller.dto.UserEventDto;
import org.springframework.stereotype.Component;

@Component
public class EventService {

    private final UserEventPublisher userEventPublisher;

    public EventService(UserEventPublisher userEventPublisher) {
        this.userEventPublisher = userEventPublisher;
    }

    public void processEvent(UserEventDto userEventDto) {

        UserEvent userEvent = UserEvent.newBuilder()
                .setTimestamp(userEventDto.getTimestamp().toDateTime().getMillis())
                .setUserId(userEventDto.getUserId())
                .setMessage(userEventDto.getMessage())
                .build();

        userEventPublisher.publish(userEvent);
    }
}
