package com.konux.konuxchallenge.api.controller;

import com.konux.usereventreceiver.api.controller.UserEventController;
import com.konux.usereventreceiver.api.controller.dto.UserEventDto;
import com.konux.usereventreceiver.service.EventService;
import org.joda.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.verify;
import static org.mockito.MockitoAnnotations.openMocks;

class UserEventControllerTest {

    @Mock
    private EventService eventService;

    @Captor
    private ArgumentCaptor<UserEventDto> eventDtoCaptor;

    @BeforeEach
    void setup() {
        openMocks(this);
    }

    @Test
    void processEvent() {
        new UserEventController(eventService).processEvent(new UserEventDto(LocalDateTime.parse("2022-01-01"), 1L, "Test message"));

        verify(eventService).processEvent(eventDtoCaptor.capture());

        UserEventDto userEventDto = eventDtoCaptor.getValue();
        assertThat(userEventDto).isNotNull();
        assertThat(userEventDto.getTimestamp()).isEqualTo(LocalDateTime.parse("2022-01-01"));
        assertThat(userEventDto.getUserId()).isEqualTo(1L);
        assertThat(userEventDto.getMessage()).isEqualTo("Test message");
    }
}
