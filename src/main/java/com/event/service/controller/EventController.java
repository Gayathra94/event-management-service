package com.event.service.controller;

import com.event.service.dto.CreateEventRequest;
import com.event.service.dto.ErrorResponse;
import com.event.service.exception.ApplicationException;
import com.event.service.model.Event;
import com.event.service.service.EventService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/events")
public class EventController {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private EventService eventService;

    @PostMapping(value = "createEvent")
    public ResponseEntity<?> createEvent(Locale locale, @Valid @RequestBody CreateEventRequest requestBody) {
        try {
            Event event = eventService.createEvent(requestBody);
            return  ResponseEntity.ok(event);
        } catch (ApplicationException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getCode(), messageSource.getMessage(e.getMessage(), null, locale));
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/getListUpcomingEvents")
    public ResponseEntity<?> getListUpcomingEvents(Locale locale){
        try {
            List<Event> events = eventService.getListUpcomingEvents();
            return ResponseEntity.ok(events);
        }catch (Exception e){
            ErrorResponse errorResponse = new ErrorResponse("1002", messageSource.getMessage("error.load.upcoming.event", null, locale));
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
