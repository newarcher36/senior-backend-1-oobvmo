package com.konux.konuxchallenge.api.controller.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.konux.usereventreceiver.api.controller.dto.UserEventDto;
import org.joda.time.LocalDateTime;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CustomJsonSerializerTest {

    @Test
    void serializeEventWithDateAndTimeAsEpochMilliseconds() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonEvent = objectMapper.writeValueAsString(new UserEventDto(LocalDateTime.parse("2022-01-01"), 1L, "Test message"));
        assertThat(jsonEvent).isEqualTo("{\"timestamp\":\"1640991600000\",\"userId\":1,\"message\":\"Test message\"}");
    }
}