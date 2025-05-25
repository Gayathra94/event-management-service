package com.event.service.controller;

import com.event.service.dto.AttendanceDTO;
import com.event.service.dto.CreateEventRequest;
import com.event.service.dto.ErrorResponse;
import com.event.service.dto.EventDTO;
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
            List<EventDTO> events = eventService.getListUpcomingEvents();
            return ResponseEntity.ok(events);
        }catch (Exception e){
            ErrorResponse errorResponse = new ErrorResponse("1002", messageSource.getMessage("error.load.upcoming.event", null, locale));
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping(value = "updateEvent")
    public ResponseEntity<?> updateEvent(Locale locale, @Valid @RequestBody CreateEventRequest createEventRequest){
        try {
            Event event = eventService.updateEvent(createEventRequest);
            return  ResponseEntity.ok(event);
        }catch (ApplicationException e){
            ErrorResponse errorResponse = new ErrorResponse(e.getCode(), messageSource.getMessage(e.getMessage(), null, locale));
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "deleteEvent/{eventId}")
    public ResponseEntity<?> deleteEvent(Locale locale, @Valid @PathVariable String eventId){
        try {
            eventService.deleteEvent(eventId);
            return  ResponseEntity.ok(null);
        }catch (ApplicationException e){
            ErrorResponse errorResponse = new ErrorResponse(e.getCode(), messageSource.getMessage(e.getMessage(), null, locale));
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "attendEvent")
    public ResponseEntity<?> attendEvent(Locale locale, @Valid @RequestBody AttendanceDTO attendanceDTO){
        try {
            eventService.attendEvent(attendanceDTO);
            return  ResponseEntity.ok(null);
        }catch (ApplicationException e){
            ErrorResponse errorResponse = new ErrorResponse(e.getCode(), messageSource.getMessage(e.getMessage(), null, locale));
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/getListEventsUserHostingOrAttend/{userId}")
    public ResponseEntity<?> getListEventsUserHostingOrAttend(Locale locale, @PathVariable String userId){
        try {
            List<EventDTO> events = eventService.getListEventsUserHostingOrAttend(userId);
            return ResponseEntity.ok(events);
        }catch (Exception e){
            ErrorResponse errorResponse = new ErrorResponse("1002", messageSource.getMessage("error.load.event.by.host", null, locale));
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
