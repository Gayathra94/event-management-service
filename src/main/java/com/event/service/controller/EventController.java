package com.event.service.controller;

import com.event.service.dto.CreateEventRequest;
import com.event.service.dto.ErrorResponse;
import com.event.service.exception.ApplicationException;
import com.event.service.model.Event;
import com.event.service.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
@RequestMapping("/events")
public class EventController {

    private MessageSource messageSource;

    @Autowired
    private EventService eventService;

    @PostMapping
    public ResponseEntity<?> createEvent(Locale locale,  @RequestBody CreateEventRequest requestBody) {
        try {
            Event event = eventService.createEvent(requestBody);
            return new ResponseEntity<>(event, HttpStatus.OK);
        }catch (ApplicationException e){
            ErrorResponse errorResponse = new ErrorResponse(e.getCode(), messageSource.getMessage(e.getMessage(),null, locale));
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }
}
