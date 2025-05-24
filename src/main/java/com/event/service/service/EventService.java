package com.event.service.service;

import com.event.service.dto.CreateEventRequest;
import com.event.service.enums.EventVisibilityType;
import com.event.service.model.Event;

import java.time.LocalDateTime;
import java.util.List;

public interface EventService {
    Event createEvent(CreateEventRequest createEventRequest);

    List<Event> getListUpcomingEvents();
}
