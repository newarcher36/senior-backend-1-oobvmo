package com.konux.usereventreceiver.api.controller.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import org.joda.time.LocalDateTime;

import static java.util.Objects.isNull;

@Getter
public class UserEventDto {
    @JsonSerialize(using = CustomJsonSerializer.class)
    @JsonDeserialize(using = CustomJsonDeserializer.class)
    private final LocalDateTime timestamp;
    private final Long userId;
    private final String message;

    public UserEventDto(LocalDateTime timestamp, Long userId, String message) {
        this.timestamp = timestamp;
        this.userId = userId;
        this.message = message;
        validate();
    }

    private void validate() {
        if (isNull(timestamp)) {
            throw new IllegalArgumentException("timestamp cannot be null");
        }
        if (isNull(userId)) {
            throw new IllegalArgumentException("userId cannot be null");
        }
        if (isNull(message)) {
            throw new IllegalArgumentException("message cannot be null");
        }
    }
}
