package com.konux.usereventreceiver.api.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ControllerAdvice
public class ErrorController {

    private final Logger logger = LoggerFactory.getLogger(ErrorController.class);

    @ResponseBody
    @ExceptionHandler(AmqpException.class)
    public ResponseEntity<?> handleIllegalArgumentException(AmqpException ex) {
        logger.error("Exception handled!\r", ex);
        return new ResponseEntity<>(ex.getMessage(), INTERNAL_SERVER_ERROR);
    }
}
