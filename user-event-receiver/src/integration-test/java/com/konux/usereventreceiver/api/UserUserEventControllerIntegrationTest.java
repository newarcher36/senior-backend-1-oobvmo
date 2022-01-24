package com.konux.usereventreceiver.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static java.net.HttpURLConnection.HTTP_INTERNAL_ERROR;
import static java.net.HttpURLConnection.HTTP_OK;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.doNothing;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class UserUserEventControllerIntegrationTest {

    @MockBean
    private RabbitTemplate rabbitTemplate;

    @Value("${local.server.port}")
    private int port;

    @BeforeEach
    void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @Test
    void sendUserEvent() throws IOException {

        doNothing().when(rabbitTemplate).send(any(Message.class));
        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(anEvent())
                .post("/konux/api/v1/events")
                .then()
                .statusCode(HTTP_OK);
    }

    @Test
    void failSendUserEventMessageWhenMessageBrokerIsDown() throws IOException {

        doThrow(new AmqpException("I/O issues with message broker")).when(rabbitTemplate).send(any(Message.class));

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(anEvent())
                .post("/konux/api/v1/events")
                .then()
                .statusCode(HTTP_INTERNAL_ERROR);
    }

    private JsonNode anEvent() throws IOException {
        return new ObjectMapper().readTree("{\"timestamp\":1642361588112,\"userId\":123,\"message\":\"Test message\"}");
    }
}

