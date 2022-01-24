package com.konux.konuxchallenge.service;

import com.konux.usereventreceiver.amqp.event.UserEvent;
import com.konux.usereventreceiver.amqp.publisher.UserEventPublisher;
import com.konux.usereventreceiver.api.controller.dto.UserEventDto;
import com.konux.usereventreceiver.service.EventService;
import org.joda.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.openMocks;

class EventServiceTest {

    @Mock
    private UserEventPublisher userEventPublisher;

    @Captor
    private ArgumentCaptor<UserEvent> userEventCaptor;

    @BeforeEach
    void setup() {
        openMocks(this);
    }

    @Test
    void processEvent() {
        LocalDateTime timestamp = LocalDateTime.parse("2022-01-01");
        new EventService(userEventPublisher).processEvent(new UserEventDto(timestamp, 1L, "Test message"));

        verify(userEventPublisher).publish(userEventCaptor.capture());

        UserEvent userEvent = userEventCaptor.getValue();
        assertThat(userEvent).isNotNull();
        assertThat(userEvent.getTimestamp()).isEqualTo(timestamp.toDateTime().getMillis());
        assertThat(userEvent.getUserId()).isEqualTo(1L);
        assertThat(userEvent.getMessage()).isEqualTo("Test message");
    }
}