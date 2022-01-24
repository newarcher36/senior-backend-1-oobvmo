package com.konux.konuxchallenge.api.controller.dto;

import com.konux.usereventreceiver.api.controller.dto.UserEventDto;
import org.joda.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.MockitoAnnotations.openMocks;

public class UserEventControllerDtoTest {

    @BeforeEach
    void setup() {
        openMocks(this);
    }

    @Test
    void failWhenTimestampFieldNotProvided() {
        Throwable throwable = catchThrowable(() -> new UserEventDto(null, 1L, "Test message"));

        assertThat(throwable)
                .isNotNull()
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("timestamp cannot be null");
    }

    @Test
    void failWhenUserIdFieldNotProvided() {
        Throwable throwable = catchThrowable(() -> new UserEventDto(LocalDateTime.parse("2022-01-01"), null, "Test message"));

        assertThat(throwable)
                .isNotNull()
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("userId cannot be null");
    }

    @Test
    void failWhenMessageFieldNotProvided() {
        Throwable throwable = catchThrowable(() -> new UserEventDto(LocalDateTime.parse("2022-01-01"), 1L, null));

        assertThat(throwable)
                .isNotNull()
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("message cannot be null");
    }
}
