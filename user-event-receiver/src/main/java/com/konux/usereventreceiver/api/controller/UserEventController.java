package com.konux.usereventreceiver.api.controller;

import com.konux.usereventreceiver.api.controller.dto.UserEventDto;
import com.konux.usereventreceiver.service.EventService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/events")
public class UserEventController {

    private final EventService eventService;

    public UserEventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void processEvent(@RequestBody UserEventDto userEventDto) {
        eventService.processEvent(userEventDto);
    }
}